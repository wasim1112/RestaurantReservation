package app.general.common.utils;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;


@Component
public class CryptoUtils {
    private final String algorithm;
    private final Base64.Encoder base64Encoder;
    private final Base64.Decoder base64Decoder;
    private final SecretKey key;
    private final IvParameterSpec ivParameterSpec;

    public CryptoUtils() throws Exception {
        algorithm = "AES/CBC/PKCS5Padding";
        base64Encoder = Base64.getEncoder();
        base64Decoder = Base64.getDecoder();
        key = getKeyFromPassword("Y4xJSyYtwanDXtgE5LNhNzCSLtsNfN4EpsZxeh7q9zeYs9yWRT4mdsLTtqNMkHAjgEhYTCepzRPStTbM3fkPfNAGpuWu43R4Sn4YNyvw6RTFmZCb5dYSntD5B5B9GqqY", "jhk9EHzAPhLSrC6yudPLRDuet8QgtEbZakksCxKnQv9kJS32fVhQDJQ4a6DFLE38NpySKCfHuyJdE9FHQY4bwd4SrGSHsPeRhcgW7ktcRLsmAvArEYpnHhpgQvKXRdWa");
        ivParameterSpec = new IvParameterSpec(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}); // because we want it to be the same across application nodes and restarts
    }

    @SneakyThrows
    public String encrypt(String input){
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return base64ToUrlComponent(base64Encoder.encodeToString(cipherText));
    }

    @SneakyThrows
    public String decrypt(String cipherText){
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] plainText = cipher.doFinal(base64Decoder.decode(base64FromUrlComponent(cipherText)));
        return new String(plainText);
    }

    private SecretKey getKeyFromPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    private IvParameterSpec generateRandomIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    private String base64ToUrlComponent(String base64){
        return base64.replace("+", "-").replace("/", "~");
    }

    private String base64FromUrlComponent(String urlComponent){
        return urlComponent.replace("-", "+").replace("~", "/");
    }

}
