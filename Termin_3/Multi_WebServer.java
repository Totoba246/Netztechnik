package Termin_3;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Multi_WebServer {
    final static String SERVER_TCP = "localhost";
    final static int SERVERPORT_TCP = 50000;

    public static void main(String[] args){
        ExecutorService pool = null;
        try{
            pool = Executors.newFixedThreadPool(10);
            final InetAddress SERVERIP = InetAddress.getByName(SERVER_TCP);
            ServerSocket welcome = new ServerSocket(SERVERPORT_TCP);
            while(true){
                Socket sock = welcome.accept();
                WebServer_Handler workerThread = new WebServer_Handler(sock);
                pool.execute(workerThread);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

    }

}
