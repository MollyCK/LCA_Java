/*************************************************************************
 *  Binary Search Tree class.
 *  Adapted from Sedgewick and Wayne.
 *
 *  @version 3.0 1/11/15 16:49:42
 *
 *  @author Molly Carles (18320282)
 *
 *************************************************************************/
package lowestCommonAncestor;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BinarySearchTree<Key extends Comparable<Key>, Value> {
	private Node root;             // root of BST

	/**
	 * Private node class.
	 */
	private class Node {
		private final Key key;           // sorted by key
		private Value val;         // associated data
		private Node left, right;  // left and right subtrees
		private int N;             // number of nodes in subtree

		public Node(Key key, Value val, int N) {
			this.key = key;
			this.val = val;
			this.N = N;
		}
	}

	// is the symbol table empty?
	public boolean isEmpty() 
	{ 
		return size() == 0; 
	}

	// return number of key-value pairs in BST
	public int size() 
	{
		return size(root); 
	}

	// return number of key-value pairs in BST rooted at x
	private int size(Node x) {
		if (x == null) return 0;
		else return x.N;
	}

	/**
	 *  Search BST for given key.
	 *  Does there exist a key-value pair with given key?
	 *
	 *  @param key the search key
	 *  @return true if key is found and false otherwise
	 */
	public boolean contains(Key key) {
		return get(key) != null;
	}

	/**
	 *  Search BST for given key.
	 *  What is the value associated with given key?
	 *
	 *  @param key the search key
	 *  @return value associated with the given key if found, or null if no such key exists.
	 */
	public Value get(Key key) 
	{ 
		return get(root, key); 
	}

	private Node getNode(Node root, Key key) 
	{
		if (root == null) 
			return null;
		int cmp = key.compareTo(root.key);
		if(cmp < 0) 
			return getNode(root.left, key);
		else if (cmp > 0) 
			return getNode(root.right, key);
		else              
			return root;
	}
	
	private Value get(Node root, Key key) 
	{
		final Node node = getNode (root, key);
		
		return node == null?null:node.val;
	}

	/**
	 *  Insert key-value pair into BST.
	 *  If key already exists, update with new value.
	 *
	 *  @param key the key to insert
	 *  @param val the value associated with key
	 */
	public void put(Key key, Value val) {
		if (val == null) 
		{
			delete(key); 
			return; 
		}
		root = put(root, key, val);
	}

	private Node put(Node x, Key key, Value val) {
		if (x == null) return new Node(key, val, 1);
		int cmp = key.compareTo(x.key);
		if      (cmp < 0) x.left  = put(x.left,  key, val);
		else if (cmp > 0) x.right = put(x.right, key, val);
		else              x.val   = val;
		x.N = 1 + size(x.left) + size(x.right);
		return x;
	}

	/**
	 * Tree height.
	 *
	 * Asymptotic worst-case running time using Theta notation: TODO
	 *
	 * @return the number of links from the root to the deepest leaf.
	 *
	 * Example 1: for an empty tree this should return -1.
	 * Example 2: for a tree with only one node it should return 0.
	 * Example 3: for the following tree it should return 2.
	 *   B
	 *  / \
	 * A   C
	 *      \
	 *       D
	 */
	public int height() {
		return height(this.root);
	}

	private int height(Node node) {
		int height = -1;
		int leftHeight = -1;
		int rightHeight = -1;

		if (node != null) {
			if(node.left != null) {
				leftHeight = height(node.left);
			}
			if(node.right != null) {
				rightHeight = height(node.right);
			}
			height = (Math.max(leftHeight,  rightHeight)) + 1;
		}
		return height;
	}

	private int compareHeights(Key key1, Key key2)
	{
		return Integer.compare(height(getNode(this.root, key1)), height(getNode(this.root, key2)));
	}
	
	/**
	 * Median key.
	 * If the tree has N keys k1 < k2 < k3 < ... < kN, then their median key 
	 * is the element at position (N+1)/2 (where "/" here is integer division)
	 *
	 * @return the median key, or null if the tree is empty.
	 * 
	 * The running time should be Theta(h), where h is the height of the tree.
	 */
	public Key median() {
		return median(this.root, (this.size() + 1)/2);
	}

	private Key median(Node node, int positionWanted)
	{
		Key returnKey = null;
		if (!isEmpty() && node != null) 
		{
			int leftCount = 0;
			if(node.left != null)
			{
				leftCount = node.left.N;
			}
			if(leftCount + 1 == positionWanted)
			{
				//the median is the root
				returnKey = node.key;
			}
			else if(leftCount >= positionWanted)
			{
				//the median has to be in the left subtree
				returnKey = median(node.left, positionWanted);
			}
			else
			{
				//the median has to be in the right subtree
				if(node.right != null)
				{
					positionWanted -= leftCount + 1; //take out the count for the left subtree and the root
					returnKey = median(node.right, positionWanted);
				}
			}
		}
		return returnKey;
	}


	/**
	 * Print all keys of the tree in a sequence, in-order.
	 * That is, for each node, the keys in the left subtree should appear before the key in the node.
	 * Also, for each node, the keys in the right subtree should appear before the key in the node.
	 * For each subtree, its keys should appear within a parenthesis.
	 *
	 * Example 1: Empty tree -- output: "()"
	 * Example 2: Tree containing only "A" -- output: "(()A())"
	 * Example 3: Tree:
	 *   B
	 *  / \
	 * A   C
	 *      \
	 *       D
	 *
	 * output: "((()A())B(()C(()D())))"
	 *
	 * output of example in the assignment: (((()A(()C()))E((()H(()M()))R()))S(()X()))
	 *
	 * @return a String with all keys in the tree, in order, parenthesized.
	 */
	public String printKeysInOrder() {
		String keysInOrder = printKeysInOrder(this.root);
		System.out.println(keysInOrder);
		return keysInOrder;
	}
	
	private String printKeysInOrder(Node node)
	{
		String keysInOrder = "";
		keysInOrder += "(";
		if(!isEmpty() || node != null)
		{
			if(node.left != null)
			{
				keysInOrder += printKeysInOrder(node.left);
			}
			else keysInOrder += "()";
			keysInOrder += node.val.toString();
			if(node.right != null)
			{
				keysInOrder += printKeysInOrder(node.right);
			}
			else keysInOrder += "()";
		}
		keysInOrder += ")";
		return keysInOrder;
	}

	/**
	 * Pretty Printing the tree. Each node is on one line -- see assignment for details.
	 *
	 * @return a multi-line string with the pretty ascii picture of the tree.
	 */
	public String prettyPrintKeys() {
		String prettyPrint = prettyPrint(this.root, "");
		System.out.println(prettyPrint);
		return prettyPrint;
	}

	private String prettyPrint(Node node, String prefix)
	{
		String prettyPrint = new String(prefix);
		if(isEmpty())
		{
			prettyPrint += "-null\n";
		}
		else
		{
			prettyPrint += "-" + node.key + "\n";
			prefix += " |";
			prettyPrint += node.left == null? prefix + "-null\n" : prettyPrint(node.left, prefix);
			prefix = prefix.substring(0, prefix.length() - 1) + " ";
			prettyPrint += node.right == null? prefix + "-null\n" : prettyPrint(node.right, prefix);
		}
		return prettyPrint;
	}

	/**
	 * Deletes a key from a tree (if the key is in the tree).
	 * Note that this method works symmetrically from the Hibbard deletion:
	 * If the node to be deleted has two child nodes, then it needs to be
	 * replaced with its predecessor (not its successor) node.
	 *
	 * @param key the key to delete
	 */
	public void delete(Key key) {
		delete(this.root, key);
	}

	private Node delete(Node node, Key key)
	{
		if (node == null) return null;
		int cmp = key.compareTo(node.key);
		if (cmp < 0) node.left = delete(node.left, key);
		else if (cmp > 0) node.right = delete(node.right, key);
		else {
			if (node.right == null) return node.left;
			if (node.left == null) return node.right;
			Node t = node;
			node = max(t.left);
			node.left = deleteMax(t.left);
			node.right = t.right;
		}
		node.N = size(node.left) + size(node.right) + 1;
		return node;
	}

	private Node deleteMax(Node node)
	{
		if (node.right == null) return node.left;
		node.right = deleteMax(node.right);
		node.N = 1 + size(node.right) + size(node.left);
		return node;
	}

	private Node max(Node node)
	{
		if(node.right == null)
		{
			return node;
		}
		return max(node.right);
	}

	/**
	 * Finds the path from root to child
	 * @param child key of destination node
	 * @return path as an ArrayList of Keys with root node at index 0
	 */
	public ArrayList<Key> getPath(Key child)
	{
		ArrayList<Node> pathNodes = new ArrayList<>();
		pathNodes = getPath(this.root, child, pathNodes);
		if(pathNodes != null) {
			ArrayList<Key> pathKeys = new ArrayList<Key>();
			for(int i = 0 ; i < pathNodes.size(); i++)
			{
				pathKeys.add(i, pathNodes.get(i).key);
			}
			return pathKeys;	
		}
		return null;
	}

	private ArrayList<Node> getPath(Node root, Key child, ArrayList<Node> path)
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
	 * The lowest common ancestor of two nodes is that node which is furthest from a root that both source nodes' paths from that root have in common.
	 * @param key1 (key of first descendant)
	 * @param key2 (key of second descendant)
	 * @return LCA (key of lowest common ancestor of both descendants)
	 */
	public Key LCA(Key key1, Key key2)
	{
		Key LCA = null;
		if(this.contains(key1) && this.contains(key2))
		{
			//Get the paths to each key but remove the key itself (last element)
			ArrayList<Key> path1 = getPath(key1);
			path1.remove(path1.size() - 1);
			ArrayList<Key> path2 = getPath(key2);
			path2.remove(path2.size() - 1);
			//Find the intersection of both paths and pick the deepest one
			return path1.stream().distinct().filter(path2::contains).min(this::compareHeights).orElse(null);
		}
		return LCA;
	}
	
}