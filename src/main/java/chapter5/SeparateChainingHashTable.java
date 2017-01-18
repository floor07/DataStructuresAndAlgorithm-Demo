package chapter5;

import java.util.LinkedList;
import java.util.List;
/**
 * 分离散列表发
 * 实现方式
 * 装填因子的概念：散列表中元素个数与该表的大小的比
 * 散列表的大小不重要，装填因子才重要r 
 * 查找时间1+r/2（一般认为 一半的其他节点要被搜索到）
 * 一般法则使表的大小与预期元素个数大致相等。即r=1
 * 平均常数时间插入，删除，查找O(1)
 * java 中认为r=75% 比较理想，大于75%会再散列（再散列O（N）），
 * 也更容易出现分离链表解决散列充分的问题
 * */
public class SeparateChainingHashTable<AnyType> {
	/**
	 * 一个好的HashCode算法示例
	 * A Hash routine for String to hash.
	 * @param key of the String to hash.
	 * @param tableSize the size of the hash table.
	 * @return the hash value
	public static int hash(String key,int tableSize){
		int hashVal=0;
		//hashVal=37*hashVal+charAt(i)
		for(int i=0;i<key.length();i++){
			hashVal=37*hashVal+key.charAt(i);
		}
		hashVal%=tableSize;
		if(hashVal<0){//允许溢出
			hashVal+=tableSize;
		}
		return hashVal;
	}
	 * **/
	private static final int DEFAULT_TABLE_SIZE=101;
	private List<AnyType>[]theLists;
	private int currentSize;
	/**
	 * Construct the hash table
	 * */
	public SeparateChainingHashTable() {
		this(SeparateChainingHashTable.DEFAULT_TABLE_SIZE);
	}
	
	@SuppressWarnings("unchecked")
	public SeparateChainingHashTable(int size) {
		this.theLists=new LinkedList[nextPrime(size)];
		for(int i=0;i<theLists.length;i++){
			this.theLists[i]=new LinkedList<AnyType>();
		}
	}
	/**
	 * Insert into the hash table ,
	 * if the item is already present, then do nothing.
	 * @param x the item to insert.
	 * **/
	public void insert(AnyType x){
		List<AnyType>whichList=this.theLists[this.myHash(x)];
		if(!whichList.contains(x)){
			whichList.add(x);
			
			if(++currentSize>this.theLists.length){
				this.rehash();
			}
		}
	}
	/**
	 * Remove from the hash table
	 * @param x the item to remove
	 * */
	public void remove(AnyType x){
		List<AnyType>whichList=this.theLists[myHash(x)];
		if(whichList.contains(x)){
			whichList.remove(x);
			this.currentSize--;
		}
		
	}
	/**
	 * Find an item in the hash table.
	 * @param x the item in search for.
	 * @return true if x is not found.
	 * */
	public boolean contain(AnyType x){
		List<AnyType>whichList=this.theLists[myHash(x)];
		return whichList.contains(x);
	}
	
	public void makeEmpty(){
		for(int i=0;i<this.theLists.length;i++){
			this.theLists[i].clear();
		}
		this.currentSize=0;
	}
	/**
	 * Rehashing for separate chaining hash table.
	 * **/
	@SuppressWarnings("unchecked")
	private void rehash(){
		List<AnyType>[]oldLists=this.theLists;
		//create new double-sized ,empty table
		this.theLists=new LinkedList[nextPrime(2*this.theLists.length)];
		for(int j=0;j<this.theLists.length;j++){
			this.theLists[j]=new LinkedList<AnyType>();
		}
		//copy table over
		this.currentSize=0;
		for(int i=0;i<oldLists.length;i++){
			for(AnyType item:oldLists[i]){
				insert(item);
			}
		}
	}
	
	private int myHash(AnyType x){
		int hashVal=x.hashCode();
		hashVal%=theLists.length;
		if(hashVal<0){
			hashVal+=theLists.length;
		}
		return hashVal;
	}
	
	private static int nextPrime(int n){
		//生成下一个素数 不做实现
		if(isPrime(2*n)){
			
		}
		return 0;
	}
	private static boolean isPrime(int n){
		// 判断是不是素数，不做实现
		return false;
	}
}
