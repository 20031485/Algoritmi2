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
	private int time;
	
	public AdjMatrixUndirWeight() {
		weight = new double [1][1];
		weight[0][0] = 0;
	}
	
	public AdjMatrixUndirWeight(Set<Integer> vertexIndex) {
		weight = new double [this.size()][this.size()];
		for(int i=0; i<this.size(); ++i) {
			for(int j=0; j<this.size(); ++j) {
				if(i == j)	
					weight[i][j]= 0;	//riempio la diagonale di zeri
				else 
					weight[i][j] = Double.MAX_VALUE; //fill all distances with infinity (max double val)
			}
		}
	}
	
	void print() {
		if(weight == null) {
			print("there are no vertices yet!");
		}
		else {
			System.out.print("=");
			for(int i = 0; i < weight.length; ++i)
				System.out.print("\t==");
			
			System.out.print("\t==\n\t");
			for(int i = 0; i < weight.length; ++i)
				System.out.print(i+"\t");
			System.out.println("\n");
			for(int i = 0; i < weight.length; ++i) {
				System.out.print(i+"");
				for(int j = 0; j < weight.length; ++j)
					if(weight[i][j] == Double.MAX_VALUE)
						System.out.print("\t[+inf]");
					else
						System.out.print("\t["+weight[i][j]+"]");
				System.out.println();
			}
			System.out.print("==");
			for(int i = 0; i < weight.length; ++i)
				System.out.print("\t==");
			
			System.out.print("\t==\n");
	
		}
	}
	
	private void print(String debugMsg) {
		System.out.println(debugMsg);
	}
	
	@Override
	public int addVertex() {
		//increase the matrix size by 1 per side
		double[][] newWeight = new double[weight.length + 1][weight.length + 1];
		for(int i = 0; i < newWeight.length; ++i) {
			for(int j = 0; j < newWeight.length; ++j) {
				//if in the last row/column, weight is Double.MAX_VALUE
				if((i == newWeight.length - 1 && j < newWeight.length - 1) 
						|| (i < newWeight.length - 1 && j == newWeight.length - 1)) {
					newWeight[i][j] = Double.MAX_VALUE;
				}
				//if on the diagonal, weight is 0
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
		if(index < weight.length && index >= 0)
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
	
	//interactive method for adding a new weighted edge between two vertices
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
	
	//adds an edge with a certain weight between two vertices
	public void addEdge(int sourceVertexIndex, int targetVertexIndex, double weight) {
		if(!(sourceVertexIndex >= 0 && sourceVertexIndex < this.weight.length)
				|| !(targetVertexIndex >= 0 && targetVertexIndex < this.weight.length))
			throw new IllegalArgumentException();
		if(this.weight[sourceVertexIndex][targetVertexIndex] == Double.MAX_VALUE) {
			this.weight[sourceVertexIndex][targetVertexIndex] = weight;
			this.weight[targetVertexIndex][sourceVertexIndex] = weight; //matrix must be symmetrical
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
		//this graph is not directed by definition
		return false;
	}
	
	@Override
	public boolean isCyclic() {
		//TODO this might come in handy, let's see if we have to implement it
		return false;
	}
	
	@Override
	public boolean isDAG() {
		//this graph is not directed by definition
		return false;
	}
	
	private VisitForest genericSearch(int sourceVertex, Fringe<Integer> fringe, VisitForest visitForest) {
//		int time = 0;
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
	
	@Override
	public VisitForest getBFSTree(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		if(!(startingVertex >= 0 && startingVertex < weight.length))
			throw new IllegalArgumentException();
		
		time = 0;
		
		VisitForest visitForest = new VisitForest(this, VisitType.BFS);
		visitForest = genericSearch(startingVertex, new Queue<Integer>(), visitForest);

		return visitForest;
	}

	@Override
	public VisitForest getDFSTree(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		if(!(startingVertex >= 0 && startingVertex < weight.length))
			throw new IllegalArgumentException();
		
		time = 0;
		
		VisitForest visitForest = new VisitForest(this, VisitType.DFS);
		visitForest = genericSearch(startingVertex, new Stack<Integer>(), visitForest);
		
		return visitForest;
	}
	
	@Override
	public VisitForest getDFSTOTForest(int startingVertex)
			throws UnsupportedOperationException, IllegalArgumentException {
		if(!(startingVertex >= 0 && startingVertex < weight.length))
			throw new IllegalArgumentException();

		time = 0;
		VisitForest visitForest = new VisitForest(this, VisitType.DFS_TOT);
		for(int i = 0; i < weight.length; ++i) {
			if(visitForest.getColor(i) == Color.WHITE) {
				visitForest = genericSearch(i, new Stack<Integer>(), visitForest);
			}
		}
		
		return visitForest;
	}
	
	@Override
	//total search that follows the order expressed by vertexOrdering
	public VisitForest getDFSTOTForest(int[] vertexOrdering)
			throws UnsupportedOperationException, IllegalArgumentException {
		
		time = 0;
		VisitForest visitForest = new VisitForest(this, VisitType.DFS_TOT);
		for(int i = 0; i < weight.length; ++i) {
			if(visitForest.getColor(vertexOrdering[i]) == Color.WHITE) {
				visitForest = genericSearch(vertexOrdering[i], new Stack<Integer>(), visitForest);
			}
		}
		
		return visitForest;
	}
	
	//no
	@Override
	public int[] topologicalSort() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Topological sorting operations can be supported by a DAG - this is not a DAG!");
	}
	
	@Override
	public Set<Set<Integer>> stronglyConnectedComponents() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This graph does not support SCC operations - this graph is not directed!");
	}
	
	@Override
	public Set<Set<Integer>> connectedComponents() throws UnsupportedOperationException {
		if(isDirected()) {
			throw new UnsupportedOperationException("Cannot find connected components of a directed graph!");
		}
		
		//initialize
		Set<Set<Integer>> allCCs = new HashSet<>();
		Set<Integer> singleCC = null;
		
		VisitForest visitForest = new VisitForest(this, VisitType.DFS);
		VisitForest visitTree = null;
		
		//cycle-search for CCs
		for(int i = 0; i < weight.length; ++i) {
			if(visitForest.getColor(i) == Color.WHITE) {
				print("CC visiting vertex " + i);
				//get DFS tree, then add it to the forest
				visitTree = getDFSTree(i);
				visitForest = genericSearch(i, new Stack<Integer>(), visitForest);
				
				//initialize empty CC
				singleCC = new HashSet<Integer>();
				
				//add the new component from the tree
				for(int j = 0; j < weight.length; ++j)
					if(visitTree.getColor(j) == Color.BLACK)
						singleCC.add(new Integer(j));
				
				//add the new CC to the group of CCs
				allCCs.add(singleCC);
			}
		}
		
		return allCCs;
	}
}
