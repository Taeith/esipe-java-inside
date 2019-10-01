package fr.umv.java.inside;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {

	/*
	  public static String toJSON(Person person) {
	    return
	        "{\n" +
	        "  \"firstName\": \"" + person.getFirstName() + "\"\n" +
	        "  \"lastName\": \"" + person.getLastName() + "\"\n" +
	        "}\n";
	  }

	  public static String toJSON(Alien alien) {
	    return 
	        "{\n" + 
	        "  \"planet\": \"" + alien.getPlanet() + "\"\n" + 
	        "  \"members\": \"" + alien.getAge() + "\"\n" + 
	        "}\n";
	  }
	  */

     private static String propertyName(String name) {
       return Character.toLowerCase(name.charAt(3)) + name.substring(4);
     }

     private static Object callGetter(Object o, Method g) {
     	Object object = Objects.requireNonNull(o);
     	Method getter = Objects.requireNonNull(g);
     	try {
     		return getter.invoke(object);
     	}
     	catch (IllegalAccessException e) {
     		throw new IllegalStateException(e);
     	}
     	catch (InvocationTargetException e) {
     		var cause = e.getCause();
     		if (cause instanceof RuntimeException) {
     			throw (RuntimeException) cause;
     		}
     		else if (cause instanceof Error) {
     			throw (Error) cause;
     		}
     		else {
     			throw new UndeclaredThrowableException(cause);
     		}
     	}
     }

	  public static String toJSON(Object o) {

	  	Object object = Objects.requireNonNull(o);

	  	return Arrays.stream(object.getClass().getMethods())
	  			.filter(method -> method.getName().startsWith("get") && method.getName() != "getClass")
	  			.map(method -> propertyName(method.getName() + " : " + callGetter(object, method)))
	  			.collect(Collectors.joining(", ", "{ ", " }"));

	  }

	}