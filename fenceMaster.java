import java.io.*;

public class fenceMaster
{
	public static void main(String[] args) throws IOException 
	{

		//Check command line parameter is valid
		if(args.length != 1) 
		{
			System.err.println("Invalid command line: exactly one input file required as an argument");
			System.exit(1);
		}

		//Read file line by line and store into a multidimentional array. This will a parameter into the main algorithm.
		String infile = "./" + args[0];
		FileReader fr = new FileReader(infile); 
		BufferedReader br = new BufferedReader(fr);

		String line;
		char[] charArray;
		//Extract board size from first line and create a game board with the an appropriate amount of rows
		line = br.readLine();
		int boardSize = Integer.parseInt(line);
		char charBoard[][] = new char[2*boardSize-1][];

		//Go through each row of the given board, take input as a string, remove all white space, convert to charArray and store in the corresponding row
		for(int i=0; i<2*boardSize-1; i++) 
		{
			line = br.readLine();
    		line = line.trim();
    		line = line.replaceAll("\\s+","");
    		charArray = line.toCharArray();
    		charBoard[i] = charArray;
		} 

		fr.close();
		br.close();

		//Setup the game board using objects and remove reference to the charBoard
		//Call function whoWon(gameBoard) which returns a character (B - black, W - white, D - draw or N - none);
		player white = new player('W');
		player black = new player('B');
		gameBoard newBoard = new gameBoard(boardSize);
		newBoard.initBoard(charBoard);
		judge judge = new judge();
		newBoard.printBoard();
		char winner = judge.whoWon(newBoard, white, black);

		switch(winner)
		{
			case 'W':
				System.out.println("\n\nWhite is the winner! However, to avoid being labelled as 'racist', Black recieves an honourable mention.");
				System.out.println("White made a " + judge.winType(white));
				break;

			case 'B':
				System.out.println("\n\nBlack is the winner! White wins nothing! Lel.");
				System.out.println("Black made a " + judge.winType(black));
				break;

			case 'D':
				System.out.println("\n\nIt's a draw! Everyone loves draws... said no one ever.");
				System.out.println("White made a " + judge.winType(white));
				System.out.println("Black made a " + judge.winType(black));
				break;

			case 'N':
				System.out.println("\n\nNobody wins! What a shameful display this game has been...");
				break;
		}
 	}
}