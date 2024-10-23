package isw.dao;

import java.sql.SQLException;

public class TestAddUser {
    public static void main(String[] args) {
        // Create an instance of CustomerDAO
        CustomerDAO customerDAO = new CustomerDAO();

        // Example data for a new user
        String username = "john_doe";
        String email = "john.doe@example.com";
        String passwordHash = "hashed_password_here";  // Hash the password before passing

        try {
            // Call the addUser method to insert the new user
            customerDAO.addUser(username, email, passwordHash);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
