package cs540.checkers.wilcox;

import cs540.checkers.*;
import static cs540.checkers.CheckersConsts.*;

import java.util.*;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

public class WilcoxPlayer extends CheckersPlayer {
	/* The number of pruned subtrees for the most recent deepening iteration. */
	protected int pruneCount;
	protected Evaluator sbe;
	protected Move bestMove;
	protected Move prevBestMove;
	protected Move prevPrevBestMove;
	
	public WilcoxPlayer(String name, int side) {
		super(name, side);
		// Use SimpleEvaluator to score terminal nodes
		sbe = new CustomEvaluator();

		/*
		 * The first of the two players you will implement, the alpha-beta
		 * player, searches using alphabeta pruning.
		 * 
		 * You must use the getAllPossibleMoves method to retrieve the list of
		 * possible moves at each non-terminal node, and you must examine these
		 * moves in order.
		 * 
		 * Use the SimpleEvaluator SBE to evaluate terminal nodes. Implement
		 * this player using iterative deepening, which expands the depth of the
		 * game tree by one level at each iteration. This expansion will
		 * continue until a maximum depth of depthLimit or until the time is up,
		 * whichever comes first. Additionally, you must count the number of
		 * subtrees pruned by the alpha-beta search. This prune count will be
		 * used to grade your alpha-beta player for correctness. Implement this
		 * alpha-beta player as a subclass of CheckersPlayer in calculateMove().
		 * Do not place the chosen move in the return value of calculateMove();
		 * instead, set the chosen move via setMove(). The game controller will 
		 * access the chosen move by calling getMove(). Note that
		 * each step of iterative deepening may produce a different best move.
		 * Therefore, you should call setMove() after each iteration so that
		 * when the game controller interrupts the computation thread on which
		 * calculateMove() runs, getMove() returns the best move found so far.
		 */
	}

    /*
	* function Alpha-Beta-Search(state, depth) returns an action
	* 		v<-MAX-VALUE(state, -inf, +inf, depth)
	* 		return the action in ACTIONS(state) with value v
	*/
	public void calculateMove(int[] bs) {
		//Using algorithm from pg 170 of book
		//Alpha Beta Search

		//set the previous moves
		prevPrevBestMove = prevBestMove;
		prevBestMove = bestMove;
		
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int depth = 0;
		int maxValue;
		for (int depthIter = 1; depthIter <= depthLimit; depthIter++) {
			pruneCount = 0;
			maxValue = Max_Value(bs, alpha, beta, depth, depthIter);

			// if verbose print a best move and the deepening step we are on...
			if (Utils.verbose) {
				System.out.printf(
					"Iterative deepening step %d. best move: %s, score: %d\n",
					depthIter, bestMove.toString(), maxValue
				);
			}
			//set best move on the rist
			if(bestMove.equals(prevBestMove) || bestMove.equals(prevPrevBestMove)){
				if(depthIter == 1){
					//set it anyway, just so we setMove to something possible
					setMove(bestMove);
				}
			}
			else{ 
				setMove(bestMove);
			}
		}
	}

	

	 /*
    * function MAX-VALUE(state, alpha, beta, depth) returns a utility value
    * 		if Terminal_Test(state) | depth == 0 then return UTILITY(state)
    * 		v<- -inf
    * 		foreach a in ACTIONS(state) do
    * 			// return the max of v and the min value of the result, 
    * 			// alpha and beta min value
    * 			v<- MAX(v, MIN-VALUE(RESULT(s,a), alpha, beta, depth-1)) 
    * 			if v >= beta, then return v
    * 			alpha <- MAX(alpha, v)
    * 		return v
    */
	private int Max_Value(int[] bs, int alpha, int beta, int currDepth,
			int depth) {
		List<Move> Moves = Utils.getAllPossibleMoves(bs, side);
		
		// is player out of moves
		if (currDepth >= depth || Moves.isEmpty()) {
			// if black we need to negate the return value
			int Value = side == BLK ? -sbe.eval(bs) : sbe.eval(bs);
			return Value;
		}

		int MaxValue = Integer.MIN_VALUE;
		int Value;
		Move CurMove;
		for (int moveIter = 0; moveIter < Moves.size(); moveIter++) {
			CurMove = Moves.get(moveIter);

			// save state and move
			Stack<Integer> rv = Utils.execute(bs, CurMove);

			Value = Min_Value(bs, alpha, beta, currDepth + 1, depth);

			// revert move for next part
			Utils.revert(bs, rv);

			// pruning
			if (Value > MaxValue) {
				MaxValue = Value;

				// If we are in the upper level set this as the
				if (currDepth == 0) {
						bestMove = CurMove;
				}
			}
			if (MaxValue >= beta) {
				// We performed a prune
				// Count the number of branches that we did not explore
				pruneCount ++;
				return MaxValue;
			}
			if (MaxValue > alpha)
				alpha = MaxValue;
		}
		return MaxValue;
	}

    /* 
    * function MIN-VALUE(state, alpha, beta, depth) returns a utility value
    * 		if TERMINAL-TEST(state) | depth == 0 then return UTILITY(state)
    * 		v <- +inf
    * 		foreach a in ACTIONS(state) do
    * 			v<- Min(v, MAX-VALUE(RESULT(s,a), alpha, beta, depth-1))
    * 			if v <= alpha then return v
    * 			beta <- MIN(beta, v)
    * 		return v
    */
	private int Min_Value(int[] bs, int alpha, int beta, int currDepth,
			int depth) {
		List<Move> Moves = Utils.getAllPossibleMoves(bs, Utils.otherSide(side));

		// is player out of moves
		if (currDepth >= depth || Moves.isEmpty()) {
			// if black we need to negate the return value
			int Value = side == BLK ? -sbe.eval(bs) : sbe.eval(bs);
			return Value;
		}

		int minValue = Integer.MAX_VALUE;
		int val;
		Move currentMove;
		for (int moveIter = 0; moveIter < Moves.size(); moveIter++) {
			currentMove = Moves.get(moveIter);

			// save state and move
			Stack<Integer> rv = Utils.execute(bs, currentMove);

			val = Max_Value(bs, alpha, beta, currDepth + 1, depth);

			// revert move for next part
			Utils.revert(bs, rv);

			// pruning
			if (val < minValue)
				minValue = val;
			if (minValue <= alpha) {
				// We performed a prune
				// Count the number of branches that we did not explore
				pruneCount++;;
				return minValue;
			}
			if (minValue < beta)
				beta = minValue;
		}
		return minValue;
	}

	public int getPruneCount() {
		return pruneCount;
	}
}
