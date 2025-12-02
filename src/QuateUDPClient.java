import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class QuateUDPClient {

    public static void main(String[] args) {

        try {

            //Getting server's address and port
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 8080;

            DatagramSocket clientSocket = new DatagramSocket(); // Create a DatagramSocket for sending and receiving packets
            Scanner scanner = new Scanner(System.in); //Initialize scanner for user input

            // Buffers for sending and receiving data
            byte[] sendBuffer;
            byte[] receiveBuffer = new byte[1024];
            System.out.println("Type messages to send to the server. Type 'exit' to quit.");


            while(true){


                System.out.print("You: ");
                String clientMessage = scanner.nextLine();
                sendBuffer = clientMessage.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, serverPort); // Create a DatagramPacket to send the message to the server
                clientSocket.send(sendPacket); //Sending the packet to the server

                if (clientMessage.equalsIgnoreCase("exit")) { // Check if the client wants to terminate the connection
                    System.out.println("Client is shutting down...");
                    break;// Exit the loop to shut down the client
                }


                //Receiving the response from the server
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                clientSocket.receive(receivePacket);
                String serverResponse = new String(receivePacket.getData(),0,receivePacket.getLength());
                System.out.println("Server: " + serverResponse);
            }

        }
        catch (Exception e){

            e.printStackTrace();

        }

    }
}
