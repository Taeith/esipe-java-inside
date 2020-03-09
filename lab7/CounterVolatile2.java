package fr.umlv.conc;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CounterVolatile2 {
	
	private final AtomicInteger counter = new AtomicInteger();
	
	public int nextInt() {
		return counter.getAndIncrement();
	}
	
	public int get() {
		return counter.get();
	}
	
	public static void main(String[] args) {
		
		CounterVolatile2 counter = new CounterVolatile2();
		var threads = new ArrayList<Thread>();
		for (int i = 0; i < 4; i++) {
			threads.add(new Thread(() -> {
				for (int j = 0; j < 1000000; j++) {
					counter.nextInt();
				}
			}));
		}
		threads.forEach(thread -> thread.start());
		threads.forEach(thread -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		System.out.println(counter.get());
		
	}
	
}
