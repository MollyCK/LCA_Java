/**
 * Directed Acyclic Graph implementation in Java
 * @author MollyCK
 * 2020/11/11
 */

/*
 * TODO
 * Commit to DAG branch with this code in LowestCommonAncestor folder
 * Assert that the graph is acyclic
 * Write comprehensive tests for DirectedAcyclicGraph class
 * Write tests to test the BST implementation of LCA on DAG
 * Write LCA implementation for DAG
 */

package lowestCommonAncestor;

import java.util.ArrayList;

public class DirectedAcyclicGraph<Key extends Comparable<Key>, Value> {

	/**
	 * Private node class.
	 */
	private class Node {
		private Key key;         				// unique identifying attribute
		private Value val;						// associated data
		private ArrayList<Key> adjacents; 		// list of nodes that share an edge with this
		private int indegree;					// the number of directed edges entering this
		private int outdegree;					// the number of directed edges leaving this

		public Node(Key key, Value val) {
			this.val = val;
			this.key = key;
			this.adjacents = new ArrayList<Key>();
		}
	}
	
	private ArrayList<Node> nodes; 	// List of all nodes in the graph
	private int E;					// Amount of edges in whole graph

	public DirectedAcyclicGraph() {
		nodes = new ArrayList<Node>();
	}
	
	public void addNode(Key key, Value val) {
		this.nodes.add(new Node(key, val));
	}
	
	public Node get(Key key) {
		for(int i = 0 ; i < this.nodes.size() ; i++) {
			if(key.compareTo(nodes.get(i).key) == 0) {
				return nodes.get(i);
			}
		}
		return null;
	}
	
	//Returns amount of nodes in whole graph
	public int N()
	{
		return nodes.size();
	}
	
	//Returns amount of edges in whole graph
	public int E()
	{
		return E;
	}
	
	/**
	 * Adds a directed edge from v to w
	 * @param v (key of source node)
	 * @param w (key of destination node)
	 * 
	 */
	public void addEdge(Key v, Key w)
	{
		if((isvalidNode(v)) && (isvalidNode(w)))
		{
			get(v).adjacents.add(w);
			get(v).outdegree++;
			get(w).indegree++; 
			E++;
		}
		else
		{
			System.out.println("Please enter valid keys");
		}		
	}
	
	private Boolean isvalidNode(Key key)
	{
		for(int i = 0 ; i < nodes.size(); i++) {
			if(key.compareTo(nodes.get(i).key) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public int indegree(Key v)
	{
		if(isvalidNode(v))
		{
			return get(v).indegree;
		}
		else
		{
			return -1;
		}
		
	}
	
	public int outdegree(Key v)
	{
		if(isvalidNode(v))
		{
			return get(v).outdegree;
		}
		else
		{
			return -1;
		}
	}

}