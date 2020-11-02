package lowestCommonAncestor;

import static org.junit.Assert.*;

import org.junit.Test;

public class BinarySearchTreeTest {

	
	@Test
	public void testIsEmpty() {
		BinarySearchTree<Integer, Character> testTree = new BinarySearchTree<Integer, Character>();
		//Test empty tree
		assertTrue( "Testing empty tree", testTree.isEmpty());
		
		//Test not empty tree
		testTree.put(1, 'A');
		assertFalse( "Testing non-empty tree", testTree.isEmpty());
	}
	
	@Test
	public void testHeight(){
		BinarySearchTree<Integer, Character> testTree = new BinarySearchTree<>();
		//Test empty tree
		assertEquals( "Testing height for empty tree", -1, testTree.height());
		
		//Test tree with 1 node
		testTree.put(2, 'B');
		assertEquals( "Testing height for tree with 1 node", 0, testTree.height());
		
		//Test tree with more than 1 node
		testTree.put(1, 'A');
		assertEquals( "Testing height for tree with 2 nodes", 1, testTree.height());
		
		testTree.put(3, 'C');
		testTree.put(4, 'D');
		assertEquals( "Testing height for tree with 4 nodes(1 root, 2 root children, 1 child of right root child", 2, testTree.height());
	}
	
	@Test
	 public void testPrettyPrint() {
		BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
	     assertEquals("Checking pretty printing of empty tree",
	             "-null\n", bst.prettyPrintKeys());
	      
	                          //  -7
	                          //   |-3
	                          //   | |-1
	                          //   | | |-null
	     bst.put(7, 7);       //   | |  -2
	     bst.put(8, 8);       //   | |   |-null
	     bst.put(3, 3);       //   | |    -null
	     bst.put(1, 1);       //   |  -6
	     bst.put(2, 2);       //   |   |-4
	     bst.put(6, 6);       //   |   | |-null
	     bst.put(4, 4);       //   |   |  -5
	     bst.put(5, 5);       //   |   |   |-null
	                          //   |   |    -null
	                          //   |    -null
	                          //    -8
	                          //     |-null
	                          //      -null
	     
	     String result = 
	      "-7\n" +
	      " |-3\n" + 
	      " | |-1\n" +
	      " | | |-null\n" + 
	      " | |  -2\n" +
	      " | |   |-null\n" +
	      " | |    -null\n" +
	      " |  -6\n" +
	      " |   |-4\n" +
	      " |   | |-null\n" +
	      " |   |  -5\n" +
	      " |   |   |-null\n" +
	      " |   |    -null\n" +
	      " |    -null\n" +
	      "  -8\n" +
	      "   |-null\n" +
	      "    -null\n";
	     assertEquals("Checking pretty printing of non-empty tree", result, bst.prettyPrintKeys());
	     }
	
	@Test
	public void testMedian()
	{
		BinarySearchTree<Character, Integer> testTree = new BinarySearchTree<>();
		//testing empty tree
		Character expectedResult = null;
		assertEquals("Testing median() for empty tree", expectedResult, testTree.median());
		
		//testing tree with median at root
		testTree.put('B', 2);
		testTree.put('A', 1);
		testTree.put('C', 3);
		expectedResult = 'B';
		assertEquals("Testing median() for CBA tree", expectedResult, testTree.median());
		
		//testing tree with median in right subtree
		testTree.put('D', 4);
		testTree.put('E', 5);
		expectedResult = 'C';
		assertEquals("Testing median() for EDCBA tree", expectedResult, testTree.median());
		
		//testing tree with median in left subtree
		testTree = new BinarySearchTree<>();
		
	}
	
	/** <p>Test {@link BinarySearchTree#delete(Comparable)}.</p> */
    @Test
    public void testDelete() {
    	BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
        bst.delete(1);
        assertEquals("Deleting from empty tree", "()", bst.printKeysInOrder());
        
        bst.put(7, 7);   //        _7_
        bst.put(8, 8);   //      /     \
        bst.put(3, 3);   //    _3_      8
        bst.put(1, 1);   //  /     \
        bst.put(2, 2);   // 1       6
        bst.put(6, 6);   //  \     /
        bst.put(4, 4);   //   2   4
        bst.put(5, 5);   //        \
                         //         5
        
        assertEquals("Checking order of constructed tree", "(((()1(()2()))3((()4(()5()))6()))7(()8()))", bst.printKeysInOrder());
        
        bst.delete(9);
        assertEquals("Deleting non-existent key",
                "(((()1(()2()))3((()4(()5()))6()))7(()8()))", bst.printKeysInOrder());

        bst.delete(8);
        assertEquals("Deleting leaf", "(((()1(()2()))3((()4(()5()))6()))7())", bst.printKeysInOrder());

        bst.delete(6);
        assertEquals("Deleting node with single child",
                "(((()1(()2()))3(()4(()5())))7())", bst.printKeysInOrder());

        bst.delete(3);
        assertEquals("Deleting node with two children",
                "(((()1())2(()4(()5())))7())", bst.printKeysInOrder());
    }
    
    private BinarySearchTree<Integer, Character> testBST = new BinarySearchTree<>();
    
    @Test
    public void testLCA() {
    	//testing empty tree
    	Integer expectedResult = null;
		assertEquals("", expectedResult, testBST.LCA(2, 4));
		
		//testing average (normal) case
		testBST.put(7, '7');   //        _7_
		testBST.put(8, '8');   //      /     \
		testBST.put(3, '3');   //    _3_      8
		testBST.put(1, '1');   //  /     \
		testBST.put(2, '2');   // 1       6
		testBST.put(6, '6');   //  \     /
		testBST.put(4, '4');   //   2   4
		testBST.put(5, '5');   //        \
							   //         5
		expectedResult = 3;
		assertEquals("", expectedResult, testBST.LCA(2, 4));
		
		expectedResult = 3;
		assertEquals("", expectedResult, testBST.LCA(2, 5));
		
		expectedResult = 7;
		assertEquals("", expectedResult, testBST.LCA(3, 8));
		
		expectedResult = 7;
		assertEquals("", expectedResult, testBST.LCA(5, 8));
		
		expectedResult = 6;
		assertEquals("", expectedResult, testBST.LCA(4, 5));
		
		expectedResult = null;
		assertEquals("", expectedResult, testBST.LCA(7, 3));
		
		
		//testing "single line" tree
		testBST = new BinarySearchTree<>();
		testBST.put(7, '7');   //        7
		testBST.put(5, '5');   //       /
		testBST.put(3, '3');   //      5    
		testBST.put(1, '1');   //     /
							   //	 3
							   //   /
							   //  1
		expectedResult = 5;
		assertEquals("", expectedResult, testBST.LCA(1, 3));
		
		expectedResult = null;
		assertEquals("", expectedResult, testBST.LCA(7, 3));
		
		expectedResult = 7;
		assertEquals("", expectedResult, testBST.LCA(5, 1));
    }
}