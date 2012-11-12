/**
  A class to test the Line class.
*/
public class LineTester 
{
   public static void main(String[] args)
   {
      Line line1 = new Line(5, -1);  // creates line y = 5x-1 (make sure you understand why)
      DoublePoint point1 = new DoublePoint(0,2); // creates point x=0 y=2
      DoublePoint point2 = new DoublePoint(1,4); // creates point x=1 y=4
      
 
      System.out.println("***Testing getSlope***");
      System.out.print(line1.getSlope());
      System.out.println("  expected answer: 5");
      
      System.out.println("***Testing getYIntercept***");
      System.out.print(line1.getYIntercept());
      System.out.println("  expected answer: -1");
      
      System.out.println("***Testing calculateY***");
      System.out.print(line1.calculateY(2));
      System.out.println("  expected answer: 9");
      
      System.out.println("***Testing point1.getX***");
      System.out.print(point1.getX());
      System.out.println("  expected answer: 0");

      System.out.println("***Testing point1.getY***");
      System.out.print(point1.getY());
      System.out.println("  expected answer: 2");
      
      System.out.println("***Testing point2.getX***");
      System.out.print(point2.getX());
      System.out.println("  expected answer: 1");

      System.out.println("***Testing point2.getY***");
      System.out.print(point2.getY());
      System.out.println("  expected answer: 4");

      System.out.println("***Testing mySlope***");
      System.out.print(((point1.getY() - point2.getY()) / (point1.getX() - point2.getX())));
      System.out.println("  expected answer: 2");

      System.out.println("***Testing myYIntercept***");
      System.out.print((point1.getY() - (((point1.getY() - point2.getY()) / (point1.getX() - point2.getX()))*point1.getX())));
      System.out.println("  expected answer: 2");
   }
  
}