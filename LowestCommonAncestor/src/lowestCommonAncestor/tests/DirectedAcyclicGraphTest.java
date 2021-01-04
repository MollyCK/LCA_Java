package lowestCommonAncestor.tests;

import lowestCommonAncestor.DirectedAcyclicGraph;
import static org.junit.Assert.*;
import org.junit.Test;

public class DirectedAcyclicGraphTest {
	DirectedAcyclicGraph<Integer, Character> testDAG;
	
	@Test
	public void testAddNode() 
	{
		testDAG = new DirectedAcyclicGraph<>();
		
		//test empty graph
		testDAG.addNode(1, 'a');
		assertTrue(testDAG.isValidNode(1));
		
		//test non-empty graph
		testDAG.addNode(4, 'd');
		assertTrue(testDAG.isValidNode(4));
		
		//test null key
		testDAG.addNode(null, 'd');
		assertFalse(testDAG.isValidNode(null));
		
		//test null val
		testDAG.addNode(9, null);
		assertFalse(testDAG.isValidNode(9));
		
		//test adding new Node with same key as a pre-existing Node
		testDAG.addNode(4, 'e');
		assertFalse(testDAG.isValidNode(4, 'e'));
	}

	@Test 
	public void testGet() 
	{
		testDAG = new DirectedAcyclicGraph<>();
		
		//test empty graph
		assertNull(testDAG.get(9));
		
		//test non-empty graph with valid Node
		testDAG.addNode(1, 'a');
		
		Integer expectedKey = 1;
		Character expectedVal = 'a';
		
		assertEquals(expectedKey, testDAG.get(1).key());
		assertEquals(expectedVal, testDAG.get(1).val());
		
		//test non-empty graph with invalid Node
		assertNull(testDAG.get(4));
		
		//test non-empty graph with null key
		assertNull(testDAG.get(null));
	}
	
	@Test
	public void testDeleteNode() {
		testDAG = new DirectedAcyclicGraph<>();
		
		//test empty graph
		assertFalse(testDAG.deleteNode(3));
		
		
		//test non-empty graph with null
		testDAG.addNode(1, 'a');
		assertFalse(testDAG.deleteNode(null));
		
		//test non-empty graph with valid Node
		assertTrue(testDAG.deleteNode(1));
		
		
		//test non-empty graph with invalid Node
		assertFalse(testDAG.deleteNode(3));
	}
	
	@Test
	public void testAddEdge() 
	{
		DirectedAcyclicGraph<Integer, Character> edgeTest = new DirectedAcyclicGraph<>();
		
		//Test invalid source & invalid destination
		assertFalse(edgeTest.addEdge(0, 1));
		
		//Test valid inputs
		edgeTest.addNode(0, 'A');
		edgeTest.addNode(1, 'B');
		edgeTest.addNode(-2, 'Y');
		edgeTest.addNode(-5, 'V');
		
		edgeTest.addEdge(0, 1);
		edgeTest.addEdge(-2, -5);

		assertEquals("Testing edge count is 2", 2, edgeTest.E());

		edgeTest.addEdge(1, 2); //there is no node with key 2

		assertEquals("Testing edge count is 2", 2, edgeTest.E());
	}

	@Test
	public void testIsAcyclic() 
	{
		testDAG = new DirectedAcyclicGraph<>();
		
		//test empty graph
		assertTrue(testDAG.isAcyclic());
		
		//test acyclic graph
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
		
		assertTrue(testDAG.isAcyclic());
		
		//test try to make cyclic graph
		assertFalse(testDAG.addEdge(6, 4)); //trying to add this edge will fail because it would make the graph cyclic
		assertTrue(testDAG.isAcyclic()); //make sure that there hasn't been an error somewhere and that the graph is still acyclic and unchanged
	}
	
