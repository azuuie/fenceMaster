package desposito.fenceMaster;

import java.util.*;

public class GameBoard 
{
	public int numPieces;
	public int maxPieces;
	public int boardSize;
	public int numRows;
	public ArrayList<ArrayList<Piece>> board;

	public GameBoard(int boardSize)
	{
		this.numPieces = 0;
		this.maxPieces = 9*boardSize + 16;
		this.boardSize = boardSize;
		this.numRows = 2*boardSize-1;
		this.board = new ArrayList<ArrayList<Piece>>();
	}

	// This function will take the ascii board and create a Piece object for each cell. The board structure is a 2D ArrayList<> object.
	public void initBoard(char[][] charBoard, Player b, Player w)
	{
		for(int i=0; i<numRows; i++)
		{
			int jmax = (numRows - Math.abs(boardSize - 1 -i));
			ArrayList<Piece> rowPieces = new ArrayList<Piece>();

			for (int j=0; j<jmax; j++)
			{
				if(charBoard[i][j] == 'W')
				{
					Piece newWhite = new Piece(i,j,'W');
					rowPieces.add(newWhite);
					numPieces++;
				}

				else if (charBoard[i][j] == 'B')
				{
					Piece newBlack = new Piece(i,j,'B');
					rowPieces.add(newBlack);
					numPieces++;
				}

				else
				{
					Piece empty = new Piece(i,j,'-');
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


	public Piece getPiece(int i, int j)
	{
		return board.get(i).get(j);
	}

	public boolean setPosition(int i, int j, Piece Piece)
	{
		if( numPieces <= maxPieces && isEmpty(i,j))
		{
			board.get(i).add(j, Piece);
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

	public ArrayList<Piece> getRow(int row)
	{
		return board.get(row);
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