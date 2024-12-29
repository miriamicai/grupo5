package isw;

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
        // Inicializa el mock de CustomerControler
        mockCustomerControler = Mockito.mock(CustomerControler.class);

        // Crea la instancia de AutentifCustomer e inyecta el mock
        autentifCustomer = new AutentifCustomer();
        autentifCustomer.setCustomerControler(mockCustomerControler);
    }

    @Test
    public void testVerificarLogin_Success() {
        // Configura el mock para devolver una lista con un usuario válido
        Mockito.doAnswer(invocation -> {
            ArrayList<Customer> lista = invocation.getArgument(0);
            lista.add(new Customer(1, "user1", "user1@example.com", "password1", "Juan", "Pérez", "García"));
            return null;
        }).when(mockCustomerControler).getCustomers(Mockito.any(ArrayList.class));

        // Ejecuta el método verificarLogin y verifica que devuelve el ID correcto
        int result = autentifCustomer.VerificarLogin("user1", "password1");
        assertEquals(1, result); // El ID esperado es 1
    }

    @Test
    public void testVerificarLogin_Failure() {
        // Configura el mock para devolver una lista que no contiene el usuario buscado
        Mockito.doAnswer(invocation -> {
            ArrayList<Customer> lista = invocation.getArgument(0);
            lista.add(new Customer(2, "user2", "user2@example.com", "password2", "Ana", "López", "Martínez"));
            return null;
        }).when(mockCustomerControler).getCustomers(Mockito.any(ArrayList.class));

        // Ejecuta el método verificarLogin y verifica que devuelve 0 para un usuario no válido
        int result = autentifCustomer.VerificarLogin("user3", "password3");
        assertEquals(0, result); // El login falla, el resultado esperado es 0
    }

    @Test
    public void testVerificarLogin_EmptyList() {
        // Configura el mock para devolver una lista vacía
        Mockito.doAnswer(invocation -> {
            ArrayList<Customer> lista = invocation.getArgument(0);
            return null;
        }).when(mockCustomerControler).getCustomers(Mockito.any(ArrayList.class));

        // Ejecuta el método verificarLogin y verifica que devuelve 0 para una lista vacía
        int result = autentifCustomer.VerificarLogin("user1", "password1");
        assertEquals(0, result); // No hay usuarios en la lista, el resultado esperado es 0
    }
}
