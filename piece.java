import java.lang.Math.*;
import java.util.*;

public class piece
{
	public int i;
	public int j;
	public char type;
	public int colour;
	public boolean visited;

	public piece(int i, int j, char type)
	{
		this.i = i;
		this.j = j;
		this.type = type;
		this.colour = "white"; //White means unexplored, gray means currently being explored and black means fully explored. 
		this.visited = false;

	}

	//Used by Tripod search
	public ArrayList<piece> checkForNeighbours(gameBoard gameBoard, ArrayList<piece> visited, ArrayList<piece> stack, int numRows, int jmax)
	{
		ArrayList<piece> result = new ArrayList<piece>();

		//This function checks all positions around the current piece (i,j) and returns a list of valid unvisited neighbours. 
		//There are three main conditions for our current piece
		if(i==0)
		{
			if(j>0 && j<jmax-1)
			{
				add(gameBoard, visited, stack, result, i+1, j);
				add(gameBoard, visited, stack, result, i+1, j+1);
				add(gameBoard, visited, stack, result, i, j-1);
				add(gameBoard, visited, stack, result, i, j+1);
			}

			else if(j==0)
			{
				add(gameBoard, visited, stack, result, i+1, j);
				add(gameBoard, visited, stack, result, i+1, j+1);
				add(gameBoard, visited, stack, result, i, j+1);
			}

			else if(j==jmax-1)
			{
				add(gameBoard, visited, stack, result, i+1, j-1);
				add(gameBoard, visited, stack, result, i+1, j+1);
				add(gameBoard, visited, stack, result, i, j-1);	
			}
		}

		else if(i>0 && i<numRows/2)
		{
			if(j>0 && j<jmax-1)
			{
				add(gameBoard, visited, stack, result, i-1, j-1);
				add(gameBoard, visited, stack, result, i+1, j+1);
				add(gameBoard, visited, stack, result, i, j-1);
				add(gameBoard, visited, stack, result, i, j+1);
				add(gameBoard, visited, stack, result, i+1, j);
				add(gameBoard, visited, stack, result, i-1, j);
			}

			else if(j==jmax-1)
			{
				add(gameBoard, visited, stack, result, i+1, j+1);
				add(gameBoard, visited, stack, result, i-1, j-1);
				add(gameBoard, visited, stack, result, i, j-1);
				add(gameBoard, visited, stack, result, i+1, j);
			}

			else if(j==0)
			{
				add(gameBoard, visited, stack, result, i+1, j);
				add(gameBoard, visited, stack, result, i-1, j);
				add(gameBoard, visited, stack, result, i, j+1);
				add(gameBoard, visited, stack, result, i+1, j+1);
			}
		}

		else if(i==numRows/2)
		{
			if(j>0 && j<jmax-1)
			{
				add(gameBoard, visited, stack, result, i-1, j-1);
				add(gameBoard, visited, stack, result, i+1, j);
				add(gameBoard, visited, stack, result, i, j+1);
				add(gameBoard, visited, stack, result, i, j-1);
				add(gameBoard, visited, stack, result, i-1, j);
				add(gameBoard, visited, stack, result, i+1, j-1);
			}

			if(j==0)
			{
				add(gameBoard, visited, stack, result, i, j+1);
				add(gameBoard, visited, stack, result, i-1, j-1);
				add(gameBoard, visited, stack, result, i+1, j-1);
			}

			if(j==jmax-1)
			{
				add(gameBoard, visited, stack, result, i-1, j-1);
				add(gameBoard, visited, stack, result, i+1, j-1);
				add(gameBoard, visited, stack, result, i, j-1);
			}
		}
		
		else if(i>numRows/2 && i<numRows-1)
		{

			if(j>0 && j<jmax-1)
			{
				add(gameBoard, visited, stack, result, i, j-1);
				add(gameBoard, visited, stack, result, i, j+1);
				add(gameBoard, visited, stack, result, i-1, j);
				add(gameBoard, visited, stack, result, i+1, j);
				add(gameBoard, visited, stack, result, i-1, j+1);
				add(gameBoard, visited, stack, result, i+1, j-1);
			}

			else if(j==0)
			{
				add(gameBoard, visited, stack, result, i-1, j);
				add(gameBoard, visited, stack, result, i+1, j);
				add(gameBoard, visited, stack, result, i+1, j+1);
				add(gameBoard, visited, stack, result, i, j+1);
			}

			else if(j==jmax-1)
			{
				add(gameBoard, visited, stack, result, i-1, j+1);
				add(gameBoard, visited, stack, result, i+1, j-1);
				add(gameBoard, visited, stack, result, i-1, j);
				add(gameBoard, visited, stack, result, i, j-1);
			}
		}

		else if(i==numRows-1)
		{
			if(j>0 && j<jmax-1)
			{ 
				add(gameBoard, visited, stack, result, i, j+1);
				add(gameBoard, visited, stack, result, i, j-1);
				add(gameBoard, visited, stack, result, i-1, j);
				add(gameBoard, visited, stack, result, i-1, j+1);
			}

			else if(j==0)
			{
				add(gameBoard, visited, stack, result, i-1, j);
				add(gameBoard, visited, stack, result, i, j+1);
				add(gameBoard, visited, stack, result, i-1, j+1);
			}

			else if(j==jmax-1)
			{
				add(gameBoard, visited, stack, result, i-1, j+1);
				add(gameBoard, visited, stack, result, i-1, j-1);
				add(gameBoard, visited, stack, result, i-1, j+1);
			}
		}

		return result;
	}

