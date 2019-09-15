package cc.ghast.auth.encryption;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

public class Hashing {

    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    public static String hashPassword (String password, String salt){

        // Transform the password to chars to hash them
        char[] chars = password.toCharArray();
        // Same goes for the salt
        byte[] bytes = salt.getBytes();
        // Specify the hashing method. We are using SHA256
        PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);
        Arrays.fill(chars, Character.MIN_VALUE);
        try {
            SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] securePassword = fac.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(securePassword);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            System.err.println("Exception encountered in hashPassword()");
            return null;

        } finally {
            spec.clearPassword();
        }
    }

    public static boolean verifyPassword (String password, String key, String salt) {
        String optEncrypted = hashPassword(password, salt);
        if (optEncrypted == null) return false;
        return optEncrypted.equals(key);
    }
}
