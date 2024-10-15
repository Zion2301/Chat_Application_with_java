package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientServer {
    private static List<ClientChat> allclients = new ArrayList<>();
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(1234)){
            System.out.println("Listing on host is ongoing.......");

            while (true){
              Socket typesocket = server.accept();
                System.out.println("Connected Successfully");

                //create the thread
                ClientChat chat = new ClientChat(typesocket);
                allclients.add(chat);
                new Thread(chat).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showMessage(String message, ClientChat Sender){
           for (ClientChat client:allclients){
               if (client != Sender){
                   client.sendMessage(message);
               }
           }
    }

    public static void removeUser(ClientChat chat){
        allclients.remove(chat);
        System.out.println("User disconnected: "+ chat.getUser());
    }
}
