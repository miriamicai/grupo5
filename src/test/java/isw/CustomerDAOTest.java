package isw;

import isw.dao.CustomerDAO;
import isw.dao.ConnectionDAO;
import isw.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerDAOTest {

    @Mock
    private ConnectionDAO connectionDAO;  // Simulación de ConnectionDAO

    @Mock
    private Connection conexion;  // Simulación de la conexión a la base de datos

    @Mock
    private PreparedStatement pst; // Simulación de PreparedStatement

    @Mock
    private ResultSet rs; // Simulación de ResultSet

    @InjectMocks
    private CustomerDAO customerDAOTest; // Inyectar mocks en CustomerDAO

    @BeforeEach
    public void setUp() {
        // Inicializar los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCustomerById() throws Exception {
        // Crear un objeto de cliente simulado con un id de tipo int
        Customer expectedCustomer = new Customer(123, "usuarioAtilano", "atilanocorreo@dominio.com", "password123", "Atilano", "Apellido1", "Apellido2");

        // Simular el comportamiento del ConnectionDAO
        when(connectionDAO.getConnection()).thenReturn(conexion); // Simula que getConnection devuelve la conexión mockeada

        // Simular el PreparedStatement y el ResultSet
        when(conexion.prepareStatement(anyString())).thenReturn(pst);  // Simula el PreparedStatement
        when(pst.executeQuery()).thenReturn(rs);  // Simula la ejecución de la consulta
        when(rs.next()).thenReturn(true);  // Simula que hay un resultado
        when(rs.getInt(1)).thenReturn(123);
        when(rs.getString(2)).thenReturn("usuarioAtilano");
        when(rs.getString(3)).thenReturn("atilanocorreo@dominio.com");
        when(rs.getString(4)).thenReturn("password123");
        when(rs.getString(5)).thenReturn("Atilano");
        when(rs.getString(6)).thenReturn("Apellido1");
        when(rs.getString(7)).thenReturn("Apellido2");

        // Llamar al método
        Customer actualCustomer = customerDAOTest.getCliente(123);

        // Verificar que el resultado no es nulo
        assertNotNull(actualCustomer);
        // Verificar que el id del cliente es 123 (de tipo int)
        assertEquals(123, actualCustomer.getId());
        // Verificar que el nombre del cliente es "Atilano"
        assertEquals("Atilano", actualCustomer.getNombre());

        // Verificar que el método getCliente fue llamado una vez con el id 123
        verify(customerDAOTest, times(1)).getCliente(123);
    }
}
