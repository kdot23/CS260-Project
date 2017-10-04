import java.io.File;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
/*
 * This class partitions a given file into equal parts. The method CreatePartition creates a number of files
 * specified by the user of approximately equally sized files
 */
public class FilePartitioner 
{
	private String fileName; 	//name of original file
	private double oldSize;		//size of original file
	private static Scanner input;	//reads input from original file
	private Formatter output;		//writes to the new files
	
	/*
	 * Constructor for FilePartitioner 
	 * @pararm fileName is the name of the file the user wishes to partition
	 */
	public FilePartitioner(String fileName)
	{
		this.fileName = fileName;
		oldSize = new File(fileName).length();		
	}
	
	/*
	 * creates a user-specified-number of approximately equally sized files from the original file
	 *@param numParts number of partitions the user wishes to divide the orignal file into
	 */

	public void createPartition (int numParts)
	{
		int newSize = (int) oldSize / numParts; //size new files should be
		openOldFile();
		for (int i = 1; i < numParts + 1; i++)
		{
			openNewFile(i);
			addNewData(newSize);
			closeNewFile();
		}
		closeOldFile();
		
	}
/*
 * Opens the scanner to read the original file
 */
	public void openOldFile()
	{
		try
		{
			input = new Scanner(Paths.get(fileName));
		}
		catch (FileNotFoundException exception)
		{
			System.err.println("Error opening file. Terminating.");
			System.exit(1);
		}
		catch (IOException exception)
		{
			System.err.println("Error opening file. Terminating.");
			System.exit(1);
		}
		input.useDelimiter(""); //input.next() normally returns the whole string until whitespace
								//this makes input.next() return just 1 character
	}
	
	public void closeOldFile()
	{
		input.close();
	}
	
	/*
	 * creates a Formatter to write to the new file
	 * @param int p the partition identifier
	 */
	public void openNewFile(int p)
	{
			//use a substring to remove the ".txt" from the original fileName
			String newFileName = fileName.substring(0, fileName.length()-4) + "part" + p + ".txt";
			try
			{
				output = new Formatter(newFileName);
			}
			
			catch (FileNotFoundException fileNotFoundException)
			{
				System.err.println("Error opening file. Terminating.");
				System.exit(1);
			} 
	}
	
	/*
	 * Writes data to the file.
	 * @param int newFileSize is the desired size of the new files (ie: old size/number of parts)
	 */
	public void addNewData(int newFileSize)
	{
			for (int i = 0; i < newFileSize; i++)
			{
				try
				{
					output.format("%s",input.next());
				}
				catch (FormatterClosedException formatterClosedException)
				{
					System.err.println("Error writing to file. Terminating.");
					break;
				}
				catch (NoSuchElementException noSuchElementException)
				{
					System.err.println("Invalid input. Please try again.");
				}
			}			
	}

	public void closeNewFile()
	{
		if (output != null)
			output.close();
	}
}
