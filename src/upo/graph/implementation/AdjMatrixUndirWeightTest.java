package upo.graph.implementation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import upo.graph.base.VisitForest;

class AdjMatrixUndirWeightTest {

	@Test
	void test1() {
		System.out.println("====================================");
		System.out.println("================TEST 1==============");
		System.out.println("====================================\n\n");
		AdjMatrixUndirWeight amuw = new AdjMatrixUndirWeight();
		System.out.println("Initial graph:");
		amuw.print();
		System.out.println("Matrix now has "+amuw.addVertex()+" vertices;");
		System.out.println("Matrix now has "+amuw.addVertex()+" vertices;");
		System.out.println("Matrix now has "+amuw.addVertex()+" vertices;");
		System.out.println();
		
		System.out.println("Graph after adding edges:");
		amuw.print();
		assertEquals(amuw.addVertex(), 5);
		assertTrue(amuw.containsVertex(4));
		assertFalse(amuw.containsVertex(7));
		
		System.out.println("Graph after adding vertex 5:");
		amuw.print();
		System.out.println();
		System.out.println("Graph after removing vertex 3:");
		amuw.removeVertex(3);
		amuw.print();
		
		System.out.println();
		amuw.addEdge(1, 3);
		amuw.addEdge(3, 1);
		
		System.out.println("Graph after adding edges <1,3> and <3,1>:");
		amuw.print();
		
		assertTrue(amuw.containsEdge(1, 3));
		assertTrue(amuw.containsEdge(3, 1));
		assertFalse(amuw.containsEdge(1, 2));
		assertFalse(amuw.containsEdge(2, 1));
		
		System.out.println();
		System.out.println("Removing edge <1,3>...");
		amuw.removeEdge(1, 3);
		System.out.println("Graph after removing edge <1,3>:");
		amuw.print();
		amuw.setEdgeWeight(1, 3, 666);
		
		System.out.println();
		amuw.addEdge(1, 3);
		amuw.addEdge(0, 1);
		System.out.println("Graph after adding edges <1,3> and <0,1>:");
		amuw.print();
		
		System.out.println();
		amuw.addVertex();
		amuw.print();
		
		System.out.println();
		System.out.println("Edge <0,1>'s weight: " + amuw.getEdgeWeight(0, 1));
		System.out.println("Adjacent vertices to vertex 1: " + amuw.getAdjacent(1));
		
	}
	
	@Test
	void test2() {
		System.out.println("\n\n\n\n====================================");
		System.out.println("================TEST 2==============");
		System.out.println("====================================\n\n");
		AdjMatrixUndirWeight amuw = new AdjMatrixUndirWeight();

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
		
		System.out.println("\nPrinting current AdjMatrixUndirWeight...");
		amuw.print();
		
		System.out.println("\nChecking for cycles...");
		assertFalse(amuw.isCyclic());
				
		System.out.println("\nConnected Components: " + amuw.connectedComponents().toString());		
	}
	
	@Test
	void test3() {
		System.out.println("\n\n\n\n====================================");
		System.out.println("================TEST 3==============");
		System.out.println("====================================\n\n");
		AdjMatrixUndirWeight amuw = new AdjMatrixUndirWeight();
		
		int n = 5;
		
		System.out.println("\nAdding " + n + " vertices (tot: " + (n + 1) + ")...");
		for(int i = 0; i < n; ++i) {
			amuw.addVertex();
		}
		
		System.out.println("Adding edges and weights...");
		amuw.addEdge(0, 1, 3);
		amuw.addEdge(0, 2, 1);
		amuw.addEdge(0, 3, 3);
		amuw.addEdge(1, 2, 2);
		amuw.addEdge(1, 4, 1);
		amuw.addEdge(2, 3, 1);
		amuw.addEdge(2, 4, 2);
		amuw.addEdge(2, 5, 3);
		amuw.addEdge(3, 4, 2);
		amuw.addEdge(4, 5, 2);
		
		System.out.println("Initial graph:");
		amuw.print();
		
		assertTrue(amuw.isConnected());
		
		System.out.println("\nMinimum Spanning Tree obtained with Kruskal's algorithm:");
		((AdjMatrixUndirWeight) amuw.getKruskalMST()).print();
	}
}
