
import java.io.*;
import java.net.*;

class TCPClient {

    public static void main(String argv[]) throws Exception {

        System.out.println("[1] Erstelle einen InputStream um Tasten einzulesen.");
		BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));


        System.out.println("[2] Erstelle eine TCP Socketverbindung zum Server \"localhost\""); 
		System.out.println("    und verbinde dich mit Port 6789.");
		Socket clientSocket = new Socket("localhost", 6789);

		
		System.out.println("[3] Erstelle einen OutputStream und verknuepfe diesen mit dem Socket.");
        DataOutputStream outToServer =
                new DataOutputStream(clientSocket.getOutputStream());


		System.out.println("[4] Erstelle einen InputStream und verknuepfe diesen mit dem Socket.");
		BufferedReader inFromServer =
                new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));


		System.out.println("[5] Lese den Satz (Bytestrom) des Nutzers ein:");
		String sentence = inFromUser.readLine();
		

		System.out.println("[6] Sende Satz an den Server");
        outToServer.writeBytes(sentence + '\n');


		System.out.println("[7] Lese den Antwortsatz vom Server");
        String modifiedSentence = inFromServer.readLine();
		
		System.out.println("[8] Gebe Antwort von Server aus: " + modifiedSentence);

 
        System.out.println("[9] Schliesse die Verbindung zum Socket");
		clientSocket.close();
    }
}