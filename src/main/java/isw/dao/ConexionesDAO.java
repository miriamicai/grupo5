package isw.dao;

import isw.domain.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConexionesDAO {

    public HashMap<Integer, List<Integer>> todasConexiones;

    public static void getSeguidosConexiones(HashMap<Integer, List<Integer>> todasConexiones) { //devuelve lista de Clientes
        Connection conexion = ConnectionDAO.getInstance().getConnection(); //instance de la DAO -> como objeto Connection
        try (PreparedStatement pst = conexion.prepareStatement("SELECT id_seguidor, id_seguido FROM seguidores");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                //creo un hashmap con todas las conexiones que aparecen en la base de datos

                int idSeguidor = rs.getInt(1); //columna 1
                int idSeguido = rs.getInt(2); //columna 2

                if (todasConexiones.containsKey(idSeguidor)) {
                    //si la clave ya existe, solo añado al nuevo seguido
                    todasConexiones.get(idSeguidor).add(idSeguido);
                } else {
                    //si la clave no existe, creo una nueva lista y lo añado al mapa
                    List<Integer> listaConexiones = new ArrayList<>();
                    listaConexiones.add(idSeguido);
                    todasConexiones.put(idSeguidor, listaConexiones);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getSeguidoresConexiones(HashMap<Integer, List<Integer>> todasConexiones) { //devuelve lista de Clientes
        Connection conexion = ConnectionDAO.getInstance().getConnection(); //instance de la DAO -> como objeto Connection
        try (PreparedStatement pst = conexion.prepareStatement("SELECT id_seguidor, id_seguido FROM seguidores");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                //creo un hashmap con todas las conexiones que aparecen en la base de datos

                int idSeguidor = rs.getInt(1); //columna 1
                int idSeguido = rs.getInt(2); //columna 2

                if (todasConexiones.containsKey(idSeguido)) {
                    //si la clave ya existe, solo añado al nuevo seguido
                    todasConexiones.get(idSeguido).add(idSeguidor);
                } else {
                    //si la clave no existe, creo una nueva lista y lo añado al mapa
                    List<Integer> listaConexiones = new ArrayList<>();
                    listaConexiones.add(idSeguidor);
                    todasConexiones.put(idSeguido, listaConexiones);
                }

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static List<Integer> getSeguidoresCliente(int id) { //se usa en CustomerControler
        Connection conexion = ConnectionDAO.getInstance().getConnection();
        List<Integer> conexionesCliente = new ArrayList<>();

        try (PreparedStatement pst = conexion.prepareStatement
                ("SELECT id_seguidor, id_seguido FROM seguidores WHERE id_seguido = ?")) { //evitar sql injection
            pst.setInt(1, id);


            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    conexionesCliente.add(rs.getInt(1));
                }
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return conexionesCliente;
    }


    public static List<Integer> getSeguidosCliente(int id) { //se usa en CustomerControler
        Connection conexion = ConnectionDAO.getInstance().getConnection();
        List<Integer> conexionesCliente = new ArrayList<>();

        try (PreparedStatement pst = conexion.prepareStatement
                ("SELECT id_seguidor, id_seguido FROM seguidores WHERE id_seguidor = ?")) { //evitar sql injection
            pst.setInt(1, id);


            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    conexionesCliente.add(rs.getInt(2));
                }
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return conexionesCliente;
    }


    public static void addConexion(int idSeguidor, int idSeguido) throws SQLException { //CAMBIAR A ESTATICO

        Connection conexion = ConnectionDAO.getInstance().getConnection();
        String query = "INSERT INTO seguidores (id_seguidor, id_seguido) VALUES (?, ?)";

        try (PreparedStatement pst = conexion.prepareStatement(query)) {
            pst.setInt(1, idSeguidor);
            pst.setInt(2, idSeguido);

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


}







    /*
    //VERIFICAR SEGUIDOS DE TODOS LOS CLIENTES
     public static void main(String[] args) {
        HashMap<Integer, List<Integer>> lista = new HashMap<>(); // Inicialización correcta del HashMap
        ConexionesDAO.getSeguidosConexiones(lista);

        // Iterar sobre el HashMap y mostrar las conexiones
        for (Map.Entry<Integer, List<Integer>> entry : lista.entrySet()) {
            int idSeguidor = entry.getKey();
            List<Integer> idSeguidos = entry.getValue();

            System.out.println("El usuario con ID " + idSeguidor + " sigue a: " + idSeguidos);
        }
    }*/

    /*
    //VERIFICAR SEGUIDORES DE TODOS LOS CLIENTES
    public static void main(String[] args) {
        HashMap<Integer, List<Integer>> lista = new HashMap<>(); //inicializo el hashmap
        ConexionesDAO.getSeguidoresConexiones(lista);

        //iiteración para mostrar las conexiones
        for (Map.Entry<Integer, List<Integer>> entry : lista.entrySet()) {
            int idSeguido = entry.getKey();  //obtener idSeguido
            List<Integer> seguidores = entry.getValue();  //obtenemos la lista de seguidores

            //imprimir el idSeguido y sus seguidores
            System.out.println("El usuario con ID " + idSeguido + " es seguido por los usuarios con IDs: " + seguidores);
        }
    }
     */


    /*
    AÑADIR CONEXIÓN ENTRE CLIENTES
    public static void main(String[] args) {

        // Llamar al método para agregar una nueva conexión entre los usuarios
        int id1 = 3;  // ID del seguidor
        int id2 = 2;    // ID del seguido

        // Llamada al método addConexion
        try {
            ConexionesDAO.addConexion(id1, id2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/



    //ELIMINAR UNA CONEXIÓN YA EXISTENTE EN LA BASE DE DATOS
