import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import isw.domain.Customer;
import isw.domain.UserSession;

public class CustomerSessionTest {
    private Customer customer;

    @Before
    public void setUp() {
        // Inicializa un cliente antes de cada prueba
        customer = new Customer(1, "testUser", "test@mail.com", "password", "Test", "User", "Example");
        UserSession.getInstance().clearSession(); // Asegura que la sesión esté limpia antes de cada prueba
    }

    @Test
    public void testSetUserSession() {
        // Prueba para verificar que la sesión de usuario se establece correctamente
        UserSession.getInstance().setUserId(customer.getId());
        assertEquals(1, UserSession.getInstance().getUserId());
    }

    @Test
    public void testClearUserSession() {
        // Prueba para verificar que la sesión de usuario se limpia correctamente
        UserSession.getInstance().setUserId(customer.getId());
        UserSession.getInstance().clearSession();
        try {
            UserSession.getInstance().getUserId();
            fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            assertEquals("No user is logged in.", e.getMessage());
        }
    }
}
