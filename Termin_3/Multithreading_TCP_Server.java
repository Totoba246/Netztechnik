package Termin_3;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Multithreading_TCP_Server {

    final static String TCP_SERVER = "localhost";
    final static int TCP_SERVERPORT = 50000;

    public static void main(String[] args) {
        ExecutorService pool = null;

        int numVerbindung = 0;
        try{
            //pool = Executors.newCachedThreadPool();
            pool = Executors.newFixedThreadPool(10);

            final InetAddress TCP_SERVERIP = InetAddress.getByName(TCP_SERVER);
            ServerSocket serWelSock = new ServerSocket(TCP_SERVERPORT, 50, TCP_SERVERIP);
            while(true){
                Socket sock = serWelSock.accept();
                numVerbindung++;
                //Thread workerThread = new TCPEchoServerHandler(sock, numVerbindung);
                //workerThread.start();

                TCPEchoServerHandler hdlr = new TCPEchoServerHandler(sock, numVerbindung);
                //Thread workerThread = new Thread(hdlr);
                //workerThread.start();

                pool.execute(hdlr);

            }

        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    
}

