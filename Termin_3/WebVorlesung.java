package Termin_3;

import java.net.*;

public class WebVorlesung {

    public static void main(String[] args) {
        try{
            final InetAddress SERVERIP = InetAddress.getByName("localhost");
            ServerSocket welcome = new ServerSocket(50000, 50, SERVERIP);

            while(true){
                Socket client = welcome.accept();
                new WebHandlerVorlesung(client).start();
            }
            

            
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    
}
