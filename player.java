import java.util.*;

public class player 
{
	public char type;
	public boolean loop;
	public boolean tripod;
	public ArrayList<String> touchedEdges;
	public HashMap<piece, ArrayList<pieces>> neighbours;

	public player(char type) 
	{
		this.type = type;
		this.loop = false;
		this.tripod = false;
		this.touchedEdges = new ArrayList<String>();
	}

	public void createHashMap(gameBoard board)
	{
		//create initial hash map. Each entry has a list of surrounding cells
	}

	public void updateHashMap(gameBoard board, peice u)
	{
		//add new piece to hash map of pieces

		//update previous entries to include new piece as their neighbours
	}

	public void makeMove(gameBoard board)
	{
		//call some ai function

		//create a new piece

		//update hashmap of pieces
	}
}
