package br.edu.ufabc.sd2015.projeto.comuns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class KeyManager {
	//Vars
	
	 public static final String ALGORITHM = "RSA";
	 
	public static final String PRIVATE_KEY_FILE = "private.key";

	public static final String PUBLIC_KEY_FILE = "public.key";
	
	public static final String SEP = System.getProperty("file.separator");
	
	//End Vars
	
	
	public void generateKey(File folder) {
	    try {
	      final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
	      keyGen.initialize(1024);
	      final KeyPair key = keyGen.generateKeyPair();

	      File privateKeyFile = new File(folder+SEP+PRIVATE_KEY_FILE);
	      File publicKeyFile = new File(folder+SEP+PUBLIC_KEY_FILE);

	      // Create files to store public and private key
	      if (privateKeyFile.getParentFile() != null) {
	        privateKeyFile.getParentFile().mkdirs();
	      }
	      privateKeyFile.createNewFile();

	      if (publicKeyFile.getParentFile() != null) {
	        publicKeyFile.getParentFile().mkdirs();
	      }
	      publicKeyFile.createNewFile();

	      // Saving the Public key in a file
	      ObjectOutputStream publicKeyOS = new ObjectOutputStream(
	          new FileOutputStream(publicKeyFile));
	      publicKeyOS.writeObject(key.getPublic());
	      publicKeyOS.close();

	      // Saving the Private key in a file
	      ObjectOutputStream privateKeyOS = new ObjectOutputStream(
	          new FileOutputStream(privateKeyFile));
	      privateKeyOS.writeObject(key.getPrivate());
	      privateKeyOS.close();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

	  }
	
	public boolean areKeysPresent(File folder) {

	    File privateKey = new File(folder+SEP+PRIVATE_KEY_FILE);
	    File publicKey = new File(folder+SEP+PUBLIC_KEY_FILE);

	    if (privateKey.exists() && publicKey.exists()) {
	      return true;
	    }
	    return false;
	  }
	
	public byte[] encrypt(String text, PublicKey key) {
	    byte[] cipherText = null;
	    try {
	      // get an RSA cipher object and print the provider
	      final Cipher cipher = Cipher.getInstance(ALGORITHM);
	      // encrypt the plain text using the public key
	      cipher.init(Cipher.ENCRYPT_MODE, key);
	      cipherText = cipher.doFinal(text.getBytes());
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return cipherText;
	  }
	
	 public  Job decrypt(byte[] text, PrivateKey key) {
		    byte[] dectyptedText = null;
		    Job j = null;
		    try {
		      // get an RSA cipher object and print the provider
		      final Cipher cipher = Cipher.getInstance(ALGORITHM);

		      // decrypt the text using the private key
		      cipher.init(Cipher.DECRYPT_MODE, key);
		      dectyptedText = cipher.doFinal(text);

		    } catch (Exception ex) {
		      ex.printStackTrace();
		    }

		   // return new String(dectyptedText);
		    return j;
		  }

}
