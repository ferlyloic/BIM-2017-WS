
import java.io.*;
import java.net.*;

class UDPServer {

    public static void main(String argv[]) throws Exception {

        System.out.println("[1]  Erstelle einen Datagram/UDP Socket und binde diesen an Port 9876.");
		DatagramSocket serverSocket = new DatagramSocket(9876);


        System.out.println("[2]  Lege 1Kbyte grosse Eingangs und Ausgangspuffer fuer die UDP Pakete an.");
		byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];


        System.out.println("[3]  Starte Endlosschleife in der die Anfragen von Clients bearbeitet werden.");
		while (true) {

			System.out.println("[4]  Erstelle ein Datagramm aus dem Eingangspuffer.");
            DatagramPacket receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);

					
			System.out.println("[5]  Binde Datagramm an den Socket und warte auf UDP Pakete.");
            serverSocket.receive(receivePacket);


            System.out.println("[6]  Extrahiere einen Satz aus der empfangenen UDP Payload und");
			System.out.println("     konvertiere diesen in Grossbuchstaben.");
            String sentence = new String(receivePacket.getData());
            String capitalizedSentence = sentence.toUpperCase();
			
			System.out.println("[7]  Schreibe Satz in Ausgangspuffer.");
            sendData = capitalizedSentence.getBytes();


            System.out.println("[8]  Extrahiere Quell-IP-Adresse und dessen Port aus dem empfangenen Paket.");
			InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();


            System.out.println("[9]  Setze ein neues Datagram mit der extrahierten IP und");
            System.out.println("     Portnummer sowie Ausgangspuffer zusammen.");
            DatagramPacket sendPacket = new DatagramPacket(
                    sendData, sendData.length, IPAddress, port);

            System.out.println("[10] Sende Datagramm ueber Socket an Client\n");
            serverSocket.send(sendPacket);
	
        }
    }
}