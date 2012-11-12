///////////////////////////////////////////////////////////////////////////////
// Title:            HW1 - Animals in Our Mind
// Files:            HW1.java
// Semester:         Fall 2011
//
// Author:           Christopher Wilcox (chris@crwilcox.com)
// CS Login:         wilcox
// Lecturer's Name:  Zhu
//
// Description:      you will program hierarchical clustering in Java to cluster
//                   animals. The data we use comes from a Dutch psychology 
//                   study by De Deyne et al.
//                   Use Complete-Linkage and Euclidean Distance to cluster 
//                   the animals using their feature vectors.
///////////////////////////////////////////////////////////////////////////////
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This is a class to do hierarchical clustering on a dataset.  
 * Specifically, this is used to cluster animals based on feature vectors
 * @author Christopher Wilcox
 */
class HW1 {
	//class variables
	int nAnimals, nFeatures, nClusters;
	String datafile;
	int[][] array = null;
	ArrayList<cluster> clusters = new ArrayList<cluster>();

	public static void main(String[] args) {
		HW1 hw1;

		// make sure the minimum args were entered
		if (args.length < 4) {
			System.err.println("Usage: HW1 nAnimals nFeatures datafile nClusters");
			System.err.println("   nAnimals: the number of columns (such as 25)");
			System.err.println("   nFeatures: the number of rows (such as 764)");
			System.err.println("   datafile: the name of the data file, such as matrix.txt");
			System.err.println("   nClusters: the number of desired clusters");
			System.exit(1);
		}

		// create the hw1 object
		try {
			int nAnimals = Integer.parseInt(args[0]);
			int nFeatures = Integer.parseInt(args[1]);
			String datafile = args[2];
			int nClusters = Integer.parseInt(args[3]);
			hw1 = new HW1(nAnimals, nFeatures, datafile, nClusters);

			hw1.FindClusters();
			hw1.PrintClusters();

			// hw1.PrintDataFile();
		} catch (Exception e) {
			System.err.println("General Error Processing");
			System.exit(1);
		}
	}

	/**
	 * Creates the HW1 Object using the command line args
	 * @param nAnimals
	 * @param nFeatures
	 * @param datafile
	 * @param nClusters
	 */
	public HW1(int nAnimals, int nFeatures, String datafile, int nClusters) {
		this.nAnimals = nAnimals;
		this.nFeatures = nFeatures;
		this.datafile = datafile;
		this.nClusters = nClusters;

		array = new int[nFeatures][nAnimals];

		// Now we need to fill the array. Mount the datafile and parse it out
		try {
			BufferedReader in = new BufferedReader(new FileReader(datafile));
			for (int i = 0; i < nFeatures; i++) {
				// Read in one row from the file
				String line = in.readLine();
				if (line == null) {
					System.err
							.println("Received a null line from the reader.  check array bounds and input file.");
					System.exit(1);
				}
				// split the line on spaces and convert each position to an int
				String[] animals = line.split(" ");
				int[] convertedAnimals = new int[animals.length];
				for (int j = 0; j < nAnimals; j++) {
					convertedAnimals[j] = Integer.parseInt(animals[j]);
				}
				// place this into our array
				array[i] = convertedAnimals;
			}
		} catch (Exception e) {
			System.err.println("Error in Datafile input");
			System.exit(1);
		}

		try {
			//Create Clusters
			for (int i = 0; i < nAnimals; i++) {
				int[] featArray = new int[nFeatures];
				for (int j = 0; j < nFeatures; j++) {
					featArray[j] = array[j][i];
				}

				cluster locCluster = new cluster();

				locCluster.animals.add(new animal(i + 1, featArray));
				clusters.add(locCluster);
			}
		} catch (Exception e) {
			System.err.println("Error Processing Data Array to Clusters");
			System.exit(1);
		}
	}

	/**
	 * this method takes the clusters list and clusters it to nClusters clusters.
	 */
	public void FindClusters() {
		//While we do not have the desired number of clusters
		while(clusters.size() != nClusters){
			int bestmatch1 = -1;
			int bestmatch2 = -1;
			double bestDistance = Double.MAX_VALUE;
			
			// so, we can use Euclidean Distance on each of the features
			for(int i = 0; i < clusters.size(); i++){
				for(int j = 0; j< clusters.size(); j++){
					if(i==j){
						//do nothing as we are looking at the same cluster
					}
					else{
						//we have two clusters to look at.  Find the max distance between the two clusters
						double maxDistanceBetweenAnimalsInClusters = 0;
						for(int k = 0; k < clusters.get(i).animals.size(); k++){
							for(int l = 0; l < clusters.get(j).animals.size(); l++){
								double distanceBetweenTwoPoints = GetEuclidean(clusters.get(i).animals.get(k).features, clusters.get(j).animals.get(l).features);
								if(distanceBetweenTwoPoints > maxDistanceBetweenAnimalsInClusters){
									maxDistanceBetweenAnimalsInClusters = distanceBetweenTwoPoints;
								}
							}
						}
						
						//if maxDistanceBetweenAnimalsInClusters is closer than the current best match, update best match
						if(maxDistanceBetweenAnimalsInClusters < bestDistance){
							bestmatch1 = i;
							bestmatch2 = j;
							bestDistance = maxDistanceBetweenAnimalsInClusters;
						}
					}
				}
			}
			
			//now that we went through everything, do we have a cluster to combine?
			// now try to combine objects
			clusters.get(bestmatch1).animals.addAll(clusters.get(bestmatch2).animals);
			clusters.remove(bestmatch2);	
		}
	}

	/**
	 * this method will print the clusters in numerical order
	 * this method uses the print clusters method
	 */
	public void PrintClusters() {
		OrderClusters();
		
		for (int i = 0; i < clusters.size(); i++) {
			cluster cl = clusters.get(i);
			for (int j = 0; j < cl.animals.size(); j++) {
				System.out.print(cl.animals.get(j).number + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Debugging Method.  Used to print the DataFile as it is read in
	 */
	public void PrintDataFile() {
		// Now for the real work
		// Sort of. Lets print the array
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * This method Orders the Clusters Numerically in both directions. This is used by PrintClusters
	 */
	private void OrderClusters(){
		//ORDER INDIVIDUAL CLUSTERS
		for(int i = 0; i < clusters.size(); i++){
			Collections.sort(clusters.get(i).animals, new Comparator<animal>(){
	            public int compare(animal an1, animal an2) {
	               return an1.number < an2.number ? -1 : 1;
	            }
	        });
	 
		}
		//ORDER CLUSTERS OVERALL
		Collections.sort(clusters, new Comparator<cluster>(){
            public int compare(cluster c1, cluster c2) {
	               return c1.animals.get(0).number < c2.animals.get(0).number ? -1 : 1;
	            }
	        });
	}
	
	/**
	 * Gets the Euclidean Distance between two feature vectors
	 * @param features1
	 * @param features2
	 * @return
	 */
	private static double GetEuclidean(int[] features1, int[] features2) {
		double result = 0.0;
		for (int i = 0; i < features1.length; i++) {
			double temp = features1[i] - features2[i];
			temp = Math.pow(temp, 2); // square temp
			result += temp; // add this component of the distance
		}
		// once we are done, square root them all
		return Math.sqrt(result);
	}
}

/**
 * Class used to represent an animal by number with
 * a feature vector
 */
class animal {
	public int number;
	public int[] features;

	public animal(int Number, int[] Features) {
		number = Number;
		features = Features;
	}
}

/**
 * used to represent the cluster of animals
 */
class cluster {
	public ArrayList<animal> animals;

	public cluster() {
		animals = new ArrayList<animal>();
	}
}
