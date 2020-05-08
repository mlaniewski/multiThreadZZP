package com.tt.concurrent.callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 1. Stwórz pulę wątków
 * 	a) Wykorzystaj Runtime.getRuntime().availableProcessors() aby pobrać ilość dostępnych procesów
 * 	b) pulę stwórz wykorzystując Executors.newFixedThreadPool(numberOfThreads);
 * 2. Stwórz zadania ComplexMathCallable w zależności od ilości wątków
 * 3. Zgłoś zadania do wykonania (metoda submit);
 * 4. Odłóż zwracane obiekty typu Future na listę - przydadzą się przy pobieraniu wyników
 * 5. Przeiteruj po liście i wywołaj metodę get() na obiektach Future
 * 	a) pamiętaj, że poszczególne wyniki należy zsumować
 * 6. Pamiętaj o zamknięciu ExecutorService'u - executor.shutdown();
 * 7. Zaprezentuj wyniki i zinterpretuj czas wykonania
 * 
 */
public class ComplexMathRunner {

	public static void main(String[] args) {
		System.out.println("Program start...");
		ComplexMath cm = new ComplexMath(8000, 8000);

		double multiThreadedResult = multiThreadedExecution(cm);
		System.out.println();
		double notMultiThreadedResult = notMultiThreadedExecution(cm);

		System.out.println(String.format("Are results equal? %s",
				multiThreadedResult == notMultiThreadedResult));
	}

	private static double multiThreadedExecution(ComplexMath cm) {
		int numberOfThreads = Runtime.getRuntime().availableProcessors();
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

		List<ComplexMathCallable> tasks = new ArrayList<>();
		for (int i = 0; i < cm.getNoColumns(); i++) {
			ComplexMathCallable cmc = new ComplexMathCallable(cm, i);
			tasks.add(cmc);
		}

		List<Future<Double>> futures = new ArrayList<>();
		long startTime = System.currentTimeMillis();
		tasks.forEach((task) -> {
			futures.add(executorService.submit(task));
		});

		double[] result = {0.};
		futures.forEach((future) -> {
			try {
				result[0] += future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
		System.out.println(result[0]);
		executorService.shutdown();
		long endTime = System.currentTimeMillis();
		System.out.println("Execution time: " + ((endTime - startTime) / 1000d) + " seconds.");
		return result[0];
	}

	private static double notMultiThreadedExecution(ComplexMath complexMath) {
		System.out.println("Not multi-threaded execution");
		long startTime = System.currentTimeMillis();
		double result = complexMath.calculate();
		System.out.println(result);
		long endTime = System.currentTimeMillis();
		System.out.println("Execution time: " + ((endTime - startTime) / 1000d) + " seconds.");
		return result;
	}
}
