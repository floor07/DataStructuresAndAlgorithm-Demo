package chapter5;
/**
 * 平方探测hash表(开放定址法)
 * 如果使用平方探测，且表的大小是素数，
 * 那么当表至少有一半是空的时候，总能够插入一个新的元素
 * 其装填因子应该低于r=0.5
 * */
public class QuadraticProbingHashTable<AnyType> {
	private static final int DEFAULT_TABLE_SIZE=11;
	private HashEntry<AnyType>[]array;//the array of elements.
	private int currentSize;
	
	
	private static class HashEntry<AnyType>{
		public AnyType elements;//the element
		public boolean isActive;//false if marked deleted
		
		public HashEntry(AnyType e,boolean i){
			this.elements=e;
			this.isActive=i;
		}
	}
	/**
	 * Construct the hash table.
	 * */
	public QuadraticProbingHashTable(){
		this(QuadraticProbingHashTable.DEFAULT_TABLE_SIZE);
	}
	/**
	 * Construct the hash table.
	 * @param size the approximate initial size
	 * */
	public QuadraticProbingHashTable(int size){
		this.allocateArray(size);
		this.makeEmpty();
	}
	/**
	 * Make the hash table logically empty
	 * */
	public void makeEmpty(){
		currentSize=0;
		for(int i=0;i<array.length;i++){
			array[i]=null;
		}
	}
	/**
	 * Find an item in the hash table
	 * @param x the item to search for
	 * @return the matching item
	 * */
	public boolean contains(AnyType x){
		int currentPos=this.findPos(x);
		return this.isActive(currentPos);
	}
	/**
	 * Insert into the hash table. if the item is
	 * already present,do nothing
	 * @param x the item to insert
	 * */
	public void insert(AnyType x){
		int currentPos=this.findPos(x);
		if(isActive(currentPos)){
			return;
		}
		array[currentPos]=new HashEntry<AnyType>(x,true);
		if(++currentSize>array.length/2){
			this.rehash();
		}
	}
	/**
	 * Remove from the hash table
	 * @param x the item to remove
	 * */
	public void remove(AnyType x){
		int currentPos=this.findPos(x);
		if(this.isActive(currentPos)){
			array[currentPos].isActive=false;
			this.currentSize--;
		}
	}
	/**
	 * Internal method to allocate array
	 * @param arraySize the size of the array.
	 * */
	@SuppressWarnings("unchecked")
	private void allocateArray(int arraySize){
		this.array=new HashEntry[arraySize];
	}
	/**
	 * Return true if currentPos exists and isActive
	 * @param currentPos the result of a call to findPos
	 * @return true id currentPos is active
	 * **/
	private boolean isActive(int currentPos){
		return array[currentPos]!=null&&array[currentPos].isActive;
	}
	/**
	 * Method that performs quadratic probing resolution
	 * @param x the item to search for.
	 * @return the position where the search terminates
	 * **/
	private int findPos(AnyType x){
		int offset=1;
		int currentPos=myHash(x);
		while(array[currentPos]!=null&&!array[currentPos].elements.equals(x)){
			currentPos+=offset;
			offset+=2;
			if(currentPos>=this.array.length){
				currentPos-=array.length;
			}
		}
		return currentPos;
	}
	/**
	 * Rehashing for quadratic probing hash table
	 * */
	private void rehash(){
		HashEntry<AnyType>[]oldArray=this.array;
		this.allocateArray(nextPrine(this.array.length*2));
		this.currentSize=0;
		for(int i=0;i<oldArray.length;i++){
			if(oldArray[i]!=null&&oldArray[i].isActive){
				this.insert(oldArray[i].elements);
			}
		}
	}
	
	private int myHash(AnyType x){
		/**
		 * 生成Hash Val 本例不实现 
		 * 下面的实现是 允许溢出的
		 * */
		int hashVal=x.hashCode()%this.array.length;
		return hashVal>0?hashVal:(hashVal+this.array.length);
	}
	private static int nextPrine(int n){
		//本例不实现
		if(isPrime(n*2+1)){
			
		}
		return 0;
	}
	private static boolean isPrime(int n){
		//判断是否是素数
		//不实现
		return false;
	}
	public boolean isEmpty() {
		return this.currentSize==0;
	}
	public AnyType find(AnyType x){
		int currentPos=this.findPos(x);
		return isActive(currentPos)?array[currentPos].elements:null;
	}
}
