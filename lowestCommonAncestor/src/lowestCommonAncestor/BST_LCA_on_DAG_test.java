package lowestCommonAncestor;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
	
/**
 * This test class is to test if the BinarySearchTree implementation of LCA works on the 
 * DirectedAcyclicGraph class
 */

public class BST_LCA_on_DAG_test {
	
	DirectedAcyclicGraph<Integer, Character> testDAG;
	
	@Test
	public void test() 
	{
		testDAG = new DirectedAcyclicGraph<Integer, Character>();
		
		testDAG.addNode(1, 'a');
		testDAG.addNode(2, 'b');
		testDAG.addNode(3, 'c');
		testDAG.addNode(4, 'd');
		testDAG.addNode(5, 'e');
		testDAG.addNode(6, 'f');
		
		testDAG.addEdge(1, 2);
		testDAG.addEdge(2, 3);
		testDAG.addEdge(2, 4);
		testDAG.addEdge(4, 5);
		testDAG.addEdge(4, 6);
		testDAG.addEdge(5, 6);	
		testDAG.addEdge(6, 3);
		
		assertEquals(4, );
	}