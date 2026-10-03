package Termin_2;
import java.net.*;
import java.io.*;

public class Copy_Server {
    final static int SERVERPORT = 50000;
    public static void main(String[] args) {
        try{
            ServerSocket welcome = new ServerSocket(SERVERPORT);

            Socket sock = welcome.accept();

            InputStream in = sock.getInputStream();
            byte[] byteFileName = new byte[1024];

            int i = 0;
            for(int b = 0; ((b = in.read()) >= 0);){
                byteFileName[i++] = (byte) b;
            }
            String name = new String(byteFileName, 0, i, "UTF-8");


            OutputStream out = sock.getOutputStream();
            File file = new File("Termin_2/copy_server_files/" + name);
            if(!file.exists()){
                out.write(1);
                out.flush();
            }
            else{
                out.write(0);
                out.flush();
            }

            long fileSize = file.length();
            DataOutputStream dout = new DataOutputStream(out);
            dout.writeLong(fileSize);

            long count = 0L;
            byte[] sendBuffer = new byte[1024];
            FileInputStream fis = new FileInputStream(file);

            while(count < fileSize){
                int n = fis.read(sendBuffer);
                count += (long)n;
                out.write(sendBuffer, 0, n);
                out.flush();
            }
            fis.close();

            out.close();
            sock.close();
            welcome.close();
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    
}
