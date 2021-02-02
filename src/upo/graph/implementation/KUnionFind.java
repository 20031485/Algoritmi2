package upo.graph.implementation;

import java.util.HashMap;
import upo.additionalstructures.UnionFind;


public class KUnionFind implements UnionFind<Integer>{
	private HashMap<Integer, Integer> parents;
	
	KUnionFind(){
		parents = new HashMap<Integer, Integer>();
	}
	
	@Override
	public void makeSet(Integer element) {
		parents.put(element, null);
	}

	@Override
	public void union(Integer el1, Integer el2) {
		if(find(el1) != find(el2))
			parents.put(el2, el1);		
	}

	@Override
	public Integer find(Integer el) {
		if(parents.get(el) != null)
			return find(parents.get(el));
		return el;
	}

	public Integer getParent(Integer el) {
		return parents.get(el);
	}
	
	public void print() {
		System.out.println();
		for(int i = 0; i < parents.size(); ++i) {
			System.out.println("i = " + i +", parent: " + parents.get(i));
		}
	}
}