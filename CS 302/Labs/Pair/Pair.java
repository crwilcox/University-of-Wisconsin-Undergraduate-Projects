public class Pair
{
	private double num1;
	private double num2;
	
	public Pair(double number1, double number2)
	{
		num1=number1;
		num2=number2;
	}
	
	public double getSum()
	{
		double total=(num1+num2);
		return total;
	}
	
	
	
	public double getAvg()
	{
		double avg=((num1+num2)/2);
		return avg;
	}
	
	public double getMax()
	{
		double maximum= Math.max(num1, num2);
		return maximum;
	}
}