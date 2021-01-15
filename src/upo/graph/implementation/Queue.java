package upo.graph.implementation;

import java.util.LinkedList;

/**
 * 
 * @author @author Lorenzo Rossi 20031485
 *
 * 
 */
public class Queue<E> implements Fringe<E>{
	private LinkedList<E> queue;
	
	public Queue(){
		this.queue = new LinkedList<E>();
	}
	//methods
	@Override
	public void add(E vertex) {
		queue.addLast(vertex);
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public E get() {
		return queue.getFirst();
	}

	@Override
	public void remove() {
		queue.removeFirst();
	}

}
