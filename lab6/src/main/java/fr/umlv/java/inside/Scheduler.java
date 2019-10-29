package fr.umlv.java.inside;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Scheduler {		
	
	private ArrayDeque<Continuation> queue = new ArrayDeque<Continuation>();
	private SchedulerStrategy strategy;
	
	public Scheduler(SchedulerStrategy strategy) {
		this.strategy = strategy;
	}		
	
	public void enqueue(ContinuationScope scope) {
		queue.offer(Objects.requireNonNull(Continuation.getCurrentContinuation(scope)));
		Continuation.yield(scope);
	}
	
	public void runLoop() {
		
		while (!queue.isEmpty()) {
			Continuation continuation = null;
			switch (strategy) {
			case STACK:
				continuation = queue.pollLast(); break;
			case FIFO:
				continuation = queue.poll(); break;
			case RANDOM:
				ArrayList<Continuation> array = new ArrayList<Continuation>(queue);
				continuation = array.remove(ThreadLocalRandom.current().ints(0, queue.size()).iterator().nextInt());
				queue = new ArrayDeque<Continuation>(array);
				break;
			}
			continuation.run();
		}		
		
	}		
	
}