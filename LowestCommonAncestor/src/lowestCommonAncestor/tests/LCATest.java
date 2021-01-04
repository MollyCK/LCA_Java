package lowestCommonAncestor.tests;

import lowestCommonAncestor.DirectedAcyclicGraph;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class LCATest {
	final int src1;
	final int src2;
	final Integer expected;
	DirectedAcyclicGraph<Integer, Character> testDAG;


	public LCATest(final int src1,final int src2, final Integer expected)
	{
		super();
		this.src1 = src1;
		this.src2 = src2;
		this.expected = expected;
	}

	@Before
	public void init()
	{
		testDAG = new DirectedAcyclicGraph<Integer, Character>();

		this.testDAG.addNode(1, 'a');
		this.testDAG.addNode(2, 'b');
		this.testDAG.addNode(3, 'c');
		this.testDAG.addNode(4, 'd');
		this.testDAG.addNode(5, 'e');
		this.testDAG.addNode(6, 'f');
		this.testDAG.addNode(7, 'g');
		this.testDAG.addNode(8, 'h');
		this.testDAG.addNode(9, 'i');
		this.testDAG.addNode(10, 'j');
		this.testDAG.addNode(11, 'k');
		this.testDAG.addNode(12, 'l');
		this.testDAG.addNode(13, 'm');
		this.testDAG.addNode(14, 'n');
		this.testDAG.addNode(15, 'o');

		this.testDAG.addEdge(1, 2);
		this.testDAG.addEdge(2, 4);
		this.testDAG.addEdge(4, 6);
		this.testDAG.addEdge(1, 3);
		this.testDAG.addEdge(3, 5);
		this.testDAG.addEdge(5, 8);
		this.testDAG.addEdge(5, 7);
		this.testDAG.addEdge(7, 10);
		this.testDAG.addEdge(10, 9);
		this.testDAG.addEdge(10, 13);
		this.testDAG.addEdge(10, 11);
		this.testDAG.addEdge(11, 12);
		this.testDAG.addEdge(6, 13);
		this.testDAG.addEdge(6, 7);
		this.testDAG.addEdge(15, 6);
		this.testDAG.addEdge(15, 4);
	}

	@Parameterized.Parameters
	public static Collection<Object[]> inputData()
	{
		//src1, src2, expected
		return Arrays.asList(new Object[][] {
			//test with 2 invalid source nodes
			{20, -4, null},
			//test with invalid source node 1
			{20, 6, null},
			//test with invalid source node 2
			{8, -4, null},
			{8, 9, 5},
			{12, 6, 6},
			{3, 1, 1},
			{14, 1, null},
			{13, 12, 10},
			{7, 13, 7}
		});
	}

	@Test
	public void testLCA()
	{
		assertEquals(expected, this.testDAG.LCA(src1, src2));
	}
}