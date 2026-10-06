package Termin_3;

import java.io.*;
import java.net.*;
import java.time.LocalTime;

public class WebServer_Handler implements Runnable{
    Socket cliSock;

    public WebServer_Handler(Socket sock){
        cliSock = sock;
    }

    @Override 
    public void run(){
        try{
            PrintStream out = new PrintStream(cliSock.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(cliSock.getInputStream()));
            String firstline = null;
            String[][] header = new String[128][2];
            String body = null;
            int headerCount = 0;
            String cur = in.readLine();
            firstline = cur;

            while (!cur.equals("")){ //Header einlesen
                System.out.println(cur);
                cur = in.readLine();
                if(cur.equals("")){
                    break;
                }
                String[] parts = cur.split(":", 2);
                String key = parts[0].trim();
                String value;

                if(parts.length == 2){
                    value = parts[1].trim();
                }
                else{
                    value = null;
                }
                
                header[headerCount][0]= key;
                header[headerCount][1] = value;
                headerCount++;
            }
            for (int i =0; i< headerCount; i++){ //Body  lesen (unwahrscheinlich, dass der Body bei GET Requests gelesen werden muss)

                if(header[i][0].equals("Content-Length")){
                    int bodyLength =Integer.parseInt(header[i][1]);
                    byte[] byteBody = new byte[1024];
                    for(int j = 0; j<bodyLength;j++){
                        byteBody[j] = (byte) in.read();
                    }
                    body = new String(byteBody, 0, bodyLength, "UTF-8");

                }
            }

            LocalTime uhrzeit = LocalTime.now();
            String uhrzeitString = uhrzeit.toString();
            //HTML bauen für  index.html
            String htmlIndex = "<!Doctype html><html lang=\"de-de\"xml:lang=\"de-de\"xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"content-Type\" content=\"text/html; charset=UTF-8\"/><title>Demo Index</title><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/><head><body><h1>Demo Index</h1><p>Hallo Welt!</p><p>Uhrzeit: " + uhrzeitString + "</p></body></html>";

            //HTML bauen für test.html
            String htmlTest = "<!Doctype html><html lang=\"de-de\"xml:lang=\"de-de\"xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"content-Type\" content=\"text/html; charset=UTF-8\"/><title>Demo Test</title><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/><head><body><h1>Demo Test</h1><p>Hallo Welt!</p><p>Uhrzeit: " + uhrzeitString + "</p></body></html>";

            String html;
            String[] firstlineParts = firstline.split(" ");
            if(firstlineParts[1].equals("/index.html")){
                html = htmlIndex;
            }
            else if(firstlineParts[1].equals("/test.html")){
                html = htmlTest;
            }
            else{
                throw new Exception("404 Not Found");
            }
            
            String contentLength = "Content-Length: " + html.getBytes().length + "\r\n";
            String statuszeile = "HTTP/1.1 200 OK\r\n";
            String response = statuszeile + contentLength + "Content-Type: text/html; charset=UTF-8\r\n\r\n" +html;

            out.print(response);
            out.flush();

            in.close();
            out.close();
            cliSock.close();

        }
        catch(Exception ex){
            System.out.println(ex);
        }

    }
    
}
