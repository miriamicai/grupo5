package isw.domain;

public class UserSession {
    private static UserSession instance;  // Singleton instance
    private Integer userId;               // ID of the logged-in user

    // Private constructor to prevent instantiation
    private UserSession() {}

    // Method to get the singleton instance
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Set the user ID once when the user logs in
    public void setUserId(int userId) {
        if (this.userId == null) {  // Only allow setting once per session
            this.userId = userId;
        } else {
            System.out.println("User ID is already set. Cannot overwrite.");
        }
    }

    // Get the user ID from anywhere in the application
    public int getUserId() {
        if (userId == null) {
            throw new IllegalStateException("No user is logged in.");
        }
        return userId;
    }

    // Clear the user session when logging out
    public void clearSession() {
        userId = null;
    }
}
