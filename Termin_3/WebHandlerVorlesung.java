package Termin_3;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class WebHandlerVorlesung extends Thread
{
    private Socket cliSock;

    WebHandlerVorlesung(Socket sock)
    {
        this.cliSock = sock;
    }
    @Override
    public void run(){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(cliSock.getInputStream()));
            PrintStream writer = new PrintStream(cliSock.getOutputStream(), true, "UTF-8");

            ArrayList<String> headerLines = getHeaderLines(reader);
            if(headerLines != null){
                /*System.out.println("Request Header:");
                for(String s : headerLines){
                    System.out.println("  " + s);
                }*/

                String[] requestLineParams = headerLines.get(0).split(" ");
                String requestMethod = requestLineParams[0];
                String requestResource = requestLineParams[1];
                System.out.println("HTTP-Command/Method: " + requestMethod);
                System.out.println("Resource/URL-Path: " + requestResource);

                String response = null;

                switch(requestMethod.trim().toUpperCase()){
                case "GET":
                    switch(requestResource.trim().toLowerCase()){
                    case "/":
                    case "/index.htm":
                    case "/index.html":
                        response = getResponse_IndexHTML();
                        sendResponse(writer,response);
                        break;

                    case "/test.html":
                    case "/test.htm":
                        response = getResponse_TestHTML();
                        sendResponse(writer,response);
                        break;

                    case "/favicon.ico":
                        sendFavicon(writer);
                        break;

                    default:
                        response = getErrorResponse404();
                        sendResponse(writer,response);
                        break;
                    }
                    break;
                default:
                    // Methode nicht implementiert
                    response = getErrorResponse501();
                    sendResponse(writer,response);
                    break;
            }
            }
            else{
                System.out.println("HTTP Fehler");
            }

            writer.close();
            reader.close();
            cliSock.close();

        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    private void sendFavicon(PrintStream writer){
        writer.print("HTTP/1.1 200 OK\r\n"
                + "Content-Type: image/x-icon\r\n"
                + "\r\n");
        String dateiName = "capybara.ico";
        FileInputStream fis;
        byte[] readBuffer = new byte[4096];
        int readBytes = 0;
        try{
            fis = new FileInputStream(dateiName);
            while((readBytes = fis.read(readBuffer)) != -1){
                writer.write(readBuffer, 0, readBytes);
            }
            fis.close();

        }
        catch(IOException ex){
            System.out.println("Fehler beim Handling von capybara.ico");
        }

    }

    private String getErrorResponse404(){
        String body = "Error: Resource not found (404)";
        int contentLength = body.getBytes(StandardCharsets.UTF_8).length;
        return "HTTP/1.1 404 Not Found\r\n" + String.format("Content-Length: %d\r\n", contentLength) + String.format("Content-Type: text/plain; charset=%s\r\n", StandardCharsets.UTF_8.name()) + "Connection: close\r\n" + "\r\n" + body;

    }

    private String getErrorResponse501(){
        return "HTTP/1.1 501 Not Implemented\r\n" + String.format("Content-Type: text/plain; charset=%s\r\n", StandardCharsets.UTF_8.name()) + "Connection: close\r\n" + "\r\n";
    }

    private String getResponse_IndexHTML() throws UnsupportedEncodingException{

        String zeitpunkt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String body = " Respose (index.html) from HTTP-Server" + zeitpunkt;
        int contentLength = body.getBytes("UTF-8").length;

        return "HTTP/1.1 200 OK\r\n" + String.format("Content-Length: %d\r\n", contentLength) + String.format("Content-Type: text/plain; charset=%s\r\n", "UTF-8") + "Connection: close\r\n" + "\r\n" + body;
    }

    private String getResponse_TestHTML() throws UnsupportedEncodingException{
        
        String zeitpunkt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String body = "";
        body += "<!DOCTYPE html>";
        body += "<html ...>";
        body += "<head>";
        body += "<meta http-equiv=\"content-type\" content=\"text/html;charset=UTF-8\"";
        body += "<title>Demo</title>";
        body += "</head>";
        body += "<body>";
        body += "<h1>Demo HTML</h1>";
        body += "<p>Diese Seite wurde vom HTTP-Server bereitgestellt.</p>";
        body += "<p><a href=\"/index.html\">Zur Startseite</a></p>";
        body += "</body>";
        body += "</html>";




        int contentLength = body.getBytes("UTF-8").length;

        return "HTTP/1.1 200 OK\r\n" + String.format("Content-Length: %d\r\n", contentLength) + String.format("Content-Type: text/html; charset=%s\r\n", "UTF-8") + "Connection: close\r\n" + "\r\n" + body;

    }

    private void sendResponse(PrintStream writer, String response){
        System.out.println("Response:\n" + response);   
        writer.print(response);
        writer.flush();
    }

    private ArrayList<String> getHeaderLines(BufferedReader reader) throws IOException{
        ArrayList<String> lines = new ArrayList<>();
        String line = reader.readLine();

        while((!line.isEmpty()) && line != null){
            lines.add(line);
            line = reader.readLine();
        }
        return lines;
    }
    
}
