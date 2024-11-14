package isw;

import isw.dao.CustomerDAO;
import isw.domain.Customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setUp() {
        // Cambiar para pasar un int en lugar de String para el id
        customer = new Customer(1, "usuario1", "correo@dominio.com", "password123", "Nombre", "Apellido1", "Apellido2");
    }

    @Test
    public void testGetId() {
        // El id debe ser un int, por lo tanto comparamos con 1
        assertEquals(1, customer.getId());
    }

    @Test
    public void testSetId() {
        // Cambiar el id a 2
        customer.setId(2);
        // Verificar que el id es 2
        assertEquals(2, customer.getId());
    }

    @Test
    public void testGetNombreUsuario() {
        // Verificar que el nombre de usuario es el esperado
        assertEquals("usuario1", customer.getNombreUsuario());
    }

    @Test
    public void testSetNombre() {
        // Cambiar el nombre a "Juan"
        customer.setNombre("Juan");
        // Verificar que el nombre se ha actualizado correctamente
        assertEquals("Juan", customer.getNombre());
    }
}