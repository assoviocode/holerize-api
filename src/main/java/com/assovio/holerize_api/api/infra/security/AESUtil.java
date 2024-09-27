package com.assovio.holerize_api.api.infra.security;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AESUtil {

    private final String algorithm = "AES";
    private byte[] key;

    @SuppressWarnings("null")
    public AESUtil(Environment env) {
        super();
        key = env.getProperty("api.security.encryption.secret").getBytes();
    }

    public String encrypt(String value) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String encrypted) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encrypted));
        return new String(decrypted);
    }
}
