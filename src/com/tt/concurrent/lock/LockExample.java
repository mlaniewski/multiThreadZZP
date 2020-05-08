package com.tt.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample implements Runnable {
	private final Lock lock = new ReentrantLock();

	public static void main(String[] args) {
		LockExample le = new LockExample();
		new Thread(le).start();
		new Thread(le).start();
	}

	@Override
	public void run() {
		if (lock.tryLock()) {
			try {
				compute();
			} finally {
				lock.unlock();
			}
		} else {
			compute2();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			run();
		}
	}

	public void compute() {
		System.out.println(Thread.currentThread().getId() + " Inside compute method");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void compute2() {
		System.out.println(Thread.currentThread().getId() + " Insiede compute2 method");
	}
}
