import java.util.*;

public class player 
{
	public char type;
	public boolean loop;
	public boolean tripod;
	public List<String> touchedEdges;
	public HashMap<piece, List<pieces>> neighbours;

	public player(char type) 
	{
		this.type = type;
		this.loop = false;
		this.tripod = false;
		this.touchedEdges = new List<String>();
		this.pieces = new HashMap<piece, List<piece>>();
	}

	//Creates a hash Map of the players pieces. Each entry in the map stores a list of it's surrounding cells.
	public void createHashMap(gameBoard board)
	{
		for(List<piece> row : board)
		{
			for (piece u : row)
			{
				List<piece> neighbours = u.getNeighbours(board);
				if(this.type == u.type && !piece.containsKey(u))
				{
					pieces.put(u, neighbours);
				}
			}
		}
	}

	public void updateHashMap(gameBoard board, piece u)
	{
		//Generate a list of neighbours for u
		List<piece> neighbours = u.getNeighbours(board)

		//Add this piece and it's neighbours to the hash map if the caller is the of the same piece type.
		if(u.type == this.type)
		{
			pieces.put(u, neighbours);
		}

		//For each neighbour, we'll need to update their list of surrounding cells to replace the previously blank cells with this one.
		//This runs regardless of the caller type, since each player needs to know about this new pieces for detecting valid loops.
		for(piece x : neighbours)
		{
			//Get v's neighours
			List<piece> tmp = pieces.get(x);
			for(piece y : tmp)
			{
				if(y.i == u.i && y.j == u.j)
				{
					tmp.set(tmp.indexOf(y), u);
				}
			}
		}
	}

	public void makeMove(gameBoard board)
	{
		//call some ai function

		//create a new piece

		//update hashmap of pieces
	}
}
