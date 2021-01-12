package upo.graph.base;

import java.util.NoSuchElementException;

/**
 * Interfaccia che modella i metodi pubblici di un oggetto di tipo Grafo Pesato.
 * Questa interfaccia non deve essere modificata. Se trovate errori contattate il docente.
 * Questa interfaccia sarà integrata con altri metodi per le prossime consegne.
 * 
 * @author Luca Piovesan
 *
 */
public interface WeightedGraph extends Graph {
	
	/**
	 * Rappresenta il peso di default di un arco, appena è stato inserito.
	 */
	public static final double defaultEdgeWeight = 1.0; 
	
	/**
	 * Restituisce il peso di un arco, identificato dal vertice di partenza e da quello di arrivo.
	 * 
	 * @param sourceVertexIndex il vertice da cui esce l'arco.
	 * @param targetVertexIndex il vertice nel quale entra l'arco.
	 * @return il peso dell'arco (sourceVertexIndex, targetVertexIndex).
	 * @throws IllegalArgumentException se uno dei due vertici non appartiene al grafo.
	 * @throws NoSuchElementException se l'arco non appartiene al grafo.
	 */
	public double getEdgeWeight(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException, NoSuchElementException;
	
	/**
	 * Assegna un peso ad un arco, identificato dal vertice di partenza e da quello di arrivo.
	 * 
	 * @param sourceVertexIndex il vertice da cui esce l'arco.
	 * @param targetVertexIndex il vertice nel quale entra l'arco.
	 * @param weight il peso da assegnare all'arco (sourceVertexIndex, targetVertexIndex).
	 * @throws IllegalArgumentException se uno dei due vertici non appartiene al grafo.
	 * @throws NoSuchElementException se l'arco non appartiene al grafo.
	 */
	public void setEdgeWeight(int sourceVertexIndex, int targetVertexIndex, double weight) throws IllegalArgumentException, NoSuchElementException;

}
