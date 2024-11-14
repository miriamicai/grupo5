package isw.domain;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordSegura {

    public static String hashPassword(String password) {
        // Generar el hash con una sal incorporada
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean comprobarPassword(String password, String hashedPassword) {
        // Verificar la contraseña contra el hash almacenado
        return BCrypt.checkpw(password, hashedPassword);
    }
}
