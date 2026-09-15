package Termin_1;
import java.net.*;

public class UDP_Client{

    final static int SERVERPORT = 50000;

    public static void main(String[] args){
        try{
            final InetAddress SERVERIPADDR = InetAddress.getByName("localhost");

            //DatagramSocket sock2 = new DatagramSocket(SERVERPORT, SERVERIPADDR);
            DatagramSocket sock2 = new DatagramSocket(); //kurzlebiger Port

            System.out.println("Sender (Client) Port:" + sock2.getLocalPort());
            System.out.println();
            
            String msgSend = "Hello World!äöüäöüßß@€€";

            byte[] byteSendBuffer = msgSend.getBytes("UTF-8");
            DatagramPacket packetOut = new DatagramPacket(byteSendBuffer, byteSendBuffer.length, SERVERIPADDR, SERVERPORT);
            sock2.send(packetOut);

            System.out.println("Message send");
            System.out.println(msgSend +  " >>>  Length(Character): " + msgSend.length() + " Bytes: " + byteSendBuffer.length);
            System.out.println(bytesToHex(byteSendBuffer));

            sock2.close();

        }
        catch (Exception ex){
            System.out.println(ex);
    
        }
    }

    private static String bytesToHex(byte[] bytes, int... ct){
        final char[] hexArray = {'0', '1', '2', '3','4','5','6','7','8','9', 'A', 'B','C','D','E','F'};
        int count = bytes.length;
        if(ct.length == 1){
            count = ct[0];
        }

        StringBuilder sBuilder = new StringBuilder();
        for(int i = 0; i<count; i++){
            char c = (char)bytes[i];
            char hexDigit = hexArray[(c >> 4) & 0x0F];
            sBuilder.append(hexDigit);
            hexDigit = hexArray[c & 0x0F];
            sBuilder.append(hexDigit);
            sBuilder.append(' ');
        }

        sBuilder.append("\r\n");
        return sBuilder.toString();
    }
}

