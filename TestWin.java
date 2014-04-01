/* 
	Daniel Esposito, desposito, 358064
	Tim Chan, Timyc
*/

package desposito.fenceMaster;

import java.util.Scanner;

public class TestWin
{
	public static void main(String[] args)
	{
		char[] charArray = null;
		int boardSize = 0;
		char charBoard[][] = null;
		
		//Read file line by line and store into a multi-dimensional array. This will a parameter into the main algorithm.
		Scanner fileScanner = new Scanner(System.in);
		String line = "";
		int index = 0;

		if(fileScanner.hasNextLine())
		{
			line = fileScanner.nextLine();
			boardSize = Integer.parseInt(line);
			charBoard = new char[2*boardSize-1][];
		}

		while(fileScanner.hasNextLine())
		{
			line = fileScanner.nextLine();
			line = line.trim();
			line = line.replaceAll("\\s+","");
			charArray = line.toCharArray();
			charBoard[index] = charArray;
			index++;
		} fileScanner.close();

		//Setup the game board using objects and remove reference to the charBoard
		//Call function whoWon(GameBoard) which returns a character (B - black, W - white, D - draw or N - none);
		Player white = new Player('W');
		Player black = new Player('B');
		
		//Create the board as an object with object pieces
		GameBoard newBoard = new GameBoard(boardSize);
		newBoard.initBoard(charBoard, black, white);

		//Initialise each players hash map of pieces
		black.createHashMap(newBoard);
		white.createHashMap(newBoard);

		//Create the Judge object to make 
		Judge Judge = new Judge();
		
		//newBoard.printBoard();
		char winner = Judge.whoWon(newBoard, white, black);

		switch(winner)
		{
			case 'W':
				System.out.println("White");
				System.out.println(Judge.winType(white));
				break;

			case 'B':
				System.out.println("Black");
				System.out.println(Judge.winType(black));
				break;

			case 'D':
				System.out.println("Draw");
				System.out.println("Nil");
				break;

			case 'N':
				System.out.println("Draw");
				System.out.println("Nil");
				break;
		}
	}
}
