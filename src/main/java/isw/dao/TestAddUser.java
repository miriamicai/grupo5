package isw.dao;

import java.sql.SQLException;

public class TestAddUser {
    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();

        String usuario = "El_Jolan_2";
        String nombre = "Marco Holland";
        String email = "hollandmarco@gmail.com";
        String contraseña = "hashed_password";

        try {
            customerDAO.addUser(usuario, nombre, email, contraseña);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

