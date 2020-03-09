package fr.umlv.conc;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class LinkedVolatile2<E> {
	
	private VarHandle HEAD_REF;
	private volatile Entry<E> head;
	
	public LinkedVolatile2() {
		Lookup lookup = MethodHandles.lookup();
		try {
			HEAD_REF = lookup.findVarHandle(LinkedVolatile2.class, "head", Entry.class);
		} 
		catch (NoSuchFieldException | IllegalAccessException e) {
			throw new AssertionError(e);
		}
	}
	
	private static class Entry<E> {
		
		private final E element;
		private final Entry<E> next;
      
		private Entry(E element, Entry<E> next) {
			this.element = element;
			this.next = next;
		}
		
	}
	
	public void addFirst(E element) {
		Entry<E> temp = null;
		do {
			temp = this.head;
		} while (HEAD_REF.compareAndSet(temp, new Entry<>(Objects.requireNonNull(element), this.head)));
	}
	
	public int size() {
		var size = 0;
		for(var link = head; link != null; link = link.next) {
			size++;
		}
		return size;
    }
	
	public static void main(String[] args) {
		
		
		
		LinkedVolatile2<Integer> linked = new LinkedVolatile2<Integer>();
		var threads = new ArrayList<Thread>();
		for (int i = 0; i < 4; i++) {
			threads.add(new Thread(() -> {
				for (int j = 0; j < 1000000; j++) {
					linked.addFirst(1);
				}
			}));
		}
		threads.forEach(thread -> thread.start());
		threads.forEach(thread -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		System.out.println(linked.size());
		
	}

}