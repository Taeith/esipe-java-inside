package fr.umlv.conc;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class LinkedVolatile<E> {
	
	private static class Entry<E> {
		
		private final E element;
		private final Entry<E> next;
      
		private Entry(E element, Entry<E> next) {
			this.element = element;
			this.next = next;
		}
		
	}
	
	AtomicReference<Entry<E>> head = new AtomicReference<>();
	
	public void addFirst(E element) {
		Objects.requireNonNull(element);
		Entry<E> temp;
		do 
		{
			temp = head.get();
		} 
		while (!head.compareAndSet(temp, new Entry<>(element,temp)));
	}
	
	public int size() {
		var size = 0;
		for(var link = head.get(); link != null; link = link.next) {
			size++;
		}
		return size;
    }
	
	public static void main(String[] args) {
		
		LinkedVolatile<Integer> linked = new LinkedVolatile<Integer>();
		var threads = new ArrayList<Thread>();
		for (int i = 0; i < 4; i++) {
			threads.add(new Thread(() -> {
				for (int j = 0; j < 100_000; j++) {
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