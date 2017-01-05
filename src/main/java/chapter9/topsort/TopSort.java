package chapter9.topsort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * topsort示例
 * @author wenl
 */
public class TopSort {
	/**
	 * 需要排序的图
	 */
	List<Vertex> graph;
	
	 public TopSort(List<Vertex> graph) {
		super();
		this.graph = graph;
	}
	 /**
	   *以临接表方式标示图中的点，包含入度
	   *  入度，即有多少条边指向该节点
		*/
	static class Vertex {
		
		int topNum;// top排序号码
		int indegree;// 入度，即有多少条边指向该节点
		String name;// 节点名称
		List<Vertex>adj;
		public Vertex( int indegree, String name) {
			super();
			this.indegree = indegree;
			this.name = name;
		}

		public List<Vertex> getAdj() {
			return adj;
		}

		public void setAdj(List<Vertex> adj) {
			this.adj = adj;
		}

		public int getTopNum() {
			return topNum;
		}

		public void setTopNum(int topNum) {
			this.topNum = topNum;
		}

		public int getIndegree() {
			return indegree;
		}

		public void setIndegree(int indegree) {
			this.indegree = indegree;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
	public static class CyclicFoundException extends Exception{
		//存在圈抛出这个异常
		private static final long serialVersionUID = 4613306058846566702L;
	}
	/**
	 * topSort示例，每条边只访问1次 ,O(|E|+|V|)
	 * @throws CyclicFoundException
	 */
	public void topSort()throws CyclicFoundException{
		Queue<Vertex>q=new LinkedList<Vertex>();
		int counter=0;
		for(Vertex v:graph){
			if(v.indegree==0){
				q.add(v);
			}
		}
		while(!q.isEmpty()){
			Vertex v=q.poll();
			v.topNum=++counter;//编号
			if(v!=null){
				if(v.getAdj()!=null&&!v.getAdj().isEmpty()){
					for(Vertex w:v.getAdj()){//每条边只访问一次
						if(--w.indegree==0){
							q.add(w);
						}
					}
				}
			}
		}
		if(counter!=graph.size()){//编号与点集合大小不同存在圈
			throw new CyclicFoundException();
		}
	}
	public static void main(String[] args) throws CyclicFoundException {
		List<Vertex> graph=new ArrayList<Vertex>();
		Vertex v1=new Vertex(0,"1");
		Vertex v20=new Vertex(1,"20");
		Vertex v21=new Vertex(1,"21");
		Vertex v22=new Vertex(1,"22");
		Vertex v30=new Vertex(2,"30");
		Vertex v31=new Vertex(1,"31");
		Vertex v32=new Vertex(2,"32");
		Vertex v4=new Vertex(3,"4");
		List<Vertex>adjV1=new ArrayList<Vertex>();
		adjV1.add(v20);
		adjV1.add(v21);
		adjV1.add(v22);
		v1.setAdj(adjV1);
		List<Vertex>adjV20=new ArrayList<Vertex>();
		adjV20.add(v30);
		adjV20.add(v32);
		v20.setAdj(adjV20);
		List<Vertex>adjV21=new ArrayList<Vertex>();
		adjV21.add(v30);
		adjV21.add(v31);
		v21.setAdj(adjV21);
		List<Vertex>adjV22=new ArrayList<Vertex>();
		adjV22.add(v32);
		v22.setAdj(adjV22);
		List<Vertex>adjV30=new ArrayList<Vertex>();
		List<Vertex>adjV31=new ArrayList<Vertex>();
		List<Vertex>adjV32=new ArrayList<Vertex>();
		adjV30.add(v4);
		adjV31.add(v4);
		adjV32.add(v4);
		v30.setAdj(adjV30);
		v31.setAdj(adjV31);
		v32.setAdj(adjV32);
		//
		graph.add(v1);
		graph.add(v30);
		graph.add(v31);
		graph.add(v32);
		graph.add(v20);
		graph.add(v21);
		graph.add(v22);
		graph.add(v4);
		TopSort topSort=new TopSort(graph);
		topSort.topSort();
		for(Vertex v:topSort.graph){
			System.out.println(v.topNum+":"+v.name);
		}
	}
}
