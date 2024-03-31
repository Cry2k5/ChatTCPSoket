
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server is running and waiting for client...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket);

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            // Thread for receiving messages from client
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = inputReader.readLine()) != null) {
                        System.out.println("Client: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            // Sending messages to client
            String serverMessage;
            while ((serverMessage = consoleReader.readLine()) != null) {
                outputWriter.println(serverMessage);
            }

            // Closing resources
            serverSocket.close();
            clientSocket.close();
            inputReader.close();
            outputWriter.close();
            consoleReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
