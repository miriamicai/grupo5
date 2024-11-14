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
    private CustomerDAO customerDAOTest; // Inyectar mocks en CustomerDAO (no en CustomerDAOTest)


    @BeforeEach
    public void setUp() {
        // Inicializar los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCustomerById() {
        // Crear un objeto de cliente simulado con un id de tipo int
        Customer expectedCustomer = new Customer(123, "usuarioAtilano", "atilanocorreo@dominio.com", "password123", "Atilano", "Apellido1", "Apellido2");

        // Simular el comportamiento del método getCliente
        when(customerDAO.getCliente(123)).thenReturn(expectedCustomer);

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



