package Termin_5;

import java.io.*;
import java.net.*; 
import java.util.*;

public class WebBrowser {

    public static void main(String[] args) {
        try{
            URL url = new URL("http://localhost:50000/test.htm");
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(15000);
            conn.setDoOutput(false);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.connect();
            System.out.println("Connected to Webserver");
            System.out.println("URL: " + conn.getURL());
            System.out.println("Content-Type: " + conn.getContentType());
            System.out.println("Content-Length: " + conn.getContentLength());
            System.out.println("Response Headers:");
            Map<String, List<String>> hf = conn.getHeaderFields();
            System.out.println(hf);
            System.out.println("Body: ");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }


            in.close();

        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}