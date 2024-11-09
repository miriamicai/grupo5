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
                lista.add(new Customer(rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(6), rs.getString(7)));
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


    public static void main(String[] args) {

        ArrayList<Customer> lista = new ArrayList<>();
        CustomerDAO.getClientes(lista);


        for (Customer customer : lista) {
            System.out.println("He leído el id: "+customer.getId()+" con nombre: "+customer.getId());
        }


    }

}
