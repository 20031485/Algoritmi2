package upo.progdin;

import java.util.Arrays;

public class DynamicProgramming {
	
	/** Calcola la LCS tra <code>s1</code> e <code>s2</code> utilizzando l'algoritmo visto a lezione.
	 * </br>CONSIGLIO: potete usare i metodi di String per accedere alle posizioni di s1 ed s2.
	 * </br>CONSIGLIO2: potete costruire l'output come un array di caratteri, e poi trasformarlo in stringa,
	 * oppure usare le concatenazioni di stringhe nelle chiamate ricorsive (vedi slide).
	 * 
	 * @param s1 una sequenza di caratteri
	 * @param s2 una sequenza di caratteri
	 * @return una LCS di <code>s1</code> e <code>s2</code>
	 */
	public static String LongestCommonSubsequence(String s1, String s2) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/** Risolve il problema dello zaino 0-1 con l'algoritmo di programmazione dinamica visto a lezione.
	 * 
	 * @param weights un vettore contenente in posizione i-esima, per ogni oggetto oi, il suo peso. 
	 * @param values un vettore contenente in posizione i-esima, per ogni oggetto oi, il suo valore. 
	 * @param maxWeight la capienza dello zaino.
	 * @return un vettore di boolean che contiene, in posizione i-esima, true se l'oggetto i-esimo �
	 * incluso nella soluzione, false altrimenti.
	 */
	public static boolean[] getKnapsack01(int[] weights, int[] values, int maxWeight) {
		int n = weights.length;
		
		boolean[] solution = new boolean[n + 1];
		Arrays.fill(solution, false);
		
		int[][] V = new int[n + 1][maxWeight + 1];
		int[][] K = new int[n + 1][maxWeight + 1];
		
		//initialize matrices
		for(int i = 0; i <= n; i++) {
			V[i][0] = 0;
			K[i][0] = 0;
		}
		
		for(int j = 0; j <= maxWeight; j++) {
			V[0][j] = 0;
			K[0][j] = 0;
		}
		
		//compute solution
		for(int i = 1; i <= n; i++) {
			for(int j = 1; j <= maxWeight; j++) {
				if(j < weights[i - 1]) {
					V[i][j] = V[i - 1][j];
					K[i][j] = 0;
				}
				//else put max of the following expression into V[i][j]
				else if(V[i - 1][j] >= V[i - 1][j - weights[i - 1]] + values[i - 1]) { 
					V[i][j] = V[i - 1][j];
				}
				else{
					V[i][j] = V[i - 1][j - weights[i - 1]] + values[i - 1];
					K[i][j] = 1;
				}
			}
		}
		
		//copy solution onto array
		int d = maxWeight;
		for(int i = n; i > 0; i--) {
			if(K[i][d] == 1) {
				solution[i] = true;
				d -= weights[i - 1];
			}
		}
		
		return solution;
	}
	
}
