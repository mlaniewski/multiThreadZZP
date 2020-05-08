package com.tt.concurrent.callable;

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
		long startTime = System.currentTimeMillis();
		System.out.println(cm.calculate());
		long endTime = System.currentTimeMillis();
		System.out.println("Execution time: " + ((endTime - startTime) / 1000d) + " seconds.");
	}
}
