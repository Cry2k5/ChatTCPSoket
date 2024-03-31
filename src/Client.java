import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to server.");

            // Thread for receiving messages from server
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = inputReader.readLine()) != null) {
                        System.out.println("Server: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            // Sending messages to server
            String clientMessage;
            while ((clientMessage = consoleReader.readLine()) != null) {
                outputWriter.println(clientMessage);
            }

            // Closing resources
            socket.close();
            inputReader.close();
            outputWriter.close();
            consoleReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
