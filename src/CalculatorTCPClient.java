import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculatorTCPClient {

    public static void main(String[] args) throws IOException {

        int port = 9090;

        Socket socket = new Socket("localhost",port); //Creating the socket for the client connection

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //Writing channel
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //Reading channel
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); //Channel for reading the data (from the keyboard)



        while (true){

            //Takes a line of bytes and read it as long as it's not null
            System.out.print("Enter expression (num op num) or 'close' to exit: ");
            String userInput = stdIn.readLine();

            if (userInput == null || userInput.equalsIgnoreCase("close")){ //Reaches null or client types close

                System.out.println("Client closed");
                break;

            }

            out.println(userInput);
            System.out.println(in.readLine());

        }


        out.close();
        in.close();
        socket.close();

    }
}
