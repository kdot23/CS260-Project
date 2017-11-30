import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public class MergeTask implements Runnable 
{	
	private File oldFile;
	private File newFile;
	private static FileInputStream input; //reads input from original file
	private FileOutputStream output; //writes output to new file
	
	private static final int MAX_INTEGER = 429496729; //max size of integer in java (if int is stored as 1 byte)
	
	public MergeTask(File oldFile, File newFile)
	{
		this.oldFile = oldFile;
		this.newFile = newFile;		
	}
	
	//reads data from the specified old file and appends it to the new file
	public void run() 
	{
		openOldFile(oldFile);
		long oldSize = oldFile.length();
		
		openNewFile(newFile);
		
		addNewData(oldSize);
				
		closeNewFile();
		closeOldFile();		
	}
	
	/*
	 * Opens the FileInputReader to read the original file
	 */
		public void openOldFile(File file)
		{
			try
			{
				input = new FileInputStream(file);
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
				System.err.println("Error closing input file. Terminating.");
				System.exit(1);
			}
		}
		
		/*
		 * Creates a FileOutputStream to write to the new file
		 * @param File is the newFile where the merged data will go
		 */
		public void openNewFile(File newFile)
		{
				try
				{
					output = new FileOutputStream(newFile, true);
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
				System.err.println("Error adding data to file. Terminating." + oldFile.getName());
				System.exit(1);
			}
		}
	
	public void addNewData(long oldFileSize)
	{
		int size;
		while (oldFileSize > 0)
		{
			if (oldFileSize > MAX_INTEGER) //if the oldSize can't be properly parsed to an int
			{
				size = MAX_INTEGER;  //add max number of data to file, and then repeat loop				
			}
			else
			{
				size = (int) oldFileSize;
			}
			
			oldFileSize = oldFileSize - MAX_INTEGER;
		
			try
			{
				byte[] dataArray = new byte[size];
				input.read(dataArray, 0, size); //fills the dataArray with buffered characters
												//starts at the 0 position and adds oldFileSize number of bytes
				output.write(dataArray); //writes the contents of dataArray to output (FileOutputStream)
			}
			
			catch (IOException exception)
			{
				System.err.println("Error adding data to file. Terminating.");
				System.exit(1);
			}
		}
			
	}
	
	

}
