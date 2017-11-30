//package Networking;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Scanner;

public class UDPClient 
{
    DatagramSocket Socket;

    public UDPClient() 
    {

    }

    public void createAndListenSocket() 
    {
        try 
        {
            Socket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("150.243.209.93");
            byte[] incomingData = new byte[1024];           
            Scanner in = new Scanner(System.in);
            File imageFile = new File("jaiswal.jpeg");
            
            //while (true)
            
                System.out.println("Type your message to send to the server:\n");
                String sentence = in.nextLine();
                byte[] data = new byte[(int) imageFile.length()];
                FileInputStream fileInputStream = new FileInputStream(imageFile);
                fileInputStream.read(data);
                //byte[] data = imageFile.readAllBytes();
                		//sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
                Socket.send(sendPacket);
                System.out.println("Message sent from client");
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                Socket.receive(incomingPacket);
                String response = new String(incomingPacket.getData());
                System.out.println("Response from server:\n" + response);
                
            
        }
        catch (UnknownHostException e) 
        {
            e.printStackTrace();
        } 
        catch (SocketException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) 
    {
        UDPClient client = new UDPClient();
        client.createAndListenSocket();
    }
}

