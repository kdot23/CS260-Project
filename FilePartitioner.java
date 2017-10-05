import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
/*
 * This class partitions a given file into equal parts. The method CreatePartition creates a 
 * number of files specified by the user of approximately equally sized files
 */
public class FilePartitioner 
{
	private File oldFile; 	//original file
	private String oldFileName; //name of original file
	private double oldSize;		//size of original file
	//private static Scanner input;	//reads input from original file
	private static FileInputStream input;
	//private Formatter output;		//writes to the new files
	private FileOutputStream output;
	private static int MAX_INTEGER = 429496729;
	
	/*
	 * Constructor for FilePartitioner 
	 * @pararm fileName is the name of the file the user wishes to partition
	 */
	public FilePartitioner(String fileName)
	{
		oldFile = new File(fileName);
		oldFileName = fileName;
		oldSize = oldFile.length();		
	}
	
	/*
	 * creates a user-specified-number of approximately equally sized files from the original file
	 *@param numParts number of partitions the user wishes to divide the original file into
	 */

	public void createPartition (int numParts)
	{
		openOldFile();
						
		for (int i = 1; i < numParts + 1; i++)
		{
			double newSize = oldSize / numParts;
			openNewFile(i);
			
			while (newSize > 0)
			{
				if (newSize > MAX_INTEGER) //if the newSize can't be properly parsed to an int
				{
					addNewData(MAX_INTEGER); //add max number of data to file, and then repeat loop
				}
				else
				{
					addNewData((int) newSize);
				}
				newSize = newSize - MAX_INTEGER;
			}
			
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
			input = new FileInputStream(oldFile);
		}
		catch (FileNotFoundException exception)
		{
			System.err.println("Error opening file. Terminating.");
			System.exit(1);
		}
	}
	
	public void closeOldFile()
	{
		try
		{
			input.close();
		}
		catch (IOException exception)
		{
			System.err.println("Error opening file. Terminating.");
			System.exit(1);
		}
	}
	
	/*
	 * creates a Formatter to write to the new file
	 * @param int p the partition identifier
	 */
	public void openNewFile(int p)
	{
			//use a substring to remove the ".txt" from the original fileName
			String newFileName = oldFileName.substring(0, oldFileName.length()-4) + "part" + p + ".txt";
			try
			{
				output = new FileOutputStream(newFileName);
			}
			
			catch (FileNotFoundException fileNotFoundException)
			{
				System.err.println("Error opening file. Terminating.");
				System.exit(1);
			} 
	}
	
	public void closeNewFile()
	{
		try
		{
			if (output != null)
				output.close();
		}
		catch (IOException exception)
		{
			System.err.println("Error adding data to file. Terminating.");
			System.exit(1);
		}
	}
	
	/*
	 * Writes data to the file.
	 * @param int newFileSize is the desired size of the new files (ie: old size/number of parts)
	 */
	public void addNewData(int newFileSize)
	{
		try
		{
			byte[] dataArray = new byte[newFileSize];
			input.read(dataArray, 0, newFileSize); //fills the dataArray with buffered characters
													//starts at the 0 position and adds newFileSize number of bytes
			output.write(dataArray); //writes the contents of dataArray to output (FileOutputStream)
		}
			
		catch (IOException exception)
		{
			System.err.println("Error adding data to file. Terminating.");
			System.exit(1);
		}
			
	}
}
