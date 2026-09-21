package Termin_2;
import java.io.*;
import java.net.*;

public class TLV_Client {
    final static int SERVERPORT = 50000;

    public static void main(String[] args){

        try{
            final InetAddress SERVERIP = InetAddress.getByName("localhost");
            Socket sock = new Socket(SERVERIP, SERVERPORT);

            OutputStream out = sock.getOutputStream();

            String msg = "Hallo Welt dies ist eine Nachricht\nUnd hier ist eine zweite Zeile";

            byte[] msgByte = msg.getBytes("UTF-8");
            int length = msgByte.length;

            byte[] sendBuffer = new byte[length + 5];

            sendBuffer[0] = (byte)'S';
            sendBuffer[1] = (byte) (length >> 24);
            sendBuffer[2] = (byte) (length >> 16);
            sendBuffer[3] = (byte) (length >> 8);
            sendBuffer[4] = (byte) (length);
            System.arraycopy(msgByte, 0, sendBuffer, 5, length);

            out.write(sendBuffer);
            out.flush();
            out.close();

            sock.close();

        }
        catch(Exception ex){
            System.out.println("Exception: " +ex);
        }
    }
}
