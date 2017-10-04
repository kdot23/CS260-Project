import java.io.File;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FilePartitioner {
	private int numParts;
	private String fileName;
	private Scanner input;
	private Formatter output;
	private double oldSize;
	private File file;
	
	public FilePartitioner(String fileName, int numParts)
	{
		this.numParts = numParts;
		this.fileName = fileName;
		file = new File(fileName);
		oldSize = file.length();
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
		input.useDelimiter("");
		
		for (int i = 1; i < numParts + 1; i++)
		{
			createPartition(i);
		}
		
		input.close();
				
	}
	
	public void createPartition(int p)
	{
		openNewFile(p);
		addNewData();
		closeNewFile();
	}
	public void openNewFile(int p)
	{
			String newFileName = fileName + "part" + p + ".txt";
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
	
	public void addNewData()
	{
			for (int i = 0; i <  oldSize/ numParts; i++)
			{
				//System.out.println(input.nextByte());
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
