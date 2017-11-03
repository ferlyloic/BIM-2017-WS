
import java.io.*;
import java.net.*;

class UDPClient {

    public static void main(String argv[]) throws Exception {

        System.out.println("[1]  Erstelle einen InputStream um Tasten einzulesen.");
		BufferedReader inFromUser =
                new BufferedReader(
					new InputStreamReader(System.in));


        System.out.println("[2]  Erstelle einen Datagram/UDP Socket");   
        DatagramSocket clientSocket = new DatagramSocket();


        System.out.println("[3]  Ermittle IP-Adresse via DNS Anfrage von Host-Name"); 
		InetAddress IPAddress = InetAddress.getByName("localhost");


        System.out.println("[4]  Lege 1Kbyte grosse Eingangs und Ausgangspuffer fuer die UDP Pakete an."); 
		byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];


        System.out.println("[5]  Lese einen Satz von der Tastatur aus und kopiere die");
		System.out.println("     Zeichen in den Ausgangspuffer:");
		String sentence = inFromUser.readLine();
        sendData = sentence.getBytes();

		
        System.out.println("[7]  Erstelle ein Datagramm aus Ausgangspuffer,");
        System.out.println("     Ziel-IP und Ziel-Port(9876).");
        DatagramPacket sendPacket =
                new DatagramPacket(
					sendData, sendData.length, IPAddress, 9876);

					
		System.out.println("[8]  Sende UDP Paket ueber Socket.");
        clientSocket.send(sendPacket);


        System.out.println("[9]  Erstelle ein Datagramm mit dem Eingangspuffer."); 
        DatagramPacket receivePacket =
                new DatagramPacket(receiveData, receiveData.length);

				
		System.out.println("[10] Empfange Datagram ueber Socket API.");
        clientSocket.receive(receivePacket);

		
		System.out.println("[11] Extrahiere die Antwort aus dem Datagramm.");
		String modifiedSentence =
                new String(receivePacket.getData());

        
		System.out.println("[12] Empfangen: " + modifiedSentence);

		
        System.out.println("[13] Schliesse den Datagramm Socket.");
		clientSocket.close();
    }
}