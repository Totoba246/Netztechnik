package Termin_6;
import java.io.*;
import java.net.*;
import java.util.*;

public class byte_Stuff_send {
    public final static byte FRAME_DELIMITER = 0x7E;
    public final static byte ESCAPE = 0x1B;
    public final  static int  SER_PORT = 50000;

    public  static void main(String[] args){
        try{
            final InetAddress SER_IP= InetAddress.getByName("localhost");
            Socket sock = new Socket(SER_IP, SER_PORT);

            PrintStream out = new PrintStream(sock.getOutputStream());
            File test = new File("Termin_6/bytestuffing_test.bin");
            FileInputStream fis = new FileInputStream(test);
            int b;

            out.write(FRAME_DELIMITER);
            while((b = fis.read()) != -1){
                if(b == FRAME_DELIMITER || b == ESCAPE){
                    out.write(ESCAPE);
                }
                out.write(b);
            }
            out.write(FRAME_DELIMITER);
            System.out.println("File sent");
            fis.close();
            sock.close();
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    
}
