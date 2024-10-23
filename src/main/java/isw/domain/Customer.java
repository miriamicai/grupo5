package isw.domain;

import java.io.Serializable; //para poder usarse como key del hashMap 'session'
import java.util.Objects;

public class Customer implements Serializable {
    //private static final long serialVersionUID = 1L;
    private String id; //nombre usuario
    private String password;
    private String correo;

    public Customer() {
        this.setId(new String());
        this.setPassword(new String());
        this.setCorreo(new String());
    }

    public Customer(String id, String password) {
        this.setId(id);
        this.setPassword(password);
        this.setCorreo(new String());
    }

    public Customer(String id, String password, String correo) {
        this.setId(id);
        this.setPassword(password);
        this.setCorreo(correo);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void getInfoPruebas() {
        System.out.println(this.id + " " + this.password + " " + this.correo);
    }

    @Override
    public boolean equals(Object o) {

        if ((o != null) && (o instanceof Customer)) {
            Customer cu = (Customer) o;
            if (Objects.equals(this.id, cu.getId()) && Objects.equals(this.password, cu.getPassword())) { //objects añade seguridad
                return true;
            }
        }
        return false;
    }


}
