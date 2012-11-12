package cs540.checkers.wilcox;
import static cs540.checkers.CheckersConsts.*;

import java.util.*;

import cs540.checkers.Evaluator;

/**
 * This simplistic static board evaluator assigns points for material.  Each 
 * pawn remaining on the board contributes one point, and each remaining king 
 * remaining on the board contributes two points. 
 */

// We want to have the pawns get to the double corner
// this is in the upper left corner from the perspective of the player
//
// it is good for kings to be near the center of the board, and we should
// give more points for this

public class CustomEvaluator implements Evaluator
{
    public int eval(int[] bs)
    {
    	//First just get a piece count as we did originally..
        int Value = 10 * getPieceCountAsSimpleEval(bs);
        
        //now we need to start weighting things based on position on the board
        // We want each pawn to try and attack the double corner / upper left
        // we want each king to favor the middle
        
        for (int r = 0; r < H; r++)
        {
            for(int c = 0; c < W; c++)
            {
            	//find center piece area
				int centerR = H/2;
				int centerC = W/2;
            	
            	//index = row*Width + column.  this should give position
            	int i = (r*W) + c;
                int v = bs[i];
                switch(v)
                {
                    case RED_PAWN:
                    	Value += 1 * ((W-c) + (H-r));
                        break;
                    case BLK_PAWN:
                    	Value -= 1 * (c + r);
                        break;
                    case RED_KING:
                    	Value += (3 * Math.abs(centerR - r)) + (2 * Math.abs(centerC - c));
                        break;
                    case BLK_KING:
                    	Value -= (3 * Math.abs(centerR - r)) + (2 * Math.abs(centerC - c));
                        break;
                }
            }
        }
        
        return Value;
    }
    
    private int getPieceCountAsSimpleEval(int[] bs){
	    int[] pawns = new int[2],
	            kings = new int[2] ;
	
	        for (int i = 0; i < H * W; i++)
	        {
	            int v = bs[i];
	            switch(v)
	            {
	                case RED_PAWN:
	                case BLK_PAWN:
	                    pawns[v % 4] += 1;
	                    break;
	                case RED_KING:
	                case BLK_KING:
	                    kings[v % 4] += 1;
	                    break;
	            }
	        }
	
	        return 1 * (pawns[RED] - pawns[BLK]) + 
	               2 * (kings[RED] - kings[BLK]);
    }
}
