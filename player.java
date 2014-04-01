package desposito.fenceMaster;

import java.util.*;

public class Player
{
	public char type;
	public boolean loop;
	public boolean tripod;
	public ArrayList<String> touchedEdges;
	public HashMap<Piece, ArrayList<Piece>> pieces;

	public Player(char type) 
	{
		this.type = type;
		this.loop = false;
		this.tripod = false;
		this.touchedEdges = new ArrayList<String>();
		this.pieces = new HashMap<Piece, ArrayList<Piece>>();
	}

	//Creates a hash Map of the players pieces. Each entry in the map stores a ArrayList of it's surrounding cells.
	public void createHashMap(GameBoard board)
	{
		for(int i = 0; i < board.numRows; i++)
		{
			ArrayList<Piece> row = board.getRow(i);

			for (Piece u : row)
			{
				//System.out.println("---PIECE " + u.i + u.j + "-----");
				if(this.type == u.type && !pieces.containsKey(u))
				{
					ArrayList<Piece> neighbours = u.getNeighbours(board);
					pieces.put(u, neighbours);
				}
			}
		}

		//printPieces();
	}

	public void updateHashMap(GameBoard board, Piece u)
	{
		//Generate a ArrayList of neighbours for u
		ArrayList<Piece> neighbours = u.getNeighbours(board);

		//Add this Piece and it's neighbours to the hash map if the caller is the of the same Piece type.
		if(u.type == this.type)
		{
			pieces.put(u, neighbours);
		}

		//For each neighbour, we'll need to update their ArrayList of surrounding cells to replace the previously blank cells with this one.
		//This runs regardless of the caller type, since each Player needs to know about this new pieces for detecting valid loops.
		for(Piece x : neighbours)
		{
			//Get v's neighbours
			ArrayList<Piece> tmp = pieces.get(x);
			for(Piece y : tmp)
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
		for(Piece u : pieces.keySet())
		{
			System.out.println("-----Piece at (" + u.i + "," + u.j + ") has neighbours at --------");
			for(Piece v : pieces.get(u))
			{
				System.out.println("(" + v.i + "," + v.j + ")");
			}
		}
	}

	// public void makeMove(GameBoard board)
	// {
	// 	//call some ai function to determine best move

	// 	//create a new Piece

	// 	//update hashmap of pieces
	// }
}