/* 
	Daniel Esposito, desposito, 358064
	Tim Chan, Timyc
*/

package desposito.fenceMaster;

import java.util.*;

public class Piece
{
	public int i;
	public int j;
	public char type;
	public String colour;
	public boolean visited;
	public Piece predecessor;

	public Piece(int i, int j, char type)
	{
		this.i = i;
		this.j = j;
		this.type = type;
		this.colour = "white"; //White means unexplored, gray means currently being explored and black means fully explored.
		this.predecessor = null;
	}

	//Used by Tripod search
	public ArrayList<Piece> getNeighbours(GameBoard board)
	{
		ArrayList<Piece> result = new ArrayList<Piece>();
		int jmax = board.getRow(i).size();
		int numRows = board.numRows;

		//This function checks all positions around the current Piece (i,j) and returns a ArrayList of valid unvisited neighbours. 
		//There are three main conditions for our current Piece
		if(i==0)
		{
			if(j>0 && j<jmax-1)
			{
				add(board, result, i+1, j);
				add(board, result, i+1, j+1);
				add(board, result, i, j-1);
				add(board, result, i, j+1);
			}

			else if(j==0)
			{
				add(board, result, i+1, j);
				add(board, result, i+1, j+1);
				add(board, result, i, j+1);
			}

			else if(j==jmax-1)
			{
				add(board, result, i+1, j-1);
				add(board, result, i+1, j+1);
				add(board, result, i, j-1);	
			}
		}

		else if(i>0 && i<numRows/2)
		{
			if(j>0 && j<jmax-1)
			{
				add(board, result, i-1, j-1);
				add(board, result, i+1, j+1);
				add(board, result, i, j-1);
				add(board, result, i, j+1);
				add(board, result, i+1, j);
				add(board, result, i-1, j);
			}

			else if(j==jmax-1)
			{
				add(board, result, i+1, j+1);
				add(board, result, i-1, j-1);
				add(board, result, i, j-1);
				add(board, result, i+1, j);
			}

			else if(j==0)
			{
				add(board, result, i+1, j);
				add(board, result, i-1, j);
				add(board, result, i, j+1);
				add(board, result, i+1, j+1);
			}
		}

		else if(i==numRows/2)
		{
			if(j>0 && j<jmax-1)
			{
				add(board, result, i-1, j-1);
				add(board, result, i+1, j);
				add(board, result, i, j+1);
				add(board, result, i, j-1);
				add(board, result, i-1, j);
				add(board, result, i+1, j-1);
			}

			if(j==0)
			{
				add(board, result, i, j+1);
				add(board, result, i-1, j-1);
				add(board, result, i+1, j-1);
			}

			if(j==jmax-1)
			{
				add(board, result, i-1, j-1);
				add(board, result, i+1, j-1);
				add(board, result, i, j-1);
			}
		}
		
		else if(i>numRows/2 && i<numRows-1)
		{

			if(j>0 && j<jmax-1)
			{
				add(board, result, i, j-1);
				add(board, result, i, j+1);
				add(board, result, i-1, j);
				add(board, result, i+1, j);
				add(board, result, i-1, j+1);
				add(board, result, i+1, j-1);
			}

			else if(j==0)
			{
				add(board, result, i-1, j);
				add(board, result, i+1, j);
				add(board, result, i+1, j+1);
				add(board, result, i, j+1);
			}

			else if(j==jmax-1)
			{
				add(board, result, i-1, j+1);
				add(board, result, i+1, j-1);
				add(board, result, i-1, j);
				add(board, result, i, j-1);
			}
		}

		else if(i==numRows-1)
		{
			if(j>0 && j<jmax-1)
			{ 
				add(board, result, i, j+1);
				add(board, result, i, j-1);
				add(board, result, i-1, j);
				add(board, result, i-1, j+1);
			}

			else if(j==0)
			{
				add(board, result, i-1, j);
				add(board, result, i, j+1);
				add(board, result, i-1, j+1);
			}

			else if(j==jmax-1)
			{
				add(board, result, i-1, j+1);
				add(board, result, i-1, j-1);
				add(board, result, i-1, j+1);
			}
		}

		return result;
	}

	public void add(GameBoard board, ArrayList<Piece> result, int i, int j)
	{	
		result.add(board.getPiece(i,j));
	}

	public boolean isSameType(char element)
	{
		if(this.type == element)
		{
			return true;
		}

		else
		{
			return false;
		}
	}
}