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
	public ArrayList<piece> getNeighbours(gameBoard board)
	{
		ArrayList<piece> result = new ArrayList<piece>();
		int jmax = board.getRow(i).size();
		int numRows = board.numRows;

		//This function checks all positions around the current piece (i,j) and returns a ArrayList of valid unvisited neighbours. 
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

	public void add(gameBoard board, ArrayList<piece> result, int i, int j)
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