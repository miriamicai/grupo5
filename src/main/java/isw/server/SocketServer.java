package isw.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import isw.configuration.PropertiesISW;
import isw.controler.ConexionesControler;
import isw.controler.CustomerControler;
import isw.domain.AutentifCustomer;
import isw.domain.Customer;
import isw.message.Message;

public class SocketServer extends Thread{

    public static int port = Integer.parseInt(PropertiesISW.getInstance().getProperty("port"));

    protected Socket socket; //se crea el socket para crear una comunicación bidireccional con el servidor

    private SocketServer(Socket socket) {
        this.socket = socket;
        //Configure connections
        System.out.println("New client connected from " + socket.getInetAddress().getHostAddress()); //mostrar IP ciente
        start(); //se inicia el hilo al recibir un Socket
    }

    public void run() {
        System.out.println("aparece en el RUN");
        InputStream in = null;
        OutputStream out = null;
        try { //se maneja la lógica principal del servidor para procesar solicitudes para enviar y leer archivos
            in = socket.getInputStream();
            out = socket.getOutputStream();
            System.out.println("después de in y out del servidor");

            //first read the object that has been sent
            //ObjectInputStream permite leer objetos en binario enviados por el cliente
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            //almaceno como Message el mensaje enviado por el cliente
            Message mensajeIn = (Message)objectInputStream.readObject();

            //Object to return information - lo mismo que antes pero con el mensaje que se envía al cliente
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            Message mensajeOut = new Message();

            HashMap<String,Object> session = mensajeIn.getSession(); //se devuelve la sesión
            CustomerControler customerControler; //CustomerControler que utilizaremos para interactuar con la bd
            ConexionesControler conexionesControler;

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

                case "/getCustomer": //me recupera un cliente -> muestra los datos
                    int id = (int) session.get("id_logged");
                    customerControler = new CustomerControler();
                    Customer cu = customerControler.getCustomer(id); //Customer con los datos sacados de la bd
                    if (cu!=null){ //solo si se encuentra el id en la base de datos
                        System.out.println("id:"+cu.getId()); //imprimo la info pedida
                    }else {
                        System.out.println("No encontrado en la base de datos");
                    }

                    mensajeOut.setContext("/getCustomerResponse");
                    session.put("Customer",cu); //meto el objeto en el hashmap de <String, Object>
                    mensajeOut.setSession(session); //lo agrego al mensaje
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/addUser":
                    customerControler = new CustomerControler();
                    String usuario = (String) session.get("usuario");
                    String nombre = (String) session.get("nombre");
                    String email = (String) session.get("email");
                    String password = (String) session.get("contraseña");
                    customerControler.addUser(usuario, nombre, email, password);

                    mensajeOut.setContext("/addUserResponse");
                    session.put("message", "User added successfully.");
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    System.out.println("Response sent to client: " + mensajeOut.getContext());
                    break;

                case "/login":
                    System.out.println("entra en /login");
                    customerControler = new CustomerControler();
                    String user = (String) session.get("usuario");
                    String passwrd = (String) session.get("contraseña");
                    int idLogged = customerControler.login(user,passwrd);
                    System.out.println(idLogged +"id en /login");

                    /*AutentifCustomer autentif = new AutentifCustomer();
                    int idLogged = autentif.VerificarLogin(user, passwrd);
                    System.out.println(idLogged);*/

                    if (idLogged != 0) {
                        session.put("id_logged", idLogged);
                        mensajeOut.setContext("/loginResponse");
                        session.put("message", "Login successfull");
                        //System.out.println(idLogged);
                    } else {
                        //session.put("id_logged", 0);
                        mensajeOut.setContext("/loginResponse");
                        session.put("message", "Error: Login unsuccessful");
                    }
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    System.out.println("Response sent to client: " + mensajeOut.getContext());
                    break;

                //NUEVO CASO PARA CONECTAR A USUARIOS
                case "/connectUser":
                    conexionesControler = new ConexionesControler();
                    int followerId = (int) session.get("followerId");
                    int followingId = (int) session.get("followingId");
                    conexionesControler.addConexion(followerId, followingId);

                    mensajeOut.setContext("/connectUserResponse");
                    session.put("message", "Connection successfully established");
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    System.out.println("Response sent to client: " + mensajeOut.getContext());
                    break;

                case "/getSeguidores":
                    conexionesControler = new ConexionesControler();
                    id = (int) session.get("id_logged");
                    List<Customer> seguidores = conexionesControler.getMisSeguidores(id);
                    mensajeOut.setContext("/getSeguidoresResponse");
                    session.put("Seguidores", seguidores);
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/getSeguidos":
                    conexionesControler = new ConexionesControler();
                    id = (int) session.get("id_logged");
                    List<Customer> seguidos = conexionesControler.getMisSeguidos(id);
                    mensajeOut.setContext("/getSeguidosResponse");
                    session.put("Seguidos", seguidos);
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/logRelease":
                    customerControler = new CustomerControler();
                    id = (int) session.get("id_usuario");
                    String mid = (String) session.get("id_musical");
                    String title = (String) session.get("titulo");
                    String artista = (String) session.get("artista");
                    Date estreno = (Date) session.get("estreno");
                    customerControler.logRelease(id, mid, title, artista, estreno);
                    mensajeOut.setContext("/logReleaseResponse");

                default:
                    System.out.println("\nParámetro no encontrado");
                    break;
            }


        } catch (IOException ex) {
            System.out.println("Unable to get streams from client");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            while (true) {
                //Socket socketCliente = server.accept();
                //SocketServer socketServer = new SocketServer(socketCliente);
                //Thread hilo = new Thread(socketServer);
                //hilo.start();
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