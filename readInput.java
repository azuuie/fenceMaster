import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadStringFromFileLineByLine {

	public static void read(File file) 
	{
		try {
			
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			System.out.println("Contents of file:");
			System.out.println(stringBuffer.toString());
		} 

		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
 
public class ScannerReadFile {
 
    public static void main(String[] args) {
 
        // Location of file to read
        File file = new File("data.txt");
 
        try 
        {
            Scanner scanner = new Scanner(file);
 
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
            scanner.close();
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
 
    }
}

            // FileReader fr = new FileReader(infile); 
            // BufferedReader br = new BufferedReader(fr);

            // String line;
            // char[] charArray;
            // //Extract board size from first line and create a game board with the an appropriate amount of rows
            // line = br.readLine();
            // int boardSize = Integer.parseInt(line);
            // char charBoard[][] = new char[2*boardSize-1][];

            // //Go through each row of the given board, take input as a string, remove all white space, convert to charArray and store in the corresponding row
            // for(int i=0; i<2*boardSize-1; i++) 
            // {
            //  line = br.readLine();
            //  line = line.trim();
            //  line = line.replaceAll("\\s+","");
            //  charArray = line.toCharArray();
            //  charBoard[i] = charArray;
            // } 

            // fr.close();
            // br.close();

public char[][] readFile(File file)
{
	String line = "";
	char[] charArray = null;
	int boardSize = 0;
	char charBoard[][] = null;
	int index = 0;
}