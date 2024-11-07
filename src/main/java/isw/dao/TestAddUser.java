package isw.dao;

import isw.controler.CustomerControler;

import java.sql.SQLException;

import isw.message.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class TestAddUser {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // or the IP where SocketServer is running
        int port = 8081; // Replace with your actual port

        try (Socket socket = new Socket(serverAddress, port)) {
            // Create the Message with context and session data
            Message messageOut = new Message();
            messageOut.setContext("/addUser");

            // Prepare the user data in the session map
            HashMap<String, Object> session = new HashMap<>();
            session.put("usuario", "El_Jolan_2");
            session.put("nombre", "Marco Holland");
            session.put("email", "hollandmarco@gmail.com");
            session.put("contraseña", "hashed_password");
            messageOut.setSession(session);

            // Send the message to the server
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(messageOut);

            // Receive the response from the server
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Message messageIn = (Message) in.readObject();

            // Check the response
            if (messageIn.getSession().containsKey("message")) {
                System.out.println((String) messageIn.getSession().get("message"));
            } else if (messageIn.getSession().containsKey("error")) {
                System.out.println("Error: " + (String) messageIn.getSession().get("error"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


