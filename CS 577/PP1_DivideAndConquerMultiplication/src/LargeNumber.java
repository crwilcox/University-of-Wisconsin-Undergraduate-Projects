public class LargeNumber {
	String number;
	
	LargeNumber(String num){
		number = num;
	}
	
	/* 
	function multiply(x; y)
		Input: Positive integers x and y, in binary
		Output: Their product
		n = max(size of x, size of y)
		if n = 1: return xy
		xL, xR = leftmost ceil n/2, rightmost n/2 bits of x
		yL, yR = leftmost ceil n/2, rightmost n/2 bits of y
		P1 = multiply(xL, yL)
		P2 = multiply(xR, yR)
		P3 = multiply(xL + xR, yL + yR)
		return P1 * 10^n + (P3 - P1 - P2) * 10^(n/2) + P2
 */
	public LargeNumber multiply(LargeNumber y) {
		LargeNumber x = this;
	
		String strX = x.toString();
		String strY = y.toString();
		
		//FIND N
		int n = Math.max(strX.length(), strY.length());// n should be set to whichever is longer
		int mid = n/2 + n%2; //get the midpoint to separate on

		//PAD THE SMALLER OF THE TWO STRINGS/NUMBERS WITH 0s TO MAKE IT THE SAME LENGTH
		while(strX.length() < n) strX = "0" + strX; 
		while(strY.length() < n) strY = "0" + strY;
		
		
		if(n <= 2) {
			return x.smallMultiply(y);
		} 
		else {
			String strXL, strXR, strYR, strYL;
			
			strXL = strX.substring(0, mid);
			strXR = strX.substring(mid, strX.length());
						
			strYL = strY.substring(0, mid);
			strYR = strY.substring(mid, strY.length());
			
			strXL = strXL.length() == 0 ? "0" : strXL;
			strXR = strXR.length() == 0 ? "0" : strXR;
			strYL = strYL.length() == 0 ? "0" : strYL;
			strYR = strYR.length() == 0 ? "0" : strYR;
			
			LargeNumber xR = new LargeNumber(strXR);//bottom n/2 digits of a
			LargeNumber xL = new LargeNumber(strXL);//top remaining digits of a
			LargeNumber yR = new LargeNumber(strYR);//bottom n/2 digits of b
			LargeNumber yL = new LargeNumber(strYL);//top remaining digits of b
			
			LargeNumber p1 = xL.multiply(yL);
			LargeNumber p2 = xR.multiply(yR);			
			
			LargeNumber p3 = (xL.add(xR)).multiply(yL.add(yR));
			
			LargeNumber retA = p1.power10(n - (n%2));// n - n%2 almost works...
			LargeNumber retB =((p3.subtract(p1)).subtract(p2)).power10( (n/2) );
			LargeNumber retC = p2;		
						
			return retA.add(retB).add(retC);
		}
	}
	
	//smallMultiply:  used for multiplications n<=2
	private LargeNumber smallMultiply(LargeNumber multiplicand){
		String ret = Integer.toString((Integer.parseInt(number) * Integer.parseInt(multiplicand.number)));
		return new LargeNumber(ret);
	}

	//add
	public LargeNumber add(LargeNumber n){
		LargeNumber ret = new LargeNumber("");
		int carry = 0;
		int i = number.length() - 1;
		int j = n.number.length() - 1;
		
		for(; i>=0 && j>=0; i--, j--){
			int temp = getNumber(number.charAt(i)) + getNumber(n.number.charAt(j)) + carry;
			if(temp > 9){//carry the second digit
				carry = getNumber(Integer.toString(temp).charAt(0));
				ret.number = Integer.toString(temp).charAt(1) + ret.number;
			}
			else{
				carry = 0; //no carry
				ret.number = Integer.toString(temp).charAt(0) + ret.number;
			}
		}
		
		if(i != -1){
			for(; i>=0; i--){
				int temp = getNumber(number.charAt(i)) + carry;
				if(temp > 9){//carry the second digit
					carry = getNumber(Integer.toString(temp).charAt(0));
					ret.number = Integer.toString(temp).charAt(1) + ret.number;
				}
				else{
					carry = 0; //no carry
					ret.number = Integer.toString(temp).charAt(0) + ret.number;
				}
			}
		}
		else if(j != -1){
			for(; j>=0; j--){
				int temp = getNumber(n.number.charAt(j)) + carry;
				if(temp > 9){//carry the second digit
					carry = getNumber(Integer.toString(temp).charAt(0));
					ret.number = Integer.toString(temp).charAt(1) + ret.number;
				}
				else{
					carry = 0; //no carry
					ret.number = Integer.toString(temp).charAt(0) + ret.number;
				}
			}
		}
		if(carry != 0){
			ret.number = Integer.toString(carry) + ret.number;
		}
		return ret;
		
	}

	//subtract
	public LargeNumber subtract(LargeNumber n){
		String top = number;
		String bottom = n.number;
		LargeNumber ret = new LargeNumber("");

		int i=top.length()-1;
		int j=bottom.length()-1;
		for(; i>=0 && j>=0; i--, j--){
			int temp = getNumber(top.charAt(i)) - getNumber(bottom.charAt(j));
			if(temp < 0 && i > 0){
				//do a borrow
				top = top.substring(0, i-1) + (getNumber(top.charAt(i-1)) - 1) + top.substring(i, top.length()); 
				temp += 10;
			}
			ret.number = temp + ret.number;
		}
		ret.number = top.substring(0, i+1) + ret.number;
		if(ret.number.length() > 1 && ret.number.charAt(0) == '0'){
			ret.number = ret.number.substring(1);
		}
		return ret;
	}
	
	//power10
	public LargeNumber power10(int n){
		LargeNumber returnVal = new LargeNumber(number);
		for(int i = 0; i < n; i++){
			returnVal.number = returnVal.number + "0";
		}
		return returnVal;
	}

	public String toString(){
		while(number.length() > 1 && number.charAt(0) == '0'){
			number = number.substring(1);
		}
		return number;
	}

	private int getNumber(char n){
		switch(n){
		case '0': return 0;
		case '1': return 1;
		case '2': return 2;
		case '3': return 3;
		case '4': return 4;
		case '5': return 5;
		case '6': return 6;
		case '7': return 7;
		case '8': return 8;
		case '9': return 9;
		default: return -1;
		}
	}
}
	

