package upo.graph.implementation;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import upo.graph.base.*;
import upo.graph.base.VisitForest.Color;
import upo.graph.base.VisitForest.VisitType;

/**
 * Implementazione mediante <strong>matrice di adiacenza</strong> di un grafo <strong>non orientato pesato</strong>.
 * 
 * @author Nome Cognome Matricola
 *
 */
public class AdjMatrixUndirWeight implements WeightedGraph{
	private double[][] weight;
	
	public AdjMatrixUndirWeight() {
		weight = new double [1][1];
		weight[0][0] = 0;
	}
	
	public AdjMatrixUndirWeight(Set<Integer> vertexIndex) {
		weight = new double [this.size()][this.size()];
		for(int i=0; i<this.size(); ++i) {
			for(int j=0; j<this.size(); ++j) {
				if(i == j)	weight[i][j]= 0;	//riempio la diagonale di zeri
				else weight[i][j] = Double.MAX_VALUE; //fill all distances with infinity (max double val)
			}
		}
	}
	
	void print() {
		for(int i = 0; i < weight.length; ++i) {
			System.out.print(i+": ");
			for(int j = 0; j < weight.length; ++j)
				if(weight[i][j] == Double.MAX_VALUE)
					System.out.print("\t[+inf]");
				else
					System.out.print("\t["+weight[i][j]+"]");
			System.out.println();
		}
	}
	
	@Override
	//indice? contenuto? wtf?
	public int addVertex() {
		//increase the matrix size by 1 per side
//		System.out.println("Current size: "+weight.length+"x"+weight.length);
		double[][] newWeight = new double[weight.length + 1][weight.length + 1];
//		System.out.println("New size: "+newWeight.length+"x"+newWeight.length);
		for(int i = 0; i < newWeight.length; ++i) {
			for(int j = 0; j < newWeight.length; ++j) {
//				System.out.println("i: "+i+"; j: "+j);
				//if in the last row/column, weight is Double.MAX_VALUE
				if((i == newWeight.length - 1 && j < newWeight.length - 1) 
						|| (i < newWeight.length - 1 && j == newWeight.length - 1)) {
					newWeight[i][j] = Double.MAX_VALUE;
				}
				//if on the diagona, weight is 0
				else if (i == j) {
					newWeight[i][j] = 0;
				}
				//if just copying
				else
					newWeight[i][j] = weight[i][j];
			}
		}
		weight = newWeight; //point to the new matrix
		return weight.length;
	}
	
	@Override
	public boolean containsVertex(int index) {
		if(index < weight.length)
			return true;
		return false;
	}
	
	@Override
	public void removeVertex(int index) throws NoSuchElementException {
		if(!this.containsVertex(index))
			throw new NoSuchElementException();
		else {
			int newSize = weight.length - 1;
			double[][] newWeight = new double[newSize][newSize];
			for(int i = 0; i < newSize; ++i) {
				for(int j = 0; j < newSize; ++j) {
					if(i >= index && j >= index){
						newWeight[i][j] = weight[i+1][j+1];
					}
					else {
						if(i >= index) {
							newWeight[i][j] = weight[i+1][j];
						}
						else if(j >= index) {
							newWeight[i][j] = weight[i][j+1];
						}
						else {
							newWeight[i][j] = weight[i][j];
						}
					}
				}
			}
			weight = newWeight; //point to the new matrix
		}
	}
	
