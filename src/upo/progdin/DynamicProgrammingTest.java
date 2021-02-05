package upo.progdin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DynamicProgrammingTest {

	@Test
	void test1() {
		//testing the slides' exercise
		int capacity = 10;
		int[] weights = {2, 7, 6, 4};
		int[] values = {12, 6, 1, 0};
		
		boolean[] solution = DynamicProgramming.getKnapsack01(weights, values, capacity);
		
		assertTrue(solution[1]);
		assertTrue(solution[2]);
		assertFalse(solution[4]);
		assertFalse(solution[3]);
	}
	
	@Test
	void test2() {
		//testing youtube exercise (https://www.youtube.com/watch?v=EH6h7WA7sDw)
		int capacity = 5;
		int[] weights = {3, 2, 1};
		int[] values = {5, 3, 4};
		
		boolean[] solution = DynamicProgramming.getKnapsack01(weights, values, capacity);
		
		assertTrue(solution[1]);
		assertFalse(solution[2]);
		assertTrue(solution[3]);
	}
}
