/**
 * Directed Acyclic Graph implementation in Java
 * @author MollyCK
 * 2020/11/11
 */

package lowestCommonAncestor;

import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;

public class DirectedAcyclicGraph<Key extends Comparable<Key>, Value> {

	/**
	 * Internal node class.
	 */
	class Node {
		private final Key key;         				// unique identifying attribute
		private final Value val;					// associated data
		private final ArrayList<Key> destinations; 	// list of nodes that you can reach from this
		private final ArrayList<Key> ancestors;		// list of nodes that can reach this node 	
		private int indegree;						// the number of directed edges entering this
		private int outdegree;						// the number of directed edges leaving this
		private boolean visited;					// variable used for topological sort
		
		public Node(Key key, Value val) {
			this.val = val;
			this.key = key;
			this.destinations = new ArrayList<Key>();
			this.ancestors = new ArrayList<Key>();
			this.visited = false;
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
	
	private final List<Node> nodes; 	// List of all nodes in the graph
	private int E;							// Amount of edges in whole graph

	public DirectedAcyclicGraph() {
		nodes = new ArrayList<Node>();
		E = 0;
	}
	
	public void addNode(Key key, Value val) 
	{
		if(key != null && val != null) {
			//check for nodes with the same key
			for(final Node node : nodes)
			{
				if(node.key() == key) 
					return;
			}
			this.nodes.add(new Node(key, val));
		}
	}
	
	public Node get(final Key key) 
	{
		return get(key, this.nodes);
	}
	
	private Node get(final Key key, final List<Node> graph)
	{
		if(key != null)
		{
			for(final Node node : graph) {
				if(key.compareTo(node.key) == 0) {
					return node;
				}
			}
		}
		return null;
	}
	
	private void resetVisited()
	{
		for(final Node node : this.nodes)
		{
			node.visited = false;
		}
	}
	
 	public boolean deleteNode(final Key key)
	{
		return deleteNode(key, this.nodes);
	}

 	/**
 	 * Deletes a node and all edges into & out of it
 	 * @param key
 	 * @return true if graph contained the node and it has been removed
 	 */
 	private boolean deleteNode(final Key key, final List<Node> graph)
 	{
 		final Node toBeDel = get(key, graph);
 		if(toBeDel == null) 
 			return false;
 		else {
 			//need to iterate through all the nodes and remove the soon to 
 			//be deleted node's key from any destinations lists, reduce E, and 
 			//the source node's outdegree
 			for(final Node node : graph)
 			{
 				if(node.destinations.contains(key))
 				{
 					node.destinations.remove(key);
 					node.outdegree--;
 					toBeDel.indegree--;
 					toBeDel.ancestors.remove(key);
 				}
 			}
 			graph.remove(toBeDel);
 			this.E -= (toBeDel.destinations.size());
 			return true;
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
	public boolean addEdge(final Key v, final Key w)
	{
		final Node source = get(v);
		final Node dest = get(w);
		
		if(source != null && dest != null)
		{
			source.destinations.add(w);
			source.outdegree++;
			dest.indegree++; 
			dest.ancestors.add(v);
			E++;
			if(!this.isAcyclic())
			{
				//undo the addEdge
				source.destinations.remove(w);
				source.outdegree--;
				dest.indegree--; 
				dest.ancestors.remove(v);
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

	public boolean isAcyclic()
	{
		return isAcyclic(cloneGraph());
	}
	
	/**
	 * Original pseudo-code algorithm from https://www.cs.hmc.edu/~keller/courses/cs60/s98/examples/acyclic/
	 * @param graph
	 * @return
	 */
	private boolean isAcyclic(final List<Node> graph)
	{
		final int size = graph.size();
		for(int i = 0 ; i <= size; i++)
		{
			if(graph.size() == 0)
				return true; //If the graph has no nodes, stop. The graph is acyclic.
			else {
				final Node leaf = getLeaf(graph);
				if(leaf == null)
					return false; //If the graph has no leaf, stop. The graph is cyclic.
				else 
					deleteNode(leaf.key(), graph); //Choose a leaf of the graph. Remove this leaf and all edges going into the leaf to get a new graph.
			}
		}
		return false;
	}

	private List<Node> cloneGraph() 
	{
		final List<Node> graphCopy = new ArrayList<>(this.nodes.size());
		for(final Node node : this.nodes) 
		{
			final Node copy = new Node(node.key(), node.val());
			//copy original node's destinations list to the copy node
			copy.destinations.addAll(node.destinations);
			copy.indegree = node.indegree;
			copy.outdegree = node.outdegree;
			copy.ancestors.addAll(node.ancestors);
			graphCopy.add(copy);
		}
		return graphCopy;
	}
		
	/**
	 * Searches a provided graph for a leaf node.
	 * A leaf is a node with no targets (no outgoing edges)
	 * @param nodes
	 * @return a node or null if there is no leaf in the provides graph
	 */
	private Node getLeaf(final List<Node> graph) 
	{
		for(final Node node : graph) 
		{
			if(node.outdegree == 0) {
				return node;
			}
		}
		return null;
	}
	
 	public boolean isValidNode(final Key key)
 	{
 		return get(key) != null;
	}
 	
 	public boolean isValidNode(final Key key, final Value val)
 	{
 		if(key != null && val != null)
 		{
 			for(final Node node : nodes) {
 				if(key.compareTo(node.key) == 0 && val.equals(node.val())) {
 					return true;
 				}
 			}
 		}
 		return false;
 	}
	
	public int indegree(final Key key)
	{
		final Node node = get(key);
		if(node != null)
			return node.indegree;
		else
			return -1;
	}
	
	public int outdegree(final Key key)
	{
		final Node node = get(key);
		if(node != null)
			return node.outdegree;
		else
			return -1;
	}
	
	/*
	 * The below implementation of topological sort was taken from https://www.geeksforgeeks.org/topological-sorting/
	 * and edited for purpose by MollyCK
	 */
	// A recursive function used by topologicalSort 
    private void topologicalSortUtil(final Key v, final Deque<Key> stack) 
    { 
        // Mark the current node as visited. 
        final Node node = get(v);
        node.visited = true; 
  
        // Recur for all the vertices adjacent to this vertex 
        for(final Key key : node.destinations) 
        {
            if (!get(key).visited) 
                topologicalSortUtil(key, stack); 
        } 
  
        // Push current vertex to stack which stores result 
        stack.push(v); 
    } 
  
    // The function to do Topological Sort. 
    // It uses recursive topologicalSortUtil() 
    public String topologicalSort() 
    { 
        final Deque<Key> stack = new ArrayDeque<Key>(); 
        
        resetVisited();
        
        // Call the recursive helper function to store Topological Sort 
        // starting from all vertices one by one 
        for (final Node node : this.nodes) {
            if (!node.visited) 
                topologicalSortUtil(node.key(), stack); 
        }
        
        // Concatenate contents of stack into a String
        final StringBuilder result = new StringBuilder();
        while (stack.size() > 0) 
            result.append(stack.pop()).append(" "); 
        return result.toString();
    } 

    /**
     * Given a node (key) in the graph, recursively traverse backwards through all ancestors and build a list of all the 
     * possible paths through the DAG rooted at the given node
     * 
     * @param srcKey Starting node
     * @return A list of all paths starting at the given node. Each path is a list of Keys
     */
	private List<List<Key>> getPaths(final Key srcKey) 
	{
		final Node srcNode = get(srcKey);
		if(srcNode == null)
			return null;
		else {
			final List<List<Key>> paths = new ArrayList<>();
			buildPaths(srcKey, paths, new ArrayList<>());
			
			return paths;
		}
	}
	
	/**
	 * Entry point for the recursion. It creates a new list of keys (path) and adds it to the list of paths (result)
	 * 
	 * @param srcKey Starting node
	 * @param paths List of paths (result)
	 * @param accPath Accumulator used to build the path for the first ancestor through subsequent recursive calls
	 */
	private void buildPaths(final Key srcKey, final List<List<Key>> paths, final List<Key> accPath)
	{
		final List<Key> ancestorList = new ArrayList<>(accPath);
		paths.add(ancestorList);
		buildAncestorsPaths(srcKey, paths, ancestorList);
	}
	
	/**
	 * Recursion down into the node's ancestors. The path through the first ancestor is accumulated into accPath.
	 * For subsequent ancestors new path lists are created by calling back into buildPaths
	 * 
	 * @param srcKey Starting node
	 * @param paths List of paths (result)
	 * @param accPath Accumulator used to build the path for the first ancestor through subsequent recursive calls
	 */
	private void buildAncestorsPaths(final Key srcKey, final List<List<Key>> paths, final List<Key> accPath)
	{
		accPath.add(srcKey);
		
		//We need to take a snapshot of the accumulator at this point as this is where all other ancestors branch off
		final List<Key> accPathClone = new ArrayList<>(accPath);
		final List<Key> srcNodeAncestors = get(srcKey).ancestors;
		
		if(!srcNodeAncestors.isEmpty()) {
			//Process the first ancestor by recursively appending to the accumulator
			final Key firstAncestor = srcNodeAncestors.get(0);
			buildAncestorsPaths(firstAncestor, paths, accPath);
			
			//Process all remaining ancestors by recursively creating a new list for each one
			for(final Key ancestor : srcNodeAncestors.subList(1, srcNodeAncestors.size())) 
			{
				//Here is where the accumulator snapshot is used
				buildPaths(ancestor, paths, accPathClone);
			}
		}
	}
	
	private List<Key> intersection(final List<Key> path1, final List<Key> path2)
	{
		return path1.stream().distinct().filter(path2::contains).collect(Collectors.toList());
	}

	/**
	 * The lowest common ancestor of 2 nodes is that node which is furthest from any root that the source nodes' longest paths from that root have in common.
	 * 
	 * @param node1
	 * @param node2
	 * @return
	 */
	public Key LCA(Key srcNode1, Key srcNode2)
	{
		//test if provided source nodes are valid
		if(isValidNode(srcNode1) && isValidNode(srcNode2))
		{
			//get all paths through the DAG starting at srcNode1 and srcNode2 respectively
			final List<List<Key>> pathList1 = getPaths(srcNode1);
			final List<List<Key>> pathList2 = getPaths(srcNode2);
			
			if(pathList1 == null || pathList2 == null)
				return null;
			
			//Compute all possible N*M intersections, where N = pathList1.size() and M = pathList2.size()
			//Pick the longest list (i.e. the deepest)
			final Optional<List<Key>> shortestFromSrc = pathList1.stream().distinct().
                    flatMap(path1 -> pathList2.stream().map(path2 -> intersection(path1, path2))).
                    max((Comparator.comparingInt(List::size)));
			//Paths are built bottom-up (from node up to ancestors), so the dirst element in the list will be the LCA.
			//Some sanity null and empty list checks
			return shortestFromSrc.map(path -> path.isEmpty()?null:path.get(0)).orElse(null);
		} 
		else return null;
	}
	
	
	
}