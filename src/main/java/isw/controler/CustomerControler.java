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
    //CustomerControler
    public void addUser(String usuario, String nombre, String email, String contraseña) throws SQLException, SQLException, SQLException {
        //CustomerDAO dao = new CustomerDAO();
        //String passwordSegura = PasswordSegura.hashPassword(contraseña);
        CustomerDAO.addUser(usuario, nombre, email, contraseña);
    }

    public void logRelease(int uid, String mid, String title, String artist, Date release){
        CustomerDAO.logRelease(uid, mid, title, artist, release);
    }
}
