package Termin_1;
import java.io.IOException;
import java.net.*;

public class UDP_Server{

    final static int SERVERPORT = 50000;

    public static void main(String[] args){
        try{
            final InetAddress SERVERIPADDR = InetAddress.getByName("localhost");
            DatagramSocket sock1 = new DatagramSocket(SERVERPORT);
            sock1.setSoTimeout(10000);

            System.out.println("Receiver (Server) Port: " + sock1.getLocalPort());
            System.out.println("Receiving: ");

            byte[] byteReceiveBuffer = new byte[1024];
            DatagramPacket packetIn = new DatagramPacket( byteReceiveBuffer, byteReceiveBuffer.length);
            sock1.receive(packetIn);

            String msgReceived = new String(packetIn.getData(), 0, packetIn.getLength(), "UTF-8");
            System.out.println("Message received");
            System.out.println(msgReceived +" >>>Length(Character): " + msgReceived.length());
            System.out.println("bytesReceived: " );
            System.out.println(bytesToHex(packetIn.getData(), packetIn.getLength()));

            sock1.close();

        }
        catch(SocketTimeoutException ex){
            System.out.println("Timeout");
        }
        catch(SocketException ex){
            System.out.println(ex);
        }
        catch(IOException ex){
            System.out.println(ex);
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

