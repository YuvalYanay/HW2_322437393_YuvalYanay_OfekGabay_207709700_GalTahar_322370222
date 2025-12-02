import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class CalculatorTCPServer {

    public static void main(String[] args) throws IOException {

        int port = 9090;

        try(ServerSocket serverSocket = new ServerSocket(port)){ //Creation of Echo server listening to port 9090

            System.out.println("Server is listening on port: " + port );//Declaring the server
            Socket socket = serverSocket.accept(); //Waiting for client connection
            System.out.println("New client connected");//Declaring on client connection

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //Enabling sending text with println
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //Enabling getting text from the client and reads his lines

            String inputline;


            while ((inputline = in.readLine()) != null) { //Looping for all the inputs from the client as long as it's not null


                System.out.println("Received expression: " + inputline);

                Boolean validFormat = formatFunc(inputline); //Validation of the format

                if (inputline.toLowerCase().equals("close")){ //If client decides to exit
                    break;
                }
                else if (!validFormat) {

                    out.println(inputline + " = Error: Invalid expression.");
                }
                else {

                    String result = calculator(inputline); //Calculating the numbers

                    if (result == null){

                        out.println(inputline + " = Error: Division by zero.");
                    }
                    else {

                        out.println(inputline + " = " + result); //Return to the client the valid result

                    }

                }



            }

            System.out.println("Client requested to close connection.");
            System.out.println("Client disconnected");
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


        if(input.matches("\\d+\\s[+\\-*/]\\s\\d+")){

            return true;

        }

        return false;

    }

    public static String calculator(String input){

        Character[] operators = {'+', '-', '*', '/'};


        StringBuilder strNum1 = new StringBuilder();
        StringBuilder strNum2 = new StringBuilder();
        char exeOperator = ' ';
        Boolean isOperator = false;

        for(int i = 0; i < input.length(); i++){

            if( Character.isDigit(input.charAt(i)) && !isOperator){

               strNum1.append(String.valueOf(input.charAt(i))) ;

            }
            else if(Arrays.asList(operators).contains(input.charAt(i))){

                exeOperator = input.charAt(i);
                isOperator = true;

            } else if (Character.isDigit(input.charAt(i)) && isOperator) {

               strNum2 = strNum2.append(String.valueOf(input.charAt(i))) ;

            }

        }

        int num1 = Integer.parseInt(strNum1.toString());
        int num2 = Integer.parseInt(strNum2.toString());


       switch (exeOperator){

           case '+':
               return Integer.toString(num1 + num2);

           case '-':
               return Integer.toString(num1 - num2);

           case '*':
               return Integer.toString(num1 * num2);

           case '/':

               if (num2 != 0){
                   return Integer.toString(num1 / num2);
               }

               return null;

           default:

               return null;


        }


    }


}
