package upo.graph.implementation;

public interface Fringe<E> {
	//add element to fringe
	public void add(E vertex);
	//true if empty, false otherwise
	public boolean isEmpty();
	//get first element of fringe
	public E get();
	//remove first element from fringe
	public void remove();
}
