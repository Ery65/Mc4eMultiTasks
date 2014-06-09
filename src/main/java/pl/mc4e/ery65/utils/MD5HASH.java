package pl.mc4e.ery65.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5HASH {
	
	public static String getHashedPassword(String password){
		String Hash = null;
		try {
			MessageDigest dg = MessageDigest.getInstance("MD5");
			dg.update(password.getBytes());
			byte[] b = dg.digest();
			StringBuilder hash = new StringBuilder();
			for (int i=0;i<b.length;i++){
				hash.append(Integer.toString((b[i] & 0xff)+ 0x100, 16).substring(1));
			}
			Hash = hash.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Hash;
	}
	
	public static boolean isMatch(String password, String HashedPassword){
		return HashedPassword.equals(getHashedPassword(password));
	}

}
