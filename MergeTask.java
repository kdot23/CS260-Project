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
	synchronized public void run() 
	{
		openOldFile(oldFile);
		long oldSize = oldFile.length();
		
		openNewFile(newFile);
		
		while (oldSize > 0)
		{
			if (oldSize > MAX_INTEGER) //if the oldSize can't be properly parsed to an int
			{
				addNewData(MAX_INTEGER); //add max number of data to file, and then repeat loop
			}
			else
			{
				addNewData((int) oldSize);
			}
			oldSize = oldSize - MAX_INTEGER;
		}
		
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
				System.err.println("Error opening file. Terminating.");
				System.exit(1);
			}
		}
		
		/*
		 * Creates a FileOutputStream to write to the new file
		 * @param int p the partition identifier
		 */
		public void openNewFile(File file)
		{
				//use a substring to remove the ".txt" from the original fileName
				//String newFileName = oldFileName.substring(0, oldFileName.length()-4) + "part" + p + ".txt";
				try
				{
					output = new FileOutputStream(file, true);
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
	
	public void addNewData(int oldFileSize)
	{
		try
		{
			byte[] dataArray = new byte[oldFileSize];
			input.read(dataArray, 0, oldFileSize); //fills the dataArray with buffered characters
												//starts at the 0 position and adds oldFileSize number of bytes
			output.write(dataArray); //writes the contents of dataArray to output (FileOutputStream)
			System.out.println("Writing data from " + oldFile.getName());
		}
			
		catch (IOException exception)
		{
			System.err.println("Error adding data to file. Terminating.");
			System.exit(1);
		}
			
	}

}
