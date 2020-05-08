package com.tt.concurrent.sync;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Administrator on 10/14/2014.
 */
public class SynchronizedBlockSharedResource {

	private int n;
	private int[] x;

	public SynchronizedBlockSharedResource(int n) {
		this.n = n;
		x = new int[n + 1];
		Random r = new Random();
		for (int i = 0; i < x.length; i++) {
			x[i] = r.nextInt();
			System.out.println(i + ". " + x[i]);
		}
	}

	public static void main(String[] args) {
		SynchronizedBlockSharedResource m = new SynchronizedBlockSharedResource(10);
		Runnable runner = m.new SimpleRunnable();
		for (int i = 0; i < m.n; i++) {
			Thread th = new Thread(runner);
			th.start();
		}
		
	}

	class SimpleRunnable implements Runnable {
		private Object lock = new Object();
		
		@Override
		public void run() {
			long threadId = Thread.currentThread().getId();
			int currentNumber = -1;
			synchronized(lock) {
				currentNumber = getCurrentNumber(threadId);
			}
			if(currentNumber != -1) {
				printSHA(threadId, currentNumber);
				run();
			}
		}

		int getCurrentNumber(long threadId) {
			if (n < 0)
				return -1;
			int currentNumber = x[n];
			n--;
			return currentNumber;
		}

		void printSHA(long threadId, int currentNumber) {
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
		}
	}
}
