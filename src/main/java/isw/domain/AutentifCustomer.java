package isw.domain;

import java.util.ArrayList;
import isw.controler.CustomerControler;

// Añadido para mejorar equals y hashCode en Customer
import java.util.Objects;

public class AutentifCustomer {

    private CustomerControler customerControler; // Cambiado a privado por buenas prácticas

    public AutentifCustomer() {
        this.customerControler = new CustomerControler();
    }

    // Nuevo setter para facilitar inyección de dependencias en tests
    public void setCustomerControler(CustomerControler customerControler) {
        this.customerControler = customerControler;
    }

    // Método para verificar login
    public int VerificarLogin(String usuario, String password) {
        ArrayList<Customer> lista = new ArrayList<>(); // Lista para almacenar los clientes
        this.customerControler.getCustomers(lista); // Obtener clientes desde el controlador

        Customer esUsuario = new Customer(usuario, password);

        for (Customer customer : lista) {
            // Comparación usando equals de Customer
            if (esUsuario.equals(customer)) {
                UserSession.getInstance().setUserId(customer.getId());
                return customer.getId(); // Devolver ID si coincide
            }
        }
        return 0; // Devolver 0 si no hay coincidencias
    }
}
