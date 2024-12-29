package isw.domain;

import java.util.ArrayList;

import isw.controler.CustomerControler;

//Añadir autentificación también con correo y contraseña
public class AutentifCustomer {

    public CustomerControler customerControler;
    public int id_logged;

    public AutentifCustomer(){
        this.customerControler = new CustomerControler();
    }

    public int VerificarLogin(String usuario, String password) {

        ArrayList<Customer> lista = new ArrayList<Customer>(); //ArrayList de Customers
        this.customerControler.getCustomers(lista);
        //System.out.println(lista);

        Customer esUsuario = new Customer(usuario, password);

        for (Customer customer : lista) {

            //customer.getInfoPruebas();

            if (esUsuario.equals(customer)) {

                if (PasswordSegura.comprobarPassword(password, customer.getPassword())) {
                    UserSession.getInstance().setUserId(customer.getId());
                    id_logged = customer.getId();
                    System.out.println("Login exitoso para el usuario: " + usuario);
                    return id_logged;
                } else {
                    System.out.println("Contraseña incorrecta para el usuario: " + usuario);
                    return 0; // Contraseña incorrecta
                }

                /*//System.out.println("The id of the user trying to log in is: " + customer.getId());
                UserSession.getInstance().setUserId(customer.getId());
                id_logged = customer.getId();
                //System.out.println("And userSession states that the logged in id is now: " +  UserSession.getInstance().getUserId());
                System.out.println(id_logged);
                return id_logged; //si coincide lo introducido con los datos de algún cliente en la base de datos*/
            }
        }
        System.out.println("0");
        return 0;
    }




    public boolean VerificarLoginTest(String usuario, String password) {

        ArrayList<Customer> lista = new ArrayList<Customer>(); //ArrayList de Customers
        this.customerControler.getCustomers(lista);
        //System.out.println(lista);

        Customer esUsuario = new Customer(usuario, password);

        for (Customer customer : lista) {

            //customer.getInfoPruebas();

            if (esUsuario.equals(customer)) {

                if (PasswordSegura.comprobarPassword(password, customer.getPassword())) {
                    UserSession.getInstance().setUserId(customer.getId());
                    id_logged = customer.getId();
                    System.out.println("Login exitoso para el usuario: " + usuario);
                    return true;
                } else {
                    System.out.println("Contraseña incorrecta para el usuario: " + usuario);
                    return false; // Contraseña incorrecta
                }

                /*//System.out.println("The id of the user trying to log in is: " + customer.getId());
                UserSession.getInstance().setUserId(customer.getId());
                id_logged = customer.getId();
                //System.out.println("And userSession states that the logged in id is now: " +  UserSession.getInstance().getUserId());
                System.out.println(id_logged);
                return id_logged; //si coincide lo introducido con los datos de algún cliente en la base de datos*/
            }
        }
        System.out.println("0");
        return false;
    }


}
