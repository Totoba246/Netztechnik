package Termin_1;
import java.net.*;

public class Echo_Server {

    final static int SERVERPORT = 50000; 
    final static int CLIENTPORT = 60000;
    public static void main(String[] args) {
        try {
            final InetAddress CLIENTIPADDR = InetAddress.getByName("localhost");

            DatagramSocket serSock = new DatagramSocket(SERVERPORT);
            System.out.println("Server Port: "+ serSock.getLocalPort() +  "\n");

            byte[] bufferIn = new byte[1024];
            DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length);

            serSock.receive(packetIn);

            String msgReceived = new String(packetIn.getData(),0, packetIn.getLength(),"UTF-8");
            System.out.println("Received: " + msgReceived + "\nLänge" + msgReceived.length() + "\n");

            byte[] bufferOut = msgReceived.getBytes("UTF-8");

            DatagramPacket packetOut = new DatagramPacket(bufferOut, bufferOut.length, CLIENTIPADDR, CLIENTPORT);
            serSock.send(packetOut);
            System.out.println("Sende: " + msgReceived);

            serSock.close();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }
    
}
