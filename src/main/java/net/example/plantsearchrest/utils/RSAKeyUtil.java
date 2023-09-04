package net.example.plantsearchrest.utils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * The RSAKeyUtil class provides utility methods for creating RSA public and private keys from encoded key strings.
 */
public class RSAKeyUtil {

    /**
     * Creates a PublicKey from an encoded key string.
     *
     * @param encodedKey The Base64-encoded string representing the public key.
     * @return The PublicKey object created from the encoded key string.
     * @throws RuntimeException if there is an error creating the PublicKey.
     */
    public static PublicKey createPublicKey(String encodedKey) {
        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(encodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error creating PublicKey from bytes", e);
        }
    }

    /**
     * Creates a PrivateKey from an encoded key string.
     *
     * @param privateKey The Base64-encoded string representing the private key.
     * @return The PrivateKey object created from the encoded key string.
     * @throws RuntimeException if there is an error creating the PrivateKey.
     */
    public static PrivateKey createPrivateKey(String privateKey) {
        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error creating PrivateKey from bytes", e);
        }
    }
}
