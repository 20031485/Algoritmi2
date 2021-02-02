package upo.graph.implementation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import upo.additionalstructures.UnionFind;

class KUnionFindTest {

	@Test
	void test() {
		AdjMatrixUndirWeight amuw = new AdjMatrixUndirWeight();
		int n = 5;
		System.out.println("\nAdding " + n + " vertices (tot: " + (n + 1) + ")...");
		for(int i = 0; i < n; ++i) {
			amuw.addVertex();
		}
		
		KUnionFind uf = new KUnionFind();
		assertNotNull(uf);
		
		for(int i = 0; i < amuw.size(); ++i) {
			uf.makeSet((Integer) i);
		}
		
		uf.print();
		System.out.println("\n\n");
		uf.union(0, 2);
		uf.union(1, 2);
		uf.union(3, 2);
		uf.union(3, 1);
		uf.union(3, 5);
		uf.print();
	}

}
