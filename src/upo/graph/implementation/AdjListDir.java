package upo.graph.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

import upo.graph.base.*;
import upo.graph.base.VisitForest.Color;
import upo.graph.base.VisitForest.VisitType;

/**
 * Implementazione mediante <strong>liste di adiacenza</strong> di un grafo <strong>orientato non pesato</strong>.
 * 
 * @author Lorenzo Rossi 20031485
 *
 */
public class AdjListDir implements Graph{
	private int time;
	private int t;
	
	/*inner class for handling vertices*/
	static class Vertex{
		//fields
		private int value;
		private LinkedList<Vertex> adjacents;
		private Integer start;
		private Integer end;
		
		//constructor
		public Vertex(int value) {
			this.setValue(value);
			adjacents = new LinkedList<Vertex>();
		}
		
		//methods
		public int getValue() {
			return this.value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public void incrVal() {
			this.value++;
		}
		
		public void decrVal() {
			this.value--;
		}
		
		public LinkedList<Vertex> getAdjacents(){
			return this.adjacents;
		}
		
		public void addAdjacent(Vertex v) {
//			System.out.println("Adding adjacent vertex "+v.getValue()+" to "+this.getValue());
			this.adjacents.addLast(v);
//			for(int i = 0; i < adjacents.size(); ++i)
//				System.out.print(adjacents.get(i).getValue()+" ");
//			System.out.println();
		}
		
//		public void removeAdjacent(Vertex v) {
//			this.adjacents.remove(v.getValue());
//		}
	}
	
	//fields
	//a list of Vertices
	ArrayList<Vertex> vertices;
	
	//constructor
	public AdjListDir() {
		vertices = new ArrayList<Vertex>();
	}
	
	//methods
	public void print() {
		for(int i = 0; i < vertices.size(); ++i) {
			int j = 0;
			System.out.print("["+vertices.get(i).getValue()+"]");
			while(j < vertices.get(i).getAdjacents().size() && vertices.get(i).getAdjacents().get(j) != null) {
				System.out.print("->[" + vertices.get(i).getAdjacents().get(j).getValue()+"]");
				j++;
			}
			System.out.println("-//");
		}
	}
	
	public void printVertex(int index) throws IndexOutOfBoundsException{
		if(!containsVertex(index))
			throw new IndexOutOfBoundsException();
		else {
			int j = 0;
			System.out.print(index+") ["+vertices.get(index).getValue()+"]");
			while(++j < vertices.get(index).getAdjacents().size() && vertices.get(index).getAdjacents().get(j) != null) {
				System.out.print("->[" + vertices.get(index).getAdjacents().get(j).getValue()+"]");
			}
			System.out.println("-//");
		}
	}
	@Override
	public int addVertex() {
		String name = null;
		Vertex newVertex = new Vertex(vertices.size());
		vertices.add(newVertex);
//		System.out.println("Vertex "+newVertex.getValue()+" added!");
		//restituisce l'indice del vertice appena aggiunto (l'ultimo)
		return vertices.get(vertices.size() - 1).getValue();
	}
	
	public int addVertex(Vertex vertex) {
		vertices.add(vertex);
		return vertices.get(vertices.size() - 1).getValue();
	}

	@Override
	public boolean containsVertex(int index) {
		if(index < vertices.size() && index >= 0)
			return true;
		return false;
	}

	@Override
	public void removeVertex(int index) throws NoSuchElementException {
		if(!this.containsVertex(index))
			throw new NoSuchElementException();
		else {
			for(int i = 0; i < vertices.size(); ++i) {
				int j = 0;
				while(j < vertices.get(i).getAdjacents().size()) {
					//delete if val == index
					if(index == vertices.get(i).getAdjacents().get(j).getValue()) {
						vertices.get(i).getAdjacents().remove(j);
						j--;
					}
					j++;
				}
				if(index < vertices.get(i).getValue())
					vertices.get(i).decrVal();
			}
			vertices.remove(index);
		}
	}

