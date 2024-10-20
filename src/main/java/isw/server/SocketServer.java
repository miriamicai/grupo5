package isw.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import isw.configuration.PropertiesISW;
import isw.controler.CustomerControler;
import isw.domain.Customer;
import isw.message.Message;

public class SocketServer extends Thread {
    public static int port = Integer.parseInt(PropertiesISW.getInstance().getProperty("port"));

    protected Socket socket; //un socket es.................................

    private SocketServer(Socket socket) {
        this.socket = socket;
        //Configure connections
        System.out.println("New client connected from " + socket.getInetAddress().getHostAddress()); //imprimir dirección IP del ciente
        start(); //se inicia el hilo al recibir un Socket
    }

    public void run() {
        InputStream in = null;
        OutputStream out = null;
        try { //se maneja la lógica principal del servidor para procesar solicitudes para enviar y leer archivos
            in = socket.getInputStream();
            out = socket.getOutputStream();

            //first read the object that has been sent
            //ObjectInputStream permite leer objetos en binario enviados por el cliente
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            Message mensajeIn = (Message)objectInputStream.readObject(); //almaceno como Message el mensaje enviado por el cliente

            //Object to return information - lo mismo que antes pero con el mensaje que se envía al cliente
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            Message mensajeOut = new Message();

            HashMap<String,Object> session = mensajeIn.getSession(); //se devuelve la sesión
            CustomerControler customerControler; //CustomerControler que utilizaremos para interactuar con la bd

            switch (mensajeIn.getContext()) { //dependiendo del tipo de petición, tendrá un contexto
                case "/getCustomers"://contexto 1. me recupera todos los clientes
                    customerControler = new CustomerControler();
                    ArrayList<Customer> lista = new ArrayList<Customer>(); //ArrayList de Customers
                    customerControler.getCustomers(lista);
                    mensajeOut.setContext("/getCustomersResponse");
                    //HashMap<String,Object> session=new HashMap<String, Object>();
                    session.put("Customers",lista);
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    break;
                case "/getCustomer": //me recupera un cliente
                    int id = (int) session.get("id");
                    customerControler = new CustomerControler();
                    Customer cu = customerControler.getCustomer(id);
                    if (cu!=null){
                        System.out.println("id:"+cu.getId()); //consigo la info pedida
                    }else {
                        System.out.println("No encontrado en la base de datos");
                    }

                    mensajeOut.setContext("/getCustomerResponse");
                    session.put("Customer",cu); //meto el objeto en cuestión
                    mensajeOut.setSession(session); //lo agrego al mensaje
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                default:
                    System.out.println("\nParámetro no encontrado");
                    break;
            }

            //Lógica del controlador
            //System.out.println("\n1.- He leído: "+mensaje.getContext());
            //System.out.println("\n2.- He leído: "+(String)mensaje.getSession().get("Nombre"));



            //Prueba para esperar
		    /*try {
		    	System.out.println("Me duermo");
				Thread.sleep(15000);
				System.out.println("Me levanto");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
            // create an object output stream from the output stream so we can send an object through it
			/*ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);

			//Create the object to send
			String cadena=((String)mensaje.getSession().get("Nombre"));
			cadena+=" añado información";
			mensaje.getSession().put("Nombre", cadena);
			//System.out.println("\n3.- He leído: "+(String)mensaje.getSession().get("Nombre"));
			objectOutputStream.writeObject(mensaje);*
			*/

        } catch (IOException ex) {
            System.out.println("Unable to get streams from client");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally { //cerrar todos los flujos y socket al completar la conexión
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("SocketServer Example - Listening port "+port);
        ServerSocket server = null; //OBJETO DE TIPO SOVERSOCKET QUÉ HACE
        try {
            server = new ServerSocket(port);
            while (true) {
                /**
                 * create a new {@link SocketServer} object for each connection
                 * this will allow multiple client connections
                 */
                new SocketServer(server.accept());
            }
        } catch (IOException e) {
            System.out.println("Unable to start server." + e.getMessage());
        } finally {
            try {
                if (server != null)
                    server.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}