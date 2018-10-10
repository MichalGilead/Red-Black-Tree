
/**
*
* RBTree
*
* An implementation of a Red Black Tree with
* non-negative, distinct integer keys and values
* 
* written by Michal Gilead, ID-203374442, user name - michalgilead  
* 			 Chen Nakar, ID-203449400 , user name - chennakar
*
*/

public class RBTree {

/**
  * public class RBNode
  */
 public static class RBNode{
	 
		String value;
		int key;
		RBNode left;
		RBNode right;
		RBNode parent;
		boolean red;
		int nodesize;
		
		public RBNode(String value, int key, RBNode left, RBNode right, RBNode parent, boolean red, int nodesize){
			this.value = value;
			this.key = key;
			this.left = left;
			this.right = right;
			this.parent = parent;
			this.red = red;	
			this.nodesize = nodesize;
		}
	 
		public boolean isRed(){ return red;}
		public RBNode getLeft(){return left;}
		public RBNode getRight(){return right;}
		public int getKey(){return key;}
}
 
	RBNode T = new RBNode("", Integer.MAX_VALUE, null, null, null, false, 1);
	RBNode NULL = new RBNode("", -1, null, null, T, false, 0);

 
 /**
  * public RBNode getRoot()
  *
  * returns the root of the red black tree
  *
  */
 public RBNode getRoot() 
 {
	 if (T.left==null)
		 return null;
	 return T.left;
 }
 
 /**
  * public boolean empty()
  *
  * returns true if and only if the tree is empty
  *
  */
 public boolean empty() 
 {
	 if (T.left==null || T.left==NULL)
		 return true;
	 return false; 
 }

/**
  * public String search(int k)
  *
  * returns the value of an item with key k if it exists in the tree
  * otherwise, returns null
  */
 public String search(int k)
 {
	if (empty())
		return null;
	RBNode temp = T;
	while(temp.key!=k)
	{
		if (temp.key<k)
		{
			if (temp.right!=NULL)
				temp=temp.right;
			else
				break;
		}
		if (temp.key>k)
		{
			if (temp.left!=NULL)
				temp=temp.left;
			else
				break;
		}
	}
	if (temp.key==k)
		return temp.value;
	return null; 
 }

 private void RightRotate(RBNode y)
 {
	  RBNode x = y.left;
	  Transplant(y, x);
	  LeftChild(y, x.right);
	  RightChild(x, y);
	  y.nodesize = y.left.nodesize + y.right.nodesize +1;
	  x.nodesize = x.left.nodesize + x.right.nodesize +1;
 }

 private void LeftRotate(RBNode x)
 {
	  RBNode y = x.right;
	  Transplant(x, y);
	  RightChild(x, y.left);
	  LeftChild(y, x); 
	  x.nodesize = x.left.nodesize + x.right.nodesize +1;
	  y.nodesize = y.left.nodesize + y.right.nodesize +1;
 }
 
 private void Transplant(RBNode x, RBNode y)
 {
	  if (x == x.parent.left)
		  LeftChild(x.parent, y);
	  else
		  RightChild(x.parent, y);  
 }
 
 private void LeftChild (RBNode x, RBNode y)
 {
	  x.left = y;
	  if (y!=NULL)
		  y.parent = x;
 }
 
 private void RightChild (RBNode x, RBNode y)
 {
	  x.right = y;
	  if (y!=NULL)
		  y.parent = x;
 }
 
 
 /**
  * public int insert(int k, String v)
  *
  * inserts an item with key k and value v to the red black tree.
  * the tree must remain valid (keep its invariants).
  * returns the number of color switches, or 0 if no color switches were necessary.
  * returns -1 if an item with key k already exists in the tree.
  */
  public int insert(int k, String v) 
  {
	  RBNode z = new RBNode(v, k, NULL, NULL, null, true, 1);
	  if (search(k)!=null)
		  return -1;
	  if (T.left==null)
	  {
		  z.red = false;
		  T.left = z;
		  z.parent = T;
	  	return 1;
	  }
	  RBNode temp = T.left;
	  RBNode prev = T;
	  while(temp!=NULL)
	  {
		  if (temp.key<k)
		  {
			  prev=temp;
			  temp=temp.right;
		  }
		  if (temp.key>k)
		  {
			  prev=temp;
			  temp=temp.left;
		  }
	  }
	  if (k<prev.key)
		  LeftChild(prev,z);
	  else
		  RightChild(prev,z);
		
	  return InsertFixup(z);	
  }
  
