
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient 
{
public static void main(String[] args) throws Exception{
        
        //Create socket
        Socket socket = new Socket("localhost", 5000);
        InputStream inStream = socket.getInputStream();
        OutputStream outStream = socket.getOutputStream();
        
        //Send file name
        Scanner in = new Scanner(System.in);
        System.out.println("Type the name of the file you want server to send:");
        String fileName = in.nextLine();
        in.close();
        outStream.write(fileName.getBytes("UTF-8"));
        
        //Read in file
        FileOutputStream fos = new FileOutputStream("newFile.txt");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        int bytesRead = 0; 
        byte[] contents = new byte[10000];
        
        while((bytesRead=inStream.read(contents))!=-1)
            bos.write(contents, 0, bytesRead); 
        
        bos.flush(); 
        bos.close();
        socket.close(); 
        
        System.out.println("Client finished");
    }
}
