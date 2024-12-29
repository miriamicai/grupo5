package isw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import isw.domain.Customer;

public class CustomerDAO {

    public static void getClientes(ArrayList<Customer> lista) { //devuelve lista de Clientes
        Connection conexion =ConnectionDAO.getInstance().getConnection(); //instance de la DAO -> como objeto Connection
        try (PreparedStatement pst = conexion.prepareStatement("SELECT * FROM usuarios");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                //creo una lista con todos los clientes que aparecen en la base de datos
                lista.add(new Customer(rs.getString(1),rs.getString(2)));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static Customer getCliente(int id) { //se usa en CustomerControler
        Connection conexion = ConnectionDAO.getInstance().getConnection();
        Customer cu = null; //es nulo
        try (PreparedStatement pst = conexion.prepareStatement("SELECT * FROM usuarios WHERE id="+id);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                cu = new Customer(rs.getString(1),rs.getString(2));
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return cu; //devuelve la información del customer si coincide el id, si no será nulo
    }

    // Method to add a new user to the users table
    public void addUser(String username, String email, String passwordHash) throws SQLException {
        String query = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionDAO.getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {

            // Set the values for the prepared statement
            pst.setString(1, username);
            pst.setString(2, email);
            pst.setString(3, passwordHash);

            // Execute the insert statement
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User added successfully!");
            } else {
                System.out.println("Failed to add user.");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding user: " + e.getMessage());
            throw e;
        }
    }


    public static void main(String[] args) {

        ArrayList<Customer> lista = new ArrayList<>();
        CustomerDAO.getClientes(lista);


        for (Customer customer : lista) {
            System.out.println("He leído el id: "+customer.getId()+" con nombre: "+customer.getName());
        }


    }

}
