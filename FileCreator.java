import java.io.FileNotFoundException;            
import java.util.Formatter;               
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;  

/*
 * Creates files of specified sizes by opening, adding data to and closing files
 */
public class FileCreator 
{
	private String size; //size of file to create
	
	private static Formatter output; // outputs text to a file 
	private static final int STRING_SIZE = 1000;
	private static final int BYTES_PER_MEGABYTE = 1000000;
	

	public FileCreator(String size)
	{
		this.size = size;
	}
	       
	public void openFile()
		{
			String fileName = "myFile" + this.size + ".txt";
			try
			{
				output = new Formatter(fileName);
			}
			catch (FileNotFoundException fileNotFoundException)
			{
				System.err.println("Error opening file. Terminating.");
				System.exit(1);
			}			
		}
	
	public void addData()
	{
		double size = Double.parseDouble(this.size);
		String data = "";
		for (int i = 0; i < STRING_SIZE; i++)
		{
			data = data + "a";
		}
		
		for (int i = 0; i < size*BYTES_PER_MEGABYTE / (STRING_SIZE); i++)
		{
			try
			{
				output.format("%s",data);
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
	
	public void closeFile()
	{
		if (output != null)
			output.close();

	}
	
}
