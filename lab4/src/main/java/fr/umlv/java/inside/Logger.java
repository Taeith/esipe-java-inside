package fr.umlv.java.inside;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Objects;
import java.util.function.Consumer;

public interface Logger {
	
	static final ClassValue<MutableCallSite> ENABLE_CALLSITES = new ClassValue<MutableCallSite>() {
		
		protected MutableCallSite computeValue(Class<?> type) {
			
			var array = new MutableCallSite[] { new MutableCallSite(MethodHandles.constant(boolean.class, true)) };
			
			MutableCallSite.syncAll(array);
			System.out.println("Protection établit !");
		    return array[0];		    
		    
		}
		
	};
	
	public static void enable(Class<?> declaringClass, boolean enable) {
		ENABLE_CALLSITES.get(declaringClass).setTarget(MethodHandles.constant(boolean.class, enable));
	}
	
  public void log(String message);
  
  public static Logger of(Class<?> declaringClass, Consumer<? super String> consumer) {
	  
    var mh = createLoggingMethodHandle(declaringClass, consumer);
    
    return new Logger() {
    	
      @Override
      public void log(String message) {
    	Objects.requireNonNull(message);
        try {
          mh.invokeExact(message);
        } catch(Throwable t) {
          if (t instanceof RuntimeException) {
            throw (RuntimeException)t;
          }
          if (t instanceof Error) {
            throw (Error)t;
          }
          throw new UndeclaredThrowableException(t);
        }
        
      }
    };
  }
  
  public static Logger fastOf(Class<?> declaringClass, Consumer<? super String> consumer) {
	  
	    var mh = createLoggingMethodHandle(declaringClass, consumer);
	    
	    return (message) -> {
	        try {
	          mh.invokeExact(message);
	        } catch(Throwable t) {
	          if (t instanceof RuntimeException) {
	            throw (RuntimeException)t;
	          }
	          if (t instanceof Error) {
	            throw (Error)t;
	          }
	          throw new UndeclaredThrowableException(t);
	        }
	    };
	  }
  
  private static MethodHandle createLoggingMethodHandle(Class<?> declaringClass, Consumer<? super String> consumer) {
	  
	  Objects.requireNonNull(declaringClass);
	  Objects.requireNonNull(consumer);
	  
	  MethodHandle target = null;
	  
	  MethodHandle test = ENABLE_CALLSITES.get(declaringClass).dynamicInvoker();
	  
	  MethodHandle fallback = MethodHandles.empty(MethodType.methodType(void.class, String.class));
	  
	  try {
		  target = MethodHandles.lookup()
					.findVirtual(Consumer.class, "accept", MethodType.methodType(void.class, Object.class));
	  } catch (NoSuchMethodException | IllegalAccessException e) {
			throw new AssertionError();
	  }
	  
	  target = MethodHandles.insertArguments(target, 0, consumer);
	  target = target.asType(MethodType.methodType(void.class, String.class));
	  
	  return MethodHandles.guardWithTest(test, target, fallback);
	  
  }	
  
  
}