package com.tt.concurrent.fork;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import com.tt.concurrent.callable.ComplexMath;

public class ForkJoinExample extends RecursiveAction {
	protected static int sThreshold = 1000;
	private int mStart;
    private int mLength;
    private ComplexMath cm;
    private static double result = 0;
    
	public static double getResult() {
		return result;
	}

	public ForkJoinExample(int mStart, int mLength, ComplexMath cm) {
		super();
		this.mStart = mStart;
		this.mLength = mLength;
		this.cm = cm;
	}

	public static void main(String[] args) {
		int jobSize = 8000;
		ForkJoinExample fje = new ForkJoinExample(0, 8000, new ComplexMath(jobSize, jobSize));
		ForkJoinPool pool = new ForkJoinPool();
		long start = System.currentTimeMillis();
		pool.invoke(fje);
		System.out.println(ForkJoinExample.getResult());
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start) / 1000d + " sec.");
	}

	@Override
	protected void compute() {
		if(mLength < sThreshold) {
			result += computeDirectly();
			return;
		}
		int split = mLength / 2;
		invokeAll(new ForkJoinExample(mStart, split, cm), 
				new ForkJoinExample(mStart + split, mLength - split, cm));
	}

	private double computeDirectly() {
		double subResult = 0.;
		if(mStart + mLength > cm.getNoColumns())
			return subResult;
		for(int i = mStart; i < mStart + mLength; i++) {
			subResult = cm.doComplexMathForColumn(i);
		}
		return subResult;
	}
}
