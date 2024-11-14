package isw.domain;

import java.util.HashMap;

import isw.cliente.Cliente;
import isw.message.Message;

public class AutentifCustomer {

    private Cliente cliente;

    public AutentifCustomer(Cliente cliente){
        this.cliente = cliente;
    }

    public int VerificarLogin(String usuario, String password) {

        //crear un mensaje con los datos de autenticación
        Message mensajeAServior = new Message();
        HashMap<String, Object> session = new HashMap<>();
        session.put("usuario", usuario);
        session.put("password", password);
        mensajeAServior.setContext("/login");
        mensajeAServior.setSession(session);

        //enviar y recibir mensajes del Servidor:
        Message mensajeDelServior = new Message();
        cliente.sent(mensajeAServior, mensajeDelServior);

        //analizar respuesta del Servidor
        if (mensajeDelServior.getContext().equals("/loginResponse")) {
            if (mensajeDelServior.getSession().containsKey("id_logged")) {
                return (int) mensajeDelServior.getSession().get("id_logged");
            }
        }
        return 0;

    }

}
