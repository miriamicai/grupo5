package isw;
import isw.dao.ConnectionDAO;
import isw.dao.CustomerDAO;
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
    private CustomerDAO customerDAO;  // Simulación de CustomerDAO

    @Mock
    private ConnectionDAO connectionDAO; // Simulación de ConnectionDAO

    @Mock
    private Connection conexion;  // Simulación de la conexión a la base de datos

    @Mock
    private PreparedStatement pst; // Simulación de PreparedStatement

    @Mock
    private ResultSet rs; // Simulación de ResultSet

    @InjectMocks
    private CustomerDAO customerDAOTest; // Inyectar mocks en CustomerDAO (no en CustomerDAOTest)

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializar los mocks
    }

    @Test
    public void testGetCustomerById() throws Exception {
        // Crear un objeto de cliente simulado con un id de tipo int
        Customer expectedCustomer = new Customer(123, "usuarioAtilano", "atilanocorreo@dominio.com", "password123", "Atilano", "Apellido1", "Apellido2");

        // Simular el comportamiento del ConnectionDAO y obtener la conexión
        when(connectionDAO.getInstance()).thenReturn(connectionDAO); // Simula el método getInstance()
        when(connectionDAO.getConnection()).thenReturn(conexion); // Simula la conexión

        // Simular el PreparedStatement y el ResultSet
        when(conexion.prepareStatement(anyString())).thenReturn(pst);
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(123);
        when(rs.getString(2)).thenReturn("usuarioAtilano");
        when(rs.getString(3)).thenReturn("atilanocorreo@dominio.com");
        when(rs.getString(4)).thenReturn("password123");
        when(rs.getString(5)).thenReturn("Atilano");
        when(rs.getString(6)).thenReturn("Apellido1");
        when(rs.getString(7)).thenReturn("Apellido2");

        // Llamar al método
        Customer actualCustomer = customerDAO.getCliente(123);

        // Verificar que el resultado no es nulo
        assertNotNull(actualCustomer);
        // Verificar que el id del cliente es 123 (de tipo int)
        assertEquals(123, actualCustomer.getId());
        // Verificar que el nombre del cliente es "Atilano"
        assertEquals("Atilano", actualCustomer.getNombre());

        // Verificar que el método getCliente fue llamado una vez con el id 123
        verify(customerDAO, times(1)).getCliente(123);
    }
}
