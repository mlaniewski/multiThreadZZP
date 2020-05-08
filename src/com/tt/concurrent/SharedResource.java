package com.tt.concurrent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SharedResource {

	private int n;
	private int[] x;

	public SharedResource(int n) {
		this.n = n;
		x = new int[n + 1];
		Random r = new Random();
		for (int i = 0; i < x.length; i++) {
			x[i] = r.nextInt();
			System.out.println(i + ". " + x[i]);
		}
	}

	public static void main(String[] args) {
		SharedResource m = new SharedResource(10);
		Runnable runner = m.new SimpleRunnable();
		for (int i = 0; i < m.n; i += 2) {
			Thread th = new Thread(runner);
			th.start();
		}
	}

	class SimpleRunnable implements Runnable {
		@Override
		public void run() {
			long threadId = Thread.currentThread().getId();
			if (n < 0)
				return;
			else {
				int currentNumber = x[n];
				MessageDigest md = null;
				try {
					md = MessageDigest.getInstance("SHA-256");
					md.update(String.valueOf(currentNumber).getBytes());
					byte[] digest = md.digest();
					StringBuilder sb = new StringBuilder();
					for (byte d : digest) {
						sb.append(Integer.toHexString(0xFF & d));
					}
					System.out.println(threadId + " : "+ sb);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				n--;
				run();
			}
		}
	}
}
