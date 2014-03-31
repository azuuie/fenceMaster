import java.lang.Math.*;
import java.util.*;

public class judge 
 { 
 	
    public String classifyEdge(piece x, int numRows, int jmax)
    {
		//Top left edge
		if(x.j==0 && (x.i<numRows/2 && x.i > 0))
		{
			return "topLeft";
		}

		//Top Right edge
		else if(x.j==jmax-1 && (x.i<numRows/2 && x.i > 0))
		{
			return "topRight";
		}

		//Bottom left edge
		else if(x.j==0 && (x.i>numRows/2 && x.i < numRows-1))
		{
			return "bottomLeft";
		}

		//Bottom right edge
		else if(x.j==jmax-1 && (x.i>numRows/2 && x.i < numRows-1))
		{
			return "bottomRight";
		}

		//Bottom edge
		else if(x.i==numRows-1 && (x.j>0 && x.j < jmax-1))
		{
			return "Bottom";
		}

		//Top edge
		else if(x.i==0 && (x.j>0 && x.j < jmax-1))
		{
			return "Top";
		}

		else
		{
			return "None";
		}
	}

	//This algorithm runs a Coloured DFS which is based on the concept of pre and post vertex ordering. 
	public void detectCycleTripod(gameBoard board, player x)
	{
		//System.out.println("-----PLAYER: " + x.type + "----------");
		ArrayList<piece> path = new ArrayList<piece>();

		//Make sure each piece is white before we start the check
		//Start the algorithm. This will run for each piece on the board beloning to x hence is O(2V) for the two passes here.
		for(piece u : x.pieces.keySet())
		{
			u.colour = "white";
			u.predecessor = null;
		}

		
		for(piece u : x.pieces.keySet())
		{
			//System.out.println("-----PIECE: " + u.i + u.j + "----------");
			if(u.colour == "white" && u.type == x.type)
			{
				visitNode(board, u, x, path);
				if(x.loop == true || x.tripod == true)
				{
					return;
				}
			}
		}
	}

	public void visitNode(gameBoard board, piece u, player x, ArrayList<piece> path)
	{
		path.add(u);
		u.colour = "gray";
		ArrayList<piece> neighbours = x.pieces.get(u);

		//Do a tripod check simultaneously
		String edge = classifyEdge(u, board.numRows, board.getRow(u.i).size());
		
		if(!(x.touchedEdges.contains(edge)) && edge != "None")
		{
			x.touchedEdges.add(edge);
			//System.out.println("Touched edge: " + edge + " at " + "(" + u.i + "," + u.j + ")");
			if(x.touchedEdges.size() >= 3)
			{
				//System.out.println("WE HAVE A TRIPOD");
				x.tripod = true;
				return; //Force termination	
			}
		}

		for(piece v : neighbours)
		{	
			if(v.type == x.type)
			{
				//Kills the recursion stack if a win has been detected.
				if(x.loop == true || x.tripod == true)
				{
					return;
				}
				//System.out.println("\n----At piece " + u.i + u.j + "-----");
				if(v.colour == "gray" && u.predecessor != v)
				{
					//System.out.println("CONNECTING!");
					path.add(v);
					piece startPiece = v;

					printLoop(path);

					if(validLoop(startPiece, path, x) == true)
					{
						//System.out.println("-----CONNECTED TO: " + v.i + v.j + "----------");
						//System.out.println("WE HAVE A LOOP!");
						x.loop = true;
						return;
					}

					path.remove(path.size()-1);
				}

				if(v.colour == "white" && v.type == x.type)
				{
					//System.out.println("-----VISITING: " + v.i + v.j + "----------");
					v.predecessor = u;
					visitNode(board, v, x, path);
				}
			}
		}

		u.colour = "black";
		path.remove(path.size()-1);
		//System.out.println("(" + u.i + "," + u.j + ")" + " is now black");
	}

	public char whoWon(gameBoard board, player w, player b) 
	{
		//These methods modify the given player states
		detectCycleTripod(board, w);
		detectCycleTripod(board, b);

		//Conditions for when White wins with a tripod
		if(w.tripod == true && (b.tripod == false && b.loop == false))
		{
			char result = 'W';
			return result;
		}

		//Conditions for when White wins with a Loop
		else if(w.loop == true && (b.tripod == false && b.loop == false))
		{
			char result = 'W';
			return result;
		}

		//Conditions for when Black wins with a tripod
		if(b.tripod == true && (w.tripod == false && w.loop == false))
		{
			char result = 'B';
			return result;
		}

		//Conditions for when Black wins with a Loop
		else if(b.loop == true && (w.tripod == false && w.loop == false))
		{
			char result = 'B';
			return result;
		}

		//Draw
		else if((b.loop == true || b.tripod == true) && (w.tripod == true || w.loop == true))
		{
			char result = 'D';
			return result;
		}	

		else
		{	
			char result = 'N';
			return 'N';
		}

	}

	public String winType(player x)
	{
		if(x.tripod && !x.loop)
		{
			return "Tripod";
		}

		else if(x.loop && !x.tripod)
		{
			return "Loop";
		}

		else
		{
			return "Both";
		}
	}

	//This function extracts the loops from path.
	//Works on the assumption that a loop is everything contained within the two occurances of each piece in loop
	//For example, if the colouredDFS starts at (0,0) and returns to (0,0) with each visited piece being added to path
	//Then a loop will be everything from the first occurance of (0,0) up to the next. 
	public boolean validLoop(piece startPiece, ArrayList<piece> path, player x)
	{
		ArrayList<piece> tmp = new ArrayList<piece>();
		boolean startRecord = false;
		
		for(piece u : path)
		{
			if(u == startPiece)
			{
				startRecord = true;
			}

			if(startRecord)
			{
				tmp.add(u);
			}
		}

		////System.out.println("REDUCED PATH: ");
		printLoop(tmp);

		if(isSparse(tmp, x))
		{
			return true;
		}

		return false;
	}

	public boolean isSparse(ArrayList<piece> loop, player x)
	{
		int imax = -1;
		int imin = 999;
		int jmax = -1;
		int jmin = 999;
		
		for(piece u : loop)
		{
			if(u.i < imin)
			{
				imin = u.i;
			}

			else if(u.i > imax)
			{
				imax = u.i;
			}

			if(u.j < jmin)
			{
				jmin = u.j;
			}

			else if(u.j > jmax)
			{
				jmax = u.j;
			}
		}

		//Check if at least one non player type piece exists within the loop bounds.
		for(piece u : loop)
		{
			ArrayList<piece> neighbours = x.pieces.get(u);
			for(piece neighbour : neighbours)
			{
				if(neighbour.type != x.type)
				{
					if(neighbour.i > imin && neighbour.i < imax)
					{
						if(neighbour.j > jmin && neighbour.j < jmax)
						{
							return true;
						}
					}
				}

			}
		}

		return false;
	}

	public void printLoop(ArrayList<piece> loop)
	{
		for(piece u : loop)
		{
			//System.out.println("(" + u.i + "," + u.j + ")");
		}
	}
}