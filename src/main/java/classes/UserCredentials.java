package classes;

public class UserCredentials {
    private final String username;
    private final String hashedPassword;

    public UserCredentials(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
}
