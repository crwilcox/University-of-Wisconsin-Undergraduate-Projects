public class Pictures{
	public static void printDesign1( int n){
		//print n rows of increasing length
		for (int row=0; row<((n+1)/2); row++){
			for (int col=0; col<row+1; col++){
				if(col==0 || col==row){
					System.out.print('*');}
				else
					System.out.print(' ');
			}
			
			System.out.println();
		}
		
		for (int row=((n+1)/2); row<n; row++){
			for (int col=0; col<(n-row); col++){
				
				if(col==0 || col==(n-1-row)){
					System.out.print('*');}
				else
					System.out.print(' ');
			}
			System.out.println();
		}
		
		
		
		
		
		
		
		System.out.println();
	}
	public static void main(String[] args){
		int n = 15;
		printDesign1(n);
	}
}