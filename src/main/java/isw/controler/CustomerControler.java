package isw.controler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import isw.dao.CustomerDAO;
import isw.domain.Customer;
//import isw.domain.PasswordSegura;

public class CustomerControler {

    public void getCustomers(ArrayList<Customer> lista) {
        //devuelve todos los clientes
        CustomerDAO.getClientes(lista);
    }
    public Customer getCustomer(int id) {
        //se devuelve los datos del cliente en función de su id (sacado de nuestra base de datos)
        return(CustomerDAO.getCliente(id));
    }

    public void addUser(String usuario, String nombre, String email, String password) throws SQLException {
        String passwordSegura = PasswordSegura.hashPassword(password);
        CustomerDAO.addUser(usuario, nombre, email, passwordSegura);
    }

    public int login (String usuario, String password_nohashed){
        int id_logged = CustomerDAO.getClienteLogin(usuario, password_nohashed);
        System.out.println(id_logged);
        return id_logged; //devuelve el id del usuario para mantener la sesión activa
    }

    public void logRelease(int uid, String mid, String title, String artist, Date release){
        CustomerDAO.logRelease(uid, mid, title, artist, release);
    }
}
