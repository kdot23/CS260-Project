import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class UDPServer 
{
    DatagramSocket socket = null;

    public UDPServer() 
    {

    }
    public void createAndListenSocket() 
    {
        try 
        {
            socket = new DatagramSocket(9876);
            byte[] incomingData = new byte[1024];
            //Scanner in = new Scanner(System.in);

            while (true) 
            {
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, 
                		incomingData.length);
                socket.receive(incomingPacket);
                InputStream in = new ByteArrayInputStream(incomingPacket.getData());
                BufferedImage bImageFromConvert = ImageIO.read(in);
                ImageIO.write(bImageFromConvert, "jpg", new File(
    					"new-darksouls.jpg"));
                //String message = new String(incomingPacket.getData());
                InetAddress IPAddress = incomingPacket.getAddress();
                int port = incomingPacket.getPort();
                
               // System.out.println("Received message from client: " + message);
                System.out.println("Client IP:"+IPAddress.getHostAddress());
                System.out.println("Client port:"+port);
                
                System.out.println("Type your message to send to the client:");               
                //String reply = in.nextLine();
                //byte[] data = reply.getBytes();
                
                //DatagramPacket replyPacket =
                        //new DatagramPacket(data, data.length, IPAddress, port);
                
                //socket.send(replyPacket);
                System.out.println("Message sent by server");
                
                Thread.sleep(2000);
                
            }
            //in.close();
            //socket.close();
        } 
        catch (SocketException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException i) 
        {
            i.printStackTrace();
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) 
    {
        UDPServer server = new UDPServer();
        server.createAndListenSocket();
    }
}

