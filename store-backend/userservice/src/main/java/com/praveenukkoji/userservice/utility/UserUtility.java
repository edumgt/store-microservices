package com.praveenukkoji.userservice.utility;

import com.praveenukkoji.userservice.exception.user.PasswordDecryptionException;
import com.praveenukkoji.userservice.exception.user.PasswordEncryptionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Service
public class UserUtility {

    @Value("${password.encryption.key}")
    private String passwordEncryptionKey;

    @Value("${password.decryption.key}")
    private String passwordDecryptionKey;

    private static final String ALGORITHM = "AES";

    public String getEncryptedPassword(String password) throws PasswordEncryptionException {
        byte[] decodedKey = Base64.getDecoder().decode(passwordEncryptionKey);

        try {
            Cipher cipher = Cipher.getInstance("AES");
            // rebuild key using SecretKeySpec
            SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, originalKey);
            byte[] cipherText = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error occurred while encrypting data", e);
        }
    }

    public String getDecryptedPassword(String encryptedPassword) throws PasswordDecryptionException {
        byte[] decodedKey = Base64.getDecoder().decode(passwordDecryptionKey);

        try {
            Cipher cipher = Cipher.getInstance("AES");
            // rebuild key using SecretKeySpec
            SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error occurred while decrypting data", e);
        }
    }
}
