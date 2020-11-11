package lowestCommonAncestor;

import static org.junit.Assert.*;
import org.junit.Test;

import org.junit.Test;

public class DirectedAcyclicGraphTest {

	@Test
	public void testAddNode() 
	{
		DirectedAcyclicGraph<Integer, Character> testDAG = new DirectedAcyclicGraph<Integer, Character>();
	}
	
	@Test
	public void testForDAG()
	{
		DirectedAcyclicGraph<Integer, Character> testDAG = new DirectedAcyclicGraph<Integer, Character>();

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

		assertEquals(1, testDAG.indegree(4));
		assertEquals(2, testDAG.outdegree(4));
		assertEquals(5, testDAG.E());
		assertEquals(7, testDAG.N());
	}

	@Test
	public void addEdgeTest() {
		DirectedAcyclicGraph<Integer, Character> edgeTest = new DirectedAcyclicGraph<Integer, Character>();
		edgeTest.addNode(0, 'A');
		edgeTest.addNode(1, 'B');
		edgeTest.addNode(-2, 'Y');
		edgeTest.addNode(-5, 'V');
		
		edgeTest.addEdge(0, 1);
		edgeTest.addEdge(-2, -5);

		assertEquals("Testing edge count is 2", 2, edgeTest.E());

		edgeTest.addEdge(1, 2); //there is on node with key 2

		assertEquals("Testing edge count is 2", 2, edgeTest.E());

	}

	@Test
	public void inDegreeTest(){
		DirectedAcyclicGraph<Integer, Character> testDAG = new DirectedAcyclicGraph<Integer, Character>();
		assertEquals("", -1, testDAG.indegree(-3));
	}

	@Test
	public void outDegreeTest(){
		DirectedAcyclicGraph<Integer, Character> testDAG = new DirectedAcyclicGraph<Integer, Character>();
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
		
		assertEquals("", -1, testDAG.outdegree(8));	
		assertEquals("", 2, testDAG.outdegree(4));
	}
}