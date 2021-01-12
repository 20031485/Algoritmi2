package upo.graph.implementation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AdjMatrixUndirWeightTest {

	@Test
	void test1() {
		AdjMatrixUndirWeight amuw = new AdjMatrixUndirWeight();
		amuw.print();
		System.out.println("Matrix now has "+amuw.addVertex()+" vertices;");
		System.out.println("Matrix now has "+amuw.addVertex()+" vertices;");
		System.out.println("Matrix now has "+amuw.addVertex()+" vertices;");
		System.out.println();
		
		amuw.print();
		assertEquals(amuw.addVertex(), 5);
		assertTrue(amuw.containsVertex(4));
		assertFalse(amuw.containsVertex(7));
		
		amuw.print();
		System.out.println();
		amuw.removeVertex(3);
		amuw.print();
		
		System.out.println();
		amuw.addEdge(1, 3);
		amuw.addEdge(3, 1);
		amuw.print();
		
		assertTrue(amuw.containsEdge(1, 3));
		assertTrue(amuw.containsEdge(3, 1));
		assertFalse(amuw.containsEdge(1, 2));
		assertFalse(amuw.containsEdge(2, 1));
		
		System.out.println();
		System.out.println("Removing edge <1,3>:");
		amuw.removeEdge(1, 3);
		amuw.print();
		amuw.setEdgeWeight(1, 3, 666);
		
		System.out.println();
		amuw.addEdge(1, 3);
		amuw.addEdge(0, 1);
		amuw.print();
		
		System.out.println();
		amuw.addVertex();
		amuw.print();
		
		System.out.println();
		System.out.println("Edge <0,1>'s weight: " + amuw.getEdgeWeight(0, 1));
		System.out.println("Adjacent to vertex 1: " + amuw.getAdjacent(1));
		
	}
	@Test
	void test2() {
		AdjMatrixUndirWeight amuw = new AdjMatrixUndirWeight();
		amuw.print();
		System.out.println("Matrix now has "+amuw.addVertex()+" vertices;");
		System.out.println("Matrix now has "+amuw.addVertex()+" vertices;");
		System.out.println("Matrix now has "+amuw.addVertex()+" vertices;");
		System.out.println();
	}
}
