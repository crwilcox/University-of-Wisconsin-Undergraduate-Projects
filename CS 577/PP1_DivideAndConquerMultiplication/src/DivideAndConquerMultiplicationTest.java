class DivideAndConquerMultiplicationTest{
	public static void main(String[] args){
		testMultiply("20", "10", "200");
		testMultiply("200", "101", "20200");
		testMultiply("50000", "33000", "1650000000");
		testMultiply("501", "331", "165831");
		testMultiply("5010", "3310", "16583100");
		testMultiply("12345", "10067", "124277115");
		testMultiply("50100", "33100", "1658310000");
		testMultiply("55555555", "33333333", "1851851814814815");
		testMultiply(	"654512316587513216575613165484621649843249676516789463541697621657987",	
						"654512316587513216575613165484621649843249676516789463541697621657987",
						"428386372564753128468200103773885297228243809851002824639273001153620718846867932823912724182825561454977856427876856091881626530800892169"
			);
		testMultiply(	"42838637256475312846820010377388529722824380985100282463927899901234565789",	
						"42838637256475312846820010377388531232824380985100282463927899901234565789",
						"1835148841991874718792304157660557986077962588144279655515467492835363805480519812208831085726400497127469861373253760552824033559837194887369192521"
			);
		
		System.out.println("TESTING USING NUMBERS FROM http://primes.utm.edu/lists/small/small.html");
		 testMultiply(	"2425967623052370772757633156976982469681", //THESE NUMBERS WERE PICKED FROM http://primes.utm.edu/lists/small/small.html
				 		"6075380529345458860144577398704761614649",
				 		"14738676461914856949907729933759816247346882957635325901068810315098149247956969"//SOLUTION FROM WolframAlpha
				 	);
	}

	public static void testMultiply(String A, String B, String expected){
		LargeNumber inA;
		LargeNumber inB;
		LargeNumber result;

		inA = new LargeNumber(A);
		inB = new LargeNumber(B);
		result = inA.multiply(inB);	
		System.out.println("Multiply(" + inA + ", " + inB + "): \n"  + result.toString());
		if(result.toString().equals(expected)) System.out.println("PASSED");
		else System.out.println("FAILED... EXPECTED: \n" + expected);
		System.out.println("************************************************************");
	}
}
