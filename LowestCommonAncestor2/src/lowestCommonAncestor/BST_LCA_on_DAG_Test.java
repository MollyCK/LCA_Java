package lowestCommonAncestor;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
	
/**
 * This test class is to test if the BinarySearchTree implementation of LCA works on the 
 * DirectedAcyclicGraph class
 */

public class BST_LCA_on_DAG_Test {
	
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
	
	
	/**
	 * Finds the path from root to child
	 * @param child key of destination node
	 * @return path as an ArrayList of Keys with root node at index 0
	 */
	public ArrayList<Integer> getPath(Integer child)
	{
		ArrayList<lowestCommonAncestor.DirectedAcyclicGraph.Node> pathNodes = new ArrayList<lowestCommonAncestor.DirectedAcyclicGraph.Node>();
		pathNodes = getPath(this.root, child, pathNodes);
		ArrayList<Integer> pathKeys = new ArrayList<Integer>();
		for(int i = 0 ; i < pathNodes.size() - 1 ; i++)
		{
			pathKeys.add(i, pathNodes.get(i).key());
		}
		return pathKeys;	
	}
	
	private ArrayList<lowestCommonAncestor.DirectedAcyclicGraph.Node> getPath(lowestCommonAncestor.DirectedAcyclicGraph.Node root, Integer child, ArrayList<lowestCommonAncestor.DirectedAcyclicGraph.Node> path)
	{
		path.add(root);
		if(root.key.equals(child))
		{
			return path;
		}
		if(root.left != null && getPath(root.left, child, path) != null)
		{
			return path;
		}
		if(root.right != null && getPath(root.right, child,path) != null)
		{
			return path;
		}
		path.remove(path.size()-1);
		return null;
	}
	
	/**
	 * Finds and returns the lowest common ancestor of two nodes in a BST given their keys
	 * @param key1 (key of first descendant)
	 * @param key2 (key of second descendant)
	 * @return LCA (key of lowest common ancestor of both descendants)
	 */
	public Integer LCA(Integer key1, Integer key2)
	{
		Integer LCA = null;
		if(!this.contains(key1) & !this.contains(key2))
		{
			return LCA;
		}
		ArrayList<Integer> path1 = getPath(key1);
		ArrayList<Integer> path2 = getPath(key2);
		boolean found = false;
		for(int i = path1.size()-1 ; i > -1 && !found; i--)
		{
			for(int j = path2.size()-1 ; j > -1 && !found; j--)
			{
				if(path1.get(i).equals(path2.get(j)))
				{
					found = true;
					LCA = path1.get(i);
				}
			}
		}
		return LCA;
	}
}
