package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Multiple {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234);
             BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to the chat server!");

            // Thread to receive messages from the server
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Main thread for sending messages to the server
            String clientMessage;
            while ((clientMessage = input.readLine()) != null) {
                out.println(clientMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
