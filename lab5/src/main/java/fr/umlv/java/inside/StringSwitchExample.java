package fr.umlv.java.inside;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

public class StringSwitchExample {
	
	private static final MethodHandle STRING_EQUALS;
	
	static {			
		Lookup lookup = MethodHandles.lookup();
		try {
			STRING_EQUALS = lookup.findVirtual(String.class, "equals", MethodType.methodType(boolean.class, Object.class));
		} catch (NoSuchMethodException | IllegalAccessException e) {
			throw new AssertionError(e);
		}
	};
	
	public static int stringSwitch(String mot) {
	  int result;
	  switch(mot) {
	    case "foo":
	      result = 0;
	      break;
	    case "bar":
	      result = 1;
	      break;
	    case "bazz":
	    	result = 2;
	    	break;
	    default:
	      result = -1;
	      break;
	  }
	  return result;
	}
	
	public static int stringSwitch2(String mot) {
	  MethodHandle MH = createMHFromString("foo", "bar", "bazz");		  
	  try {
		  return (int) MH.invokeExact(mot);
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
	
	static MethodHandle createMHFromString(String... words) {
		
		var MH = MethodHandles.dropArguments(MethodHandles.constant(int.class, -1), 0, String.class);
		
		for (var i = 0; i < words.length; i++) {
			MethodHandle test = MethodHandles.insertArguments(STRING_EQUALS, 1, words[i]);
			MethodHandle target = MethodHandles.dropArguments(MethodHandles.constant(int.class, i), 0, String.class) ;
			MethodHandle fallback = MH;
			MH = MethodHandles.guardWithTest(test, target, fallback);
		}		
		return MH;		
	}
	
	public static int stringSwitch3(String mot) {
	  MethodHandle MH = createMHFromStrings3("foo", "bar", "bazz");		  
	  try {
		  return (int) MH.invokeExact(mot);
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
	
	 public static MethodHandle createMHFromStrings3(String... matches) {
	    return new InliningCache(matches).dynamicInvoker(); 
	  }
	  
	 static class InliningCache extends MutableCallSite {
		 
	    private static final MethodHandle SLOW_PATH;
	    static {
	      SLOW_PATH = ;
	    }
	    
	    private final List<String> matches;
	    
	    public InliningCache(String... matches) {
	      super(MethodType.methodType(int.class, String.class));
	      this.matches = List.of(matches);
	      setTarget(MethodHandles.insertArgument(SLOW_PATH, 0, this));
	    }
	    
	    private int slowPath(String value) {
	    	var index =  matches.indexOf(value);
	    	MethodHandle test = MethodHandles.insertArguments(STRING_EQUALS, 1, value);
			MethodHandle target = MethodHandles.dropArguments(MethodHandles.constant(int.class, index), 0, String.class) ;
			MethodHandle fallback = getTarget();
			setTarget(MethodHandles.guardWithTest(test, target, fallback));	    	
	    }
	    
	  }
	
	
	
	
	
	
	
}
