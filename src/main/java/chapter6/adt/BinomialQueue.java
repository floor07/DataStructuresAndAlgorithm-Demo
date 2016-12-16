package chapter6.adt;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * 二项式堆
 * @author wenl
 * @param <AnyType>
 */
public class BinomialQueue<AnyType extends Comparable<? super AnyType>> {
	
	private int currentSize;
	private Node<AnyType>[]theTrees;
	
	private static class Node<AnyType>{
		AnyType element;
		Node<AnyType>leftChild;
		Node<AnyType>nextSibling;
		public Node(AnyType element) {
			this(element,null,null);
		}
		public Node(AnyType element, Node<AnyType> leftChild, Node<AnyType> nextSibling) {
			super();
			this.element = element;
			this.leftChild = leftChild;
			this.nextSibling = nextSibling;
		}
	}
	
	@SuppressWarnings("unchecked")
	public BinomialQueue(){
		currentSize=0;
		theTrees=(Node<AnyType>[])new Comparable[10];
	}
	@SuppressWarnings("unchecked")
	public BinomialQueue(AnyType item){
		currentSize=1;
		theTrees=((Node<AnyType>[])new Comparable[10]);
		theTrees[0]=new Node<AnyType>(item);
	}
	public int capacity(){
		return (1<<theTrees.length)-1;
	}
	private int findMinIndex(){
		int min=0;
		AnyType item=theTrees[0].element;
		for(int i=0;i<theTrees.length;i++){
			if(item.compareTo(theTrees[i].element)>0){
				item=theTrees[i].element;
				min=i;
			}
		}
		return min;
	}

	public boolean isEmpty(){
		return currentSize==0;
	}
	public void makeEmpty(){
		theTrees=null;
		currentSize=0;
	}
	public void merge(BinomialQueue<AnyType>rhs){
	//TBD
		if(this==rhs){
			return;
		}
		currentSize=currentSize+rhs.currentSize;
		if(currentSize>capacity()){
			int maxlength=Math.max(theTrees.length, rhs.theTrees.length);
			expandTheTrees(maxlength+1);
		}
		//i负责访问根节点，j负责统计实际节点数量
		Node<AnyType>carry=null;
		for(int i=1,j=0;j<currentSize;j=j*2,i++){
			Node<AnyType>thsNode=theTrees[i];
			Node<AnyType>rhsNode=i<rhs.theTrees.length?rhs.theTrees[i]:null;
			int whichCase=thsNode==null?0:1;
			whichCase+=(rhsNode==null?0:2);
			whichCase+=(carry==null?0:4);
			switch(whichCase){
			case 0://没有节点
			case 1://只有this
				break;
			case 2://只有rhs
				theTrees[i]=rhsNode;
				rhs.theTrees[i]=null;
				break;
			case 3://this 和 rhs
				carry=combineTrees(thsNode, rhsNode);
				theTrees[i]=null;
				rhs.theTrees[i]=null;
				break;
			case 4://carry
				theTrees[i]=carry;
				carry=null;
				break;
			case 5://carry和this
				carry=combineTrees(thsNode, carry);
				theTrees[i]=null;
				break;
			case 6://rhsNode和carry
				carry=combineTrees(rhsNode, carry);
				rhs.theTrees[i]=null;
				break;
			case 7://rhsNode,this,carry
				theTrees[i]=carry;
				carry=combineTrees(thsNode, rhsNode);
				theTrees[i]=null;
				rhs.theTrees[i]=null;
			default:
				break;
			}
		}
		for(int k=0;k<rhs.theTrees.length;k++){
			rhs.theTrees[k]=null;
		}
		rhs.currentSize=0;
	}
	public AnyType deleteMin(){
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		int minIndex=findMinIndex();
		AnyType minItem=theTrees[minIndex].element;
		//构建H"和H'
		Node<AnyType>deletedTree=theTrees[minIndex].leftChild;
		//H"
		BinomialQueue<AnyType>deletedQueue=new BinomialQueue<AnyType>();
		deletedQueue.expandTheTrees(minIndex+1);
		deletedQueue.currentSize=(1<<minIndex)-1;
		for(int j=minIndex-1;j>=0;j--){
			deletedQueue.theTrees[j]=deletedTree;
			deletedQueue.theTrees[j].nextSibling=null;
			deletedTree=deletedTree.nextSibling;
		}
		//H'
		theTrees[minIndex]=null;
		currentSize-=deletedQueue.currentSize+1;
		//合并
		merge(deletedQueue);
		return minItem;
	}
	private void expandTheTrees(int i) {
		theTrees=Arrays.copyOf(theTrees, i);
	}
	private Node<AnyType>combineTrees(Node<AnyType>t1,Node<AnyType>t2){
		if(t1.element.compareTo(t2.element)<0){
			return combineTrees(t2,t1);
		}
		t2.nextSibling=t1.leftChild;
		t1.leftChild=t2;
		return t1;
	}
	
}
