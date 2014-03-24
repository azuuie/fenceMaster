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
		//Make sure each piece is white before we start the check
		//Start the algorithm. This will run for each piece on the board beloning to x hence is O(2V) for the two passes here.
		for(int i=0; i<board.numRows; i++)
		{
			int jmax = (board.numRows - Math.abs(board.boardSize - 1 - i));
			for (int j=0; j<jmax; j++)
			{
				gameBoard.getPiece(i,j).colour = "white";
				gameBoard.getPiece(i,j).predecessor = null;
			}
		}

		for(int i=0; i<board.numRows; i++)
		{
			int jmax = (board.numRows - Math.abs(board.boardSize - 1 - i));
			for (int j=0; j<jmax; j++)
			{
				piece u = gameBoard.getPiece(i,j);
				if(u.colour == "white" && u.type == x.type)
				{
					//Do a tripod check simultaneously
					String edge = v.classifyEdge(board, board.numRows, jmax);
					if(!(x.touchedEdges.contains(edge)) && edge != "None")
					{
						x.touchedEdges.add(edge);
						if(x.touchedEdges.size() >= 3)
						{
							System.out.println("WE HAVE A TRIPOD");
							x.tripod = true;
							return; //Force termination	
						}
					}

					//Otherwise if we didn't terminate in the previous step, continue looking for a loop.
					visitNode(board, u, x, jmax);
				}
			}
		}
	}

	public void visitNode(gameBoard board, piece u, player x, int jmax)
	{
		u.colour = "gray";
		ArrayList<piece> neighours = u.getNeighbours(board, board.numRows, jmax);

		for(piece v : neighbours)
		{
			if(v.colour == "gray" && v.predecessor != u)
			{
				System.out.println("WE HAVE A LOOP!");
				x.loop = true;
				return;
			}

			if(v.colour == "white")
			{
				v.predecessor = u;
				visitNode(v);
			}

			u.colour = "black";
		}
	}

	public char whoWon(gameBoard board, player w, player b) 
	{
		//These methods modify the given player states
		System.out.println("\n=========LOOKIN FOR WHITE TRIPOD===========");
		searchTripod(board, w);

		System.out.println("\n=========LOOKIN FOR WHITE LOOP===========");
		searchLoop(board, w);

		System.out.println("\n=========LOOKIN FOR BLACK TRIPOD===========");
		searchTripod(board, b);

		System.out.println("\n=========LOOKIN FOR BLACK LOOP===========");
		searchLoop(board, b);

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
			return "Loop and Tripod";
		}
	}
}