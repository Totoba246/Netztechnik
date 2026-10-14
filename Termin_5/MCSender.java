package Termin_5;


import java.net.*;
import java.io.*;
import java.util.*;

public class MCSender {
    private static final String HOST_IP_ADDRESS = "127.0.0.1";
    private static final String MULTICAST_IP_ADDRESS = "224.0.100.0";
    private static final int MULTICAST_PORT = 55000;
    private static final int MAXPACKETSIZE = 1024;

    public static void main(String[] args) {
        try{

            InetAddress groupADDR = InetAddress.getByName(MULTICAST_IP_ADDRESS);
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getByName(HOST_IP_ADDRESS));

            MulticastSocket socket = new MulticastSocket(MULTICAST_PORT);
            socket.setNetworkInterface(networkInterface);

           
            socket.joinGroup(groupADDR);
            System.out.println("Multicast-Group joined");


            String msg;
            Scanner sc = new Scanner(System.in);

            while(true){
                System.out.print("Enter message to send (or 'exit' to quit): ");
                msg= sc.nextLine();
                if(msg.trim().toLowerCase().equals("exit")){
                    break;
                }
                byte[] buffer = msg.getBytes("UTF-8");
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, groupADDR, MULTICAST_PORT);
                socket.send(packet);
            }

            socket.leaveGroup(groupADDR);
            System.out.println("Multicast-Group left");
            socket.close();



        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    
}
