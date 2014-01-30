package exp.viswanath.encryption;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;
import android.util.Log;

/*
 *
 * Author: Viswanath L
 *
 * viswanath.l@experionglobal.com
 *
 * 06-Jan-2014
 *
 */

public class SecurityProvider {

	private static final String ALGORITHM = "AES";
	private byte[] key;
	
	private static SecurityProvider instance;
	
	private SecurityProvider() {
		generateKey();
	}
	
	/**
	 * @return instance of this class
	 */
	public static SecurityProvider getInstance()
	{
		if(instance == null)
			instance = new SecurityProvider();
		
		return instance;
	}
	
	/**
	 * Key generation is done only once during the initialization of the object <br>
	 * Key is not publicly available
	 */
	private void generateKey()
	{
		byte[] keyStart = "safe is locked".getBytes();
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			sr.setSeed(keyStart);
			kgen.init(128, sr); // 192 and 256 bits may not be available
			SecretKey skey = kgen.generateKey();
			key = skey.getEncoded();  
			Log.d("", "KEY:" + key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * AES Encryption with Base64 Encoding
	 * @param plainText
	 * @return Encrypted string
	 * @throws Exception
	 */
	public String encrypt(String plainText) throws Exception {
		
		byte[] data = plainText.getBytes("UTF-8");
		
        SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encryptedData = cipher.doFinal(data);
        
		byte[] base64EncryptedData = Base64.encode(encryptedData, Base64.DEFAULT);
        return new String(base64EncryptedData, "UTF-8");
    }

    public String decrypt(String cipherText) throws Exception {
    	byte[] base64DecryptedData = Base64.decode(cipherText.getBytes("UTF-8"), Base64.DEFAULT);
    	
        SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decryptedData = cipher.doFinal(base64DecryptedData);
        return new String(decryptedData, "UTF-8");
    }
    
}
