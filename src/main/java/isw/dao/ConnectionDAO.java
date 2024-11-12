package isw.dao;

import java.sql.*;

import isw.configuration.PropertiesISW;

public class ConnectionDAO {
    private static ConnectionDAO connectionDAO;
    private Connection conexion;

    private ConnectionDAO() {
        String url = PropertiesISW.getInstance().getProperty("ddbb.connection");
        String user = PropertiesISW.getInstance().getProperty("ddbb.user");
        String password = PropertiesISW.getInstance().getProperty("ddbb.password");
        try {
            conexion = DriverManager.getConnection(url, user, password); //con la base de datos
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static ConnectionDAO getInstance() {
        if (connectionDAO == null) {
            connectionDAO=new ConnectionDAO();
        }
        return connectionDAO;
    }

    public Connection getConnection() {
        return conexion;
    }



    //IMPORTANTE
    /*public static void main(String[] args) {

        String url = PropertiesISW.getInstance().getProperty("ddbb.connection");
        String user = PropertiesISW.getInstance().getProperty("ddbb.user");
        String password = PropertiesISW.getInstance().getProperty("ddbb.password");

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement("SELECT * FROM usuarios");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                System.out.print(rs.getString(1));
                System.out.print(": ");
                System.out.println(rs.getString(2));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }*/
}