  private int InsertFixup(RBNode z)
  {
	  int changes=0;
	  if (!z.parent.red)
	  {
		  while (z.parent!=T){
			  z.parent.nodesize++;			//update subtree size
			  z=z.parent;
		  }
	  }
	  while (z.parent.red)
	  {
		  if(z.parent==z.parent.parent.left) //if z's parent is a left child
		  {
			  RBNode y = z.parent.parent.right; //y is z's uncle
			  if (y.red == true)				//if z's uncle is red
			  {               
				  z.parent.red = false;
				  y.red = false;
				  changes+=2;
				  if (z.parent.parent!=T.left)
				  {
					  z.parent.parent.red = true;
					  changes++;
				  }else{ // counting double coloring of the root
					  changes+=2;
				  }
				  z.parent.nodesize++;			//update subtree size
				  if (z.parent.parent!=T)
					  z.parent.parent.nodesize++;
				  z = z.parent.parent;
			  }else{								//if z's uncle is black								
				  if (z == z.parent.right)
				  {
					  z = z.parent;
					  LeftRotate(z);
					  
				  }
				  z.parent.red = false;
				  z.parent.parent.red = true;
				  RightRotate(z.parent.parent);
				  changes+=2;
			  }
		  }else{								//if z's parent is a right child
			  RBNode y = z.parent.parent.left;  //y is z's uncle
			  if (y.red == true)				//if z's uncle is red
			  {				
				  z.parent.red = false;
				  y.red = false;
				  changes+=2;
				  if (z.parent.parent!=T.left)
				  {
					  z.parent.parent.red = true;
					  changes++;
				  }else{ // counting double coloring of the root
					  changes+=2;
				  }
				  z.parent.nodesize++;			//update subtree size
				  if (z.parent.parent!=T)
					  z.parent.parent.nodesize++;
				  z = z.parent.parent;
			  }else{							//if z's uncle is black
				  if (z == z.parent.left)
				  {
					  z = z.parent;
					  RightRotate(z);
				  }
				  z.parent.red = false;
				  z.parent.parent.red = true;
				  LeftRotate(z.parent.parent);
				  changes+=2;
			  }
		  }
	  }
	  while (z.parent!=T){
		  z.parent.nodesize=z.parent.right.nodesize+z.parent.left.nodesize+1;			//update subtree size
		  z=z.parent;
	  }
	  return changes;
  }
  
 
 /**
  * public int delete(int k)
  *
  * deletes an item with key k from the binary tree, if it is there;
  * the tree must remain valid (keep its invariants).
  * returns the number of color switches, or 0 if no color switches were needed.
  * returns -1 if an item with key k was not found in the tree.
  */
  public int delete(int k)
  {
	  int changes = 0;
	  if (search(k)==null)
		  return -1;
	  	RBNode z = T.left;
		while(z.key!=k)
		{
			if (z.key<k)
				z=z.right;
			else
				z=z.left;
		}
		RBNode y;
		RBNode x;
		if (z.left==NULL || z.right==NULL) // z has NULL as a child
			y = z;
		else  
		{
			y=z.right;
			while(y.left!=NULL)
				y=y.left; // finding z's successor that has NULL as a child
		}
		if(y.left!=NULL) // y is z and has only a left child
			x=y.left;
		else
			x=y.right;
		x.parent=y.parent; // deleting y
		if (y.parent!=T) // if y is not the root
		{
			if (y==y.parent.left)
				y.parent.left=x;
			else
				y.parent.right=x;
		}else{
			T.left=x;
		}
		if (y!=z)
		{
			z.key=y.key;
			z.value=y.value;
			if (y.red!=z.red)
				changes++;
		}
		if (!y.red)
			return deleteFixup(x)+changes;
		else
		{
			while (y!=T) // update subtree size
			{
				y.parent.nodesize--;
				y=y.parent;
			}
			return changes;
		}
  }
  
  private int deleteFixup(RBNode x)
  {
	  int changes=0;
	  RBNode w;
	  while(x!=T.left&&!x.red)
	  {
		  if (x==x.parent.left) // x is a left child
		  {
			  w=x.parent.right; // w is x's sibling
			  if (w.red)
			  {
				  w.red=false;
				  x.parent.red=true;
				  changes+=2;
				  LeftRotate(x.parent);
				  w=x.parent.right; // w is x's new sibling, w is black
			  }
			  if (!w.left.red && !w.right.red) // w's children are both black
			  {
				  w.red=true;
				  changes++;
				  x=x.parent;
				  x.nodesize=x.left.nodesize+x.right.nodesize+1;				// update subtree size
			  }
			  else // w has at least one red child 
			  {
				  if(!w.right.red) // w's right child is black and left child is red
				  {
					  w.left.red=false;
					  w.red=true;
					  changes+=2;
					  RightRotate(w);
					  w=x.parent.right;
				  }
				  if (w.red!=x.parent.red)
				  {
					  w.red=x.parent.red;
					  changes++;
				  }
				  if (x.parent.red)
				  {
					  x.parent.red=false;
					  changes++;
				  }
				  w.right.red=false;
				  changes++;
				  LeftRotate(x.parent);
				  if (x!=T.left)
					  if (x.parent.parent!=T)
					  {
						  x=x.parent.parent;
						  while (x!=T) // update subtree size
						  {
								x.parent.nodesize--;
								x=x.parent;
						  }
					  }	
				  x=T.left; // done fixing, while loop will be terminated
			  }  
		  }
		  else // x is a right child
		  {
			  w=x.parent.left; // w is x's sibling
			  if (w.red)
			  {
				  w.red=false;
				  x.parent.red=true;
				  changes+=2;
				  RightRotate(x.parent);
				  w=x.parent.left; // w is x's new sibling, w is black
			  }
			  if (!w.left.red && !w.right.red) // w's children are both black
			  {
				  w.red=true;
				  changes++;
				  x=x.parent;
				  x.nodesize=x.left.nodesize+x.right.nodesize+1;				// update subtree size
			  }
			  else // w has at least one red child 
			  {
				  if(!w.left.red) // w's left child is black and right child is red
				  {
					  w.right.red=false;
					  w.red=true;
					  changes+=2;
					  LeftRotate(w);
					  w=x.parent.left;
				  }
				  if (w.red!=x.parent.red)
				  {
					  w.red=x.parent.red;
					  changes++;
				  }
				  if (x.parent.red)
				  {
					  x.parent.red=false;
					  changes++;
				  }
				  w.left.red=false;
				  changes++;
				  RightRotate(x.parent);
				  if (x!=T.left)
					  if (x.parent.parent!=T)
					  {
						  x=x.parent.parent;
						  while (x!=T) // update subtree size
						  {
								x.parent.nodesize--;
								x=x.parent;
						  }
					  }	
				  x=T.left; // done fixing, while loop will be terminated
			  }   
		  }
	  }
	  if (x.red)
	  {
		  x.red=false;
		  changes++;
	  }
	  if (x!=T.left)
		  x=x.parent;
	  while (x!=T&&x!=NULL) // update subtree size
	  {
			x.nodesize=x.left.nodesize+x.right.nodesize+1;
			x=x.parent;
	  }
	  return changes;
  }