	@Test
	public void testIsValidNodes() 
	{
		testDAG = new DirectedAcyclicGraph<>();
		
		//test empty graph
		assertFalse(testDAG.isValidNode(4));
		assertFalse(testDAG.isValidNode(4, 'D'));
		
		//Non-empty graph tests...
		testDAG.addNode(0, 'A');
		testDAG.addNode(1, 'B');
		testDAG.addNode(-2, 'Y');
		testDAG.addNode(-5, 'V');
		
		//test with invalid key
		assertFalse(testDAG.isValidNode(4));
		
		//test with null values
		assertFalse(testDAG.isValidNode(null));
		assertFalse(testDAG.isValidNode(null, null));
		
		//test with valid key and incorrect val
		assertFalse(testDAG.isValidNode(1, 'A'));
		
		//test with valid key and correct val
		assertTrue(testDAG.isValidNode(1, 'B'));
		
		//test with null key and valid val
		assertFalse(testDAG.isValidNode(null, 'A'));
		
		//test with valid key and null val
		assertFalse(testDAG.isValidNode(1, null));
	}

	@Test
	public void inDegreeTest(){
		DirectedAcyclicGraph<Integer, Character> testDAG = new DirectedAcyclicGraph<>();

		//test empty graph
		assertEquals(-1, testDAG.indegree(-3));

		//Non-empty graph tests...
		testDAG.addNode(0, 'A');
		testDAG.addNode(1, 'B');
		testDAG.addNode(-2, 'Y');
		testDAG.addNode(-5, 'V');

		testDAG.addEdge(0, 1);
		testDAG.addEdge(-2, -5);

		//test valid key
		assertEquals(1, testDAG.indegree(1));

		//test invalid key
		assertEquals(-1, testDAG.indegree(2));

	}

	@Test
	public void outDegreeTest(){
		DirectedAcyclicGraph<Integer, Character> testDAG = new DirectedAcyclicGraph<>();
		testDAG.addNode(1, 'A');
		testDAG.addNode(2, 'B');
		testDAG.addNode(3, 'C');
		testDAG.addNode(4, 'D');
		testDAG.addNode(5, 'E');
		testDAG.addNode(6, 'F');
		testDAG.addNode(7, 'G');

		testDAG.addEdge(1, 2);
		testDAG.addEdge(2, 4);
		testDAG.addEdge(2, 5);
		testDAG.addEdge(4, 6);
		testDAG.addEdge(4, 7);

		assertEquals(-1, testDAG.outdegree(8));	
		assertEquals(2, testDAG.outdegree(4));
	}

	@Test
	public void topologicalSortTest()
	{
		testDAG = new DirectedAcyclicGraph<>();

		testDAG.addNode(0, 'a');
		testDAG.addNode(1, 'b');
		testDAG.addNode(2, 'c');
		testDAG.addNode(3, 'd');
		testDAG.addNode(4, 'e');
		testDAG.addNode(5, 'f');

		testDAG.addEdge(5, 2);
		testDAG.addEdge(5, 0);
		testDAG.addEdge(4, 0);
		testDAG.addEdge(4, 1);
		testDAG.addEdge(2, 3);
		testDAG.addEdge(3, 1);	

		String expectedResult = "5 4 2 3 1 0 ";
		assertEquals(expectedResult, testDAG.topologicalSort());
	}

	@Test
	public void testN() 
	{
		testDAG = new DirectedAcyclicGraph<>();

		testDAG.addNode(0, 'a');
		testDAG.addNode(1, 'b');
		testDAG.addNode(2, 'c');
		testDAG.addNode(3, 'd');
		testDAG.addNode(4, 'e');
		testDAG.addNode(5, 'f');

		testDAG.addEdge(5, 2);
		testDAG.addEdge(5, 0);
		testDAG.addEdge(4, 0);
		testDAG.addEdge(4, 1);
		testDAG.addEdge(2, 3);
		testDAG.addEdge(3, 1);

		assertEquals(testDAG.N(), 6);
	}

	/**
	 * The tests for LCA are in a separate file for ease because they are parameterised tests
	 */

	
}