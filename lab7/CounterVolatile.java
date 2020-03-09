package fr.umlv.conc;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CounterVolatile {
	
	private final AtomicInteger counter = new AtomicInteger();
	
	public int nextInt() {
		int tmp;		
		do {
			tmp = counter.get();
		}
		while (!counter.compareAndSet(tmp, tmp + 1));		
		return tmp;
	}
	
	public int get() {
		return counter.get();
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		CounterVolatile counter = new CounterVolatile();
		var threads = new ArrayList<Thread>();
		for (int i = 0; i < 4; i++) {
			threads.add(new Thread(() -> {
				for (int j = 0; j < 1000000; j++) {
					counter.nextInt();
				}
			}));
		}
		threads.forEach(thread -> thread.start());
		for (var thread : threads) {
			thread.join();
		}
		System.out.println(counter.get());
		
	}
	
}
