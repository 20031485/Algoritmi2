package upo.graph.implementation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import upo.graph.base.VisitForest;

class AdjMatrixUndirWeightTest {

//	@Test
//	void test1() {
//		AdjMatrixUndirWeight amuw = new AdjMatrixUndirWeight();
//		amuw.print();
//		System.out.println("Matrix now has "+amuw.addVertex()+" vertices;");
//		System.out.println("Matrix now has "+amuw.addVertex()+" vertices;");
//		System.out.println("Matrix now has "+amuw.addVertex()+" vertices;");
//		System.out.println();
//		
//		amuw.print();
//		assertEquals(amuw.addVertex(), 5);
//		assertTrue(amuw.containsVertex(4));
//		assertFalse(amuw.containsVertex(7));
//		
//		amuw.print();
//		System.out.println();
//		amuw.removeVertex(3);
//		amuw.print();
//		
//		System.out.println();
//		amuw.addEdge(1, 3);
//		amuw.addEdge(3, 1);
//		amuw.print();
//		
//		assertTrue(amuw.containsEdge(1, 3));
//		assertTrue(amuw.containsEdge(3, 1));
//		assertFalse(amuw.containsEdge(1, 2));
//		assertFalse(amuw.containsEdge(2, 1));
//		
//		System.out.println();
//		System.out.println("Removing edge <1,3>:");
//		amuw.removeEdge(1, 3);
//		amuw.print();
//		amuw.setEdgeWeight(1, 3, 666);
//		
//		System.out.println();
//		amuw.addEdge(1, 3);
//		amuw.addEdge(0, 1);
//		amuw.print();
//		
//		System.out.println();
//		amuw.addVertex();
//		amuw.print();
//		
//		System.out.println();
//		System.out.println("Edge <0,1>'s weight: " + amuw.getEdgeWeight(0, 1));
//		System.out.println("Adjacent to vertex 1: " + amuw.getAdjacent(1));
//		
//	}
	
	@Test
	void test2() {
		AdjMatrixUndirWeight amuw = new AdjMatrixUndirWeight();
		System.out.println("\nPrinting new AdjMatrixUndirWeight...");
		amuw.print();
		int n = 9;
		
		System.out.println("\nAdding " + n + " vertices (tot: " + (n + 1) + ")...");
		for(int i = 0; i < n; ++i) {
			amuw.addVertex();
		}
		
		System.out.println("\nAdding edges and respective weights...");
		amuw.addEdge(0, 1, 1);
		amuw.addEdge(0, 5, 1);
		amuw.addEdge(0, 4, 1);
		amuw.addEdge(2, 3, 1);
		amuw.addEdge(2, 6, 1);
		amuw.addEdge(8, 9, 1);
//		amuw.addEdge(3, 0, 1);
		
		System.out.println("\nPrinting current AdjMatrixUndirWeight...");
		amuw.print();
		
		
//		amuw.print("cycle-check");
		System.out.println("\nCycle-check");
		assertFalse(amuw.isCyclic());
		
		System.out.println("\nBFS visit");
		VisitForest visitBFS = amuw.getBFSTree(0);
		for(int i = 0; i < amuw.size(); ++i)
			System.out.println("vertex: "+i+" start: "+visitBFS.getStartTime(i) +" end: "+ visitBFS.getEndTime(i));
		
		System.out.println("\nDFS visit");
		VisitForest visitDFS = amuw.getDFSTree(0);
		for(int i = 0; i < amuw.size(); ++i)
			System.out.println("vertex: "+i+" start: "+visitDFS.getStartTime(i) +" end: "+ visitDFS.getEndTime(i));
		
		System.out.println("\nDFSTOT visit");
		VisitForest visitDFSTOT = amuw.getDFSTOTForest(0);
		for(int i = 0; i < amuw.size(); ++i)
			System.out.println("vertex: "+i+" start: "+visitDFSTOT.getStartTime(i) +" end: "+ visitDFSTOT.getEndTime(i));
		
		System.out.println("\nConnected Components: " + amuw.connectedComponents().toString());		
	}
}
