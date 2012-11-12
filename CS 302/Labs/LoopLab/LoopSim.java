public class LoopSim{
	public static void count3s(int numberOfRolls, int totTrials){
		Die die=new Die();
		int rolls=numberOfRolls ;
		int totalRolls=1;
		int threeRoll=0;
		int trial=1;
		int totThreeRolls=0;
		int totNumRolls=0;
		while (trial<=totTrials){
			
		while(rolls>=totalRolls){
				die.roll();
				if (die.getSideUp()==3)
					threeRoll++;
				totalRolls++;
		}
		totThreeRolls= totThreeRolls + threeRoll;
		totNumRolls= totNumRolls + numberOfRolls;
		trial++;
	}
		double avgThree=totThreeRolls;
		double avgTrial=totTrials;
		double avg = (avgThree/avgTrial);
		System.out.println("Threes Rolled on Avg: "+avg);
		
	}

	public static void main(String [] args){
		count3s(1000, 100);
	
	}
}