/**
 * An application class to test the the Timer class.  
 *
 * Please note: this class uses programming constructs that have not yet been 
 * covered in class and as such you are not expected to understand all of the 
 * details of how this class works (yet).  It is sufficient to know that you 
 * can run this class as a Java application and it will test the functionality
 * of your Timer class.
 */
public class TimerTester {
	
	public static void main (String[] args) {
		
		System.out.println("*** This is a tester for the Timer class.***\n");
		int failedTests = 0;
		
		//******* Testing constructor and getValue() method *********
		
		Timer timer1 = new Timer(13); // one-arg constructor 
		Timer timer2 = new Timer(1);  // edge case, base is 1
		Timer timer3 = new Timer(4, 3); // two-arg constructor
		Timer timer4 = new Timer(7, 11); // two-arg constructor, value > base
		
		// expected data member values values
		int expVal1=0, expVal2=0, expVal3=3, expVal4=4;
		
		if (timer1.getValue() != expVal1) {
			System.out.println("*** Test Failed: Error in one-argument " +
					"constructor with parameter 13, or getValue() method. ****");
			System.out.println("*** Expected data member value: " + expVal1);
			System.out.println("*** Actual value: " + timer1.getValue() + "\n");
			failedTests++;
		}
		
		if (timer2.getValue() != expVal2) {
			System.out.println("*** Test Failed: Error in one-argument " +
					"constructor with parameter 2, or getValue() method. ****");
			System.out.println("*** Expected data member value: " + expVal2);
			System.out.println("*** Actual value: " + timer2.getValue() + "\n");
			failedTests++;
		}
		
		if (timer3.getValue() != expVal3) {
			System.out.println("*** Test Failed: Error in two-argument " +
					"constructor with parameters 4 and 2, or " +
					"getValue() method. ****");
			System.out.println("*** Expected data member value: " + expVal3);
			System.out.println("*** Actual value: " + timer3.getValue() + "\n");
			failedTests++;
		}
		
		if (timer4.getValue() != expVal4) {
			System.out.println("*** Test Failed: Error in two-argument " +
					"constructor with parameters 7 and 11, or " +
					"getValue() method. ****");
			System.out.println("*** Expected data member value: " + expVal4);
			System.out.println("*** Actual value: " + timer4.getValue() + "\n");
			failedTests++;
		}
		
		// ******* Testing increment() method *************
		
		/* timer1 : base == 13
		 * timer2 : base == 1
		 * timer3 : base == 4
		 * timer4 : base == 7
		 */
		// increment each timer object
		int retValue1 = timer1.increment();
		int retValue2 = timer2.increment();
		int retValue3 = timer3.increment();
		int retValue4 = timer4.increment();
		
		// expected data member values
		expVal1=1;
		expVal2=0;
		expVal3=0;
		expVal4=5;
		
		// expected return values on increment
		int expRetVal1=0;
		int expRetVal2=1;
		int expRetVal3=1;
		int expRetVal4=0;
		
		if (timer1.getValue() != expVal1) {
			System.out.println("*** Test Failed: Error in " +
					"increment() method. ****");
			System.out.println("*** Expected data member value: " + expVal1);
			System.out.println("*** Actual data member value " + 
					timer1.getValue() + "\n");
			failedTests++;
		}
		
		if (retValue1 != expRetVal1) {
			System.out.println("*** Test Failed: Error in " +
					"increment() method. ****");
			System.out.println("*** Expected return value: " + expRetVal1);
			System.out.println("*** Actual return value " + retValue1 + "\n");
			failedTests++;
		}
		
		if (timer2.getValue() != expVal2) {
			System.out.println("*** Test Failed: Error in " +
					"increment() method. ****");
			System.out.println("*** Expected data member value: " + expVal2);
			System.out.println("*** Actual data member value " + 
					timer2.getValue() + "\n");
			failedTests++;
		}
		
		if (retValue2 != expRetVal2) {
			System.out.println("*** Test Failed: Error in " +
					"increment() method. ****");
			System.out.println("*** Expected return value: " + expRetVal2);
			System.out.println("*** Actual return value " + retValue2 + "\n");
			failedTests++;
		}
		
		if (timer3.getValue() != expVal3) {
			System.out.println("*** Test Failed: Error in " +
					"increment() method. ****");
			System.out.println("*** Expected data member value: " + expVal3);
			System.out.println("*** Actual data member value " + 
					timer3.getValue() + "\n");
			failedTests++;	
		}
		
		if (retValue3 != expRetVal3) {
			System.out.println("*** Test Failed: Error in " +
					"increment() method. ****");
			System.out.println("*** Expected return value: " + expRetVal3);
			System.out.println("*** Actual return value " + retValue3 + "\n");
			failedTests++;
		}
		
		if (timer4.getValue() != expVal4) {
			System.out.println("*** Test Failed: Error in " +
					"increment() method. ****");
			System.out.println("*** Expected data member value: " + expVal4);
			System.out.println("*** Actual data member value " + 
					timer4.getValue() + "\n");
			failedTests++;	
		}
		
		if (retValue4 != expRetVal4) {
			System.out.println("*** Test Failed: Error in " +
					"increment() method. ****");
			System.out.println("*** Expected return value: " + expRetVal4);
			System.out.println("*** Actual return value " + retValue4 + "\n");
			failedTests++;
		}
		
		// ************ Testing increment(int) method ****************
		
		/* timer1 : base == 13
		 * timer2 : base == 1
		 * timer3 : base == 4
		 * timer4 : base == 7
		 */
		
		// increment each timer object by a varying amount
		retValue1 = timer1.increment(0);
		retValue2 = timer2.increment(28);
		retValue3 = timer3.increment(54);
		retValue4 = timer4.increment(8493);
		
		// expected data member values after each increment
		expVal1=1;
		expVal2=0;
		expVal3=2;
		expVal4=0;
		
		// expected return values after each increment
		expRetVal1=0;
		expRetVal2=28;
		expRetVal3=13;
		expRetVal4=1214;
		
		if (timer1.getValue() != expVal1) {
			System.out.println("*** Test Failed: Error in " +
					"increment(int) method. ****");
			System.out.println("*** Expected data member value: " + expVal1);
			System.out.println("*** Actual data member value " + 
					timer1.getValue() + "\n");
			failedTests++;
		}
		
		if (retValue1 != expRetVal1) {
			System.out.println("*** Test Failed: Error in " +
					"increment(int) method. ****");
			System.out.println("*** Expected return value: " + expRetVal1);
			System.out.println("*** Actual return value " + retValue1 + "\n");
			failedTests++;
		}
		
		if (timer2.getValue() != expVal2) {
			System.out.println("*** Test Failed: Error in " +
					"increment(int) method. ****");
			System.out.println("*** Expected data member value: " + expVal2);
			System.out.println("*** Actual data member value " + 
					timer2.getValue() + "\n");
			failedTests++;
		}
		
		if (retValue2 != expRetVal2) {
			System.out.println("*** Test Failed: Error in " +
					"increment(int) method. ****");
			System.out.println("*** Expected return value: " + expRetVal2);
			System.out.println("*** Actual return value " + retValue2 + "\n");
			failedTests++;
		}
		
		if (timer3.getValue() != expVal3) {
			System.out.println("*** Test Failed: Error in " +
					"increment(int) method. ****");
			System.out.println("*** Expected data member value: " + expVal3);
			System.out.println("*** Actual data member value " + 
					timer3.getValue() + "\n");
			failedTests++;	
		}
		
		if (retValue3 != expRetVal3) {
			System.out.println("*** Test Failed: Error in " +
					"increment(int) method. ****");
			System.out.println("*** Expected return value: " + expRetVal3);
			System.out.println("*** Actual return value " + retValue3 + "\n");
			failedTests++;
		}
		
		if (timer4.getValue() != expVal4) {
			System.out.println("*** Test Failed: Error in " +
					"increment(int) method. ****");
			System.out.println("*** Expected data member value: " + expVal4);
			System.out.println("*** Actual data member value " + 
					timer4.getValue() + "\n");
			failedTests++;	
		}
		
		if (retValue4 != expRetVal4) {
			System.out.println("*** Test Failed: Error in " +
					"increment(int) method. ****");
			System.out.println("*** Expected return value: " + expRetVal4);
			System.out.println("*** Actual return value " + retValue4 + "\n");
			failedTests++;
		}
		
		if (failedTests == 0) {
			System.out.println("*** Congratulations, all tests passed. ***");
		}
		else {
			System.out.println("*** " + failedTests + " tests have failed. ***");	
		}
	}

}
