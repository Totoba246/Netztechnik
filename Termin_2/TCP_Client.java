package Termin_2;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCP_Client {

    final static int SERVERPORT = 50000;
    public static void main(String[] args) {
        try{
            final InetAddress SERVERIPADDR = InetAddress.getByName("10.105.147.169");
            Socket sock1 = new Socket(SERVERIPADDR, SERVERPORT); //Sender = CLient

            System.out.println("Sender-Port" + sock1.getLocalPort());
            System.out.println("----------------------------------------");

            System.out.println("Sending: ");

            OutputStream out = sock1.getOutputStream();
            String msgSend = "Noch was";
            byte[] byteSendBuffer = msgSend.getBytes("UTF-8");
            out.write(byteSendBuffer);
            out.flush(); //zwingen zu senden
            out.close();

            sock1.close();

        }
        catch(Exception ex){
            System.out.println("Exception: " + ex);
        }
    }
    
}
