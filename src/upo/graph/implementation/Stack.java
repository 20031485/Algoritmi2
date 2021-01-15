package upo.graph.implementation;

import java.util.LinkedList;

/**
 * 
 * @author @author Lorenzo Rossi 20031485
 *
 * 
 */
public class Stack<E> implements Fringe<E> {
	//fields
	private LinkedList<E> stack;
	
	//constructors
	public Stack(){
		this.stack = new LinkedList<E>();
	}
	
	//methods
	@Override
	public void add(E vertex) {
		this.stack.addFirst(vertex);
	}

	@Override
	public boolean isEmpty() {
		return this.stack.isEmpty();
	}

	@Override
	public E get() {
		return this.stack.getFirst();
	}

	@Override
	public void remove() {
		this.stack.removeFirst();
	}

}
