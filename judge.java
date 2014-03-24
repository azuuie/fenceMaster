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
	public void searchLoop(gameBoard board, player x)
	{
		//Iterate through each row and find an 'entry point' that has not been used before
		//Reference two players as white and 
		//create some arrayLists that will be used as stacks to store coordinates
		ArrayList<piece> visited = new ArrayList<piece>();
		ArrayList<piece> stack = new ArrayList<piece>();
		ArrayList<piece> neighbours = new ArrayList<piece>();
		boolean atStart =  false;

		//Iterate through each position of gameboard
		for(int i=0; i<board.numRows; i++)
		{
			int jmax = (board.numRows - Math.abs(board.boardSize - 1 - i));
			for (int j=0; j<jmax; j++)
			{
				piece start = board.getPiece(i,j);
				atStart = false;
				piece curr;
				int count = 0;

				//Finds an entry point that hasn't been used in the current row and is of the same type as the class passed as an argument
				if(start.isSameType(x.type) && !visited.contains(start))
				{
					stack.add(0,start);
				}

				//This is where the backtracking algorithm starts
				while(!stack.isEmpty() && !atStart)
				{
					//pop element off stack and keep looking for a path
					curr = stack.remove(0);
					int jmaxStack = (board.numRows - Math.abs(board.boardSize - 1 - curr.i));
					
					System.out.println("\n------WE ARE AT POS: " + curr.i + "," + curr.j + "---------");
					System.out.println("\nLooking for neighbours...");
					//Check diagnals and up, down, left, right for next candidate
					neighbours = curr.checkForNeighbours(board, start, visited, stack, board.numRows, jmaxStack, count);

					for(int k=0; k<neighbours.size(); k++)
					{
						System.out.println("\nChecking each neighbour for loop");
						piece item = neighbours.get(k);
						System.out.println("item is: " + item.i + "," + item.j);

						//Check for a valid loop. The min max index check gurantees at least one white/space is surrounded.
						if(item == start)
						{
							atStart = true;
							x.loop = true;
							stack.clear();
							neighbours.clear();
							System.out.println("WE HAVE A LOOPER");
							return; //Force termination
						}

						else
						{
							System.out.println("\nAdding neighbour to stack");
							stack.add(0,item);
						}
					}

					//add current node to visited
					System.out.println("\nAdding current to visited");
					System.out.println("\n----------------------------------");
					visited.add(curr);
					neighbours.clear();
					count++;
				}
			}
		}
	}

	public void searchTripod(gameBoard board, player x)
	{
		//Iterate through each row and find an 'entry point' that has not been used before
		//Reference two players as white and 
		//create some arrayLists that will be used as stacks to store coordinates
		ArrayList<piece> visited = new ArrayList<piece>();
		ArrayList<piece> stack = new ArrayList<piece>();
		ArrayList<piece> neighbours = new ArrayList<piece>();

		//Iterate through each position of gameboard
		for(int i=0; i<board.numRows; i++)
		{
			int jmax = (board.numRows - Math.abs(board.boardSize - 1 - i));
			System.out.println(jmax);
			for (int j=0; j<jmax; j++)
			{
				piece start = board.getPiece(i,j);
				piece curr;

				//Finds an entry point that hasn't been used in the current row and is of the same type as the class passed as an argument
				if(start.isSameType(x.type) && !visited.contains(start))
				{
					stack.add(0, start);
				}

				//This is where the backtracking algorithm starts
				while(!stack.isEmpty() && (x.touchedEdges.size()<3))
				{
					//pop element off stack and keep looking for a valid path
					curr = stack.remove(0);
					int jmaxStack = (board.numRows - Math.abs(board.boardSize - 1 - curr.i));

					//Check diagnals and up, down, left, right for next candidate
					System.out.println("\n------WE ARE AT POS: " + curr.i + "," + curr.j + "---------");
					System.out.println("\nLooking for neighbours...");
					neighbours = curr.checkForNeighbours(board, visited, stack, board.numRows, jmaxStack);

					for(int k=0; k<neighbours.size(); k++)
					{
						System.out.println("\nChecking each neighbour");
						piece item = neighbours.get(k);
						String edge = classifyEdge(item, board.numRows, jmax);
						//Check if any are on a distinct side and add them if not already in the touchedEdges list
						if(!(x.touchedEdges.contains(edge)) && edge != "None")
						{
							System.out.println("\nAdding edges" + edge);
							x.touchedEdges.add(edge);
						}

						System.out.println("\nAdding neighbour to stack");
						stack.add(0,item);
					}
					
					if(x.touchedEdges.size() >= 3)
					{
						System.out.println("\nWe made a tripod!");
						stack.clear();
						neighbours.clear();
						x.tripod = true;
						System.out.println(x.tripod);
						System.out.println("WE HAVE A TRIPOD");
						return; //Force termination	
					}

					//add current node to visited
					System.out.println("\nAdding current to visited");
					System.out.println("\n----------------------------------");
					visited.add(curr);
					neighbours.clear();
				}
			}
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