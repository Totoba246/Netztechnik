package Termin_2;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPEcho_Client {
    final static int SERVERPORT = 50000;
    public static void main(String[] args) {
        try{
            final InetAddress SERVERIPADDR = InetAddress.getByName("localhost");
            Socket sock1 = new Socket(SERVERIPADDR, SERVERPORT); //Sender = Client

            System.out.println("Sender-Port" + sock1.getLocalPort());
            System.out.println("----------------------------------------");

            System.out.println("Sending: ");

            OutputStream out = sock1.getOutputStream();
            String msgSend = "Hello World";
            byte[] byteSendBuffer = msgSend.getBytes("UTF-8");
            out.write(byteSendBuffer);
            out.flush(); //zwingen zu senden
            out.close();


            System.out.println("Receiving: ");
            byte[] byteReceiveBuffer = new byte[1024];
            InputStream in = sock1.getInputStream();
            int i = 0;
            for(int b = 0; ((b = in.read()) >= 0);){
                byteReceiveBuffer[i++] = (byte) b;
            }
            System.out.println("End of Stream");

            String msg = new String(byteReceiveBuffer, 0, i, "UTF-8");
            System.out.println("Message Received(Bytes): ");
            System.out.println(msg + ">>> Chars >>>" + msg.length());
            System.out.println("Bytes Received: " + i);

            sock1.close();

        }
        catch(Exception ex){
            System.out.println("Exception: " + ex);
        }
    }
    
}