	@Override
	public void addEdge(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException {
		if(!(sourceVertexIndex >= 0 && sourceVertexIndex < weight.length)
				|| !(targetVertexIndex >= 0 && targetVertexIndex < weight.length))
			throw new IllegalArgumentException();
		if(weight[sourceVertexIndex][targetVertexIndex] == Double.MAX_VALUE) {
			System.out.println("Insert edge weight: ");
			double w;
			Scanner scanner = new Scanner(System.in);
			w = Double.parseDouble(scanner.nextLine());
			while(w < 0) {
				System.out.println("Invalid edge weight!");
				System.out.println("Insert edge weight: ");
				w = Double.parseDouble(scanner.nextLine());
			}
			weight[sourceVertexIndex][targetVertexIndex] = w;
			weight[targetVertexIndex][sourceVertexIndex] = w; //matrix must be symmetrical
		}
		else
			System.out.println("That edge already exists!");
	}
	
	@Override
	public boolean containsEdge(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException {
		if(!(sourceVertexIndex >= 0 && sourceVertexIndex < weight.length)
				|| !(targetVertexIndex >= 0 && targetVertexIndex < weight.length))
			throw new IllegalArgumentException();
		else
			if(weight[sourceVertexIndex][targetVertexIndex] < Double.MAX_VALUE)
				return true;
		return false;
	}
	
	@Override
	public void removeEdge(int sourceVertexIndex, int targetVertexIndex)
			throws IllegalArgumentException, NoSuchElementException {
		if(!(sourceVertexIndex >= 0 && sourceVertexIndex < weight.length)
				|| !(targetVertexIndex >= 0 && targetVertexIndex < weight.length))
			throw new IllegalArgumentException();
		else {
			if(weight[sourceVertexIndex][targetVertexIndex] != Double.MAX_VALUE) {
				weight[sourceVertexIndex][targetVertexIndex] = Double.MAX_VALUE;
				weight[targetVertexIndex][sourceVertexIndex] = Double.MAX_VALUE;
			}
			else {
				System.out.println("The edge you are trying to remove does not exist! Throwing exception...");
				throw new NoSuchElementException();
			}
		}
	}
	
	@Override
	public double getEdgeWeight(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException, NoSuchElementException {
		if(!(sourceVertexIndex >= 0 && sourceVertexIndex < weight.length)
				|| !(targetVertexIndex >= 0 && targetVertexIndex < weight.length))
			throw new IllegalArgumentException();
		else {
			if(weight[sourceVertexIndex][targetVertexIndex] != Double.MAX_VALUE)
				return weight[sourceVertexIndex][targetVertexIndex];
			else {
				System.out.println("The edge you are trying to visit does not exist! Throwing exception...");
				throw new NoSuchElementException();
			}
		}
	}
	
	@Override
	public void setEdgeWeight(int sourceVertexIndex, int targetVertexIndex, double weight) throws IllegalArgumentException, NoSuchElementException {
		if(!(sourceVertexIndex >= 0 && sourceVertexIndex < this.weight.length)
				|| !(targetVertexIndex >= 0 && targetVertexIndex < this.weight.length))
			throw new IllegalArgumentException();
		//must be 0 on the diagonal
		else if(sourceVertexIndex == targetVertexIndex)
			throw new NoSuchElementException();
		else {
			this.weight[sourceVertexIndex][targetVertexIndex] = weight;
			this.weight[targetVertexIndex][sourceVertexIndex] = weight;
		}
	}
	
	@Override
	public Set<Integer> getAdjacent(int vertexIndex) throws NoSuchElementException {
		HashSet<Integer> adj = new HashSet<>();
		if(vertexIndex >= 0 && vertexIndex < weight.length) {
			for(int i = 0; i < weight.length; ++i) {
				if(weight[vertexIndex][i] > 0 && weight[vertexIndex][i] < Double.MAX_VALUE) {
					adj.add(i);
				}
			}
		}
		return adj;
	}
	
	@Override
	public boolean isAdjacent(int targetVertexIndex, int sourceVertexIndex) throws IllegalArgumentException {
		if(!(sourceVertexIndex >= 0 && sourceVertexIndex < this.weight.length)
				|| !(targetVertexIndex >= 0 && targetVertexIndex < this.weight.length))
			throw new IllegalArgumentException();
		if(weight[targetVertexIndex][sourceVertexIndex] != Double.MAX_VALUE)
			return true;
		return false;
	}
	
	@Override
	public int size() {
		return weight.length;
	}
	
	@Override
	public boolean isDirected() {
		return false;
	}
	
	@Override
	public boolean isCyclic() {
		//False because I cannot use BFS or DFS to visit it
		return false;
	}
	
	@Override
	public boolean isDAG() {
		//it's not directed!
		return false;
	}
	
	private VisitForest genericSearch(int sourceVertex, Fringe<Integer> fringe, VisitForest visitForest) {
		int time = 0;
		visitForest.setColor(sourceVertex, Color.GRAY);
		visitForest.setStartTime(sourceVertex, time);
		fringe.add(sourceVertex);
		time++;
		
		//while some vertices are grey
		while(!fringe.isEmpty()) {
			//get first fringe element
			//auxiliary vertex
			Integer u = fringe.get();
			Integer v = null;
			
			//get his adjacent vertices
			Set<Integer> adjacentVertices = this.getAdjacent(u);
			//for each adj vertex
			for(Integer a : adjacentVertices) {
				//get the first available vertex
				if(visitForest.getColor(a) == Color.WHITE) {
					v = a;
					continue;
				}
			}
			//if there are no adjacent vertices
			if(v == null) {
				visitForest.setColor(u, Color.BLACK);
				visitForest.setEndTime(u, time);
				fringe.remove();
				time++;
			}
			//if there is at least one adjacent vertex
			else {
				visitForest.setColor(v, Color.GRAY);
				visitForest.setStartTime(v, time);
				visitForest.setParent(v, u);
				fringe.add(v);
				time++;
			}
		}
		//return forest populated with times, relationships and distances
		return visitForest;
	}
	
	
	/*
	 * TODO
	 * */
	@Override
	public VisitForest getBFSTree(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		/*if(!(startingVertex >= 0 && startingVertex < weight.length))
			throw new IllegalArgumentException();
		return this.genericSearch(startingVertex, new Queue<Integer>(), new VisitForest(this, VisitType.BFS));
		*/
		throw new UnsupportedOperationException("This graph does not support BFS search operations!");
	}
	
	/*
	 * TODO
	 * */
	@Override
	public VisitForest getDFSTree(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		/*if(!(startingVertex >= 0 && startingVertex < weight.length))
			throw new IllegalArgumentException();
		return this.genericSearch(startingVertex, new Stack<Integer>(), new VisitForest(this, VisitType.DFS));
		*/
		throw new UnsupportedOperationException("This graph does not support DFS search operations!");
	}
	
	
	
	@Override
	public VisitForest getDFSTOTForest(int startingVertex)
			throws UnsupportedOperationException, IllegalArgumentException {
		/*if(!(startingVertex >= 0 && startingVertex < weight.length))
			throw new IllegalArgumentException();
		
		VisitForest visitForest = new VisitForest(this, VisitType.DFS_TOT);
		for(int i = 0; i < weight.length; ++i) {
			if(visitForest.getColor(i) == Color.WHITE)
				genericSearch(i, new Stack<Integer>(), visitForest);
		}
		return visitForest;*/
		throw new UnsupportedOperationException("This graph does not support DFSTOT forest operations!");
	}
	
	@Override
	//no
	public VisitForest getDFSTOTForest(int[] vertexOrdering)
			throws UnsupportedOperationException, IllegalArgumentException {
		throw new UnsupportedOperationException("This graph does not support DFSTOT forest operations!");
	}
	
	//no
	@Override
	public int[] topologicalSort() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This graph does not support topological sorting operations!");
	}
	
	@Override
	public Set<Set<Integer>> stronglyConnectedComponents() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This graph does not support SCC operations!");
	}
	
	
	/*
	 * TODO
	 * */
	@Override
	public Set<Set<Integer>> connectedComponents() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This graph does not support CC operations!");
		//sticazzi, le supporta eccome
	}
}
