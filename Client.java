import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client 
{
	public static final String MAIN_FILE_NAME = "myFile100.txt";
	public static final double NUM_PARTS = 5;
	
	public static void main(String args[])
	{
		Client client = new Client();
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for (int i = 1; i <= NUM_PARTS; i++)
		{			
			Runnable task = client.new ClientTask(MAIN_FILE_NAME, i);
			executor.execute(task);;			
		}
		executor.shutdown();
		try
		{
			executor.awaitTermination(5, TimeUnit.MINUTES);
		
			File newFile = new File("new" + MAIN_FILE_NAME);
		
			if (newFile.exists()) 
			{
				newFile.delete(); 
			}
			
			newFile.createNewFile();
		
			//singleThreadExecutor is used so that threads are executed in order	
			ExecutorService executorService = Executors.newSingleThreadExecutor();		
				
			for (int i = 1; i <= 5; i++)
			{
				String oldFileName = "new" + MAIN_FILE_NAME.substring(0, MAIN_FILE_NAME.length() -4) + "part" + i + ".txt";
				File oldFile = new File(oldFileName);			
				Runnable task = new MergeTask(oldFile, newFile);
				executorService.submit(task);
			}
				
				executorService.shutdown();	
		}
		
		catch (IOException e)
		{
			System.out.println("Error creating empty file");
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public class ClientTask implements Runnable
	{
		private int part;
		private String oldFileName;
		
		public ClientTask(String oldFileName, int part)
		{
			this.part = part;
			this.oldFileName = oldFileName;
		}
		public void run()
		{
			try
			{
				//Create socket
				int port_num = 5000 + part;
				System.out.println(port_num);
				Socket socket = new Socket("localhost", port_num);
				InputStream inStream = socket.getInputStream();
			
				//Read in file				
				File newFile = new File("new" + oldFileName.substring(0, oldFileName.length()-4) + "part" + part + ".txt");
				if (newFile.exists()) 
				{
				     newFile.delete(); 
				}
				try
				{
					newFile.createNewFile();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				FileOutputStream fos = new FileOutputStream(newFile,true);
		        BufferedOutputStream bos = new BufferedOutputStream(fos);
		        int bytesRead = 0; 
		        byte[] contents = new byte[10000];
		        
		        while((bytesRead=inStream.read(contents))!=-1)
		            bos.write(contents, 0, bytesRead); 
		        
		        bos.flush(); 
		        bos.close();
		        socket.close(); 
		        
		        System.out.println("Client " + part + " finished");
				
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
