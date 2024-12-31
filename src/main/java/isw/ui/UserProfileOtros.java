package isw.ui;

import java.util.HashMap;

import isw.cliente.Cliente;

import javax.swing.*;

public class UserProfileOtros extends UserProfileForm {

    public UserProfileOtros(int id, HashMap<String, Object> session, Cliente cliente) {
        super(id, session, cliente);
        // No mostrar el botón "Añadir Seguidos"
    }

    @Override
    protected void addFollowingButton(JPanel buttonPanel) {
        // no se crea el botón
    }
}