/**
 * Directed Acyclic Graph implementation in Java
 * @author MollyCK
 * 2020/11/11
 * 
 * LCA & getLongestPath functions inspired by https://web.archive.org/web/20190914030055/http://www.gghh.name:80/dibtp/2014/02/25/how-does-mercurial-select-the-greatest-common-ancestor.html
 */

/*
 * TODO
 * Write tests to test the BST implementation of LCA on DAG
 * Write LCA implementation for DAG (https://web.archive.org/web/20190914030055/http://www.gghh.name:80/dibtp/2014/02/25/how-does-mercurial-select-the-greatest-common-ancestor.html)
 */

package lowestCommonAncestor;

import java.util.ArrayList;

public class DirectedAcyclicGraph<Key extends Comparable<Key>, Value> {

	/**
	 * Private node class.
	 */
	class Node {
		private Key key;         				// unique identifying attribute
		private Value val;						// associated data
		private ArrayList<Key> destinations; 	// list of nodes that you can reach from this
		private int indegree;					// the number of directed edges entering this
		private int outdegree;					// the number of directed edges leaving this

		public Node(Key key, Value val) {
			this.val = val;
			this.key = key;
			this.destinations = new ArrayList<Key>();
		}
		
		public Key key()
		{
			return key;
		}
		
		public Value val() 
		{
			return val;
		}
		
		public String toString()
		{
			return "(" + this.key + "," + val + ")";
		}
	}
	
	private ArrayList<Node> nodes; 	// List of all nodes in the graph
	private int E;					// Amount of edges in whole graph

	public DirectedAcyclicGraph() {
		nodes = new ArrayList<Node>();
		E = 0;
	}
	
	public void addNode(Key key, Value val) {
		if(key != null && val != null) {
			//check for nodes with the same key
			for(int i = 0 ; i < nodes.size(); i++)
			{
				if(this.nodes.get(i).key() == key) {
					return;
				}
				
			}
			this.nodes.add(new Node(key, val));
		}

	}
	
	public Node get(Key key) {
		return get(key, this.nodes);
	}
	
	private Node get(Key key, ArrayList<Node> graph)
	{
		if(key != null)
		{
			for(int i = 0 ; i < graph.size() ; i++) {
				if(key.compareTo(graph.get(i).key) == 0) {
					return graph.get(i);
				}
			}
		}
		return null;
	}
	
	public Boolean deleteNode(Key key)
	{
		Node toBeDel = get(key);
		Boolean returnValue = deleteNode(key, this.nodes);
		if(returnValue)
			this.E -= (toBeDel.destinations.size());
		return returnValue;
	}
	
	/**
	 * Deletes a node and all edges into & out of it
	 * @param key
	 * @return true if graph contained the node and it has been removed
	 */
	private Boolean deleteNode(Key key, ArrayList<Node> graph)
	{
		if(isValidNode(key))
		{
			//need to iterate through all the nodes and remove the soon to 
			//be deleted node's key from any destinations lists, reduce E, and 
			//the source node's outdegree
			for(int i = 0 ; i < graph.size(); i++)
			{
				if(graph.get(i).destinations.contains(key))
				{
					graph.get(i).destinations.remove(key);
					graph.get(i).outdegree--;
					get(key, graph).indegree--;
				}
			}
			graph.remove(get(key, graph));
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
			get(v).destinations.add(w);
			get(v).outdegree++;
			get(w).indegree++; 
			E++;
			if(!this.isAcyclic())
			{
				//undo the addEdge
				get(v).destinations.remove(w);
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

	public Boolean isAcyclic()
	{
		ArrayList<Node> graphCopy = new ArrayList<Node>(this.nodes.size());
		for(int i = 0 ; i < this.nodes.size() ; i++)
		{
			Node copy = new Node(this.nodes.get(i).key(), this.nodes.get(i).val());
			//copy original Node's destinations ArrayList to the copy Node
			for(int j = 0 ; j < this.nodes.get(i).destinations.size(); j++)
			{
				copy.destinations.add(this.nodes.get(i).destinations.get(j));
			}
			copy.indegree = this.nodes.get(i).indegree;
			copy.outdegree = this.nodes.get(i).outdegree;
			graphCopy.add(copy);
		}
		return isAcyclic(graphCopy);
	}
	
	
	/**
	 * Original pseudo-code algorithm from https://www.cs.hmc.edu/~keller/courses/cs60/s98/examples/acyclic/
	 * @param graph
	 * @return
	 */
	private Boolean isAcyclic(ArrayList<Node> graph)
	{
		int size = graph.size();
		for(int i = 0 ; i <= size; i++)
		{
			if(graph.size() == 0)
				return true; //If the graph has no nodes, stop. The graph is acyclic.
			else if(getLeaf(graph) == null)
				return false; //If the graph has no leaf, stop. The graph is cyclic.
			else 
				deleteNode(getLeaf(graph).key(), graph); //Choose a leaf of the graph. Remove this leaf and all edges going into the leaf to get a new graph.
		}
		return false;
	}
	
	/**
	 * Searches a provided graph for a leaf node.
	 * A leaf is a node with no targets (no outgoing edges)
	 * @param nodes
	 * @return a node or null if there is no leaf in the provides graph
	 */
	private Node getLeaf(ArrayList<Node> graph) 
	{
		for(int i = 0 ; i < graph.size(); i++) 
		{
			if(graph.get(i).outdegree == 0) {
				return graph.get(i);
			}
		}
		return null;
	}
	
 	public Boolean isValidNode(Key key)
 	{
 		if(key != null)
 		{
 			for(int i = 0 ; i < nodes.size(); i++) {
 				if(key.compareTo(nodes.get(i).key) == 0) {
 					return true;
 				}
 			}
 		}
 		return false;
	}
 	
 	public Boolean isValidNode(Key key, Value val)
 	{
 		if(key != null && val != null)
 		{
 			for(int i = 0 ; i < nodes.size(); i++) {
 				if(key.compareTo(nodes.get(i).key) == 0 && val == nodes.get(i).val()) {
 					return true;
 				}
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

	public ArrayList<Key> getLongestPath(Key root, Key node) 
	{
		ArrayList<Key> paths = new ArrayList<Key>();
		//do something here
		return paths;
		
	}
	
	/**
	 * The lowest common ancestor of 2 nodes is that node which is furthest from a root that the source nodes' longest paths from that root have in common.
	 * @param node1
	 * @param node2
	 * @return
	 * Limitations: the function will return the first possible LCA that it comes across in the first source node's path
	 * 				E.g. If srcNode1's path has a Key 'g' 5 steps from the root and 'g' is also present in the srcNodes2's path, then it will return 'g' as the LCA regardless of how far it is from the root on srcNode2's path
	 */
	public Key LCA(Key root, Key srcNode1, Key srcNode2)
	{
		//test if the provided root is actually a root (should only have outcoming edges with no incoming ones) and if provided source nodes are valid
		if(get(root).indegree == 0 && get(root).outdegree != 0 && isValidNode(srcNode1) && isValidNode(srcNode2))
		{
			ArrayList<Key> path1 = getLongestPath(root, srcNode1);
			ArrayList<Key> path2 = getLongestPath(root, srcNode2);
			//iterate through the paths from back to front to find the common node (if it exists) that is furthest from the root.
			Key LCA = null;
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
		else return null;
	}
	
}