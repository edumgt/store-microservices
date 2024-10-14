package com.praveenukkoji.userservice.utility;

import com.praveenukkoji.userservice.exception.user.PasswordDecryptionException;
import com.praveenukkoji.userservice.exception.user.PasswordEncryptionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class UserUtility {

    @Value("${password.encryption.key}")
    private String passwordEncryptionKey;

    @Value("${password.decryption.key}")
    private String passwordDecryptionKey;

    private static final String ALGORITHM = "AES";

    public String getEncryptedPassword(String password) throws PasswordEncryptionException {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(
                    passwordEncryptionKey.getBytes(StandardCharsets.UTF_8),
                    ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new PasswordEncryptionException(e.getMessage());
        }
    }

    public String getDecryptedPassword(String encryptedPassword) throws PasswordDecryptionException {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(
                    passwordDecryptionKey.getBytes(StandardCharsets.UTF_8),
                    ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));

            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new PasswordDecryptionException(e.getMessage());
        }
    }
}
