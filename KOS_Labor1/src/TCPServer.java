
import java.io.*;
import java.net.*;

class TCPServer {

    public static void main(String argv[]) throws Exception {

        System.out.println("[1] Erstelle einen persistenten Socket auf dem sich Clients anmelden koennen.");
		ServerSocket welcomeSocket = new ServerSocket(6789);


        System.out.println("[2] Starte Endlosschleife in der die Anfragen von Clients");
        System.out.println("    bearbeitet werden.");
        while (true) {

   		    System.out.println("[3] Warte blockierend auf einen Client.");
			Socket connectionSocket = welcomeSocket.accept();

			
            System.out.println("[4] Erstelle einen InputStream und verknuepfe diesen mit dem Socket.");
            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(
						connectionSocket.getInputStream()));


			System.out.println("[5] Erstelle einen OutputStream und verknuepfe diesen mit dem Socket.");
			DataOutputStream outToClient =
                    new DataOutputStream(
						connectionSocket.getOutputStream());


            System.out.println("[6] Empfange den Satz vom Client und bearbeite diesen.");
			String clientSentence = inFromClient.readLine();
			
			
			System.out.println("[7] Konvertiere empfangenen Satz in Grossbuchstaben");
            String capitalizedSentence = clientSentence.toUpperCase();

 
			System.out.println("[8] Sende den Satz an den Client.");
			outToClient.writeBytes(capitalizedSentence + '\n');
			
			System.out.println("[9] Beende TCP Verbindung.");
			connectionSocket.close();
        }
    }
}