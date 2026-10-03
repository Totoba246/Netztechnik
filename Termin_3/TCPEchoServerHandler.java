package Termin_3;
import java.io.*;
import java.net.*;

//public class TCPEchoServerHandler extends Thread{
public class TCPEchoServerHandler implements Runnable{
    private final Socket clientSocket;
    private final int numVerbindung;

    TCPEchoServerHandler(Socket sock, int nr){
        clientSocket = sock;
        numVerbindung = nr;
    }

    @Override 
    public void run(){
        try{
            clientSocket.setSoTimeout(0);
            PrintStream out = new PrintStream(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while(true){
                String msgReceived = in.readLine();
                System.out.println("Message from Client: " + msgReceived);

                if(msgReceived.trim().toLowerCase().equals("exit")){
                    break; //Verbindung wurde vom CLient beendet
                }
                String msgSend = msgReceived.toUpperCase();
                out.print(msgSend);
                out.print("\n");
                out.flush();
            }

            in.close();
            out.close();
            clientSocket.close();

        }
        catch(Exception ex){
            System.out.println(ex);
        }

    }
    
}
