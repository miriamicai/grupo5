package isw.cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;


import isw.configuration.PropertiesISW;
import isw.domain.Customer;
import isw.message.Message;

public class Cliente {
    private String host;
    private int port;

    public Cliente(String host, int port) { //constructor de Cliente: caracterísiticas petición host y puerto
        this.host = host;
        this.port = port;
    }

    public Cliente() { //constructor vacío de Cliente: host y puerto sacado de PropertiesISW.java
        this.host = PropertiesISW.getInstance().getProperty("host");
        this.port = Integer.parseInt(PropertiesISW.getInstance().getProperty("port"));
        if (this.host == null || this.port == 0) {
            throw new IllegalArgumentException("Host o puerto no válidos.");
        }
    }

    public HashMap<String, Object> sentMessage(String Context, HashMap<String, Object> session) {
        //función que devuelve un HashMap<String, Object> que recibe un String y un HashMap


        //Configure connections -> ya hecho en el constructor vacío
        //String host = PropertiesISW.getInstance().getProperty("host");
        //int port = Integer.parseInt(PropertiesISW.getInstance().getProperty("port"));

        System.out.println("Host: " + host + " port" + port);
        //Create a cliente class
        //Client cliente=new Client(host, port);

        //HashMap<String,Object> sessionActual = new HashMap<String, Object>();
        //sessionActual.put("/getCustomer",""); //clase CustomerControler -> se saca de la base de datos

        Message mensajeEnvio = new Message();
        Message mensajeVuelta = new Message();
        mensajeEnvio.setContext(Context);///getCustomer"
        mensajeEnvio.setSession(session);
        this.sent(mensajeEnvio, mensajeVuelta);


        switch (mensajeVuelta.getContext()) { //Devolver los Customers dependiendo del mensaje que devuelva el servidor (mensajeVuelta)

            case "/getCustomersResponse": //CustomerS (varios)
                ArrayList<Customer> customerList = (ArrayList<Customer>) (mensajeVuelta.getSession().get("Customer"));
                for (Customer customer : customerList) { //se recorre la tabla de clientes y los muestra por pantalla
                    System.out.println("He leído el id: " + customer.getId() + " con nombre: " + customer.getNombreUsuario());
                }
                break;
            case "/getCustomerResponse": //1 Customer solo
                session = mensajeVuelta.getSession();
                Customer customer = (Customer) (session.get("Customer"));
                if (customer != null) {
                    System.out.println("He leído el id: " + customer.getId() + " con nombre: " + customer.getNombreUsuario());
                } else {
                    System.out.println("No se ha recuperado nada de la base de datos");
                }
                break;

            case "/getSeguidoresResponse": // Seguidores
                ArrayList<Customer> seguidores = (ArrayList<Customer>) mensajeVuelta.getSession().get("Seguidores");
                if (seguidores != null && !seguidores.isEmpty()) {
                    System.out.println("Lista de seguidores:");
                    for (Customer seguidor : seguidores) {
                        System.out.println("Id: " + seguidor.getId() + ", Nombre: " + seguidor.getNombreUsuario());
                    }
                } else {
                    System.out.println("No se encontraron seguidores.");
                }
                break;

            case "/getSeguidosResponse": // Seguidos
                ArrayList<Customer> seguidos = (ArrayList<Customer>) mensajeVuelta.getSession().get("Seguidos");
                if (seguidos != null && !seguidos.isEmpty()) {
                    System.out.println("Lista de seguidos:");
                    for (Customer seguido : seguidos) {
                        System.out.println("Id: " + seguido.getId() + ", Nombre: " + seguido.getNombreUsuario());
                    }
                } else {
                    System.out.println("No se encontraron personas seguidas.");
                }
                break;

            case "/addUserResponse":
                String message = (String) mensajeVuelta.getSession().get("message");
                if (message != null) {
                    System.out.println("Server response: " + message);
                } else if (mensajeVuelta.getSession().containsKey("error")) {
                    System.out.println("Error: " + mensajeVuelta.getSession().get("error"));
                } else {
                    System.out.println("Unexpected response from server for /addUserResponse");
                }
                break;

            case "/connectUserResponse":
                String mensaje = (String) mensajeVuelta.getSession().get("message");
                if (mensaje != null) {
                    System.out.println("Server response: " + mensaje);
                } else if (mensajeVuelta.getSession().containsKey("error")) {
                    System.out.println("Error: " + mensajeVuelta.getSession().get("error"));
                } else {
                    System.out.println("Unexpected response from server for /addUserResponse");
                }
                break;

            default:

                System.out.println("\nError a la vuelta");
                break;

        }
        //System.out.println("3.- En Main.- El valor devuelto es: "+((String)mensajeVuelta.getSession().get("Nombre")));
        return session;
    }


