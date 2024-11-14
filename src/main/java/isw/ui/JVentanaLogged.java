package isw.ui;

import isw.cliente.Cliente;

import javax.swing.*;
import java.util.HashMap;

public class JVentanaLogged extends JVentana{

    public JVentanaLogged(HashMap<String, Object> session, Cliente cliente) {
        super();

        //obtener seguidos y seguidores a través de la clase cliente
        cliente.sentMessage("/getSeguidores", session);
        cliente.sentMessage("/getSeguidos", session);

        //creo el botón "Mi perfil"
        JButton btnMiPerfil = createStyledButton("Mi perfil");

        //colocar el botón
        getTopPanel().add(btnMiPerfil);


        btnMiPerfil.addActionListener(e -> {
            HashMap<String, Object> response = cliente.sentMessage("/getCustomer", session);

            //se tiene la respuesta con el cliente
            if (response != null && response.get("Customer") != null) {
                new UserProfileForm(response, cliente);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo recuperar la información del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }


    /*public static void main(String[] args) {
        JVentanaLogged ventanaLogged = new JVentanaLogged();
        ventanaLogged.setVisible(true);
    }*/

}
