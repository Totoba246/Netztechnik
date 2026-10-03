package Termin_2;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class Copy_Client {

    public static void main(String[] args) {

        try{
            //final String SERVERIP = args[0];
            //final int SERVERPORT = Integer.parseInt(args[1]);
            //final String quellName = args[2];
            //final String zielName = args[3];
            final String quellName = "41.jpg";
            final String zielName = "kopierte_41.jpg";
            final String SERVERIP = "localhost";
            final int SERVERPORT = 50000;

            Socket sock = new Socket(SERVERIP, SERVERPORT);
            //Anfrage Stellen
            OutputStream out = sock.getOutputStream();

            byte[] byteName = quellName.getBytes("UTF-8");

            out.write(byteName);
            sock.shutdownOutput();

            //Datei vorhanden ja(0)/nein(1)
            InputStream in = sock.getInputStream();

            int exist = in.read();

            if(exist == 1){ //abbruch wenn nicht exestiert
                throw new FileNotFoundException("404 File not Found");
            }
            //Größe der Date entgegennehmen
            byte[] byteSize = new byte[8];
            byteSize[0] = (byte) in.read();
            byteSize[1] = (byte) in.read();
            byteSize[2] = (byte) in.read();
            byteSize[3] = (byte) in.read(); 
            byteSize[4] = (byte) in.read();
            byteSize[5] = (byte) in.read();
            byteSize[6] = (byte) in.read();
            byteSize[7] = (byte) in.read();

            long fileSize = ByteBuffer.wrap(byteSize).getLong();
            long sizeCount = 0L;

            FileOutputStream fos = new FileOutputStream("Termin_2/copy_client_files/" + zielName);
            byte[] byteBuffer = new byte[1024];

            while(sizeCount < fileSize){
                int n = in.read(byteBuffer);

                if(n == -1){
                    break;
                }

                fos.write(byteBuffer, 0, n);

                sizeCount += (long)n;
            }
            fos.close();

            File file = new File("Termin_2/copy_client_files/" + zielName);

            long actFileSize = file.length();

            System.out.println("Deklarierte Größe (vom Server): " + fileSize);
            System.out.println("Tatsächliche Größe (kopierte Datei): " + actFileSize);
            if(actFileSize != fileSize){
                throw new IOException("File could not be read completely");
            }

            sock.close();
        }
        catch(Exception ex){
            System.out.println(ex);
        }

    }
    
}