  /**
   * public String min()
   *
   * Returns the value of the item with the smallest key in the tree,
   * or null if the tree is empty
   */
  public String min()
  {
	  if (empty())
		  return null;
	  RBNode minnode = T.left; 
	  while (minnode.left!=NULL)
		  minnode = minnode.left;
	   return minnode.value;
  }

  /**
   * public String max()
   *
   * Returns the value of the item with the largest key in the tree,
   * or null if the tree is empty
   */
  public String max()
  {
	  if (empty())
		  return null;
	  RBNode maxnode = T.left; 
	  while (maxnode.right!=NULL)
		  maxnode = maxnode.right;
	  return maxnode.value;
  }

  
 /**
  * public int[] keysToArray()
  *
  * Returns a sorted array which contains all keys in the tree,
  * or an empty array if the tree is empty.
  */
 public int[] keysToArray()
 {
	 int[] arr = new int[size()];
     if (empty())  
    	 return arr;
     inorderKeys(arr, T.left, 0);
     return arr;             
 }

 private int inorderKeys(int[] arr, RBNode x, int index)
 {
	 if (x.left!=NULL)
		 index = inorderKeys(arr, x.left, index);
	 arr[index++] = x.key;
	 if (x.right!=NULL)
		 index = inorderKeys(arr, x.right, index);
	 return index;									// return the last index that was filled
 }

 /**
  * public String[] valuesToArray()
  *
  * Returns an array which contains all values in the tree,
  * sorted by their respective keys,
  * or an empty array if the tree is empty.
  */
 public String[] valuesToArray()
 {
       String[] arr = new String[size()]; 
       if (empty())  
      	 return arr;
       inorderVals(arr, T.left, 0);
       return arr;                 
 }
 
 private int inorderVals(String[] arr, RBNode x, int index)
 {
	 if (x.left!=NULL)
		 index = inorderVals(arr, x.left, index);
	 arr[index++] = x.value;
	 if (x.right!=NULL)
		 index = inorderVals(arr, x.right, index);
	 return index;									// return the last index that was filled
 }

  /**
   * public int size()
   *
   * Returns the number of nodes in the tree.
   *
   * precondition: none
   * postcondition: none
   */
  public int size()
  {
	  if (empty())
		  return 0;
	  return T.left.nodesize;
  }

/**
   * public int rank(k)
   *
   * Returns the number of nodes in the tree with a key smaller than k.
   *
   * precondition: none
   * postcondition: none
   */
  public int rank(int k)
  {
	  int sum=0;
	  if (empty())
		  return sum;
	  RBNode temp = T.left;
	  RBNode x = T.left;
	  
	  while (temp!=NULL&&x.key!=k)
	  {
		  if (temp.key>k)
			  temp=temp.left;
		  else // temp.key<k or temp.key=k
		  {
			  if (temp.key>x.key || x.key>k || temp.key==k)
				  x=temp;  
			  temp=temp.right;
		  }
	  }
	  if (x.key>k) // there are no keys smaller than k, return 0
		  return sum;
	  sum=x.left.nodesize;
	  if (x.key!=k)
		  sum+=1;
	  RBNode y=x;
	  while(y!=T.left)
	  {
		  if (y==y.parent.right)
			  sum+=y.parent.left.nodesize+1;
		  y=y.parent;
	  }
	  return sum;
  }
  
/**
  * If you wish to implement classes, other than RBTree and RBNode, do it in this file, not in 
  * another file.
  */
}
