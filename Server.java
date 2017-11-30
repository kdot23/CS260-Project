import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server 
{
	public static final String MAIN_FILE_NAME = "myFile100.txt";
	public static final int NUM_PARTS = 5;
	
	public static void main(String args[])
	{
		FilePartitioner filePartitioner = new FilePartitioner(MAIN_FILE_NAME);
		filePartitioner.createPartition(NUM_PARTS);
		
		Server server = new Server();
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for (int i = 1; i <= NUM_PARTS; i++)
		{			
			Runnable task = server.new ServerTask(MAIN_FILE_NAME, i);
			executor.execute(task);;			
		}
		executor.shutdown();
		
//		for (int i = 1; i <= NUM_PARTS; i++)
//		{
//			Runnable task = server.new ServerTask(MAIN_FILE_NAME, i);
//			task.run();			
//		}
	}
	
	public class ServerTask implements Runnable
	{
		private int part;
		private File newFilePart;
			
		public ServerTask(String mainFileName, int part)
		{
			//oldFile = new File(oldFileName);
			String newFilePartName = mainFileName.substring(0,mainFileName.length()-4) + "part" + part + ".txt";
			newFilePart = new File(newFilePartName);
			this.part = part;
		}
		public void run()
		{
			//acceptConnection();
			try
			{
				int port_num = 5000 + part;
				System.out.println(port_num);

				ServerSocket serverSocket = new ServerSocket(port_num);
				Socket socket = serverSocket.accept();
				//InputStream inStream = socket.getInputStream();
				OutputStream outStream = socket.getOutputStream();
				FileInputStream fis = new FileInputStream(newFilePart);
	            BufferedInputStream bis = new BufferedInputStream(fis); 
	            
	            byte[] contents;
	            long fileLength = newFilePart.length(); 
	            long current = 0;
	            
	            while(current!= fileLength)
	            { 
	                int size = 10000;
	                if(fileLength - current >= size)
	                    current += size;    
	                else
	                { 
	                    size = (int) (fileLength - current); 
	                    current = fileLength;
	                } 
	                contents = new byte[size]; 
	                bis.read(contents, 0, size); 
	                outStream.write(contents);
	                
	            }
	            outStream.flush(); 
	            bis.close();
	            socket.close();
	            serverSocket.close();
	            System.out.println("File " + part + " sent succesfully!");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
									
		}
		
	}
}
