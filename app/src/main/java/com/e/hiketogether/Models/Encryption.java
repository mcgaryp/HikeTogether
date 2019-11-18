package com.e.hiketogether.Models;

import android.util.Log;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

// This class was taken from online and edited for encription purposes
public class Encryption {

    private static final String TAG = "ENCRYPTION";
    private KeyPair myPair;

    Encryption() {
        // Get an instance of the RSA key generator
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
            // Generate the keys — might take sometime on slow computers
            myPair = kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // Encrypts the message
    public String encrypt(String message) throws Exception {
        try {
            // Get an instance of the Cipher for RSA encryption/decryption
            Cipher c = Cipher.getInstance("RSA");
            // Initiate the Cipher, telling it that it is going to Encrypt, giving it the public key
            c.init(Cipher.ENCRYPT_MODE, myPair.getPublic());
            // Encrypt that message using a new SealedObject and the Cipher we created before
            SealedObject myEncryptedMessage= new SealedObject(message, c);
            Log.d(TAG, myEncryptedMessage.toString());
            return myEncryptedMessage.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("Failed to encrypt");
        }
    }

    // Decrypt the message
    public String decrypt(String message) throws Exception {
        // Get an instance of the Cipher for RSA encryption/decryption
        Cipher dec;
        try {
            dec = Cipher.getInstance("RSA");
        // Initiate the Cipher, telling it that it is going to Decrypt, giving it the private key
        dec.init(Cipher.DECRYPT_MODE, myPair.getPrivate());
        // Tell the SealedObject we created before to decrypt the data and return it
//        String message = (String) myEncryptedMessage.getObject(dec);
        return message;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("Failed to decrypt message.");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new Exception("Failed to decrypt message.");
        }
    }
}