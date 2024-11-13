package isw;


import isw.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer("1", "Beltran");
    }

    @Test
    public void testGetId() {
        assertEquals("1", customer.getId());
    }

    @Test
    public void testSetId() {
        customer.setId("2");
        assertEquals("2", customer.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Beltran", customer.getName());
    }

    @Test
    public void testSetName() {
        customer.setName("Smith");
        assertEquals("Smith", customer.getName());
    }

    @Test
    public void testConstructorWithNullValues() {
        Customer customerWithNull = new Customer(null, null);

        assertNull(customerWithNull.getId());
        assertNull(customerWithNull.getName());
    }

    @Test
    public void testSetNameToNull() {
        customer.setName(null);
        assertNull(customer.getName());
    }
}