import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

public class Merger 
{
	
	//takes args of old file name stem and new file name
	public static void main(String[] args)
	{
		File newFile = new File(args[1]);
		if (newFile.exists()) {
		     newFile.delete(); //you might want to check if delete was successfull
		     }
		try
		{
		 newFile.createNewFile();
		}
		catch (IOException e)
		{
			System.out.println("Error creating empty file");
		}
		 		
		ExecutorService executorService = Executors.newCachedThreadPool();		
		
		for (int i = 1; i <= 5; i++)
		{
			String oldFileName = args[0] + "part" + i + ".txt";
			File oldFile = new File(oldFileName);
			executorService.execute(new MergeTask(oldFile, newFile));	
		}
		
		executorService.shutdown();
		try
		{
			executorService.awaitTermination(1, TimeUnit.MINUTES);
		}
		catch (Exception e)
		{
			System.out.println("Await Termination error");
		}
		
		
	} 

}
