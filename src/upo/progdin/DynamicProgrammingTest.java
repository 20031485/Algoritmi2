package upo.progdin;

import org.junit.jupiter.api.Test;

class DynamicProgrammingTest {

	@Test
	void test() {
		int maxWeight = 10;
		
		int[] weights = new int[4];
		int[] values = new int[4];
		
		weights[0] = 2;
		values[0] = 12;
		
		weights[1] = 7;
		values[1] = 6;
		
		weights[2] = 6;
		values[2] = 2;
		
		weights[3] = 4;
		values[3] = 1;
		
		boolean[] solution = DynamicProgramming.getKnapsack01(weights, values, maxWeight);
		
		System.out.println("Solutions:\n{");
		for(int i = 1; i < solution.length; ++i)
			System.out.println("\tx_" + i + ": " + solution[i]);
		System.out.println("}");
	}

}
