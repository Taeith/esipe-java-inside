package fr.umlv.java.inside;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

/*
 * 
 * guardWithTest
 * 3 MethodHandle
 * 1 de condition
 * 1 si vrai
 * 1 si faux
 * Tous doivent avoir les m^emes arguments et doivent renvoyer un int
 * 
 * 
 */

public class ExampleTests {
	
	@Test
	void testGuardWithTest() throws Throwable {
		
		Lookup lookUp = MethodHandles.privateLookupIn(Example.class, MethodHandles.lookup());
		MethodType methodType = MethodType.methodType(boolean.class, Object.class);
		
		MethodHandle test = lookUp.findVirtual(String.class, "equals", methodType);
		test = MethodHandles.insertArguments(test, 1, new String("foo"));
		
		MethodHandle target = MethodHandles.constant(Object.class, 1);
		target = MethodHandles.dropArguments(target, 0, String.class);
				
		MethodHandle fallback = MethodHandles.constant(Object.class, -1);
		fallback = MethodHandles.dropArguments(fallback, 0, String.class);		
		
		System.out.println(test);
		System.out.println(target);
		System.out.println(fallback);
		
		var methodHandle = MethodHandles.guardWithTest(test, target, fallback);
		assertEquals(1, methodHandle.invokeExact(new String("foo")));
		assertEquals(-1, methodHandle.invokeExact(new String("Cheval")));
	}
	
	@Test
	void testConstant() throws Throwable {
		Lookup lookup = MethodHandles.lookup();
		Lookup exLookUp = MethodHandles.privateLookupIn(Example.class, lookup);
		var methodHandle = exLookUp.findStatic(Example.class,"aStaticHello", MethodType.methodType(String.class, int.class));
		methodHandle = MethodHandles.constant(String.class, "question 0");
		assertEquals("question 0", (String) methodHandle.invokeExact());
	}
	
	@Test
	void testTypeValue() throws Throwable {
		Lookup lookup = MethodHandles.lookup();
		Lookup exLookUp = MethodHandles.privateLookupIn(Example.class, lookup);
		var methodHandle = exLookUp.findStatic(Example.class,"aStaticHello", MethodType.methodType(String.class, int.class));
		methodHandle = methodHandle.asType(MethodType.methodType(String.class, Integer.class));
		assertEquals("question 42", (String) methodHandle.invokeExact(Integer.valueOf(42)));
	}
	
	@Test
	void testInsertArguments() throws Throwable {
		Example e = new Example();
		Lookup lookup = MethodHandles.lookup();
		Lookup exLookUp = MethodHandles.privateLookupIn(Example.class, lookup);
		var methodHandle = exLookUp.findVirtual(Example.class, "anInstanceHello", MethodType.methodType(String.class, int.class));
		methodHandle = MethodHandles.insertArguments(methodHandle, 1, 42);
		assertEquals("question 42", (String) methodHandle.invokeExact(e));
	}
	
	@Test
	void testDropArguments() throws Throwable {
		Example e = new Example();
		Lookup lookup = MethodHandles.lookup();
		Lookup exLookUp = MethodHandles.privateLookupIn(Example.class, lookup);
		var methodHandle = exLookUp.findVirtual(Example.class, "anInstanceHello", MethodType.methodType(String.class, int.class));
		methodHandle = MethodHandles.dropArguments(methodHandle, 1, String.class);
		assertEquals("question 8", (String) methodHandle.invokeExact(e, "toto", 8));
	}	

	@Test 
	void testStaticHello() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = Example.class.getDeclaredMethod("aStaticHello", int.class);
		method.setAccessible(true);
		assertEquals("question 42", method.invoke(null, 42));		
	}
	
	@Test 
	void testInstanceHello() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Example e = new Example();
		Method method = e.getClass().getDeclaredMethod("anInstanceHello", int.class);
		method.setAccessible(true);
		assertEquals("question 42", method.invoke(e, 42));		
	}
	
	@Test 
	void testStaticHelloLookup() throws Throwable {
		Lookup lookup = MethodHandles.lookup();
		Lookup exLookUp = MethodHandles.privateLookupIn(Example.class, lookup);
		var methodHandle = exLookUp.findStatic(Example.class, "aStaticHello", MethodType.methodType(String.class, int.class));
		assertEquals("question 42", (String) methodHandle.invokeExact(42));
	}
	
	@Test 
	void testInstanceHelloLookup() throws Throwable {
		Example e = new Example();
		Lookup lookup = MethodHandles.lookup();
		Lookup exLookUp = MethodHandles.privateLookupIn(Example.class, lookup);
		var methodHandle = exLookUp.findVirtual(Example.class, "anInstanceHello", MethodType.methodType(String.class, int.class));
		assertEquals("question 42", (String) methodHandle.invokeExact(e, 42));
	}	

}
