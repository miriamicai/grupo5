package isw.domain;

public class UserSession {
    private static UserSession instance;
    private int userId;

    // Private constructor to prevent instantiation
    private UserSession() {}

    // Get the singleton instance
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Set user ID when the user logs in
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Retrieve the user ID from anywhere in the application
    public int getUserId() {
        return userId;
    }
}

