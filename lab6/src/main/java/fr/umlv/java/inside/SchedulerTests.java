package fr.umlv.java.inside;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.StringJoiner;

import org.junit.Test;

public class SchedulerTests {
	
	@Test
	public void testSTACK() {
		StringJoiner SJ = new StringJoiner(", ", "[", "]"); 
		var scope = new ContinuationScope("scope");		 
	    var scheduler = new Scheduler(SchedulerStrategy.STACK);	      
	    var continuation1 = new Continuation(scope, () -> {
	    	SJ.add("A1");
	        scheduler.enqueue(scope);
	        SJ.add("B1");
	        scheduler.enqueue(scope);
	        SJ.add("C1");
	    });
	    var continuation2 = new Continuation(scope, () -> {
	    	SJ.add("A2");
	    	scheduler.enqueue(scope);
	    	SJ.add("B2");
	    	scheduler.enqueue(scope);
	    	SJ.add("C2");
	    });
	    var list = List.of(continuation1, continuation2);	      
	    list.forEach(Continuation::run);	      
	    scheduler.runLoop();
	    assertEquals("[A1, A2, B2, C2, B1, C1]", SJ.toString());
	}
	
	@Test
	public void testFIFO() {
		StringJoiner SJ = new StringJoiner(", ", "[", "]"); 
		var scope = new ContinuationScope("scope");		 
	    var scheduler = new Scheduler(SchedulerStrategy.FIFO);	      
	    var continuation1 = new Continuation(scope, () -> {
	    	SJ.add("A1");
	        scheduler.enqueue(scope);
	        SJ.add("B1");
	        scheduler.enqueue(scope);
	        SJ.add("C1");
	    });
	    var continuation2 = new Continuation(scope, () -> {
	    	SJ.add("A2");
	    	scheduler.enqueue(scope);
	    	SJ.add("B2");
	    	scheduler.enqueue(scope);
	    	SJ.add("C2");
	    });
	    var list = List.of(continuation1, continuation2);	      
	    list.forEach(Continuation::run);	      
	    scheduler.runLoop();
	    assertEquals("[A1, A2, B1, B2, C1, C2]", SJ.toString());
	}
	
}
