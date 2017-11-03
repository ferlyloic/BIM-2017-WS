// Laboraufgabe Webserver
// Kommunikationssysteme
// Hochschule Mannheim
// 
// Stand 02.11.2017
//
import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;


class HttpRequest {
    ArrayList<String> m_httpRequest;
    DataOutputStream m_outStream;

    // Each HTTP request contains a object which should be loaded.
    // Question: Which are possible object names in our case and what is the first object's name
    // Do we get the objects name as absolute filename pointing to our hard disk?
    // Hint: Check System.getProperty
    public String getObjectNameFrom(String httpGetMessage) {
       String[] httpSegments = httpGetMessage.split("_");
        return httpSegments[1];
    }

    // Each HTTP response contains the MIME type of the returned object.
    public String getTypeFromObject(String objectName){
        int lastIndexOfDot =objectName.lastIndexOf('.');
        return objectName.substring(lastIndexOfDot + 1);
    }

    // Abstracts the binary file reading process from the hard drive.
    public FileInputStream loadObjectFromHd(String objectName) throws Exception {
        return new FileInputStream(new File(objectName));
    }

    // Returns the file size of the requested object in BYTES. Information taken from the file system
    // Check class File for some helper
    public long getSizeFromObjectInBytes(String objectName) {
        File file = new File(objectName);
        return file.length();
    }


    // Returns the full HTTP response header without the payload. For the sake of easyness, we want use
    // HTTP 1.0!
    //
    // Question:   1) Which is the delimiter of a HTTP request / response header
    //             2) What minimal information should be added to a HTTP response?
    public String createHttpSuccessResponseHeader( String contentType, long entityLengthInBytes) {
        String n = "\r\n";
        String succesHeader = "HTTP/1.0 200 OK"+ n;
        succesHeader += "Content-Type: "+ contentType + n;
        succesHeader += "Content-Length: "+ entityLengthInBytes + n;
        succesHeader += "Connection: Closed" +n + n;
        return succesHeader;
    }


    // Simple error handling in case an object does not exist
    public void sendHttpErrorResponse() throws Exception {
    }


    // Creating a valid HTTP response by adding the Entity-Body (payload) to the HTTP response header.
    // Hint: Writing a file taken from FileInputStream to DataOutputStream does only work if passed through 
    // a ByteBuffer
    public void sendHttpResponse(String responseHeader, FileInputStream fis) throws Exception {

    }

    // Handles the HTTP request by parsing the request message, reading the object from the hard drive and
    // creating the HTTP response.
    // Resulting binary stream could be directly written into DataOutputStream before quitting the HTTP request
    public void Handle() throws Exception {
        System.out.println(m_httpRequest.toString());
    }

    // Constructor
    HttpRequest(ArrayList<String> httpRequestHeader, DataOutputStream streamToClient) throws Exception {
        m_httpRequest = httpRequestHeader;
        m_outStream = streamToClient;
    }
}

//Represents the single threaded WebServer. All socket related calls could be handled here
public class Webserver {
    private static boolean isRunning = true;

    // Read received byte stream from socket and convert to string.
    // This string contains the whole HTTP request header
    // Hint: What is the end of each HTTP Request message?
    public static String readMessageFromBytestream(Socket connectionSocket) throws Exception {
        return "";
    }

    // Mainloop handling all incoming requests in an endless loop.
    // Handling for request could be delegated to an own class.
    public static void run() throws Exception {
        System.out.println("[1] Erstelle einen persistenten Socket auf dem sich Clients anmelden koennen.");
        ServerSocket welcomeSocket = new ServerSocket(6789);

        System.out.println("[2] Starte Endlosschleife in der die Anfragen von Clients");
        System.out.println("    bearbeitet werden.");
        while (isRunning) {

            System.out.println("[3] Warte blockierend auf einen Client.");
            Socket connectionSocket = welcomeSocket.accept();


            System.out.println("[4] Erstelle einen InputStream und verknuepfe diesen mit dem Socket.");
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));


            System.out.println("[5] Erstelle einen OutputStream und verknuepfe diesen mit dem Socket.");
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());


            System.out.println("[6] Empfange die Abfrage vom Client und bearbeite diesen.");
            ArrayList<String> myLines = new ArrayList<>();
            String line = inFromClient.readLine();
            System.out.println(line);
            String header = "";
            while (line == null){
                myLines.add(line);
                line = inFromClient.readLine();
                System.out.println(line);
            }
            System.out.println(myLines);

            HttpRequest httpRequest = new HttpRequest(myLines,outToClient);
            httpRequest.Handle();

            System.out.println("[7] Bereite eine passende Antwort vor");

            System.out.println("[8] Sende die Antwort an den Client zurück.");
           outToClient.writeBytes("HTTP/1.0 200 OK\r\n" +
                   "Date: Mon, 27 Jul 2009 12:28:53 GMT\r\n" +
                   "Server: Apache/2.2.14 (Win32)\r\n" +
                   "Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT\r\n" +
                   "Content-Length: 88\r\n" +
                   "Content-Type: text/html\r\n" +
                   "Connection: Closed\r\n\r\n" +
                   "<html>\n" +
                   "<body>\n" +
                   "<h1>Hello, World!</h1>\n" +
                   "</body>\n" +
                   "</html>"+
                    '\n');

            System.out.println("[9] Beende HTTP Verbindung.");
            connectionSocket.close();
        }
    }

    // In main the WebServer could be initialized and set into "run" state
    public static void main(String argv[]) throws Exception {
        System.out.println("### KOS Webserver started ###");
        run();
    }
}