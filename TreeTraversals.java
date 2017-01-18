/* Section13.java.
 * Used by Parts II and III of Section 13, CSc 127B, Fall 2016 (McCann).
 *
 * Expected Output:
 *
 * Part II's testing output:
 * 
 *  tree1 has 0 leaf/leaves.
 *  tree2 has 1 leaf/leaves.
 *  tree3 has 1 leaf/leaves.
 *  tree4 has 4 leaf/leaves.
 * 
 * 
 * Part III's testing output:
 * 
 *  tree1's Inorder Traversal:  
 *  tree2's Inorder Traversal:  2 
 *  tree3's Inorder Traversal:  100 200 300 400 500 
 *  tree4's Inorder Traversal:  35 40 45 50 55 60 65
 */

import java.util.Iterator;
import java.util.Stack;
import java.util.NoSuchElementException;

public class Section13
{
    public static void main (String[] args)
    {
        BinarySearchTree<Integer> tree1 = null, tree2 = null,
                                  tree3 = null, tree4 = null;

                // Create four BSTs for testing

        tree1 = new BinarySearchTree<Integer>();

        tree2 = new BinarySearchTree<Integer>();
        tree2.add(2);

        tree3 = new BinarySearchTree<Integer>();
        tree3.add(100);    // 100
        tree3.add(200);    //    200
        tree3.add(300);    //       300
        tree3.add(400);    //          400
        tree3.add(500);    //             500

        tree4 = new BinarySearchTree<Integer>();
        tree4.add(50);
        tree4.add(40);     //         50
        tree4.add(60);     //    40        60
        tree4.add(35);     //  35  45    55  65
        tree4.add(45);
        tree4.add(55);
        tree4.add(65);


        System.out.println("\nPart II's testing output:\n");

        System.out.println("\ttree1 has " + tree1.countLeaves() 
                         + " leaf/leaves.");
        System.out.println("\ttree2 has " + tree2.countLeaves()
                         + " leaf/leaves.");
        System.out.println("\ttree3 has " + tree3.countLeaves()
                         + " leaf/leaves.");
        System.out.println("\ttree4 has " + tree4.countLeaves()
                         + " leaf/leaves.");


        System.out.println("\n\nPart III's testing output:\n");

                // Note the "for each" loops, which we can use because
                // of the presence of an iterator in BinarySearchTree!

        System.out.print("\ttree1's Inorder Traversal:  ");
        for (int i : tree1) {                // for each Integer in the tree...
            System.out.print(i + " ");       // ...print it!
        }
        System.out.println();

        System.out.print("\ttree2's Inorder Traversal:  ");
        for (int i : tree2) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.print("\ttree3's Inorder Traversal:  ");
        for (int i : tree3) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.print("\ttree4's Inorder Traversal:  ");
        for (int i : tree4) {
            System.out.print(i + " ");
        }
        System.out.println('\n');

    }
}


class BinarySearchTree< E extends Comparable<E> > implements Iterable<E>
{
    protected TreeNode<E> root;

    public BinarySearchTree ()
    {
        root = null;
    }

    public void add (E newData)
    {
        root = addHelper(root, newData);
    }

    private TreeNode<E> addHelper (TreeNode<E> current, E newData)
    {
        TreeNode<E> temp;
        if (current == null) {              // need to create a new subtree
            temp = new TreeNode<E>(newData);
            return temp;
        }
        else if (current.getData().compareTo(newData) > 0) {   // add on left
            temp = addHelper(current.getLeft(), newData);
            current.setLeft( temp );
            return current;
        } else {                                         // add on right
            temp = addHelper(current.getRight(), newData);
            current.setRight( temp );
            return current;
        }
        //return node;
    } 

            // inOrder() is obsolete, now that we have InorderIterator!
            // We've left it here because bits are cheap.

    public void inOrder ()
    {
        inOrder(root);
    }

    private void inOrder (TreeNode<E> node)
    {
        if (node != null) {
            inOrder(node.getLeft());
            System.out.print(node.getData().toString() + " ");
            inOrder(node.getRight());
        }
    }


            // ===== Code for Part II =====

    public int countLeaves()
    {
      return countLeavesHelper(root);  // STUB!
    }

    private int countLeavesHelper(TreeNode<E> current){
      if(current == null)
        return 0;
      if(current.getLeft() == null && current.getRight() == null)
        return 1;
      return countLeavesHelper(current.getLeft()) + countLeavesHelper(current.getRight());
    }



            // ===== Code for Part III =====

    public Iterator<E> iterator()
    {
      InorderIterator myIterator = new InorderIterator();
      return myIterator;
    }

    private class InorderIterator implements Iterator<E>
    {
        private Stack<TreeNode<E>> nodeStack;   // history of little brothers
        private TreeNode<E>        currentNode; // place to start next time

        public InorderIterator()
        {
          currentNode = root;
          nodeStack = new Stack<TreeNode<E>>();
        }

        public boolean hasNext()
        {
            if(nodeStack.isEmpty() == false)
              return true;
            if(currentNode != null)
              return true;
            
            return false;
        }

        public E next()
        {
          TreeNode<E> nextNode = null;  // next node in the traversal
          
          while(currentNode != null){
            nodeStack.push(currentNode);
            currentNode = currentNode.getLeft();
          }
          
          if(nodeStack.isEmpty() == false){
            nextNode = nodeStack.pop();
            currentNode = nextNode.getRight();
          }
          else
            throw new NoSuchElementException();
          
          return nextNode.getData();
        }

