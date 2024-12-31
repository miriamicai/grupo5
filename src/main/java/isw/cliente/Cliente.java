package isw.cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import isw.configuration.PropertiesISW;
import isw.controler.ConexionesControler;
import isw.controler.CustomerControler;
import isw.domain.Customer;
import isw.message.Message;

public class Cliente {
    private String host;
    private int port;
    public ArrayList<Customer> seguidores;
    public ArrayList<Customer> seguidos;

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
        //Create a cliente class                             -> por qué se necesita una clase Cliente??
        //Client cliente=new Client(host, port);

        //HashMap<String,Object> session=new HashMap<String, Object>();
        //session.put("/getCustomer",""); //clase CustomerControler -> se saca de la base de datos

        Message mensajeEnvio = new Message();
        Message mensajeVuelta = new Message();
        mensajeEnvio.setContext(Context);///getCustomer"
        mensajeEnvio.setSession(session);
        this.sent(mensajeEnvio, mensajeVuelta);


        switch (mensajeVuelta.getContext()) { //Devolver los Customers dependiendo del mensaje que devuelva el servidor (mensajeVuelta)
            case "/getCustomersResponse": //CustomerS (varios)
                ArrayList<Customer> customerList = (ArrayList<Customer>) (mensajeVuelta.getSession().get("Customer"));
                for (Customer customer : customerList) { //se recorre la tabla de clientes y los muestra por pantalla
                    System.out.println("He leído el id: " + customer.getId() + " con nombre: " + customer.getPassword());
                }
                break;
            case "/getCustomerResponse": //1 Customer solo
                session = mensajeVuelta.getSession();
                Customer customer = (Customer) (session.get("Customer"));
                if (customer != null) {
                    System.out.println("He leído el id: " + customer.getId() + " con nombre: " + customer.getPassword());
                } else {
                    System.out.println("No se ha recuperado nada de la base de datos");
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

            //CAMBIO 1
            case "/loginResponse": //tengo session con el id_logged
                if (session != null && session.containsKey("id_logged")) {
                    int idLogged = (int) session.get("id_logged");

                    if (idLogged!=0) {
                        //session.put("id_logged", idLogged);
                        System.out.println("Inicio de sesión exitoso. ID de usuario: " + idLogged);
                    }
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
            case "/getSeguidoresResponse": // Seguidores
                seguidores = (ArrayList<Customer>) mensajeVuelta.getSession().get("Seguidores");
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

            case "logReleaseResponse":
                String m = (String) mensajeVuelta.getSession().get("message");
                if (m != null) {
                    System.out.println("Server response: " + m);
                } else if (mensajeVuelta.getSession().containsKey("error")) {
                    System.out.println("Error: " + mensajeVuelta.getSession().get("error"));
                } else {
                    System.out.println("Unexpected response from server for /logReleaseResponse");
                }
                break;

            default:

                System.out.println("\nError a la vuelta");
                break;

        }
        //System.out.println("3.- En Main.- El valor devuelto es: "+((String)mensajeVuelta.getSession().get("Nombre")));
        return session;
    }

    public ArrayList<Customer> getSeguidoresList(){
        return seguidores;
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

                //Create the objetct to send
                System.out.println("Antes output");
                objectOutputStream.writeObject(messageOut);
                System.out.println("Después output");

                // create a DataInputStream so we can read data from it.
                ObjectInputStream objectInputStream = new ObjectInputStream(in);
                Message msg = (Message) objectInputStream.readObject();
                messageIn.setContext(msg.getContext());
                messageIn.setSession(msg.getSession());
                System.out.println("Llega hasta antes de los catch");
		        /*System.out.println("\n1.- El valor devuelto es: "+messageIn.getContext());
		        String cadena=(String) messageIn.getSession().get("Nombre");
		        System.out.println("\n2.- La cadena devuelta es: "+cadena);*/

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

        System.out.println("Connection established from Cliente establishConnection() method.");
    }

    public void getFollowers(int id){
        Message messageOut = new Message();
        messageOut.setContext("/getSeguidores");
        HashMap<String, Object> session = new HashMap<>();
        session.put("id_logged", id);
        messageOut.setSession(session);
        sent(messageOut, new Message());
        System.out.println("Followers list retrieved from Cliente method.");
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

    public void logRelease(int uid, String mid, String title, String artist, Date release){
        Message messageOut = new Message();
        messageOut.setContext("/logRelease");

        HashMap<String, Object> session = new HashMap<>();
        session.put("id_usuario", uid);
        session.put("id_musical", mid);
        session.put("titulo", title);
        session.put("artista", artist);
        session.put("estreno", release);

        sent(messageOut, new Message());
        System.out.println("Release logged successfully via release ID.");
    }

    public void logRelease(int uid, String title,String artist, Date release){
        Message messageOut = new Message();
        messageOut.setContext("/logRelease");

        HashMap<String, Object> session = new HashMap<>();
        session.put("id_usuario", uid);
        session.put("titulo", title);
        session.put("artista", artist);
        session.put("estreno", release);

        sent(messageOut, new Message());
        System.out.println("Release logged successfully via title and artist.");
    }

    public List<Customer> getSeguidores(int id){
        /*Message messageOut = new Message();
        messageOut.setContext("/getSeguidores");
        HashMap<String, Object> session = new HashMap<>();
        session.put("id_logged", id);
        messageOut.setSession(session);
        sent(messageOut, new Message());
        System.out.println("Followers list retrieved from Cliente method.");*/

        ConexionesControler conexionesControler = new ConexionesControler();
        List<Customer> seguidores = conexionesControler.getMisSeguidores(id);
        return seguidores;
    }

    public List<Customer> getSeguidos(int id){
        /*Message messageOut = new Message();
        messageOut.setContext("/getSeguidos");
        HashMap<String, Object> session = new HashMap<>();
        session.put("id_logged", id);
        messageOut.setSession(session);
        sent(messageOut, new Message());
        System.out.println("Following list retrieved from Cliente method.");*/
        ConexionesControler conexionesControler = new ConexionesControler();
        List<Customer> seguidos = conexionesControler.getMisSeguidos(id);
        return seguidos;
    }

    public Customer getCustomer(int id) {
        /*Message messageOut = new Message();
        messageOut.setContext("/getCustomer");

        //se envía el id del cliente en el HashMap
        HashMap<String, Object> session = new HashMap<>();
        session.put("id", id);
        messageOut.setSession(session);

        Message messageIn = new Message();
        sent(messageOut, messageIn); //se envía el mensaje al servidor

        //procesar la respuesta del SocketServer
        Customer customer = null;
        if ("/getCustomerResponse".equals(messageIn.getContext())) {
            customer = (Customer) messageIn.getSession().get("Customer");
            if (customer != null) {
                System.out.println("Cliente recuperado: " + customer.getId() + ", " + customer.getNombreUsuario());
            } else {
                System.out.println("Cliente no encontrado.");
            }
        } else {
            System.out.println("Respuesta inesperada del servidor.");
        }
        return customer;*/
        CustomerControler customerControler = new CustomerControler();
        Customer customer = customerControler.getCustomer(id);
        return customer;
    }


    public ArrayList<Customer> getCustomers(){
        /*Message messageOut = new Message();
        messageOut.setContext("/getCustomers");

        //se envía el id del cliente en el HashMap
        HashMap<String, Object> session = new HashMap<>();
        messageOut.setSession(session);

        Message messageIn = new Message();
        sent(messageOut, messageIn); //se envía el mensaje al servidor

        //procesar la respuesta del SocketServer
        ArrayList<Customer> customers = null;
        if ("/getCustomerResponse".equals(messageIn.getContext())) {
            customers = (ArrayList<Customer>) messageIn.getSession().get("Customers");
            if (customers != null) {
                System.out.println("Cliente: Customers recuperados.");
            } else {
                System.out.println("Cliente: No hay customers en la base de datos.");
            }
        } else {
            System.out.println("Cliente: Respuesta inesperada del servidor.");
        }
        return customers;*/
        CustomerControler customerControler = new CustomerControler();
        ArrayList<Customer> customers = new ArrayList<Customer>();
        ArrayList<Customer> clientes = customerControler.getCustomers(customers);
        return clientes;
    }


    //CAMBIO2
    public int login(HashMap<String, Object> session) {
        // Enviar el mensaje al servidor
        //System.out.println("mensaje a enviar");

        //HashMap<String, Object> respuesta = this.sentMessage("/login", session);

        //System.out.println("mensaje enviado al servidor");
        /*Message messageOut = new Message();
        messageOut.setContext("/login");
        messageOut.setSession(session);

        System.out.println("si lo envío desde aquí, aparece:");
        sent(messageOut, new Message());*/

        CustomerControler customerControler = new CustomerControler();
        String user = (String) session.get("usuario");
        String passwrd = (String) session.get("contraseña");
        int idLogged = customerControler.login(user,passwrd);
        System.out.println(idLogged);
        return idLogged;

        // Procesar la respuesta
        /*if (respuesta != null && respuesta.containsKey("id_logged")) {
            int idLogged = (int) respuesta.get("id_logged");

            if (idLogged!=0) {
                //session.put("id_logged", idLogged);
                System.out.println("Inicio de sesión exitoso. ID de usuario: " + idLogged);
                return true;
            } else {
                System.out.println("Error en el inicio de sesión.");
            }
        } else {
            System.out.println("Respuesta inesperada del servidor.");
        }
        return false;*/
    }



    public static void main(String[] args) {
        Cliente c = new Cliente();
        c.establishConnection(25, 2);
    }
}