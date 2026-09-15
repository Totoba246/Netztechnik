package Termin_2;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEcho_Server {
    final static int SERVERPORT = 50000;

    public static void main(String[] args) {
        try{
            final InetAddress SERVERIPADDR = InetAddress.getByName("localhost");

            //ServerSocket welcomeSocket = new ServerSocket(SERVERPORT);
            ServerSocket welcomeSocket = new ServerSocket(SERVERPORT, 100, SERVERIPADDR); //mit fester Anzahl an Puffern und angabe der IP (falls mehrere)

            Socket sock2 = welcomeSocket.accept(); //wartet auf TCP verbindung und gibt neuen Socket zurück (Receiver = Server; kurzlebig)

            System.out.println("Receiver-Port: " +  sock2.getLocalPort());
            System.out.println("---------------------------------------");


            System.out.println("Receiving: ");
            byte[] byteReceiveBuffer = new byte[1024];
            InputStream in = sock2.getInputStream();
            int i = 0;
            for(int b = 0; ((b = in.read()) >= 0);){
                byteReceiveBuffer[i++] = (byte) b;
            }
            System.out.println("End of Stream");

            String msg = new String(byteReceiveBuffer, 0, i, "UTF-8");
            System.out.println("Message Received(Bytes): ");
            System.out.println(msg + ">>> Chars >>>" + msg.length());
            System.out.println("Bytes Received: " + i);

            System.out.println("Sending: ");
            OutputStream out = sock2.getOutputStream();
            out.write(byteReceiveBuffer);
            out.flush(); //zwingen zu senden
            out.close();

            sock2.close();
            welcomeSocket.close();

        }
        catch(Exception ex){
            System.out.println("Exception" + ex);

        }
    }
    
}
