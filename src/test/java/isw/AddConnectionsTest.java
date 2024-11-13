package isw;

import isw.dao.CustomerDAO;
import isw.domain.Customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AddConnectionsTest {

    private Customer customerA;
    private Customer customerB;
    private CustomerDAO customerDAO;

    @BeforeEach
    public void setUp() {
        customerA = new Customer("1", "Alice");
        customerB = new Customer("2", "Bob");
        customerDAO = new CustomerDAO();
    }

    @Test
    public void testAddConnectionSuccess() {
        // Agregar una nueva conexión
        boolean connectionAdded = customerDAO.addConnection(customerA, customerB);

        // Verificar que la conexión se haya agregado correctamente
        assertTrue(connectionAdded, "La conexión debería haberse añadido correctamente.");
        assertTrue(customerA.getConnections().contains(customerB), "La lista de conexiones de Alice debería contener a Bob.");
    }

    @Test
    public void testAddConnectionDuplicate() {
        // Agregar la misma conexión dos veces
        customerDAO.addConnection(customerA, customerB);
        boolean connectionAddedAgain = customerDAO.addConnection(customerA, customerB);

        // Verificar que la segunda vez no se añada la conexión
        assertFalse(connectionAddedAgain, "La conexión duplicada no debería haberse añadido.");
    }

    @Test
    public void testAddNullConnection() {
        // Intentar agregar una conexión nula
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerDAO.addConnection(customerA, null);
        });

        // Verificar el mensaje de la excepción
        assertEquals("El cliente de destino no puede ser nulo", exception.getMessage());
    }
}