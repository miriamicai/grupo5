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
        this.host=host;
        this.port=port;
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

        System.out.println("Host: "+host+" port"+port);
        //Create a cliente class                             -> por qué se necesita una clase Cliente??
        //Client cliente=new Client(host, port);

        //HashMap<String,Object> session=new HashMap<String, Object>();
        //session.put("/getCustomer",""); //clase CustomerControler -> se saca de la base de datos

        Message mensajeEnvio=new Message();
        Message mensajeVuelta=new Message();
        mensajeEnvio.setContext(Context);///getCustomer"
        mensajeEnvio.setSession(session);
        this.sent(mensajeEnvio,mensajeVuelta);


        switch (mensajeVuelta.getContext()) { //Devolver los Customers dependiendo del mensaje que devuelva el servidor (mensajeVuelta)
            case "/getCustomersResponse": //CustomerS (varios)
                ArrayList<Customer> customerList=(ArrayList<Customer>)(mensajeVuelta.getSession().get("Customer"));
                for (Customer customer : customerList) { //se recorre la tabla de clientes y los muestra por pantalla
                    System.out.println("He leído el id: "+customer.getId()+" con nombre: "+customer.getName());
                }
                break;
            case "/getCustomerResponse": //1 Customer solo
                session=mensajeVuelta.getSession();
                Customer customer =(Customer) (session.get("Customer"));
                if (customer!=null) {
                    System.out.println("He leído el id: " + customer.getId() + " con nombre: " + customer.getName());
                }else {
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

                //Create the objetct to send
                objectOutputStream.writeObject(messageOut);

                // create a DataInputStream so we can read data from it.
                ObjectInputStream objectInputStream = new ObjectInputStream(in);
                Message msg=(Message)objectInputStream.readObject();
                messageIn.setContext(msg.getContext());
                messageIn.setSession(msg.getSession());
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

    public static void main(String[] args) {

    }

}
