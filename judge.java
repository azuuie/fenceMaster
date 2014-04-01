/* 
	Daniel Esposito, desposito, 358064
	Tim Chan, Timyc
*/

package desposito.fenceMaster;

import java.util.*;

public class Judge
 { 
    public String classifyEdge(Piece x, int numRows, int jmax)
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
	public void detectCycleTripod(GameBoard board, Player x)
	{
		//System.out.println("-----PLAYER: " + x.type + "----------");
		ArrayList<Piece> path = new ArrayList<Piece>();

		//Make sure each Piece is white before we start the check
		//Start the algorithm. This will run for each Piece on the board beloning to x hence is O(2V) for the two passes here.
		for(Piece u : x.pieces.keySet())
		{
			u.colour = "white";
			u.predecessor = null;
		}

		
		for(Piece u : x.pieces.keySet())
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

	public void visitNode(GameBoard board, Piece u, Player x, ArrayList<Piece> path)
	{
		path.add(u);
		u.colour = "gray";
		ArrayList<Piece> neighbours = x.pieces.get(u);

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

		for(Piece v : neighbours)
		{	
			if(v.type == x.type)
			{
				//Kills the recursion stack if a win has been detected.
				if(x.loop == true || x.tripod == true)
				{
					return;
				}
				//System.out.println("\n----At Piece " + u.i + u.j + "-----");
				if(v.colour == "gray" && u.predecessor != v)
				{
					//System.out.println("CONNECTING!");
					path.add(v);
					Piece startPiece = v;

					//printLoop(path);

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

	public char whoWon(GameBoard board, Player w, Player b) 
	{
		//These methods modify the given Player states
		detectCycleTripod(board, w);
		detectCycleTripod(board, b);

		//Conditions for when White wins with a tripod
		if(w.tripod == true && (b.tripod == false && b.loop == false))
		{
			return 'W';
		}

		//Conditions for when White wins with a Loop
		else if(w.loop == true && (b.tripod == false && b.loop == false))
		{
			return 'W';
		}

		//Conditions for when Black wins with a tripod
		if(b.tripod == true && (w.tripod == false && w.loop == false))
		{
			return 'B';
		}

		//Conditions for when Black wins with a Loop
		else if(b.loop == true && (w.tripod == false && w.loop == false))
		{
			return 'B';
		}

		//Draw
		else if((b.loop == true || b.tripod == true) && (w.tripod == true || w.loop == true))
		{
			return 'D';
		}	

		else
		{	
			return 'N';
		}

	}

	public String winType(Player x)
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
	//Works on the assumption that a loop is everything contained within the two occurrences of each Piece in loop
	//For example, if the colouredDFS starts at (0,0) and returns to (0,0) with each visited Piece being added to path
	//Then a loop will be everything from the first occurrence of (0,0) up to the next. 
	public boolean validLoop(Piece startPiece, ArrayList<Piece> path, Player x)
	{
		ArrayList<Piece> tmp = new ArrayList<Piece>();
		boolean startRecord = false;
		
		for(Piece u : path)
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
		//printLoop(tmp);

		if(isSparse(tmp, x))
		{
			return true;
		}

		return false;
	}

	public boolean isSparse(ArrayList<Piece> loop, Player x)
	{
		int imax = -1;
		int imin = 999;
		int jmax = -1;
		int jmin = 999;
		
		for(Piece u : loop)
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

		//Check if at least one non Player type Piece exists within the loop bounds.
		for(Piece u : loop)
		{
			ArrayList<Piece> neighbours = x.pieces.get(u);
			for(Piece neighbour : neighbours)
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

//	public void printLoop(ArrayList<Piece> loop)
//	{
//		for(Piece u : loop)
//		{
//			//System.out.println("(" + u.i + "," + u.j + ")");
//		}
//	}
}