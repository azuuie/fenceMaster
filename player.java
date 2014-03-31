import java.util.*;

public class player 
{
	public char type;
	public boolean loop;
	public boolean tripod;
	public ArrayList<String> touchedEdges;
	public HashMap<piece, ArrayList<piece>> pieces;

	public player(char type) 
	{
		this.type = type;
		this.loop = false;
		this.tripod = false;
		this.touchedEdges = new ArrayList<String>();
		this.pieces = new HashMap<piece, ArrayList<piece>>();
	}

	//Creates a hash Map of the players pieces. Each entry in the map stores a ArrayList of it's surrounding cells.
	public void createHashMap(gameBoard board)
	{
		for(int i = 0; i < board.numRows; i++)
		{
			ArrayList<piece> row = board.getRow(i);

			for (piece u : row)
			{
				//System.out.println("---PIECE " + u.i + u.j + "-----");
				if(this.type == u.type && !pieces.containsKey(u))
				{
					ArrayList<piece> neighbours = u.getNeighbours(board);
					pieces.put(u, neighbours);
				}
			}
		}

		//printPieces();
	}

	public void updateHashMap(gameBoard board, piece u)
	{
		//Generate a ArrayList of neighbours for u
		ArrayList<piece> neighbours = u.getNeighbours(board);

		//Add this piece and it's neighbours to the hash map if the caller is the of the same piece type.
		if(u.type == this.type)
		{
			pieces.put(u, neighbours);
		}

		//For each neighbour, we'll need to update their ArrayList of surrounding cells to replace the previously blank cells with this one.
		//This runs regardless of the caller type, since each player needs to know about this new pieces for detecting valid loops.
		for(piece x : neighbours)
		{
			//Get v's neighours
			ArrayList<piece> tmp = pieces.get(x);
			for(piece y : tmp)
			{
				if(y.i == u.i && y.j == u.j)
				{
					tmp.set(tmp.indexOf(y), u);
				}
			}
		}

		//printPieces();
	}

	public void printPieces()
	{
		for(piece u : pieces.keySet())
		{
			System.out.println("-----Piece at (" + u.i + "," + u.j + ") has neighbours at --------");
			for(piece v : pieces.get(u))
			{
				System.out.println("(" + v.i + "," + v.j + ")");
			}
		}
	}

	// public void makeMove(gameBoard board)
	// {
	// 	//call some ai function to determine best move

	// 	//create a new piece

	// 	//update hashmap of pieces
	// }
}
