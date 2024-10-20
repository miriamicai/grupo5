package isw.controler;

import java.util.ArrayList;

import isw.dao.CustomerDAO;
import isw.domain.Customer;

public class CustomerControler {

    public void getCustomers(ArrayList<Customer> lista) {
        CustomerDAO.getClientes(lista);
    }
    public Customer getCustomer(int id) {return(CustomerDAO.getCliente(id));} //hace referencia al cliente (su id) que aparece en nuestra base de datos isw
}
