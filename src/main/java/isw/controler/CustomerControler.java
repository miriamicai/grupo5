package isw.controler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import isw.dao.CustomerDAO;
import isw.domain.Customer;
import isw.domain.PasswordSegura;

//import isw.domain.PasswordSegura;
import static isw.dao.CustomerDAO.getCliente;

public class CustomerControler {

    public void getCustomers(ArrayList<Customer> lista) {
        //devuelve todos los clientes
        CustomerDAO.getClientes(lista);
    }
    public Customer getCustomer(int id) {
        //se devuelve los datos del cliente en función de su id (sacado de nuestra base de datos)
        return(CustomerDAO.getCliente(id));
    }

    public int autentifLogin(String usuario, String password) {
        ArrayList<Customer> lista = new ArrayList<Customer>(); //ArrayList de Customers
        this.getCustomers(lista);

        Customer esUsuario = new Customer(usuario, password);

        for (Customer customer : lista) {

            //customer.getInfoPruebas();

            if (esUsuario.equals(customer)) {
                return customer.getId(); //devuelve el id si coincide con lo introducido por el usuario
            }
        }
        return 0;
    }

    public void addUser(String usuario, String nombre, String email, String password) throws SQLException, SQLException, SQLException {
        String passwordSegura = PasswordSegura.hashPassword(password);
        CustomerDAO.addUser(usuario, nombre, email, password);
    }



    /*
    public String getUserName(int id_logged){
        Customer customer = getCliente(id_logged);
        return customer.getNombreUsuario();
    }*/





    /* PRUEBA PARA OBTENER UNA LISTA DE CUSTOMERS A PARTIR DE LOS INT DE LOS SEGUIDORES DE UN USUARIO LOGUEADO
    public static void main(String[] args) {
       // Instanciar el controlador de Customer
        CustomerControler customerControler = new CustomerControler();

        // Supongamos que el ID del usuario logueado es 1
        int id_logged = 1;

        // Llamar al método getSeguidores
        ArrayList<Customer> seguidores = customerControler.getSeguidores(id_logged);

        // Verificar y mostrar los resultados
        if (seguidores.isEmpty()) {
            System.out.println("El usuario con ID " + id_logged + " no tiene seguidores.");
        } else {
            System.out.println("Lista de seguidores para el usuario con ID " + id_logged + ":");
            for (Customer seguidor : seguidores) {
                System.out.println("ID: " + seguidor.getId() + ", Nombre: " + seguidor.getNombre());
            }
        }
    }*/



    //PRUEBA PARA OBTENER UNA LISTA DE CUSTOMERS A PARTIR DE LOS INT DE LOS SEGUIDOS DE UN USUARIO LOGUEADO
    /*public static void main(String[] args) {
       //intancio customerControler
        CustomerControler customerControler = new CustomerControler();

        //conexiones del usuario 1
        int id_logged = 1;

        //GETSEGUIDORES  de controler
        ArrayList<Customer> seguidos = customerControler.getSeguidos(id_logged);

        //resultados
        if (seguidos.isEmpty()) {
            System.out.println("El usuario con ID " + id_logged + " no tiene seguidores.");
        } else {
            System.out.println("Lista de seguidos para el usuario con ID " + id_logged + ":");
            for (Customer seguido : seguidos) {
                System.out.println("ID: " + seguido.getId() + ", Nombre: " + seguido.getNombre());
            }
        }
    }*/

}
