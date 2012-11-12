import java.awt.*;

public class CondMazeLab extends Maze {
    
    public void run (){
        PussInBoots puss = getPuss();
        Point finish = new Point(10,10);

        // This while loop evaluates until Puss reaches the finish
        // square of the maze, or until he does something illegal
        // (like trying to walk through a wall).
	
        while(!finish.equals(puss.getPosition())) {

        	
        	
        	
            if((puss.isFacingGully()|| puss.isFacingDog() || puss.isFacingMud() || puss.isFacingWall())!=true)
            {
            	puss.forward();
            }
            
            if (puss.isFacingWall())
            {
            	puss.right();
            	if (puss.isFacingWall())
            	{
            		puss.left();
            	}
            	if (puss.isFacingWall())
            	{
            		puss.left();
            		  
            	}
            	
            	if (puss.isFacingMud())
                {
                	if (puss.isTipToeing())
                	{
                		puss.stopTipToe();
                	}
                	puss.putOnBoots();
                	puss.forward();
                }
                
                if (puss.isFacingDog())
                {
                    if (puss.isInBoots());
                	{
                		puss.takeOffBoots();
                	}
                	puss.startTipToe();
                	  puss.forward();
                }
                

                if (puss.isFacingGully())
                {
                	if (puss.isInBoots())
                	{
                		puss.takeOffBoots();
                	}
                	if (puss.isTipToeing())
                	{
                		puss.stopTipToe();
                	}
                	puss.jump();
                }
                
                puss.forward();
            }
            
            
            
            
            
            if (puss.isFacingMud())
            {
            	if (puss.isTipToeing())
            	{
            		puss.stopTipToe();
            	}
            	puss.putOnBoots();
            	puss.forward();
            }
            

            if (puss.isFacingDog())
            {
                if (puss.isInBoots());
            	{
            		puss.takeOffBoots();
            	}
            	puss.startTipToe();
            	  puss.forward();
            }
            
            
            if (puss.isFacingGully())
            {
            	if (puss.isInBoots())
            	{
            		puss.takeOffBoots();
            	}
            	if (puss.isTipToeing())
            	{
            		puss.stopTipToe();
            	}
            	puss.jump();
            }
            

            
        }
    }
    
    public CondMazeLab(boolean whichMaze) {
        super(whichMaze);
    }
    
    public static void main (String [] args) {
        /* Pass the boolean value false into the constructor
         * if you want to use the maze in mymaze.txt
         *  **Optional Part**
         */
        CondMazeLab myMaze = new CondMazeLab(true);
        boolean done = false;

        while (! done) {
            String userChoice = myMaze.getUserChoice();
            if (userChoice.equals("Quit")) {
            done = true;
            }
            else if (userChoice.equals("Run")) {
            myMaze.run();
            myMaze.userChoiceReset();
            }
            else if (userChoice.equals("Reset")) {
            myMaze.reset();
            myMaze.userChoiceReset();
            }
            else if (userChoice.equals("Step")) {
            myMaze.exec.step();
            myMaze.userChoiceReset();
            }
        }
        System.out.println("All done, bye!");
        System.exit(0);
    }
}
