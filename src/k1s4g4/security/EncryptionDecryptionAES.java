package k1s4g4.security;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;    
import javax.crypto.Cipher;  
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
 

public class EncryptionDecryptionAES {  
    static Cipher cipher;  
    private static final String key="Ul0xMZO8TBU51PWwAft5Vu5en2zrFh4s/4zVYrbehvs=";
    
    private static Cipher cipher() {
    	if(cipher==null) {
    		try {
				cipher=Cipher.getInstance("AES");
			} catch (NoSuchAlgorithmException e) {
			} catch (NoSuchPaddingException e) {
			}
    	}
    	return cipher;
    }

    public static String encryptMessage(String msg)  {
    	try {
			return encrypt(msg,stringToKey(key));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
    }
    
    public static String decryptMessage(String msg) {
    	try {
			return decrypt(msg,stringToKey(key));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
    }
    
    public static String encryptPassword(String pass)  {
    	try {
			return encrypt(pass,stringToKey(key));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
    }
    
    public static String decryptPassword(String pass)  {
    	try {
			return decrypt(pass,stringToKey(key));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
    }
    
    public static String encrypt(String plainText, SecretKey secretKey)
            throws Exception {
        byte[] plainTextByte = plainText.getBytes();
        cipher().init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher().doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }

    public static String decrypt(String encryptedText, SecretKey secretKey)
            throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        cipher().init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher().doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }
    
    public static String keyToString(SecretKey key) {
    	return Base64.getEncoder().encodeToString(key.getEncoded());
    }
    
    public static SecretKey stringToKey(String str) {
    	// decode the base64 encoded string
    	byte[] decodedKey = Base64.getDecoder().decode(str);
    	// rebuild key using SecretKeySpec
    	return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
    }
}
