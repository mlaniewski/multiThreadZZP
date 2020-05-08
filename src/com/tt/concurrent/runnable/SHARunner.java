package com.tt.concurrent.runnable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SHARunner {

	private String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private int randomStringLength;
	
	public SHARunner(int randomStringLength) {
		super();
		this.randomStringLength = randomStringLength;
	}

	public void generateSHA(int maximum) {
		try {
			for(int i = 0; i < maximum; i++) {
				String line = randomString(randomStringLength);
				getSHA(line);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        Random rnd = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
	
	private String getSHA(String fileContentsBuilder) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
		messageDigest.update(String.valueOf(fileContentsBuilder.toString()).getBytes());
		byte[] digest = messageDigest.digest();
		StringBuilder sb = new StringBuilder();
		for (byte d : digest) {
			sb.append(Integer.toHexString(0xFF & d));
		}
		return sb.toString();
	}	
	
}
