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

    public int VerificarLogin(String usuario, String password) {

        ArrayList<Customer> lista = new ArrayList<Customer>(); //ArrayList de Customers
        this.customerControler.getCustomers(lista);

        Customer esUsuario = new Customer(usuario, password);

        for (Customer customer : lista) {

            //customer.getInfoPruebas();

            if (esUsuario.equals(customer)) {
                return customer.getId(); //devuelve el id si coincide con lo introducido por el usuario
            }
        }
        return 0;
    }

}
