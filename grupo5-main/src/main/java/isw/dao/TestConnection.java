package isw.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection connection = ConnectionDAO.getConnection();

            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection to PostgreSQL successful!");
            }else{
                System.out.println("No connection.");
            }
            
            ConnectionDAO.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

