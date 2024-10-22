package isw.controler;

import java.util.ArrayList;

import isw.dao.CustomerDAO;
import isw.domain.Customer;

public class CustomerControler {

    public void getCustomers(ArrayList<Customer> lista) {
        //devuelve todos los clientes
        CustomerDAO.getClientes(lista);
    }
    public Customer getCustomer(int id) {
        //se devuelve los datos del cliente en función de su id (sacado de nuestra base de datos)
        return(CustomerDAO.getCliente(id));
    }
}
