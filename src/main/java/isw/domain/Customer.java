package isw.domain;

import java.io.Serializable; //para poder usarse como key del hashMap 'session'
import java.util.Objects;

public class Customer implements Serializable {
    //private static final long serialVersionUID = 1L;
    private int id; //cambiar a int?
    private String nombre_usuario; //id asociado en nuestra base de datos
    private String correo;
    private String password;
    private String nombre;
    private String apellido1;
    private String apellido2;


    //constructor que voy a usar al recoger un customer de la base de datos
    public Customer(int id, String nombre_usuario, String correo, String password, String nombre, String apellido1, String apellido2) {
        this.setId(id);
        this.setNombreUsuario(nombre_usuario);
        this.setCorreo(correo);
        this.setPassword(password);
        this.setNombre(nombre);
        this.setApellido1(apellido1);
        this.setApellido2(apellido2);
    }

    //constructor que se usará cuando se registre un nuevo cliente, asignándole un nuevo id
    /*public Customer(String nombre_usuario, String correo, String password, String nombre, String apellido1, String apellido2) {
        this.setIdNuevo(); //siempre se asigna de manera automática
        this.setNombreUsuario(nombre_usuario);
        this.setCorreo(correo);
        this.setPassword(password);
        this.setNombre(nombre);
        this.setApellido1(apellido1);
        this.setApellido2(apellido2);
    }*/

    public Customer(String usuario, String password) { //constructor para verificar el log in del usuario
        this.setNombreUsuario(usuario); //se podrá almacenar el correo o el nombre de usuario para este tipo de usuario
        this.setPassword(password);
    }

    //CONTRUCTOR MARCO
    /*public Customer(String usuario, String nombre, String email, String contraseña, int id){
        this.setNombreUsuario(usuario);
        this.setNombre(nombre);
        this.setCorreo(email);
        this.setPassword(contraseña);
        this.setId(id);
    }*/



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        //CREAR LA LÓGICA POR LA QUE SE ASIGNA DIFERENTES ID A LOS CLIENTES
    }

    public String getNombreUsuario() {
        return nombre_usuario;
    }

    public void setNombreUsuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }





    public void getInfoPruebas() {
        System.out.println(this.id + " " + this.password + " " + this.correo);
    }


    @Override
    public boolean equals(Object o) {

        if ((o != null) && (o instanceof Customer)) {
            Customer cu = (Customer) o;
            if ((
                    (  (Objects.equals(this.nombre_usuario, cu.getNombreUsuario())) ||
                    (Objects.equals(this.nombre_usuario, cu.getCorreo())) //estará almacenado en nombre_usuario (sette)
                    )
                    && PasswordSegura.comprobarPassword(this.password, cu.getPassword()))) { //objects añade seguridad

                return true; //determino que son iguales por su nombre de usuario o por su correo
            }
        }
        return false;
    }


}