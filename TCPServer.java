
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer 
{
	public static void main(String[] args) throws Exception 
	{
        //Accept socket
        ServerSocket serverSocket = new ServerSocket(5000);
        Socket socket = serverSocket.accept();
        InputStream inStream = socket.getInputStream();
        OutputStream outStream = socket.getOutputStream();
        
        //Read in fileName from client
        byte[] readBuffer = new byte[200];
        int num = inStream.read(readBuffer);
        if (num > 0) 
        {
        	byte[] arrayBytes = new byte[num];
            System.arraycopy(readBuffer, 0, arrayBytes, 0, num);
            String fileName = new String(arrayBytes, "UTF-8");
            
            //Write to file from output stream
            File file = new File(fileName);            
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis); 
            
            byte[] contents;
            long fileLength = file.length(); 
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
                System.out.print("Sending file ... "+(current*100)/fileLength+"% complete!");
            }   
            
            outStream.flush(); 
            bis.close();
            socket.close();
            serverSocket.close();
            System.out.println("File sent succesfully!");
        }
   }
}
