package Termin_3;
import java.io.*;
import java.net.*;

public class Multithreading_TCP_Client {

    final static String TCP_SERVER = "10.169.97.169";
    final static int TCP_SERVERPORT = 50000;
    public static void main(String[] args) {
        try (Socket sock = new Socket(TCP_SERVER, TCP_SERVERPORT);
             PrintStream out = new PrintStream(sock.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Verbunden mit " + TCP_SERVER + ":" + TCP_SERVERPORT + " – 'exit' zum Beenden");

            String line;
            while ((line = console.readLine()) != null) {
                out.print(line + "\n");
                out.flush();

                if (line.trim().equalsIgnoreCase("exit")) {
                    break; // Server beendet die Verbindung dann selbst
                }

                String reply = in.readLine();
                if (reply == null) {
                    System.out.println("Server hat die Verbindung geschlossen.");
                    break;
                }
                System.out.println("Antwort vom Server: " + reply);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}