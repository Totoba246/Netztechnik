package Termin_2;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class TLV_Server {
    final static int SERVERPORT = 50000;
    public static void main(String[] args){
        try{
            final InetAddress SERVERIP = InetAddress.getByName("localhost");

            ServerSocket welcome = new ServerSocket(SERVERPORT);
            Socket sockSer = welcome.accept();

            byte[] byteReceive = new byte[1024];
            InputStream in = sockSer.getInputStream();
            char type = (char) in.read();
            byte[] byteLen = new  byte[4];
            byteLen[0] = (byte)in.read();
            byteLen[1] = (byte)in.read();
            byteLen[2] = (byte)in.read();
            byteLen[3] = (byte)in.read();

            int length = ByteBuffer.wrap(byteLen).getInt();

            byte[] msgReceived = new byte[length];

            for(int i = 0; i<length; i++){
                msgReceived[i] = (byte)in.read();
            }

            switch(type){
                case 'S':
                    String msgStr = new String(msgReceived, 0, length, "UTF-8");
                    System.out.println("Nachricht: " + msgStr);
                    break;
                case 'I':
                    int msgInt =  ByteBuffer.wrap(msgReceived).getInt();
                    System.out.println("Nachricht: " + msgInt);
                    break;

            }

        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

}
