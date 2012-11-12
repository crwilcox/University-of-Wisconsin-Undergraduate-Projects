import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.io.*;


public class FindPartialScan {

	private static final int TOTALLATCHES = 29;
	private static final int NUMBERTOCHANGE = 6;
	private static final String PREVIOUSLYTRIEDFILE = "previouslyTriedFile.txt";
	private static final String ORIGINALFILE = "seq_1";
	private static final String FAULTSFILE = "seq_1-faults";
	private static final String PASSDIRECTORY = "bestFiles";
	private static final String GENFILESDIRECTORY = "generatedFiles";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Store all previously processed sequences in a list.  We can store them as a binary data/true false data, don't care
		// our circuit has 29 latches. We need to find the best 6 of them
		
		// We should attempt to save this file often as to avoid having to redo work on crash
		// save every RUNS TO SAVE runs?
		
		// run the fastest command, grep the output file for the number of faults found.
		// breaking ties?  Lets not break ties.  If we tie, just put multiple files in the directory :)
		
		// what about recording highest score?  We will have to initialize. Just run the file in the best output ?		
		
		//using this to get a randomish seed
		long time = (new Date()).getTime();
		Random rand = new Random(time);
				
		// LOAD UP PREVIOUSLY TRIED FROM PREVIOUSLYTRIEDFILE
		ArrayList<String> previouslyTried = ReadPreviouslyPatternsFile(PREVIOUSLYTRIEDFILE);
		
		// GET A BASEPOINT.  RUN OUR OLD FILE, SHOULD BE IN PASSDIRECTORY
		int currentBest = GetCurrentBest(PASSDIRECTORY);

