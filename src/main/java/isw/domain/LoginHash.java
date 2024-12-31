package isw.domain;

import java.util.HashMap;

public class LoginHash {

    private LoginHash(){}
    private static HashMap<String, Object> credentials;

    public static void setCredentials(HashMap<String, Object> creds) {
        credentials = creds;
    }

    public static HashMap<String, Object> getCredentials() {
        return credentials;
    }
}
