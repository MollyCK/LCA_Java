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
		private ArrayList<Key> adjacents; 		// list of nodes that you can reach from this
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
	
	public Boolean deleteNode(Key key)
	{
		return deleteNode(key, this.nodes);
	}
	
	/**
	 * Deletes a node and all edges into & out of it
	 * @param key
	 * @return true if graph contained the node and it has been removed
	 */
	private Boolean deleteNode(Key key, ArrayList<Node> nodes)
	{
		if(isValidNode(key))
		{
			//need to iterate through all the nodes and remove the soon to 
			//be deleted node's key from any adjacents lists
			for(int i = 0 ; i < this.nodes.size(); i++)
			{
				if(nodes.get(i).adjacents.contains(key))
				{
					nodes.get(i).adjacents.remove(key);
				}
			}
			nodes.remove(get(key));
			return true;
		} else {
			return false;
		}
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
	 * @return true if adding the edge kept the graph acyclic, false if otherwise
	 */
	public Boolean addEdge(Key v, Key w)
	{
		if((isValidNode(v)) && (isValidNode(w)))
		{
			get(v).adjacents.add(w);
			get(v).outdegree++;
			get(w).indegree++; 
			E++;
			if(!this.isAcyclic())
			{
				//undo the addEdge
				get(v).adjacents.remove(w);
				get(v).outdegree--;
				get(w).indegree--; 
				E--;
				return false;
			}
			else
				return true;
		}
		else
		{
			System.out.println("Please enter valid keys");
			return false;
		}		
	}

	private Boolean isAcyclic()
	{
		ArrayList<Node> graphCopy = new ArrayList<Node>(this.nodes.size());
		for(int i = 0 ; i < graphCopy.size() ; i++)
		{
			graphCopy.set(i, this.nodes.get(i));
		}
		return isAcyclic(graphCopy);
	}
	
	private Boolean isAcyclic(ArrayList<Node> nodes)
	{
		for(int i = 0 ; i < nodes.size(); i++)
		{
			if(nodes.size() == 0)
				return true; //If the graph has no nodes, stop. The graph is acyclic.
			else if(getLeaf(nodes) == null)
				return false; //If the graph has no leaf, stop. The graph is cyclic.
			else 
				nodes.remove(getLeaf(nodes)); //Choose a leaf of the graph. Remove this leaf and all edges going into the leaf to get a new graph.
		}
		return false;
	}
	
	/**
	 * Searches a provided graph for a leaf node.
	 * A leaf is a node with no targets (no outgoing edges)
	 * @param nodes
	 * @return a node or null if there is no leaf in the provides graph
	 */
	private Node getLeaf(ArrayList<Node> nodes) 
	{
		for(int i = 0 ; i < nodes.size(); i++) 
		{
			if(nodes.get(i).outdegree == 0) {
				return nodes.get(i);
			}
		}
		return null;
	}
	
 	private Boolean isValidNode(Key key)
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
		if(isValidNode(v))
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
		if(isValidNode(v))
		{
			return get(v).outdegree;
		}
		else
		{
			return -1;
		}
	}

}