	@Override
	public void addEdge(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException {
		//check parameters
		if((containsVertex(sourceVertexIndex) && containsVertex(targetVertexIndex))) {
			//se l'arco esiste, non fa nulla
			//se l'arco non esiste, lo aggiungo
			if(!this.containsEdge(sourceVertexIndex, targetVertexIndex)) {
				vertices.get(sourceVertexIndex).addAdjacent(vertices.get(targetVertexIndex));
			}
		}
		else
			throw new IllegalArgumentException();
	}

	@Override
	public boolean containsEdge(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException {
		if((containsVertex(sourceVertexIndex) && containsVertex(targetVertexIndex))){
			int j = 0;
			while(j < vertices.get(sourceVertexIndex).getAdjacents().size()) {
				if(vertices.get(sourceVertexIndex).getAdjacents().get(j).getValue() == targetVertexIndex)
					return true;
				j++;
			}
			return false;
		}
		else
			throw new IllegalArgumentException();
	}

	@Override
	public void removeEdge(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException, NoSuchElementException {
		if((containsVertex(sourceVertexIndex) && containsVertex(targetVertexIndex))){
			if(this.containsEdge(sourceVertexIndex, targetVertexIndex)) {
				int j = 0;
				while(j < vertices.get(sourceVertexIndex).getAdjacents().size()) {
					if(vertices.get(sourceVertexIndex).getAdjacents().get(j).getValue() == targetVertexIndex) {
						vertices.get(sourceVertexIndex).getAdjacents().remove(j);
					}
					j++;
				}
			}
			else
				throw new NoSuchElementException();
		}
		else
			throw new IllegalArgumentException();
	}

	@Override
	public Set<Integer> getAdjacent(int vertexIndex) throws NoSuchElementException {
		if(!this.containsVertex(vertexIndex))
			throw new NoSuchElementException();
		
		HashSet<Integer> set = new HashSet<Integer>();
		int j = 0;
		while(j < vertices.get(vertexIndex).getAdjacents().size()) {
			set.add(vertices.get(vertexIndex).getAdjacents().get(j).getValue());
			j++;
		}
		return set;
	}

	@Override
	public boolean isAdjacent(int targetVertexIndex, int sourceVertexIndex) throws IllegalArgumentException {
		if((containsVertex(sourceVertexIndex) && containsVertex(targetVertexIndex))){
			if(containsEdge(sourceVertexIndex, targetVertexIndex))
				return true;
			return false;
		}
		else
			throw new IllegalArgumentException();
	}

	@Override
	public int size() {
		return vertices.size();
	}

	@Override
	public boolean isDirected() {
		return true;
	}

	@Override
	public boolean isCyclic() {
//		print("entering isCyclic");
		//initialize graph
		VisitForest visitForest = new VisitForest(this, VisitType.DFS);
		
		//call recursive cycle search on all vertices
		for(Vertex u : vertices) {
//			print("isCyclic: Checking " + u.getValue());
			
			//exit as soon as a cycle is found
			if(visitForest.getColor(u.getValue()) == Color.WHITE && recCyclicSearch(this, u.getValue(), visitForest)) {
//				System.out.println("[ERROR]: Cycle found for " + u.getValue() + "!");
				return true;
			}
		}
		
		//return false if no cycle is found
		return false;
	}
	
	//returns true if no cycle is found, false otherwise
	private boolean recCyclicSearch(Graph graph, int sourceVertex, VisitForest visitForest) {
		//source vertex is visited
		visitForest.setColor(sourceVertex, Color.GRAY);
		
		Set<Integer> adj = this.getAdjacent(sourceVertex);
		
		//search recursively for cycles in sourceVertex's adjacents
		for(Integer v : adj) {
			if(visitForest.getColor(v) == Color.WHITE) {
//				System.out.println("recCyclicSearch: Checking " + v);
				visitForest.setParent(v, sourceVertex);
				if(recCyclicSearch(graph, v, visitForest)) {
//					System.out.println("recCyclicSearch: No cycles for " + v);
					return true;
				}
			}
			else if(visitForest.getColor(v) == Color.GRAY) {
//				print("recCyclicSearch: no cycles for v");
				return true;
			}
//			else if(v != visitForest.getParent(sourceVertex)) {
//				System.out.println("recCyclicSearch: No cycles for " + v);
//				return true;
//			}
		}
//		print("recCyclicSearch: no cycles for " + sourceVertex);
		visitForest.setColor(sourceVertex, Color.BLACK);
		return false;
	}

	@Override
	public boolean isDAG() {
		if(isDirected() && !isCyclic())
			return true;
		return false;
	}

	private VisitForest genericSearch(int sourceVertex, Fringe<Integer> fringe, VisitForest visitForest) {
//		int time = 0;
//		int distance = 0;
		visitForest.setColor(sourceVertex, Color.GRAY);
		visitForest.setStartTime(sourceVertex, time);
//		visitForest.setDistance(sourceVertex, distance);
		fringe.add(sourceVertex);
		time++;
//		distance++;
//		print("\tBEGIN genericSearch");
		//while some vertices are grey
		while(!fringe.isEmpty()) {
			
			//get first fringe element
			Integer u = fringe.get();
			//auxiliary vertex
			Integer v = null;
			
			//get its adjacent vertices
			Set<Integer> adjacentVertices = this.getAdjacent(u);
//			print("\t>>>adjacents to " + u + ": " + adjacentVertices.toString());
			
			//for each adj vertex
			for(Integer a : adjacentVertices) {
				//get the first available vertex
				if(visitForest.getColor(a) == Color.WHITE) {
					v = a;
					continue;
				}
			}
			
			//if u has no adjacent vertices
			if(v == null) {
//				print("\t\t[genericSearch]: setting " + u + " to " + Color.BLACK);
				visitForest.setColor(u, Color.BLACK);
				visitForest.setEndTime(u, time);
				fringe.remove();
				time++;
			}
			
			//if u has at least one adjacent vertex
			else {
//				print("\t\t[genericSearch]: setting " + u + " to " + Color.GRAY);
				visitForest.setColor(v, Color.GRAY);
				visitForest.setStartTime(v, time);
//				visitForest.setDistance(v, distance);
				visitForest.setParent(v, u);
				fringe.add(v);
				time++;
//				distance++;
			}
		}
//		print("\tEND genericSearch");
		//return TREE (not forest) populated with times, relationships and distances
		return visitForest;
	}
	
	public void printVisit(VisitForest visitForest) {
		for(int i = 0; i < vertices.size(); ++i) {
			System.out.println(
					"vertex: "+vertices.get(i).getValue()+
					"\tstart: "+visitForest.getStartTime(i)+
					"\tend: "+visitForest.getEndTime(i)//+
//					"distance: "+visitForest.getDistance(i)
			);
		}
	}
	
	@Override
	public VisitForest getBFSTree(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		if(containsVertex(startingVertex)) {
			time = 0;
			return this.genericSearch(startingVertex, new Queue<Integer>(), new VisitForest(this, VisitType.BFS));
		}
		else
			throw new IllegalArgumentException();
	}

	@Override
	public VisitForest getDFSTree(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		if(containsVertex(startingVertex)) {
			time = 0;
			return this.genericSearch(startingVertex, new Stack<Integer>(), new VisitForest(this, VisitType.DFS));
		}
		else
			throw new IllegalArgumentException();
	}

	@Override
	public VisitForest getDFSTOTForest(int startingVertex)
			throws UnsupportedOperationException, IllegalArgumentException {
		
//		print("\tBEGIN getDFSTOTForest");
		time = 0;
		if(containsVertex(startingVertex)) {
			VisitForest visitForest = new VisitForest(this, VisitType.DFS_TOT);
//			print("\n\t\tchoosing vertex " + startingVertex + " for DFSTOT");
			visitForest = genericSearch(vertices.get(startingVertex).getValue(), new Stack<Integer>(), visitForest);
			for(Vertex u : vertices) {
				if(visitForest.getColor(u.getValue()) == Color.WHITE) {
//					print("\n\t\tchoosing vertex " + u.getValue() + " for DFSTOT");
					visitForest = genericSearch(u.getValue(), new Stack<Integer>(), visitForest);
				}
			}
//			print("\tEND getDFSTOTForest");
//			print("roots: " + visitForest.getRoots().toString());
			return visitForest;
		}
		else
			throw new IllegalArgumentException();
	}

	//return visit order
	@Override
	public VisitForest getDFSTOTForest(int[] vertexOrdering)
			throws UnsupportedOperationException, IllegalArgumentException {
		if(vertexOrdering == null || vertexOrdering.length != this.size())
			throw new IllegalArgumentException();

		time = 0;
		
		VisitForest visitForest = new VisitForest(this, VisitType.DFS_TOT);
		
		for(int i = 0; i < this.size(); ++i) {
			if(visitForest.getColor(vertexOrdering[i]) == Color.WHITE) {
				visitForest = genericSearch(vertexOrdering[i], new Stack<Integer>(), visitForest);
			}
		}

		return visitForest;
	}

	//based on DFS search
	@Override
	public int[] topologicalSort() throws UnsupportedOperationException {
		if(!this.isDAG())
			throw new UnsupportedOperationException("This graph is not a DAG!");
		
		//initialize graph
		int[] ord = new int[this.size()];
		Arrays.fill(ord, -1);
		t = this.size() - 1;
		VisitForest visitForest = new VisitForest(this, VisitType.DFS);
		time = 0;
		
		//for each vertex in the graph
		for(int i = 0; i < this.size(); ++i) {
			//if a vertex is not visited, launch DFSSearch on it
			if(visitForest.getColor(i) == Color.WHITE) {
//				print("launching topologicalDFSSort on vertex " + i);
				topologicalDFSSort(i, ord, /*t,*/ visitForest);
			}
		}
		
		return ord;
	}
	
	private void topologicalDFSSort(int startingVertex, int[] ord,/* int t,*/ VisitForest visitForest) {
		visitForest.setColor(startingVertex, Color.GRAY);
		visitForest.setStartTime(startingVertex, time);
		time++;
		Set<Integer> adj = this.getAdjacent(startingVertex);
		
		for(Integer v : adj) {
			if(visitForest.getColor(v) == Color.WHITE) {
				visitForest.setParent(v, startingVertex);
				topologicalDFSSort(v, ord, /*t,*/ visitForest);
			}
		}
		visitForest.setColor(startingVertex, Color.BLACK);
		visitForest.setEndTime(startingVertex, time);
		
		ord[t] = startingVertex;
		time++;
		t--;
	}
	
	@Override
	public Set<Set<Integer>> stronglyConnectedComponents() throws UnsupportedOperationException {
		//initialize sets of integers (SCCs)
		Set<Set<Integer>> allSCCs = new HashSet<>();
		Set<Integer> singleSCC = null;
		Set<Integer> auxSCC = new HashSet<>();
		
		//initialize graph and perform total DFS
		VisitForest visitForest = getDFSTOTForest(0);

		int vertexOrdering[] = new int[this.size()];
		
		//descend-sort vertices according to their end-visit time
		LinkedList<VisitedVertex> visited = new LinkedList<>();
		for(int i = 0; i < size(); ++i) {
			visited.add(new VisitedVertex(i, visitForest.getStartTime(i), visitForest.getEndTime(i)));
		}
		visited.sort((VisitedVertex v1, VisitedVertex v2) -> v2.getEnd() - v1.getEnd());
		
		//fill vertexOrdering with the vertices' values (they are sorted by descending end-visit times)
		for(int i = 0; i < vertexOrdering.length; ++i)
			vertexOrdering[i] = visited.get(i).getValue();

		//transpose graph
		AdjListDir transposedGraph = this.transpose();
		transposedGraph.print();
		
		//initialize handlers for the next DFSTOT search
		VisitForest visitTransposedForest = new VisitForest(transposedGraph, VisitType.DFS_TOT);
		VisitForest visitTransposedTree = null;
		
		print("\n\n\n");
		//perform DFS visit
//		time = 0;
		for(int i = 0; i < vertexOrdering.length; ++i) {
			if(visitTransposedForest.getColor(vertexOrdering[i]) == Color.WHITE) {
				print("vertexOrdering: " + vertexOrdering[i]);
				time = 0;
				visitTransposedTree = transposedGraph.getDFSTree(vertexOrdering[i]);
//				time = 0;
				visitTransposedForest = transposedGraph.genericSearch(vertexOrdering[i], new Stack<Integer>(), visitTransposedForest);
				singleSCC = new HashSet<Integer>();
//				print("\n\ntransposedTree");
//				printVisit(visitTransposedTree);
//				print("\n\ntransposedForest:");
//				printVisit(visitTransposedForest);
				for(int j = 0; j < transposedGraph.size(); ++j) {
					
					if(visitTransposedTree.getColor(j) == Color.BLACK) {
						if(!auxSCC.contains(j)) {
//							print("\tadding " + j + " to singleSCC");
							singleSCC.add(j);
						}
						auxSCC.add(j);
					}
					
//					print("aux: " + auxSCC.toString());
				}
				
//				print("\tadding " + singleSCC.toString() + " to allSCCs");
				
				
				allSCCs.add(singleSCC);
//				visitTransposedTree = new VisitForest(transposedGraph, VisitType.DFS);
			}
		}
//		print(allSCCs.toString());
		return allSCCs;
	}

	@Override
	public Set<Set<Integer>> connectedComponents() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Searching for CC in a directed graph is meaningless.");
	}
	
	//transpose the current graph
	private AdjListDir transpose() {
		AdjListDir transposedGraph = new AdjListDir();
		
		for(int i = 0; i < this.size(); ++i) {
			transposedGraph.addVertex();
		}
		
		for(int i = 0; i < transposedGraph.size(); ++i) {
			for(Vertex v : this.vertices.get(i).getAdjacents()) {
				transposedGraph.addEdge(v.getValue(), i);
			}
		}	
		
		return transposedGraph;
	}
	
	private void print(String debugMsg) {
		System.out.println(debugMsg);
	}
	
	
	
	//class for handling start/end visit times of vertices
	private class VisitedVertex{
		private Integer vertex;
		private Integer start;
		private Integer end;
		
		public VisitedVertex(Integer vertex, Integer start, Integer end){
			this.vertex = vertex;
			this.start = start;
			this.end = end;
		}
		
		protected Integer getVertex() {
			return vertex;
		}
		
		protected Integer getValue() {
			return vertex;
		}

		protected void setVertex(Integer vertex) {
			this.vertex = vertex;
		}

		protected Integer getStart() {
			return start;
		}

		protected void setStart(Integer start) {
			this.start = start;
		}

		protected Integer getEnd() {
			return end;
		}

		protected void setEnd(Integer end) {
			this.end = end;
		}
	}
}