                // remove() is required to exist (by the Iterator interface
                // definition) but there's no requirement that it do anything!

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

    } // InorderIterator



    class TreeNode<E>
    {
        E             data;       
        TreeNode<E> leftChild,  
                      rightChild; 

        public TreeNode (E newData)
        {
            data = newData;
            leftChild = rightChild = null;
        }
        
        public void setData( E someData )
        {
            data = someData;
        }
        
        public E getData()
        {
            return data;
        }
        
        public void setLeft( TreeNode<E> newLeft )
        {
            leftChild = newLeft;
        }
        
        public TreeNode<E> getLeft()
        {
            return leftChild;
        }
        
        public void setRight( TreeNode<E> newRight )
        {
            rightChild = newRight;
        }
        
        public TreeNode<E> getRight()
        {
            return rightChild;
        }

    }

}

// CSc 127B Fall 2016 Section 12 Part I (McCann)

class LLTiming
{
    final static long BILLION = 1000000000;  // # of nanoseconds in a second


    public static LLNode<Integer> prepend (LLNode<Integer> head, int item)
    {
        LLNode<Integer> newNode = new LLNode<Integer> (item);

        newNode.setNext(head);

        return newNode;
    }


    public static LLNode<Integer> appendIterative (LLNode<Integer> head,
                                                   int item)
    {
        LLNode<Integer> newNode = new LLNode<Integer> (item); 

        newNode.setNext(null);

        if (head == null) {
            head = newNode;
        } else {
            LLNode<Integer> fore = head, aft = null;

            while (fore != null) {
                aft = fore;
                fore = fore.getNext();
            }

            aft.setNext(newNode);
        }

        return head;
    }


    public static LLNode<Integer> appendRecursive (LLNode<Integer> head,
                                                   int item)
    {
                // Base Case:  Create a node for the new value.
        if (head == null) {
            LLNode<Integer> newNode = new LLNode<Integer> (item);
            newNode.setNext(null);
            return newNode;
        }

                // General Case: Attach the value further down the list.
        head.setNext(appendRecursive(head.getNext(),item));

        return head;
    }


            // Helpful for debugging!
    public static String toString (LLNode<Integer> head)
    {
/*
        if (head == null) return "()";

        LLNode<Integer> temp = head;
        String str = "";

        while (temp != null) {
            str += "(" + temp.getData().toString() + ")";
            temp = temp.getNext();
        }

        return str;
*/
        return "";
    }

            // Get the current system time
    public static long startTiming ()
    {
        System.gc();              // Try to avoid a mid-op garbage collection
        return (System.nanoTime());
    }


            // Get the elapsed time since the given clock value
    public static double stopTiming (long startingTime)
    {
        long elapsedTime = System.nanoTime() - startingTime;
        return (1.0 * elapsedTime / BILLION);    // return seconds
    }


    public static void main (String [] args)
    {
        LLNode<Integer> head; // reference to the first node of the list
        int listSize = 0;     // # of items in the final list(s)
        long startTime;       // system time when a list was started
        double seconds;       // execution time required to build the list


        System.out.println("\nThis program compares the time required to "
                         + "prepend, append (iteratively),\nand append "
                         + "(recursively) the same number of nodes to an "
                         + "initially empty\nlinked list.\n");

        if (args.length < 1) {
            System.out.println("\nUsage: java LLTiming <final list size>\n");
            System.exit(1);
        } else {
            listSize = Integer.parseInt(args[0]);
        }

                // Prepend n items to a new LL

        head = null;
        System.out.printf("               Prepending ");
        startTime = startTiming();
        for (int i = 0; i < listSize; i++) {
            head = prepend(head,i);
        }
        seconds = stopTiming(startTime);
        System.out.printf(listSize + " nodes required "
                        + "%7.3f seconds.\n", seconds);
        //System.out.println("                          List content: "
                         //+ toString(head));

                // Append (iteratively) n items to a new LL

        head = null;
        System.out.printf("  Appending (iteratively) ");
        startTime = startTiming();
        for (int i = 0; i < listSize; i++) {
            head = appendIterative(head,i);
        }
        seconds = stopTiming(startTime);
        System.out.printf(listSize + " nodes required "
                        + "%7.3f seconds.\n", seconds);
        //System.out.println("                          List content: "
                         //+ toString(head));

                // Append (recursively) n items to a new LL

        /*head = null;
        System.out.printf("  Appending (recursively) ");
        startTime = startTiming();
        for (int i = 0; i < listSize; i++) {
            head = appendRecursive(head,i);
        }
        seconds = stopTiming(startTime);
        System.out.printf(listSize + " nodes required "
                        + "%7.3f seconds.\n", seconds);
        //System.out.println("                          List content: "
                         //+ toString(head));*/
    }

} // LLTiming

// CSc 127B Fall 2016 Section 9 Parts I and II

class LLNode<E> {

    public E data;
    public LLNode<E> next;

    public LLNode (E value)
    {
        data = value;
        next = null;
    }

    public LLNode (E value, LLNode<E> ref)
    {
        data = value;
        next = ref;
    }

    public E getData()
    {
       return data;
    }
    public LLNode<E> getNext()           
    {
        return next;
    }
    public void setData(E dataItem)
    {
        data = dataItem;
    }
    public void setNext(LLNode<E> nextItem)
    {
        next = nextItem;
    }

} // LLNode<E>