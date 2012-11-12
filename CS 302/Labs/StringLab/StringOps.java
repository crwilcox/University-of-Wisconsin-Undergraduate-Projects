/**
*StringOps Class
*/

public class StringOps
{
	private String word1;//creates a string variable word1
	private String word2;//creates a string variable word2

	public StringOps(String string1, String string2)
	{
		word1 = string1;
		word2 = string2;
	}
	
	public char giveFirstChar()
	{
	char firstChar=word1.charAt(0);
	return firstChar;
	}
	
	
	public char giveLastChar()
	{
		int wordLength;
		wordLength=word1.length();
		char lastChar=word1.charAt(wordLength-1);
		return lastChar;
	}
	
	
	public char giveMidChar()
	{
		int wordLength;
		wordLength=word1.length();
		char midChar=word1.charAt(((wordLength/2)+(wordLength%2))-1);
		return midChar;
	}

	
	
	
	public String giveConcat()
	{
		String concat=word1.concat(word2);
		return concat;
		
	}
	
}



