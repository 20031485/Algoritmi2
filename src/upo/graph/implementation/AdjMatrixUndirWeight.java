package upo.graph.implementation;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import upo.graph.base.*;
import upo.graph.base.VisitForest.Color;
import upo.graph.base.VisitForest.VisitType;
import upo.graph.implementation.AdjListDir.Vertex;

/**
 * Implementazione mediante <strong>matrice di adiacenza</strong> di un grafo <strong>non orientato</strong> e <strong>pesato</strong>.
 * 
 * @author Lorenzo Rossi 20031485
 *
 */
public class AdjMatrixUndirWeight implements WeightedGraph{
	private double[][] weight;
	private int time;
	
	public AdjMatrixUndirWeight() {
		weight = new double [1][1];
		weight[0][0] = 0;
	}
	
	//might be useful if starting from a set of vertices
	public AdjMatrixUndirWeight(Set<Integer> vertexIndex) {
		weight = new double [this.size()][this.size()];
		for(int i=0; i<this.size(); ++i) {
			for(int j=0; j<this.size(); ++j) {
				if(i == j)	
					weight[i][j]= 0;
				else 
					weight[i][j] = Double.MAX_VALUE; //fill all distances with infinity (max double val)
			}
		}
	}
	
	public void print() {
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
	
	void print(String debugMsg) {
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
		
		//point to the new matrix
		weight = newWeight; 
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
			//point to the new matrix
			weight = newWeight; 
		}
	}
	
	//interactive method for adding a new weighted edge between two vertices
	@Override
	public void addEdge(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException {
		if(!(sourceVertexIndex >= 0 && sourceVertexIndex < weight.length)
				|| !(targetVertexIndex >= 0 && targetVertexIndex < weight.length))
			throw new IllegalArgumentException();
		if(weight[sourceVertexIndex][targetVertexIndex] == Double.MAX_VALUE) {
			System.out.println("Insert edge <"+sourceVertexIndex+","+targetVertexIndex+">'s weight: ");
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
				throw new NoSuchElementException("The edge you are trying to remove does not exist! Throwing exception...");
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
				throw new NoSuchElementException("The edge you are trying to remove does not exist! Throwing exception...");
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
//		print("\tentering isCyclic");
		//initialize graph
		VisitForest visitForest = new VisitForest(this, VisitType.DFS);
		
		//call recursive cycle search on all vertices
		for(int i = 0; i < size(); ++i) {
			//exit as soon as a cycle is found
			if(visitForest.getColor(i) == Color.WHITE && recCyclicSearch(this, i, visitForest)){
				return true;
			}
		}
		
		//return false if no cycle is found
		return false;
	}
	
	public boolean recCyclicSearch(AdjMatrixUndirWeight graph, int u, VisitForest visitForest) {
		//color of u = gray
		visitForest.setColor(u, Color.GRAY);
		
		Set<Integer> adjacents = getAdjacent(u);
		
		//foreach adjacent to u
		for(int v : adjacents) {
			//if color = white
			if(visitForest.getColor(v) == Color.WHITE) {
				//set u as parent of v
				visitForest.setParent(v, u);
				
				//if recCyclicSearch(..., v, ...)
				if(recCyclicSearch(graph, v, visitForest))
					//return true
					return true;
				
				//else if v is not parent of u
				else try{
					if(visitForest.getParent(u) != v)
						return true;
				}
				catch(NullPointerException e) {
					//no operation
				}
			}
		}
		//color of u = black
		return false;
	}
	
	@Override
	public boolean isDAG() {
		//this graph is not directed by definition
		return false;
	}
	
	private VisitForest genericSearch(int sourceVertex, Fringe<Integer> fringe, VisitForest visitForest) {
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

	@Override
	public WeightedGraph getBellmanFordShortestPaths(int startingVertex)
			throws UnsupportedOperationException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WeightedGraph getDijkstraShortestPaths(int startingVertex)
			throws UnsupportedOperationException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WeightedGraph getPrimMST(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WeightedGraph getKruskalMST() throws UnsupportedOperationException {
		if(!isConnected())
			throw new UnsupportedOperationException("Trying to find MST of non-connected graph!");

		int edgeCount = 0;
		//THIS is the "A" (Kruskal Tree) of the notes!!!!
		AdjMatrixUndirWeight mst = new AdjMatrixUndirWeight();
		
		//add the same number of vertices of the initial graph
		for(int i = 0; i < weight.length - 1; ++i)
			mst.addVertex();

		LinkedList<Edge> s = new LinkedList<>();
		
		//fill it with all the edges
		for(int i = 0; i < weight.length; ++i) {
			for(int j = 0; j < weight.length; ++j) {
				//it is a symmetrical matrix, so we only need
				//to visit its upper (or lower) half, without
				//visiting the diagonal
				//PLUS
				//don't add an edge if its weight is MAX_DOUBLE (i.e. infinity)
				if((i < j) && (weight[i][j] != Double.MAX_VALUE)) {
					s.add(new Edge(i, j, weight[i][j]));
				}
			}
		}

		//sort them by non-decreasing weight
		s.sort((Edge e1, Edge e2) -> Double.compare(e1.getWeight(), e2.getWeight()));
		
		//create unionFind containing the original G vertices as initial single-element collections
		KUnionFind unionFind = new KUnionFind();
		
		for(int i = 0; i < size(); ++i)
			unionFind.makeSet(i);
		
//		for each edge(u, v) in s
		for(Edge e : s) {
//			if(count = n-1)
			if(edgeCount == this.size() - 1)
				return mst;
			
			//union between the two vertices - on success, vertex2's father should be vertex1
			unionFind.union(e.getVertex1(), e.getVertex2());

			//if vertex2's father is vertex1
//			if(k-union(u, v))
			if(e.getVertex1() == unionFind.getParent(e.getVertex2())) {
				mst.addEdge(e.getVertex1(), e.getVertex2(), weight[e.getVertex1()][e.getVertex2()]);
				edgeCount++;
			}
		}
		
		return null;
	}
	
	private class Edge{
		private int vertex1;
		private int vertex2;
		private double weight;
		
		Edge(int vertex1, int vertex2, double weight2){
			this.vertex1 = vertex1;
			this.vertex2 = vertex2;
			this.weight = weight2;
		}
		
		protected int getVertex1() {
			return vertex1;
		}
		protected void setVertex1(int vertex1) {
			this.vertex1 = vertex1;
		}
		protected int getVertex2() {
			return vertex2;
		}
		protected void setVertex2(int vertex2) {
			this.vertex2 = vertex2;
		}
		protected double getWeight() {
			return weight;
		}
		protected void setWeight(int weight) {
			this.weight = weight;
		}
	}
	
	boolean isConnected() {
		VisitForest visitForest = new VisitForest(this, VisitType.DFS);
		visitForest = genericSearch(0, new Stack<Integer>(), visitForest);
		for(int i = 0; i < weight.length; ++i) {
			if(i != 0){
				try {
					//the next comparison is only written to throw the exception,
					//in which case the connection check continues
					if(visitForest.getParent(i) == visitForest.getParent(i));
				}
				catch(NullPointerException e) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}
}
