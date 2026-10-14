package Termin_5;

import java.net.*;
import java.io.*;

/*UDP Broadcast
InetAddress ip = InetAddress.getByName("255.255.255.255");
…(Sonst alles wie immer)
sock.setBroadcast(true);*/

public class MCReceiver {
    private static final String HOST_IP_ADDRESS = "127.0.0.1";
    private static final String MULTICAST_IP_ADDRESS = "224.0.100.0";
    private static final int MULTICAST_PORT = 55000;
    private static final int MAXPACKETSIZE = 1024;

    public static void main(String[] args) {
        try{
            
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getByName(HOST_IP_ADDRESS));

            MulticastSocket socket = new MulticastSocket(MULTICAST_PORT);
            socket.setNetworkInterface(networkInterface);

            InetAddress groupADDR = InetAddress.getByName(MULTICAST_IP_ADDRESS);
            socket.joinGroup(groupADDR);
            System.out.println("Multicast-Group joined");

            for(int i = 0; i< 5; i++){
                byte[] buffer = new byte[MAXPACKETSIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String msgReceived = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
                System.out.println("Received: " + msgReceived);
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
