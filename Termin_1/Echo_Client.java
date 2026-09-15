package Termin_1;
import java.net.*;


public class Echo_Client {
    final static int SERVERPORT = 50000;
    final static int CLIENTPORT = 60000;

    public static void main(String[] args) {
        try {
            final InetAddress SERVERIPADDR = InetAddress.getByName("localhost");

            DatagramSocket cliSock = new DatagramSocket(CLIENTPORT);
            System.out.println("Client Port: "+ cliSock.getLocalPort()+ "\n");
        
            String msgSend = "Hallo, Echooooo !!!!!!€€€€%%";
            byte[] byteSend = msgSend.getBytes("UTF-8");
            DatagramPacket packetOut = new DatagramPacket(byteSend, byteSend.length, SERVERIPADDR, SERVERPORT);
            System.out.println("Sende:" + msgSend+ "\nLänge: " + msgSend.length() + "\n");
            cliSock.send(packetOut);
            byte[] bufferIn = new byte[byteSend.length];

            DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length,SERVERIPADDR, SERVERPORT);
            cliSock.receive(packetIn);
            String received = new String(packetIn.getData(), 0, packetIn.getLength(), "UTF-8");
            System.out.println("Received: " + received + "\nLänge: " + received.length());

            cliSock.close();

        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
    }

}
