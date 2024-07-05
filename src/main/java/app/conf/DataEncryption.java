package app.conf;

import javafx.beans.NamedArg;

import java.util.Base64;

public class DataEncryption {

    public static String DefaultPassword = "keys123";

    /** THIS METHOD WHEN CALLED SHALL TAKE A USER'S INPUT AS AN ARGUMENT AND RETURN A HASHED
     * REPRESENTATIVE OF THE USER'S INPUT.
     */
    public static String encryptText(@NamedArg("User's Password")String userInput) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(userInput.getBytes());
    }

    /**
     * THIS METHOD WHEN INVOKED SHALL TAKE TWO ARGUMENTS AND RETURN TRUE IF THEY MATCH ELSE FALSE.
     **/
    public static boolean checkPwd(@NamedArg("Hash String") String hashCode, @NamedArg("Plain Text")String plainText) {
        boolean flag = false;
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] hashedByte = decoder.decode(hashCode);
        String dehashString = new String(hashedByte);
        if(dehashString.equals(plainText)) {
            flag = true;
        }
        return flag;
    }

    //THIS METHOD WHEN CALLED SHALL DECODE AND RETURN THE PASSWORD IN PLAIN TEXT.
    public static String convertHashToText(@NamedArg("Encrypted Password") String hashCode) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] hashedByte = decoder.decode(hashCode);
        return new String(hashedByte);
    }

}
