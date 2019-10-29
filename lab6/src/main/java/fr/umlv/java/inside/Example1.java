package fr.umlv.java.inside;

import java.util.ArrayDeque;
import java.util.List;	
	
public class Example1 {
	
	
	public static void stop(List<Continuation> list) {
		
		ArrayDeque<Continuation> queue = new ArrayDeque<Continuation>(list);	
		
		while (!queue.isEmpty()) {
			var continuation = queue.poll();
			continuation.run();
			if (!continuation.isDone())
				queue.offer(continuation);
		}
		
	}	
	
	public static void main(String[] args) {
		
		ContinuationScope scope= new ContinuationScope("hello");
		Object lock = new Object();
		Continuation c = new Continuation(scope, () -> {
			
			System.out.println("toto");
			Continuation.yield(scope);
			System.out.println("titi");
			Continuation.yield(scope);
			System.out.println("tata");			
			/*
			synchronized(lock) {
				Continuation.yield(scope);
			}
			*/
			
			
			//System.out.println(Continuation.getCurrentContinuation(scope) + " " + Thread.currentThread());
			
		});
		
		//System.out.println(Continuation.getCurrentContinuation(scope) + " " + Thread.currentThread());
		
		
		stop(List.of(c));
		
		
		//System.out.println(Continuation.getCurrentContinuation(scope) + " " + Thread.currentThread());
		
	}
	
}
