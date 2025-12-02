import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class CalculatorTCPServer {

    public static void main(String[] args) {

        int port = 9090;

        try(ServerSocket serverSocket = new ServerSocket(port)){ //Creation of Echo server listening to port 9090

            System.out.println("Server is listening on port: " + port );//Declaring the server
            Socket socket = serverSocket.accept(); //Waiting for client connection
            System.out.println("New client connected");//Declaring on client connection

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //Enabling sending text with println
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //Enabling getting text from the client and reads his lines

            String inputline;


            while ((inputline = in.readLine()) != null) { //Looping for all the inputs from the client as long as it's not null

                Boolean validFormat = formatFunc(inputline); //Validation of the format
                String result = calculator(inputline); //Calculating the numbers

                if (result == null){

                    System.out.println("Error: Division by zero.");

                }
                else if (!validFormat) {
                    System.out.println("Error: Invalid expression.");
                }
                else {
                    System.out.println(inputline + " = " + result); //Return to the client the valid result

                }
            }

            //Closing everything
            in.close();
            out.close();
            socket.close();
            serverSocket.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }


    public static Boolean formatFunc(String input){


        if(input.matches("\\d+\\s*[+\\-*/]\\s*\\d+")){

            return true;

        }

        return false;

    }

    public static String calculator(String input){

        int[] operators = {'+', '-', '*', '/'};

        ArrayList<Integer> nums = new ArrayList<>();
        char exeOperator = ' ';

        for(int i = 0; i < input.length(); i++){

            if( Character.isDigit(input.charAt(i)) ){

                nums.add(Integer.parseInt(String.valueOf(input.charAt(i))));

            }
            if(Arrays.asList(operators).contains(input.charAt(i))){

                exeOperator = input.charAt(i);

            }

        }


       switch (exeOperator){

           case '+':
               return Integer.toString(nums.get(0) + nums.get(1));

           case '-':
               return Integer.toString(nums.get(0) + nums.get(1));

           case '*':
               return Integer.toString(nums.get(0) + nums.get(1));

           case '/':

               if (nums.get(1) != 0){
                   return Integer.toString(nums.get(0) + nums.get(1));
               }

               return null;

           default:

               throw new IllegalArgumentException("Illegal numbers");
       }


    }


}
