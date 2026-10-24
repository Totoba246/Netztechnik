package Termin_6;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.*;

public class byte_Stuff_receive {
    public final static byte FRAME_DELIMITER = 0x7E;
    public final static byte ESCAPE = 0x1B;
    public final  static int  SER_PORT = 50000;

    public static void main(String[] args){
        try{
            final InetAddress SER_IP =  InetAddress.getByName("localhost");
            ServerSocket welcome = new ServerSocket(SER_PORT, 10, SER_IP);
            FileOutputStream fos = new FileOutputStream("Termin_6/bytestuffing_test_copy.bin");
            Socket cliSock = welcome.accept();
            InputStream  in  =  cliSock.getInputStream();
            int b = 0;

            while((b = in.read()) != FRAME_DELIMITER){
                continue;
            }
            System.out.println("Start receiving file");
            boolean endOfFile = false;
            int count = 0;
            while(!endOfFile){
                b = in.read();
                //System.out.println("Byte " + count + ": " + b);
                count++;

                if(b == ESCAPE){
                    b = in.read();
                    fos.write(b);
                }
                else if(b == FRAME_DELIMITER){
                    endOfFile = true;
                    break;
                }
                else{
                    fos.write(b);

                }
            }
            System.out.println("File received");
            fos.close();


            File copied = new File("Termin_6/bytestuffing_test_copy.bin");
            File original = new File("Termin_6/bytestuffing_test.bin");

            if(Files.mismatch(copied.toPath(), original.toPath()) == -1){
                System.out.println("They are the same, you  did Great");
            }
            else{
                System.out.println("They are not the same, you did something wrong");
            }
            cliSock.close();
            welcome.close();

        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    
}
