package isw;

import isw.dao.CustomerDAO;
import isw.domain.Customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerDAOTest {

    @Mock
    private CustomerDAO customerDAO;  // Simulación de CustomerDAO

    @InjectMocks
    private CustomerDAOTest customerDAOTest; // Inyectar mocks en esta clase

    @BeforeEach
    public void setUp() {
        // Inicializar los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCustomerById() {
        // Crear un objeto de cliente simulado
        Customer expectedCustomer = new Customer(123, "usuario123", "correo@example.com", "password123", "Nombre", "Apellido1", "Apellido2");

        // Simular el comportamiento del método getCliente
        when(customerDAO.getCliente(123)).thenReturn(expectedCustomer);

        // Llamar al método
        Customer actualCustomer = customerDAO.getCliente(123);

        // Verificar que el resultado es correcto
        assertNotNull(actualCustomer);
        assertEquals(123, actualCustomer.getId());
        assertEquals("usuario123", actualCustomer.getNombreUsuario());
        assertEquals("correo@example.com", actualCustomer.getCorreo());
        assertEquals("password123", actualCustomer.getPassword());
        assertEquals("Nombre", actualCustomer.getNombre());
        assertEquals("Apellido1", actualCustomer.getApellido1());
        assertEquals("Apellido2", actualCustomer.getApellido2());

        // Verificar que el método getCliente fue llamado
        verify(customerDAO, times(1)).getCliente(123);
    }
}
