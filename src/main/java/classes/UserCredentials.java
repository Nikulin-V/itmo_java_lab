package classes;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UserCredentials implements Serializable {
    private final String username;
    private final String password;
    private boolean isRegistration = false;


    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashPassword(password);
    }

    public void setRegistration(boolean registration) {
        isRegistration = registration;
    }

    public boolean isRegistration() {
        return isRegistration;
    }

    private static String hashPassword(String password) {
        Dotenv env = Dotenv.load();
        String salt = env.get("SALT");
        byte[] hash = new byte[0];
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            hash = md.digest((password + salt).getBytes());
        } catch (NoSuchAlgorithmException ignored) {
        }
        return Base64.getEncoder().encodeToString(hash);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCredentials that = (UserCredentials) o;

        if (!username.equals(that.username)) return false;
        if (!password.equals(that.password)) return false;
        return hashPassword(password).equals(hashPassword(that.password));
    }
}
