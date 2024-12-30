package isw.controler;

import java.sql.SQLException;
import java.util.ArrayList;

import isw.dao.CustomerDAO;
import isw.domain.Customer;
import isw.domain.PasswordSegura;

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
    public void addUser(String usuario, String nombre, String email, String password) throws SQLException {
        String passwordSegura = PasswordSegura.hashPassword(password);
        CustomerDAO.addUser(usuario, nombre, email, passwordSegura);
    }
}
