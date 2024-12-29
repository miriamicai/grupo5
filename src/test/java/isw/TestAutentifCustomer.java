import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import isw.domain.AutentifCustomer;
import isw.domain.Customer;
import isw.controler.CustomerControler;
import org.mockito.Mockito;
import java.util.ArrayList;

public class TestAutentifCustomer {
    private AutentifCustomer autentifCustomer;
    private CustomerControler mockCustomerControler;

    @Before
    public void setUp() {
        // Inicializa el mock del CustomerControler y la instancia de AutentifCustomer
        mockCustomerControler = Mockito.mock(CustomerControler.class);
        autentifCustomer = new AutentifCustomer();
        autentifCustomer.customerControler = mockCustomerControler;
    }

    @Test
    public void testVerificarLogin_Success() {
        // Configura el comportamiento del mock para simular un usuario válido
        ArrayList<Customer> mockCustomers = new ArrayList<>();
        mockCustomers.add(new Customer("user1", "password1"));
        Mockito.doAnswer(invocation -> {
            ArrayList<Customer> list = invocation.getArgument(0);
            list.addAll(mockCustomers);
            return null;
        }).when(mockCustomerControler).getCustomers(Mockito.any(ArrayList.class));

        boolean result = autentifCustomer.VerificarLoginTest("user1", "password1");
        assertTrue(result);
    }

    @Test
    public void testVerificarLogin_Failure() {
        // Configura el comportamiento del mock para simular que no existe el usuario
        ArrayList<Customer> mockCustomers = new ArrayList<>();
        mockCustomers.add(new Customer("user1", "password1"));
        Mockito.doAnswer(invocation -> {
            ArrayList<Customer> list = invocation.getArgument(0);
            list.addAll(mockCustomers);
            return null;
        }).when(mockCustomerControler).getCustomers(Mockito.any(ArrayList.class));

        boolean result = autentifCustomer.VerificarLoginTest("user2", "password2");
        assertFalse(result);
    }
}
