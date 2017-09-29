import java.io.FileNotFoundException;            
import java.util.Formatter;               
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;  

/*
 * Creates files of sizes specified in the arguments (note: size is in megabytes
 * and 1G = 1000 MB)
 */
public class FileCreator
{
	private static Formatter output; // outputs text to a file 
	private static final int STRING_SIZE = 1000;
	private static final int BYTES_PER_MEGABYTE = 1000000;
	
	public static void main(String[] args)
	{
		for (int i = 0; i < args.length; i++)
		{
			openFile(args[i]);
			addData(args[i]);
			closeFile();
		}
	}
	       
	public static void openFile(String argName)
		{
			String fileName = "myFile" + argName + ".txt";
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
	
	public static void addData(String argSize)
	{
		double size = Double.parseDouble(argSize);
		System.out.println(size);
		String data = "";
		for (int i = 0; i < STRING_SIZE; i++)
		{
			data = data + "a";
		}
		
		for (int i = 0; i < size*BYTES_PER_MEGABYTE / (STRING_SIZE); i ++)
		{
			System.out.println("ran" + i);
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
	
	public static void closeFile()
	{
		if (output != null)
			output.close();

	}
	
}