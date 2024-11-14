package isw.domain;

import java.util.ArrayList;

import isw.controler.CustomerControler;

//Añadir autentificación también con correo y contraseña
public class AutentifCustomer {

    //private String usuario;
    //private String password;
    public CustomerControler customerControler;

    public AutentifCustomer(){
        this.customerControler = new CustomerControler();
    }

    /*public AutentifCustomer(String usuario, String password){
        this.usuario = usuario;
        this.password = password;
    }

    public AutentifCustomer() {
        this.usuario = "";
        this.password = "";
    }*/

    int id_logged;

    public int VerificarLogin(String usuario, String password) {

        ArrayList<Customer> lista = new ArrayList<Customer>(); //ArrayList de Customers
        this.customerControler.getCustomers(lista);
        System.out.println(lista);

        Customer esUsuario = new Customer(usuario, password);

        for (Customer customer : lista) {

            //customer.getInfoPruebas();

            if (esUsuario.equals(customer)) {
                //System.out.println("The id of the user trying to log in is: " + customer.getId());
                UserSession.getInstance().setUserId(customer.getId());
                id_logged = customer.getId();
                //System.out.println("And userSession states that the logged in id is now: " +  UserSession.getInstance().getUserId());
                return id_logged; //si coincide lo introducido con los datos de algún cliente en la base de datos
            }
        }
        return 0;
    }

    /*public static void main(String[] args) {
      System.out.println("But if I run it through here the id is: " + UserSession.getInstance().getUserId());
    }*/
}
