package isw.ui;

import isw.cliente.Cliente;

import javax.swing.*;
import java.util.HashMap;

public class JVentanaLogged extends JVentana{

    public JVentanaLogged(HashMap<String, Object> session, Cliente cliente) {
        super();
        this.setVisible(true);

        //creo el botón "Mi perfil"
        JButton btnMiPerfil = createStyledButton("Mi perfil");

        //colocar el botón
        getTopPanel().add(btnMiPerfil);


        btnMiPerfil.addActionListener(e -> {

            new UserProfileForm();
            /*
            //System.out.println("Detecta el botón");
            HashMap<String, Object> response = cliente.sentMessage("/getCustomerResponse", session); //FALLO EN ESTA LÍNEA
            System.out.println("Conexión en cliente");

            //se tiene la respuesta con el cliente
            if (response != null && response.get("Customer") != null) {
                new UserProfileForm(response, cliente);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo recuperar la información del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }*/
        });

    }


    /*public static void main(String[] args) {
        JVentanaLogged ventanaLogged = new JVentanaLogged();
        ventanaLogged.setVisible(true);
    }*/

}
