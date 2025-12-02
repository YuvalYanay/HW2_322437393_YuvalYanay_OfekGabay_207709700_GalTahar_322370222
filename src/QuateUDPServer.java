import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class QuateUDPServer {

    public static void main(String[] args)  {

        try{

            String[] quates = { "I think, therefore I exist.",
                    "You only know me as you see me, not as I actually am",
                    "I have striven not to laugh at human actions, ",
                    "not to weep at them, nor to hate them, but to understand the",
                    "Great spirits have always encountered violent opposition from mediocore minds.",
                    "Where is the lamb sauce!"
            };

            int port = 8080;
            DatagramSocket serverSocket = new DatagramSocket(port); //Making the channel for sending and getting data
            System.out.println("Server is listening on port: " + port);

            //Buffers for sending and getting data
            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer;


            while (true){

                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length); // Prepare to receive a datagram packet from the client
                serverSocket.receive(receivePacket); // Receive the packet from the client

                // Extract the message from the received packet
                String clientMessage = new String(receivePacket.getData(),0,receivePacket.getLength());
                System.out.println("Received from client: " + clientMessage);

                // Check if the client wants to terminate the connection

                if (clientMessage.equalsIgnoreCase("exit")){ //Checking if the client wants to disconnect from the server
                    System.out.println("Client requested to exit the server.");
                    System.out.println("Client is disconnected");
                    break;
                }

                //Sending the client a message from the server


                String serverResponse = "Received " + clientMessage;
                sendBuffer = serverResponse.getBytes(); //Convert the response to bytes

                // Get the client's IP address and port from the received packet
                InetAddress clientAdress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Create a packet with the response data, client address, and client port
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAdress, clientPort);
                serverSocket.send(sendPacket); //Sending the response to the client
            }


            //Closing the server
            serverSocket.close();

        }
        catch (Exception e){

            e.printStackTrace();

        }


    }
}
