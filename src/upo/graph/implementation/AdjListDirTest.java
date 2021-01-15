package upo.graph.implementation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import upo.graph.base.VisitForest;
import upo.graph.implementation.AdjListDir.Vertex;

class AdjListDirTest {

	@Test
	void test1() {
		System.out.println("\t===TEST 1===\n");
		AdjListDir adjListDir = new AdjListDir();
		assertNotNull(adjListDir);
		for(int i = 0; i < 3; ++i) {
			adjListDir.addVertex();
		}
		
		adjListDir.print();
		assertTrue(adjListDir.containsVertex(2));
		assertFalse(adjListDir.containsVertex(3));
		
		System.out.println("Printing vertex...");
		adjListDir.printVertex(1);
		adjListDir.removeVertex(0);
		adjListDir.print();
		
		System.out.println("Adding edge...");
		adjListDir.addEdge(0, 1);
		assertFalse(adjListDir.isAdjacent(0, 1));
//		assertTrue(adjListDir.isAdjacent(1, 0));
		adjListDir.print();
		
		System.out.println("Removing edge...");
		adjListDir.removeEdge(0, 1);
		adjListDir.print();
		
		System.out.println("Adjacent to 0: " + adjListDir.getAdjacent(0));
		
	}
	
	@Test
	void test2() {
		System.out.println("\n\n\t===TEST 2===\n");
		AdjListDir ald = new AdjListDir();
		assertNotNull(ald);
		ald.addVertex();
		ald.addVertex();
		ald.addVertex();
		ald.addVertex();
		assertEquals(ald.addVertex(), 4);
		
		
		ald.print();
		assertTrue(ald.containsVertex(2));
		assertFalse(ald.containsVertex(90));
		
//		System.out.println("Printing vertex 1...");
//		adjListDir.printVertex(1);
		System.out.println("Adding edges...");
		ald.addEdge(0, 1);
		ald.addEdge(0, 2);
		ald.addEdge(0, 4);
		ald.addEdge(0, 3);
		ald.addEdge(3, 1);
		ald.addEdge(4, 1);
		ald.addEdge(4, 2);
		ald.addEdge(2, 1);
		ald.addEdge(1, 3);
		ald.print();
		
		System.out.println(">Adjacent to 0: " + ald.getAdjacent(0));
		System.out.println(">Adjacent to 1: " + ald.getAdjacent(1));
		System.out.println(">Adjacent to 2: " + ald.getAdjacent(2));
		System.out.println(">Adjacent to 3: " + ald.getAdjacent(3));
		System.out.println(">Adjacent to 4: " + ald.getAdjacent(4));
		
		int[] order = new int[ald.size()];
		
		System.out.println("Searches...");
		VisitForest bfsTree = ald.getBFSTree(0);
		VisitForest dfsTree = ald.getDFSTree(0);
		VisitForest dfsTotForest = ald.getDFSTOTForest(0);
		VisitForest dfsTotForest2 = ald.getDFSTOTForest(order);
		
		System.out.println("BFS...");
		assertNotNull(bfsTree);
		ald.printVisit(bfsTree);
		
		System.out.println("DFS...");
		assertNotNull(dfsTree);
		ald.printVisit(dfsTree);
		
		System.out.println("DFSTOTForest...");
		assertNotNull(dfsTotForest);
		ald.printVisit(dfsTotForest);
		
		System.out.println("DFSTOTForest2...");
		assertNotNull(dfsTotForest2);
		ald.printVisit(dfsTotForest2);
		
		System.out.println();
		
//		assertFalse(ald.isCyclic());
		
		if(ald.isDAG()) {
			System.out.println("Topological sort...");
			int[] tSort = ald.topologicalSort();
			for(int i = 0; i < ald.size(); ++i) {
				System.out.print("["+tSort[i]+"]");
			}
			System.out.println();
		}
			
		System.out.println("\nRemoving edges...");
		ald.removeEdge(0,1);
		ald.removeEdge(0,2);
		ald.removeEdge(0,3);
		ald.print();
		
		System.out.println("\nRemoving vertex 1...");
		ald.removeVertex(1);
		ald.print();
		
		System.out.println("\nRemoving vertex 2...");
		ald.removeVertex(2);
		ald.print();
		
		System.out.println("\n>Adjacent to 0: " + ald.getAdjacent(0));
		System.out.println(">Adjacent to 1: " + ald.getAdjacent(1));
		System.out.println(">Adjacent to 2: " + ald.getAdjacent(2));
		assertFalse(ald.containsEdge(0, 1));
		assertTrue(ald.containsEdge(0, 2));
		
		System.out.println("Removing edge <0,2>...");
		ald.removeEdge(0, 2);
		ald.print();
		
		
//		assertFalse(ald.containsEdge(0, 1));
//		assertFalse(ald.containsEdge(0, 2));
//		assertTrue(adjListDir.isCyclic());

		
	}
	
	@Test
	void test3() {
		System.out.println("\n\n\t===TEST 3===\n");
		AdjListDir ald = new AdjListDir();
		assertNotNull(ald);
		
		ald.addVertex();
		ald.addVertex();
		ald.addVertex();
		ald.addVertex();
		ald.addVertex();
		
		ald.addEdge(0, 1);
		ald.addEdge(1, 2);
		ald.addEdge(1, 3);
		ald.addEdge(2, 4);
		ald.addEdge(3, 4);

		System.out.println("Printing AdjListDir:");
		ald.print();
		
		assertTrue(ald.isDAG());
		
		System.out.println("\nPerforming TopologicalSort:");
		int[] tSort = ald.topologicalSort();
		for(int i = 0; i < tSort.length; i++)
			System.out.print("["+tSort[i]+"]");
			
	}
	
	@Test
	void test4() {
		System.out.println("\n\n\t===TEST 4===\n");
		AdjListDir ald = new AdjListDir();
		assertNotNull(ald);
		
		for(int i = 0; i < 6; ++i) {
			ald.addVertex();
		}
		
		ald.addEdge(0, 3);
		ald.addEdge(3, 1);
		ald.addEdge(3, 4);
		ald.addEdge(1, 2);
		ald.addEdge(2, 3);
		ald.addEdge(4, 5);
		ald.addEdge(5, 4);

		
		ald.print();
		
		System.out.println("Strongly Connected Components: " + ald.stronglyConnectedComponents().toString());
	}

}
