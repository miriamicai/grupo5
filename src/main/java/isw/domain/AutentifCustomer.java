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

    public boolean VerificarLogin(String usuario, String password_nohash, String password_hash) {

        ArrayList<Customer> lista = new ArrayList<Customer>(); //ArrayList de Customers
        this.customerControler.getCustomers(lista);
        //System.out.println(lista);

        Customer esUsuario = new Customer(usuario, password_nohash);
        //esUsuario.getInfoPruebas(); //imprimir contraseña NO HASHEDADA

        for (Customer customer : lista) {

            if (esUsuario.equals(customer)) { //con equals comprueba que coincidan los nombres de usuario o correos
                if (PasswordSegura.comprobarPassword(password_nohash, password_hash)) {
                    //UserSession.getInstance().setUserId(customer.getId());
                    //customer.setId(id_logged);
                    System.out.println("Login exitoso para el usuario: " + usuario);
                    return true;
                } else {
                    System.out.println("Contraseña incorrecta para el usuario: " + usuario);
                    return false; // Contraseña incorrecta
                }
            }
        }
        System.out.println("falso");
        return false;
    }



    /*public static void main(String[] args) {
      System.out.println("But if I run it through here the id is: " + UserSession.getInstance().getUserId());
    }*/
}