import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;

public class Merger 
{
	
	//takes args of old file name stem and new file name
	public static void main(String[] args)
	{
		File newFile = new File(args[1]);
		if (newFile.exists()) 
		{
		     newFile.delete(); 
		}
		try
		{
			newFile.createNewFile();
		}
		catch (IOException e)
		{
			System.out.println("Error creating empty file");
		}
		//singleThreadExecutor is used so that threads are executed in order	
		ExecutorService executorService = Executors.newSingleThreadExecutor();		
		
		for (int i = 1; i <= 5; i++)
		{
			String oldFileName = args[0] + "part" + i + ".txt";
			File oldFile = new File(oldFileName);
			Runnable task = new MergeTask(oldFile, newFile);
			executorService.submit(task);
		}
		
		executorService.shutdown();		
	} 

}
