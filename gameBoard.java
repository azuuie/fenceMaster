import java.lang.Math.*;
import java.util.*;

public class gameBoard 
{
	public int numPieces;
	public int maxPieces;
	public int boardSize;
	public int numRows;
	public List<List<piece>> board;

	public gameBoard(int boardSize)
	{
		this.numPieces = 0;
		this.maxPieces = 9*boardSize + 16;
		this.boardSize = boardSize;
		this.numRows = 2*boardSize-1;
		this.board = new List<List<piece>>();
	}

	// This function will take the ascii board and create a piece object for each cell. The board structure is a 2D List<> object.
	public void initBoard(char[][] charBoard, player b, player w)
	{
		for(int i=0; i<numRows; i++)
		{
			int jmax = (numRows - Math.abs(boardSize - 1 -i));
			List<piece> rowPieces = new List<piece>();

			for (int j=0; j<jmax; j++)
			{
				if(charBoard[i][j] == 'W')
				{
					piece newWhite = new piece(i,j,'W');
					rowPieces.add(newWhite);
					numPieces++;
				}

				else if (charBoard[i][j] == 'B')
				{
					piece newBlack = new piece(i,j,'B');
					rowPieces.add(newBlack);
					numPieces++;
				}

				else
				{
					piece empty = new piece(i,j,'-');
					rowPieces.add(empty);
				}
			}

			board.add(rowPieces);
		}
	}

	public char getPositionType(int i, int j)
	{
		return board.get(i).get(j).type;
	}


	public piece getPiece(int i, int j)
	{
		return board.get(i).get(j);
	}

	public boolean setPosition(int i, int j, piece piece)
	{
		if( numPieces <= maxPieces && isEmpty(i,j))
		{
			board.get(i).add(j, piece);
			return true;
		}

		else
		{
			return false;
		}
	}

	public boolean isEmpty(int i, int j)
	{
		if(getPositionType(i, j) == '-')
		{
			return true;
		}

		else
		{
			return false;
		}
	}

	public void clearBoard()
	{
		board.clear();
	}

	public void printBoard()
	{
		for(int i=0; i<numRows; i++)
		{
			System.out.println("");
			for (int j=0; j<(numRows - Math.abs(boardSize - 1 - i)); j++)
			{
				System.out.print(getPositionType(i,j));
			}
		}
	}
}