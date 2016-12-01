package chapter8;

/**
 * 不相交集合
 * @author wenlei
 *
 */
public class DisjSets {
	private Integer[] s;

	public DisjSets(int numElements) {
		s = new Integer[numElements];
		for (int i = 0; i < s.length; i++) {
			s[i]=-1;
		}
	}

	/**
	 * 采用按秩求并
	 * @param root1 不相交集合1的根
	 * @param root2 不相交集合2的根
	 */
	public void union(int root1, int root2) {
		if(s[root2]<s[root1]){
			s[root1]=root2;
		}else{
			if(s[root1]==s[root2]){
				s[root1]--;
			}
			s[root2]=root1;
		}
	}

	/**
	 * 查找方式 ：路径压缩，没有错误检查 
	 * @param x 要查找的元素
	 * @return x所属集合的根的值
	 */
	public int find(int x) {
		if (s[x] < 0) {
			return x;
		} else {
			return s[x] = find(s[x]);
		}
	}
}
