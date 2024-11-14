package isw;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import isw.controler.ConexionesControler;
import isw.dao.ConexionesDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

        import java.sql.SQLException;

public class SeguidoresTest {

    @Mock
    private ConexionesDAO conexionesDAO;

    private ConexionesControler conexionesControler;

    @Before
    public void setUp() {
        // Inicializar los mocks y la clase bajo prueba
        MockitoAnnotations.openMocks(this);
        conexionesControler = new ConexionesControler();
    }

    @Test
    public void testAgregarConexion() throws SQLException {
        // Datos de prueba
        int idSeguidor = 1;
        int idSeguido = 2;

        // Llamar al método de agregar conexión
        doNothing().when(conexionesDAO).addConexion(idSeguidor, idSeguido);

        // Verificar que el método se llama correctamente
        conexionesControler.addConexion(idSeguidor, idSeguido);

        // Verificar que la acción se ejecutó sin errores (aunque no podemos verificar directamente en el mock)
        verify(conexionesDAO, times(1)).addConexion(idSeguidor, idSeguido);
    }

    @Test(expected = SQLException.class)
    public void testAgregarConexionConError() throws SQLException {
        // Datos de prueba
        int idSeguidor = 1;
        int idSeguido = 2;

        // Configurar el mock para lanzar una excepción
        doThrow(new SQLException("Error al agregar conexión")).when(conexionesDAO).addConexion(idSeguidor, idSeguido);

        // Llamar al método y esperar la excepción
        conexionesControler.addConexion(idSeguidor, idSeguido);
    }
}
