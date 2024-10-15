package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientChat implements Runnable{
      private Socket usersocket;
      private PrintWriter print;
      private BufferedReader read;
      private String user;

    public ClientChat(Socket usersocket) {
        this.usersocket = usersocket;
    }

    public String getUser() {
        return user;
    }

    @Override
    public void run() {

        //try seeting up the streams type shiiiii
       try {
           read = new BufferedReader(new InputStreamReader(usersocket.getInputStream()));
           print = new PrintWriter(usersocket.getOutputStream(), true);

           //ask the user for names and shi
           print.println("Enter username; ");
           user = read.readLine();
           System.out.println(user+" has joined. Welcome!");

           //tell the other users the mf has joined
           ClientServer.showMessage(user + " has joined chat",this);

           //read the chats and show em
           String message;
           while ((message = read.readLine()) != null){
               System.out.println(user + ": " + message);
               ClientServer.showMessage(user+ ": " + message, this);
           }
       } catch (IOException e){
             e.printStackTrace();
       } finally {
           try {
               read.close();
               print.close();
               usersocket.close();
           } catch (IOException e){
               e.printStackTrace();
           }
           ClientServer.removeUser(this);
       }
    }

    public void sendMessage(String message){
        print.println(message);
    }
}
