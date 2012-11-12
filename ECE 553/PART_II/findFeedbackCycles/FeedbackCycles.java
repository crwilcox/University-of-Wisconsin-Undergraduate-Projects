import java.util.ArrayList;
import java.util.Stack;
import java.io.*;


public class FeedbackCycles {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String netlistPath = "seq1";
		
		Cycles cycles = new Cycles(netlistPath);
		//Call Cycles.  It will build the tree
		ArrayList<Integer[]> allCycles = cycles.getCycles();
		
		//Print Cycles
		for(Integer[] arr : allCycles){
			//print out backwards since it returns the stack (which pops it all
			//off, so in reverse
			for(int i = arr.length - 1 ; i >= 0; i--){
				int el = arr[i];
				if(i == 0){
					System.out.print(el);
				}
				else{
					System.out.print(el + ", ");
				}
			}
			System.out.println();
		}
		
	}
}
