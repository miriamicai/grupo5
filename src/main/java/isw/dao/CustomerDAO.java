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
                lista.add(new Customer(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7)));
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
                cu = new Customer(rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7));
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return cu; //devuelve la información del customer si coincide el id, si no será nulo
    }

    // Método para añadir usuarios a la tabla (revisado para evitar duplicacion)
    public void addUser(String usuario, String nombre, String email, String contraseña) throws SQLException {
        Connection conexion = ConnectionDAO.getInstance().getConnection();
        String query = "INSERT INTO users (usuario, nombre, email, contraseña) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = conexion.prepareStatement(query)) {
            pst.setString(1, usuario);
            pst.setString(2, nombre);
            pst.setString(3, email);
            pst.setString(4, contraseña);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User added successfully, amazing");
            } else {
                System.out.println("Failed to add user oh no");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding user: " + e.getMessage());
            throw e; // rethrow exception to allow SocketServer to handle it
        }
    }

    public void addUser(String usuario, String nombre, String email, String password) throws SQLException { //CAMBIAR A ESTATICo
        Connection conexion = ConnectionDAO.getInstance().getConnection();
        String query = "INSERT INTO users (usuario, nombre, email, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = conexion.prepareStatement(query)) {
            pst.setString(1, usuario);
            pst.setString(2, nombre);
            pst.setString(3, email);
            pst.setString(4, password);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User added successfully, amazing");
            } else {
                System.out.println("Failed to add user oh no");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding user: " + e.getMessage());
            throw e; // rethrow exception to allow SocketServer to handle it
        }
    }


    /*public static void main(String[] args) {

        ArrayList<Customer> lista = new ArrayList<>();
        CustomerDAO.getClientes(lista);


        for (Customer customer : lista) {
            System.out.println("He leído el id: "+customer.getId()+" con nombre: "+customer.getId());
        }


    }*/

}
