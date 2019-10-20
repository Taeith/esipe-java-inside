package fr.umlv.java.inside;

public class Main {

	static class AndroidApp {
		private static StringBuilder builder = new StringBuilder();
		private static final Logger LOGGER = Logger.of(AndroidApp.class, builder::append);
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		var nbThreads = 3;
	    for (var i = 0; i < nbThreads; i++) {
	      var fi = i;
	      var thread = new Thread(() -> {
	    	  while(true) {
	    		  AndroidApp.LOGGER.log("(Thread " + fi + " )");
	    		  System.out.println(AndroidApp.builder.toString());
	    		  try { Thread.sleep(2000); } catch (Exception e) { e.printStackTrace(); }
	    	  }	        
	      });
	      thread.start();
	    }
	    Thread.sleep(4000);	    
	    Logger.enable(AndroidApp.class, false);
	    System.out.println("Logger has been disabled.");
	}

}