 		try {
			Process p = Runtime.getRuntime().exec("mkdir " + GENFILESDIRECTORY);
			p.waitFor();
		} catch (Exception e1) {
			System.err.println("Error while creating generated files directory");
			e1.printStackTrace();
		}


		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		while(true){
			// TRY TO FIND A SEQUENCE WE HAVENT USED YET
			String formattedDate = formatter.format(new Date());
			System.out.println(formattedDate + " - Attempting to find a new sequence...");
			boolean[] newSeq = FindNewSequence(previouslyTried, rand);
			
			// BUILD FILE WITH THAT ARRAY
			String extension = "";
			for(boolean i: newSeq){
				extension += i ? "1" : "0";
			}
			String filename = GENFILESDIRECTORY + "/" + ORIGINALFILE + "-" + extension;
			WriteFileWithThisSequence(newSeq, filename);
			
			// RUN AGAINST OUR NEW CREATED FILE
			int currentResult = RunFile(filename);
			
			// COMPARE RESULTS OF OUR RUN WITH THE BEST ONE
			if (currentResult > currentBest){
				System.out.println("\tFound a new best! Old Best:" + currentBest + " New Best:" + currentResult);
				//empty out the pass directory and put our file in it.  Update currentBest
				try {
					Process p = Runtime.getRuntime().exec("rm -rf " + PASSDIRECTORY);
					p.waitFor();
					p = Runtime.getRuntime().exec("mkdir " + PASSDIRECTORY);
					p.waitFor();
				} catch (Exception e1) {
					System.err.println("Error while clearing out pass directory");
					e1.printStackTrace();
				} 
				//COPY TO DIRECTORY
				try {
					Process p = Runtime.getRuntime().exec("cp " + filename + " " + PASSDIRECTORY);
					p.waitFor();
				} catch (Exception e1) {
					System.err.println("Error while copying file to " + PASSDIRECTORY);
					e1.printStackTrace();
				}
			   	//update the current best	
				currentBest = currentResult;	
			}
			else if (currentResult == currentBest){
				System.out.println("\tTied the current best! Best:" + currentBest);
				//they are the same, add our file to the pass directory
				try {
					Process p = Runtime.getRuntime().exec("cp " + filename + " " + PASSDIRECTORY);
					p.waitFor();
				} catch (Exception e1) {
					System.err.println("Error while copying file to " + PASSDIRECTORY);
					e1.printStackTrace();
				} 
			}
			else{
				System.out.println("\tResult was not better: Result:" + currentResult + " Best:" + currentBest);
				//ignore our result.  We did not beat anything
			}
			
			// MARK OUR FILE AS BEING RAN (WAIT UNTIL AFTER COMPARISON AND COPY FOR CONSISTENCY)
			// THIS IS MOSTLY SO IF SOMEONE KILLS THE PROGRAM MID PART, WE DON'T SKIP ANYTHING
			previouslyTried.add(sequenceToString(newSeq));
			//WRITE THE FILE WITH THESE SEQUENCES
			WriteLineToPreviousPatternsFile(PREVIOUSLYTRIEDFILE, newSeq);
			//REMOVE THE FILE WE JUST RAN FROM GENERATED FILES
			try {
				Process p = Runtime.getRuntime().exec("rm " + filename);
				p.waitFor();
			} catch (Exception e1) {
				System.err.println("Error while removing generated file:" + filename);
				e1.printStackTrace();
			} 

		}
			
	}

	/**
	 * Runs file at specified path and returns an integer representing the number of faults detected
	 * @param filename the filename that holds the test file
	 * @return number of faults detected
	 */
	private static int RunFile(String filename) {
		
		//RUN THE TEST
		String command = "fastest -c " + filename + " -f " + FAULTSFILE + " -o seq_1-output";
	      Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
		} catch (IOException e1) {
			System.err.println("Error while running fastest command");
			e1.printStackTrace();
		} catch (InterruptedException e) {
			System.err.println("Error while running fastest command");
			e.printStackTrace();
		}
	      
		
		//LOOK AT THE OUTPUT seq_1-output
		try{
			File outFile = new File("seq_1-output");
			FileReader rstream = new FileReader(outFile);
			BufferedReader outReader = new BufferedReader(rstream);
			String line;
			while(outReader.ready()){
				line = outReader.readLine();
				if(line.contains("Detected: ")){
					//now we can strip our data out
					//Faults: 1073; Detected: 864; Coverage: 0.805219

					String detectedFaults = line.split("Detected: ")[1].split(";")[0];
					return Integer.parseInt(detectedFaults);
				}
			}
		}
		catch(Exception e){
			System.err.println("Something went wrong checking the output file");
		}
		return 0;
	}

	/**
	 * Using the RunFile Method, This attempts to get the current best score
	 * @param passdirectory the directory containing the current best files
	 * @return the integer showing the current faults detected
	 */
	private static int GetCurrentBest(String passdirectory) {
		File dir = new File(passdirectory);
		File[] files = dir.listFiles();
		
		if(files == null || files.length <= 0) {// we have no best result.  just return 0
			System.out.println("Current Best does not exist");
			return 0; 
		}
		
		String path = files[0].getPath();
		//Run the file in the pass directory.  record its best score from the output file...
		//use run file and get the result.
		int best = RunFile(path);
		System.out.println("Current Best is " + best);
		return best;
	}


	/**
	 * This attempts to find a sequence that we haven't yet had
	 * @param previouslyTried A list of previously tried sequences
	 * @param rand our random number generator
	 * @return a boolean array representing the new sequence
	 */
	private static boolean[] FindNewSequence(ArrayList<String> previouslyTried, Random rand) {
		
		boolean[] seq;
		while(true){
			seq = MakeRandomLatchSequence(rand);
			String str = sequenceToString(seq);
			
			if(previouslyTried.contains(str)){
				System.out.println("\tFAILED, try again.");
			}
			else{
				System.out.println("\tSUCCESS, found a new pattern");
				System.out.print("\t");
				for(boolean i:seq){
					System.out.printf("%s", i ? "1" : "0");
				}
				System.out.println();
				break;
			}
		}
		
		return seq;
	}

	private static String sequenceToString(boolean[] seq) {
		String str = "";
		for(boolean i: seq){
			str += i ? "1" : "0";
		}		
		
		return str;
	}

	/**
	 * Reads in the previouslyTried File in order to avoid repeating patterns
	 * @param previouslytriedfile the file path to the previously tried file...
	 * @return The ArrayList of Previously tried values
	 */
	private static ArrayList<String> ReadPreviouslyPatternsFile(String previouslytriedfile) {
		ArrayList<String> ret = new ArrayList<String>();
		System.out.println("Loading All Previously Tried Combinations To Avoid Repetition");

		try {
			File originalFile = new File(previouslytriedfile);
			FileReader rstream= new FileReader(originalFile);
			BufferedReader reader = new BufferedReader(rstream);	
			
			while(reader.ready()){
				String line = reader.readLine();
				ret.add(line);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Failed to Read Previous Patterns File");
		} catch (IOException e) {
			System.err.println("Failed to Read Previous Patterns File");
		}
			
		return ret;
	}
	
	/**
	 * This appends a line to the end of the previous patterns file, so we know to not write this pattern again
	 * @param previouslytriedfile This is the file of previous patterns
	 * @param arrayToWrite This is the array holding the values to write to the last line...
	 */
	private static void WriteLineToPreviousPatternsFile(String previouslytriedfile, boolean[] arrayToWrite) {
		
		//System.out.println("Writing Combination To Disk");
		try{
			String arrayVal = "";
			for(boolean i: arrayToWrite){
				arrayVal += i ? "1" : "0";
			}
			
			FileWriter fstream = new FileWriter(previouslytriedfile, true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(arrayVal);
			out.newLine();
			out.flush();
		}
		catch(IOException e){
			System.err.println("Failed to Write to Disk");
		}
	}

	/**
	 * This makes a random sequence of latch values.  It will set 6 to true, and the rest to false...
	 * @return returns the randome sequence as a boolean array
	 */
	private static boolean[] MakeRandomLatchSequence(Random random) {
		boolean[] ret = new boolean[TOTALLATCHES];
		//initialize to all false (boolean arrays should do this automatically, but being safe)
		for(int i = 0; i < ret.length; i++){ ret[i]= false; }
		
		//now choose 6 random to use
		int changed = 0;
		while(changed < NUMBERTOCHANGE){
			//GET THE INDEX TO CHANGE
			int indexToChange = random.nextInt();
			if(indexToChange < 0) indexToChange *= -1;
			indexToChange %= TOTALLATCHES;
			//THINK ABOUT CHANGING IT
			if(ret[indexToChange] == false){
				//this hasn't been changed yet.  change it,, and increase changed count
				ret[indexToChange] = true;
				changed++;
			}
		}
		
		return ret;
	}

	/**
	 * This method writes a file with modified SCANL
	 * @param newSeq The sequence of SCANL (as true values)
	 * @param filename The name of the file to write to
	 */
	private static void WriteFileWithThisSequence(boolean[] newSeq,	String filename) {
		//only modification to file is LATCH to SCANL for specified lines
		//Open the original for reading ORIGINALFILE
		try {
			//OPEN UP FILE TO READ FROM
			File originalFile = new File(ORIGINALFILE);
			FileReader rstream = new FileReader(originalFile);
			BufferedReader reader = new BufferedReader(rstream);
			
			//OPEN UP FILE TO WRITE TO
			File file = new File(filename);
			FileWriter fstream = new FileWriter(file, false);
			BufferedWriter writer  = new BufferedWriter(fstream);
			
			int latchCount = 0;
			while(reader.ready()){
				// read a line from the reader
				// if it is a latch line, find out if this is one we need
				// to modify or not
				// write line to writer
				
				String in = reader.readLine();
				if(in.contains("LATCH")){
					if(newSeq[latchCount]){
						//we need to modify this one
						in = in.replace("LATCH", "SCANL");
					}
					latchCount++;
				}
				//now we need to write to the writer
				writer.write(in);
				writer.newLine();
				writer.flush();
			}
			//at the end, close the streams
			reader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		
	}

	
	
}
