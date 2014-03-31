import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class fenceMaster
{
	public static void main(String[] args)
	{

		char[] charArray = null;
		int boardSize = 0;
		char charBoard[][] = null;
		
		//Check command line parameter is valid
		if(args.length != 1) 
		{
			System.err.println("Invalid command line: exactly one input file required as an argument");
			System.exit(1);
		}

		//Read file line by line and store into a multidimentional array. This will a parameter into the main algorithm.
		String infile = "./" + args[0];
		try
		{
			String line = "";
			int index = 0;
			File file = new File(infile);
			Scanner scan = new Scanner(file);

			if(scan.hasNextLine())
			{
				line = scan.nextLine();
				boardSize = Integer.parseInt(line);
				charBoard = new char[2*boardSize-1][];
			}

			while(scan.hasNextLine())
			{
				line = scan.nextLine();
				line = line.trim();
				line = line.replaceAll("\\s+","");
				charArray = line.toCharArray();
				charBoard[index] = charArray;
				index++;
			} scan.close();
		}

		catch(IOException e) 
		{
			System.out.println("\nFile not found or incomplete board!");
			System.out.println("Make sure file is in the root of the compile directory and is correcly formatted");
			e.printStackTrace();
			System.exit(1);
		}

		//Setup the game board using objects and remove reference to the charBoard
		//Call function whoWon(gameBoard) which returns a character (B - black, W - white, D - draw or N - none);
		player white = new player('W');
		player black = new player('B');
		
		//Create the board as an obeject with object pieces
		gameBoard newBoard = new gameBoard(boardSize);
		newBoard.initBoard(charBoard, black, white);

		//Initialise each players hash map of pieces
		black.createHashMap(newBoard);
		white.createHashMap(newBoard);

		//Create the judge object to make 
		judge judge = new judge();
		//newBoard.printBoard();
		char winner = judge.whoWon(newBoard, white, black);

		switch(winner)
		{
			case 'W':
				System.out.println("White");
				System.out.println(judge.winType(white));
				break;

			case 'B':
				System.out.println("Black");
				System.out.println(judge.winType(black));
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