	//Used by Loop search
	public ArrayList<piece> checkForNeighbours(gameBoard gameBoard, piece start, ArrayList<piece> visited, ArrayList<piece> stack, int numRows, int jmax, int count)
	{
		ArrayList<piece> result = new ArrayList<piece>();

		//This function checks all positions around the current piece (i,j) and returns a list of valid unvisited neighbours. 
		//There are three main conditions for our current piece
		if(i==0)
		{
			if(j>0 && j<jmax-1)
			{
				add(gameBoard, start, visited, stack, result, i+1, j, count);
				add(gameBoard, start, visited, stack, result, i+1, j+1, count);
				add(gameBoard, start, visited, stack, result, i, j-1, count);
				add(gameBoard, start, visited, stack, result, i, j+1, count);
			}

			else if(j==0)
			{
				add(gameBoard, start, visited, stack, result, i+1, j, count);
				add(gameBoard, start, visited, stack, result, i+1, j+1, count);
				add(gameBoard, start, visited, stack, result, i, j+1, count);
			}

			else if(j==jmax-1)
			{
				add(gameBoard, start, visited, stack, result, i+1, j-1, count);
				add(gameBoard, start, visited, stack, result, i+1, j+1, count);
				add(gameBoard, start, visited, stack, result, i, j-1, count);	
			}
		}

		else if(i>0 && i<numRows/2)
		{
			if(j>0 && j<jmax-1)
			{
				add(gameBoard, start, visited, stack, result, i-1, j-1, count);
				add(gameBoard, start, visited, stack, result, i+1, j+1, count);
				add(gameBoard, start, visited, stack, result, i, j-1, count);
				add(gameBoard, start, visited, stack, result, i, j+1, count);
				add(gameBoard, start, visited, stack, result, i+1, j, count);
				add(gameBoard, start, visited, stack, result, i-1, j, count);
			}

			else if(j==jmax-1)
			{
				add(gameBoard, start, visited, stack, result, i+1, j+1, count);
				add(gameBoard, start, visited, stack, result, i-1, j-1, count);
				add(gameBoard, start, visited, stack, result, i, j-1, count);
				add(gameBoard, start, visited, stack, result, i+1, j, count);
			}

			else if(j==0)
			{
				add(gameBoard, start, visited, stack, result, i+1, j, count);
				add(gameBoard, start, visited, stack, result, i-1, j, count);
				add(gameBoard, start, visited, stack, result, i, j+1, count);
				add(gameBoard, start, visited, stack, result, i+1, j+1, count);
			}
		}

		else if(i==numRows/2)
		{
			if(j>0 && j<jmax-1)
			{
				add(gameBoard, start, visited, stack, result, i-1, j-1, count);
				add(gameBoard, start, visited, stack, result, i+1, j, count);
				add(gameBoard, start, visited, stack, result, i, j+1, count);
				add(gameBoard, start, visited, stack, result, i, j-1, count);
				add(gameBoard, start, visited, stack, result, i-1, j, count);
				add(gameBoard, start, visited, stack, result, i+1, j-1, count);
			}

			if(j==0)
			{
				add(gameBoard, start, visited, stack, result, i, j+1, count);
				add(gameBoard, start, visited, stack, result, i-1, j-1, count);
				add(gameBoard, start, visited, stack, result, i+1, j-1, count);
			}

			if(j==jmax-1)
			{
				add(gameBoard, start, visited, stack, result, i-1, j-1, count);
				add(gameBoard, start, visited, stack, result, i+1, j-1, count);
				add(gameBoard, start, visited, stack, result, i, j-1, count);
			}
		}
		
		else if(i>numRows/2 && i<numRows-1)
		{
			if(j>0 && j<jmax-1)
			{
				add(gameBoard, start, visited, stack, result, i, j-1, count);
				add(gameBoard, start, visited, stack, result, i, j+1, count);
				add(gameBoard, start, visited, stack, result, i-1, j, count);
				add(gameBoard, start, visited, stack, result, i+1, j, count);
				add(gameBoard, start, visited, stack, result, i-1, j+1, count);
				add(gameBoard, start, visited, stack, result, i+1, j-1, count);
			}

			else if(j==0)
			{
				add(gameBoard, start, visited, stack, result, i-1, j, count);
				add(gameBoard, start, visited, stack, result, i+1, j, count);
				add(gameBoard, start, visited, stack, result, i+1, j+1, count);
				add(gameBoard, start, visited, stack, result, i, j+1, count);
			}

			else if(j==jmax-1)
			{
				add(gameBoard, start, visited, stack, result, i-1, j+1, count);
				add(gameBoard, start, visited, stack, result, i+1, j-1, count);
				add(gameBoard, start, visited, stack, result, i-1, j, count);
				add(gameBoard, start, visited, stack, result, i, j-1, count);
			}
		}

		else if(i==numRows-1)
		{
			if(j>0 && j<jmax-1)
			{ 
				add(gameBoard, start, visited, stack, result, i, j+1, count);
				add(gameBoard, start, visited, stack, result, i, j-1, count);
				add(gameBoard, start, visited, stack, result, i-1, j, count);
				add(gameBoard, start, visited, stack, result, i-1, j+1, count);
			}

			else if(j==0)
			{
				add(gameBoard, start, visited, stack, result, i-1, j, count);
				add(gameBoard, start, visited, stack, result, i, j+1, count);
				add(gameBoard, start, visited, stack, result, i-1, j+1, count);
			}

			else if(j==jmax-1)
			{
				add(gameBoard, start, visited, stack, result, i-1, j+1, count);
				add(gameBoard, start, visited, stack, result, i-1, j-1, count);
				add(gameBoard, start, visited, stack, result, i-1, j+1, count);
			}
		}

		return result;
	}

	//Used by loop search
	public void add(gameBoard gameBoard, piece start, ArrayList<piece> visited, ArrayList<piece> stack, ArrayList<piece> result, int i, int j, int count)
	{	
		if(isSameType(gameBoard.getPositionType(i,j)))
		{
			piece tmp = gameBoard.getPiece(i,j);

			if(!visited.contains(tmp) && !stack.contains(tmp))
			{
				System.out.println("\n N at: " + i + "," + j);
				result.add(tmp);
			}

			if(start == tmp && count > 1)
			{
				System.out.println("\n N at: " + i + "," + j);
				result.add(tmp);
			}
		}
	}

	//Used by tripod search
	public void add(gameBoard gameBoard, ArrayList<piece> visited, ArrayList<piece> stack, ArrayList<piece> result, int i, int j)
	{	
		if(isSameType(gameBoard.getPositionType(i,j)))
		{
			piece tmp = gameBoard.getPiece(i,j);

			if(!visited.contains(tmp) && !stack.contains(tmp))
			{
				System.out.println("\n N at: " + i + "," + j);
				result.add(tmp);
			}
		}
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