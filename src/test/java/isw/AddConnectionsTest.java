/*package isw;

import isw.dao.CustomerDAO;
import isw.domain.Customer;

import isw.controler.ConexionesControler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

public class AddConnectionsTest {

    private ConexionesControler conexionesControler;

    @BeforeEach
    public void setUp() {
        conexionesControler = new ConexionesControler();
    }

    @Test
    public void testAddConnectionSuccess() throws SQLException {
        // Agregar una nueva conexión entre dos usuarios
        int idUsuarioA = 1;
        int idUsuarioB = 2;

        // Agregar la conexión
        conexionesControler.addConexion(idUsuarioA, idUsuarioB);

        // Verificar que la conexión se haya agregado correctamente
        assertTrue(conexionesControler.getMisSeguidos(idUsuarioA).contains(idUsuarioB), "El usuario A debería estar siguiendo al usuario B.");
        assertTrue(conexionesControler.getMisSeguidores(idUsuarioB).contains(idUsuarioA), "El usuario B debería tener al usuario A como seguidor.");
    }

    @Test
    public void testAddConnectionDuplicate() throws SQLException {
        // Agregar la misma conexión dos veces entre dos usuarios
        int idUsuarioA = 1;
        int idUsuarioB = 2;

        // Primera conexión
        conexionesControler.addConexion(idUsuarioA, idUsuarioB);
        // Segunda conexión (duplicada)
        conexionesControler.addConexion(idUsuarioA, idUsuarioB);

        // Verificar que la conexión duplicada no se haya agregado
        assertEquals(1, conexionesControler.getMisSeguidos(idUsuarioA).stream().filter(id -> id == idUsuarioB).count(), "La conexión duplicada no debería ser agregada.");
    }

    @Test
    public void testAddNullConnection() {
        // Intentar agregar una conexión con un id nulo
        int idUsuarioA = 1;
        int idUsuarioB = 0; // Suponiendo que 0 no es un ID válido

        Exception exception = assertThrows(SQLException.class, () -> {
            conexionesControler.addConexion(idUsuarioA, idUsuarioB);
        });

        // Verificar el mensaje de la excepción
        assertEquals("Invalid target user ID", exception.getMessage());
    }
}
*/