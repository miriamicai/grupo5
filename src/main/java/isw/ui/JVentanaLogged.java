package isw.ui;

import isw.cliente.Cliente;

import javax.swing.*;
import java.util.HashMap;

public class JVentanaLogged extends JVentana{
    int idLogged;

    public JVentanaLogged(int idLogged, HashMap<String, Object> session, Cliente cliente) {
        super();
        this.idLogged = idLogged;
        this.setVisible(true);

        //creo el botón "Mi perfil"
        JButton btnMiPerfil = createStyledButton("Mi perfil");

        //colocar el botón
        getTopPanel().add(btnMiPerfil);

        btnMiPerfil.addActionListener(e -> {
            try {
                // Muestra un mensaje para confirmar que se detecta el clic
                System.out.println("Botón 'Mi perfil' presionado.");

                // Intenta abrir la ventana de perfil
                new UserProfileForm(idLogged, session, cliente);
                System.out.println("Ventana 'UserProfileForm' abierta correctamente.");
            } catch (Exception ex) {
                // Maneja excepciones y muestra el error en la consola
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al abrir la ventana de perfil: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


    }


    /*public static void main(String[] args) {
        JVentanaLogged ventanaLogged = new JVentanaLogged();
        ventanaLogged.setVisible(true);
    }*/

}