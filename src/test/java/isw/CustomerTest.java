package isw;

import isw.dao.CustomerDAO;
import isw.domain.Customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer(1, "usuario1", "correo1@example.com", "password1", "Nombre1", "Apellido1", "Apellido2");
    }

    @Test
    public void testGetId() {
        assertEquals(1, customer.getId());
    }

    @Test
    public void testSetId() {
        customer.setId(2);
        assertEquals(2, customer.getId());
    }

    @Test
    public void testGetNombreUsuario() {
        assertEquals("usuario1", customer.getNombreUsuario());
    }

    @Test
    public void testSetNombreUsuario() {
        customer.setNombreUsuario("usuario2");
        assertEquals("usuario2", customer.getNombreUsuario());
    }

    @Test
    public void testGetCorreo() {
        assertEquals("correo1@example.com", customer.getCorreo());
    }

    @Test
    public void testSetCorreo() {
        customer.setCorreo("correo2@example.com");
        assertEquals("correo2@example.com", customer.getCorreo());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password1", customer.getPassword());
    }

    @Test
    public void testSetPassword() {
        customer.setPassword("password2");
        assertEquals("password2", customer.getPassword());
    }

    @Test
    public void testGetNombre() {
        assertEquals("Nombre1", customer.getNombre());
    }

    @Test
    public void testSetNombre() {
        customer.setNombre("Nombre2");
        assertEquals("Nombre2", customer.getNombre());
    }

    @Test
    public void testGetApellido1() {
        assertEquals("Apellido1", customer.getApellido1());
    }

    @Test
    public void testSetApellido1() {
        customer.setApellido1("Apellido3");
        assertEquals("Apellido3", customer.getApellido1());
    }

    @Test
    public void testGetApellido2() {
        assertEquals("Apellido2", customer.getApellido2());
    }

    @Test
    public void testSetApellido2() {
        customer.setApellido2("Apellido4");
        assertEquals("Apellido4", customer.getApellido2());
    }
}
