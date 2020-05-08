package com.tt.concurrent.runnable;

public class ExecutionTime {

	public static void main(String[] args) {
		System.out.println("Program start...");
		long startTime = System.currentTimeMillis();
		SHARunner runner = new SHARunner(10000);
		runner.generateSHA(40000);
		long endTime = System.currentTimeMillis();
		System.out.println("Execution time: " + ((endTime - startTime) / 1000d) + " seconds.");
	}

}
