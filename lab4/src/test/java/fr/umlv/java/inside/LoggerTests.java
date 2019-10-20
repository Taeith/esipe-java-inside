package fr.umlv.java.inside;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class LoggerTests {
	
	static class AndroidApp {
		private static StringBuilder builder = new StringBuilder();
		private static final Logger LOGGER = Logger.of(AndroidApp.class, builder::append);
	}
	
	static class AndroidApp2 {
		private static StringBuilder builder = new StringBuilder();
		private static final Logger LOGGER = Logger.of(AndroidApp2.class, builder::append);
	}
	
	static class MultiThreadApp {
		private static StringBuilder builder = new StringBuilder();
		private static final Logger LOGGER = Logger.of(MultiThreadApp.class, builder::append);
	}
	
	@Test
	public void AndroidAppLog() {
		AndroidApp.builder.setLength(0);
		AndroidApp.LOGGER.log("toto");
		assertEquals("toto", AndroidApp.builder.toString());
	}
	
	@Test
	public void AndroidAppExceptions1() {
		assertAll(
			() -> assertThrows(NullPointerException.class, () -> Logger.of(null, __ -> {}).log("")),
			() -> assertThrows(NullPointerException.class, () -> Logger.of(LoggerTests.class, null).log(""))
		);
	}
	
	@Test
	public void AndroidAppExceptions2() {
		assertThrows(NullPointerException.class, () -> AndroidApp.LOGGER.log(null));
	}
	
	@Test
	public void AndroidApp2Log() {
		AndroidApp2.builder.setLength(0);
		AndroidApp2.LOGGER.log("toto");
		assertEquals("toto", AndroidApp2.builder.toString());
	}
	
	@Test
	public void AndroidApp2Exceptions2() {
		assertThrows(NullPointerException.class, () -> AndroidApp2.LOGGER.log(null));
	}
	
	
	@Test
	public void testThreads() throws InterruptedException {
		MultiThreadApp.builder.setLength(0);
		var nbThreads = 5;
	    for (var i = 0; i < nbThreads; i++) {
	      var fi = i;
	      var thread = new Thread(() -> {
	    	  MultiThreadApp.LOGGER.log("(Thread " + fi + " )");
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	      });
	      thread.start();
	    }
	    Thread.sleep(3000);
	    Logger.enable(MultiThreadApp.class, false);
	    System.out.println("Arrêt des messages");
	}
	
	@Test
	public void testDisabledLog() {
		AndroidApp.builder.setLength(0);
		AndroidApp.LOGGER.log("titi");
		assertEquals("titi", AndroidApp.builder.toString());
		Logger.enable(AndroidApp.class, false);
		AndroidApp.LOGGER.log("tata");
		assertEquals("titi", AndroidApp.builder.toString());
	}
	
}