package isw.domain;

import java.util.ArrayList;

import isw.controler.CustomerControler;

//Añadir autentificación también con correo y contraseña
public class AutentifCustomer {

    //private String usuario;
    //private String password;
    private CustomerControler customerControler;

    public AutentifCustomer(){
        this.customerControler = new CustomerControler();
    }

    public boolean VerificarLogin(String usuario, String password) {

        ArrayList<Customer> lista = new ArrayList<Customer>(); //ArrayList de Customers
        this.customerControler.getCustomers(lista);

        Customer esUsuario = new Customer(usuario, password);

        for (Customer customer : lista) {

            //customer.getInfoPruebas();

            if (esUsuario.equals(customer)) {
                return true; //si coincide lo introducido con los datos de algún cliente en la base de datos
            }
        }
        return false;
    }
}
