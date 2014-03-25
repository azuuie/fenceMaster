import java.lang.Math.*;
import java.util.*;

public class piece
{
	public int i;
	public int j;
	public char type;
	public String colour;
	public boolean visited;
	public piece predecessor;

	public piece(int i, int j, char type)
	{
		this.i = i;
		this.j = j;
		this.type = type;
		this.colour = "white"; //White means unexplored, gray means currently being explored and black means fully explored.
		this.predecessor = null;
	}

	//Used by Tripod search
	public List<piece> getNeighbours(gameBoard board)
	{
		List<piece> result = new List<piece>();
		int jmax = (board.numRows - Math.abs(board.boardSize - 1 - i));

		//This function checks all positions around the current piece (i,j) and returns a list of valid unvisited neighbours. 
		//There are three main conditions for our current piece
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

	public void add(gameBoard board, List<piece> result, int i, int j)
	{	
		System.out.println("\n N at: " + i + "," + j);
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