package fr.umlv.java.inside;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		var scope = new ContinuationScope("scope");
		 
	    var scheduler = new Scheduler(SchedulerStrategy.FIFO);
	      
	    var continuation1 = new Continuation(scope, () -> {
	    	System.out.println("A1");
	        scheduler.enqueue(scope);
	        System.out.println("B1");
	        scheduler.enqueue(scope);
	        System.out.println("C1");
	    });
	    var continuation2 = new Continuation(scope, () -> {
	    	System.out.println("A2");
	        scheduler.enqueue(scope);
	        System.out.println("B2");
	        scheduler.enqueue(scope);
	        System.out.println("C2");
	    });
	      
	      var list = List.of(continuation1, continuation2);
	      
	      list.forEach(Continuation::run);
	      
	      scheduler.runLoop();

	}

}
