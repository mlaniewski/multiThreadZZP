package com.tt.concurrent.thread;

public class FellowshipThread extends Thread {
	private String name;
	public FellowshipThread(String name) {
		this.name = name;
	}
	@Override
	public void run() {
		for(int i = 0; i < 10; i++) {
			System.out.println("Hello " + this.name + "!");
			try {
				sleep(1000);
			} catch (InterruptedException e) {	}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		new FellowshipThread("Sam").start();
		new FellowshipThread("Frodo").start();
	}
}
