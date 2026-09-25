import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class Copy_Client {

    public static void main(String[] args) {

        try{
            final String SERVERIP = args[0];
            final int SERVERPORT = Integer.parseInt(args[1]);
            final String quellName = args[2];
            final String zielName = args[3];

            Socket sock = new Socket(SERVERIP, SERVERPORT);
            //Anfrage Stellen
            OutputStream out = sock.getOutputStream();

            byte[] byteName = quellName.getBytes();

            out.write(byteName);
            sock.shutdownOutput();

            //Datei vorhanden ja(0)/nein(1)
            InputStream in1 = sock.getInputStream();

            int exist = in1.read();
            sock.shutdownInput();

            if(exist == 1){ //abbruch wenn nicht exestiert
                throw new FileNotFoundException("404 File not Found");
            }
            //Größe der Date entgegennehmen
            byte[] byteSize = new byte[4];
            InputStream in2 = sock.getInputStream();
            int i = 0;
            for(int b = 0; ((b = in2.read()) >= 0);){
                byteSize[i++] = (byte) b;
            }
            sock.shutdownInput();

            long FileSize = Integer.toUnsignedLong(ByteBuffer.wrap(byteSize).getInt());

            long iterations = FileSize / 1024L; //Anzahl der erwarteten "Blöcke"

            InputStream in3 =  sock.getInputStream();
            for(int j = 0; j<iterations; j++){
                //nicht fertig


            

        }
        catch(Exception ex){
            System.out.println(ex);
        }



        
    }
    
}