    public void sent(Message messageOut, Message messageIn) {
        try {

            System.out.println("Connecting to host " + host + " on port " + port + ".");

            Socket echoSocket = null; //A socket is an endpoint for communication between two machines.
            OutputStream out = null;
            InputStream in = null;

            /**
             *
             * InputStream and OutputStream is to abstract different ways to input and output: whether
             * the stream is a file, a web page, or the screen shouldn't matter. All that matters is
             * that you receive information from the stream (or send information into that stream.)
             *
             */


            /**
             *
             * El siguiente try-catch: este código es parte de un cliente que se comunica con un
             * servidor mediante sockets. El cliente abre una conexión con el servidor, envía un
             * objeto, espera una respuesta del servidor y luego la procesa.
             *
             */

            try {
                echoSocket = new Socket(host, port); //se crea la conexión a Internet entre el cliente y el servidor
                in = echoSocket.getInputStream(); //flujo de entrada desde el socket (leer datos del servidor)
                out = echoSocket.getOutputStream(); //flujo de salida del socket (enviar datos del servidor)
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                ObjectInputStream objectInputStream = new ObjectInputStream(in);

                //Create the objetct to send
                objectOutputStream.writeObject(messageOut);

                /*
                // create a DataInputStream so we can read data from it.
                ObjectInputStream objectInputStream = new ObjectInputStream(in);
                Message msg=(Message)objectInputStream.readObject();
                messageIn.setContext(msg.getContext());
                messageIn.setSession(msg.getSession());
		        /*System.out.println("\n1.- El valor devuelto es: "+messageIn.getContext());
		        String cadena=(String) messageIn.getSession().get("Nombre");
		        System.out.println("\n2.- La cadena devuelta es: "+cadena);*/

                // Enviar el objeto al servidor
                objectOutputStream.writeObject(messageOut);
                objectOutputStream.flush(); // Asegúrate de que los datos se envíen

                // Leer la respuesta del servidor
                Message msg = (Message) objectInputStream.readObject();
                messageIn.setContext(msg.getContext());
                messageIn.setSession(msg.getSession());

            } catch (UnknownHostException e) {
                System.err.println("Unknown host: " + host);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Unable to get streams from server");
                System.exit(1);
            }

            /** Closing all the resources */
            out.close();
            in.close();
            echoSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void establishConnection(int followerId, int followingId){
        Message messageOut = new Message();
        messageOut.setContext("/connectUser");

        HashMap<String, Object> session = new HashMap<>();
        session.put("followerId", followerId);
        session.put("followingId", followingId);
        messageOut.setSession(session);
        sent(messageOut, new Message());

        System.out.println("Connection established from Cliente estabishConnection() method.");
    }

    public void registerUser(String username, String name, String email, String password) {
        //Cliente cliente = new Cliente();

        Message messageOut = new Message();
        messageOut.setContext("/addUser");

        HashMap<String, Object> session = new HashMap<>();
        session.put("usuario", username);
        session.put("nombre", name);
        session.put("email", email);
        session.put("contraseña", password);
        messageOut.setSession(session);

        sent(messageOut, new Message());

        System.out.println("User added to database from Cliente registerUser() method.");
    }

}

    /*public static void main(String[] args) {
        Cliente cliente = new Cliente();

        Message messageOut = new Message();
        messageOut.setContext("/addUser");

        HashMap<String, Object> session = new HashMap<>();
        session.put("usuario", "El_Jolan_7");
        session.put("nombre", "Marco Holland");
        session.put("email", "hollandmarco@gmail.com");
        session.put("contraseña", "hashed_password");
        messageOut.setSession(session);

        cliente.sent(messageOut, new Message());

        System.out.println("User added to database from Cliente main method.");
    }*/


    /* Prueba mensajes conexiones clientes
    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        HashMap<String, Object> session = new HashMap<>();

        // Prueba de obtener seguidores
        session.put("id_logged", 1); // ID de ejemplo
        cliente.sentMessage("/getSeguidores", session);

        // Prueba de obtener seguidos
        cliente.sentMessage("/getSeguidos", session);
    }*/
