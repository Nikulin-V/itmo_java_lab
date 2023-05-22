package classes;

import io.github.cdimascio.dotenv.Dotenv;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UserCredentials {
    private final String username;
    private final String hashedPassword;


    public UserCredentials(String username, String password) {
        this.username = username;
        this.hashedPassword = hashPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    //TODO Connect with salt from server's
    private String hashPassword(String password) {
        Dotenv env = Dotenv.load();
        String salt = env.get("SALT");
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD2");
        } catch (NoSuchAlgorithmException ignored) {
        }
        byte[] hash = md.digest((password + salt).getBytes());
        String encodedHash = Base64.getEncoder().encodeToString(hash);

        return encodedHash;
    }
}
