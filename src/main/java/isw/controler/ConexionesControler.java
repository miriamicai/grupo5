package isw.controler;

import isw.dao.ConexionesDAO;
import isw.dao.CustomerDAO;
import isw.domain.Customer;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConexionesControler {

    public void getTodosSeguidores(HashMap<Integer, List<Integer>> lista) {
        //devuelve todos los clientes
        ConexionesDAO.getSeguidoresConexiones(lista);
    }

    public void getTodosSeguidos(HashMap<Integer, List<Integer>> lista){
        ConexionesDAO.getSeguidosConexiones(lista);
    }

    //PARA UN ÚNICO USUARIO:
    public List<Customer> getMisSeguidores(int id) {
        List<Integer> idsSeguidores = ConexionesDAO.getSeguidoresCliente(id);
        CustomerControler customerControler = new CustomerControler();
        List<Customer> seguidores = new ArrayList<>();
        for (int idSeguidor : idsSeguidores) {
            Customer customer = customerControler.getCustomer(idSeguidor);
            if (customer != null) {
                seguidores.add(customer);
            }
        }
        return seguidores;
    }

    public List<Customer> getMisSeguidos(int id) {
        List<Integer> idsSeguidos = ConexionesDAO.getSeguidosCliente(id);
        CustomerControler customerControler = new CustomerControler();
        List<Customer> seguidos = new ArrayList<>();
        for (int idSeguido : idsSeguidos) {
            Customer customer = customerControler.getCustomer(idSeguido);
            if (customer != null) {
                seguidos.add(customer);
            }
        }
        return seguidos;
    }

    /*public void addConexion(String usuario, String nombre, String email, String password) throws SQLException {
        CustomerDAO dao = new CustomerDAO();
        dao.addUser(usuario, nombre, email, password);
    }*/

    public void addConexion(int followerId, int followingId) throws SQLException {
        ConexionesDAO conexionesDAO = new ConexionesDAO();
        conexionesDAO.addConexion(followerId, followingId);
    }





    /*

    private HashMap<Integer, List<Integer>> seguidores = new HashMap<>();
    private HashMap<Integer, List<Integer>> seguidos = new HashMap<>();
    private Connection conexion;

    public ConexionesControler(){
        this.conexion = ConnectionDAO.getInstance().getConnection();
    }

    //se cargan todas las conexiones de la base de datos de seguidores y se almacenan en un hashmap
    public void cargarConexiones() {
        String sql = "SELECT id_seguidor, id_seguido FROM seguidores";

        try (PreparedStatement pst = conexion.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int idSeguidor = rs.getInt("id_seguidor");
                int idSeguido = rs.getInt("id_seguido");

                seguidores.putIfAbsent(idSeguido, new ArrayList<>());
                seguidos.putIfAbsent(idSeguidor, new ArrayList<>());

                seguidores.get(idSeguido).add(idSeguidor);
                seguidos.get(idSeguidor).add(idSeguido);
            }

            System.out.println("Conexiones cargadas correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al cargar las conexiones: " + e.getMessage());
        }
    }

    //obtener seguidores de un usuario
    public List<Integer> obtenerSeguidores(int idUsuario) {
        return seguidores.getOrDefault(idUsuario, new ArrayList<>());
    }

    //obtener usuarios a los que sigue un usuario
    public List<Integer> obtenerSeguidos(int idUsuario) {
        return seguidos.getOrDefault(idUsuario, new ArrayList<>());
    }*/
}