package Termin_2;

import java.net.*;
import java.nio.ByteBuffer;
import java.time.*;

public class Aufgabe_Zeit {
    final static int SERVERPORT = 123;
    public static void main(String[] args){
        try{
            final InetAddress SERVERIPADDR = InetAddress.getByName("ptbtime1.ptb.de");
            DatagramSocket client = new DatagramSocket();

            //Request senden
            byte[] byteBuffer = new byte[48];
            byteBuffer[0] = 27;
            DatagramPacket out = new DatagramPacket(byteBuffer, byteBuffer.length, SERVERIPADDR, SERVERPORT);
            client.send(out);

            //Auf antwort warten
            byte[] bufferIn = new byte[48];
            DatagramPacket packIn = new DatagramPacket(bufferIn, bufferIn.length, SERVERIPADDR, SERVERPORT);
            client.receive(packIn);

            //Relevante Bytes in transTime packen
            byte[] data = packIn.getData();
            byte[] transTime = new byte[4];
            transTime[0] = data[40];
            transTime[1] = data[41];
            transTime[2] = data[42];
            transTime[3] = data[43];

            //umrechnen
            long secSince1900 = Integer.toUnsignedLong(ByteBuffer.wrap(transTime).getInt());
            long secSince1970 = secSince1900 - 2208988800L;
            System.out.println("Time since 1.1.1970: " + secSince1970);

            //Datum mit Uhrzeit berechnen
            Instant ins = Instant.ofEpochSecond(secSince1970);
            ZonedDateTime datum = ins.atZone(ZoneId.of("Europe/Berlin"));
            System.out.println("Es ist " + datum.getDayOfWeek() +" der " + datum.getDayOfMonth() + "." + datum.getMonth() + " " + datum.getYear());
            System.out.println("Die Uhrzeit lautet: " + datum.getHour() + ":" + datum.getMinute() + ":" + datum.getSecond());


            client.close();
        }
        catch(Exception ex){
            System.out.println("Exception: " + ex);
        }
        
    }


}
