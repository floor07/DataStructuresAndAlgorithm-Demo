package chapter9.smallestpath;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * 无权最短路径
 * 使用广度优先搜索
 * @author wenl
 */
public class UnweightedShortPath {
	List<Vertex> graph;

	public static class Vertex {
		Integer dist;// 到该节点的路径
		String name;
		List<Vertex> adj;
		Vertex path;// 最短路径上连接到该节点的父节点

		public Vertex getPath() {
			return path;
		}

		public void setPath(Vertex path) {
			this.path = path;
		}

		public List<Vertex> getAdj() {
			return adj;
		}

		public void setAdj(List<Vertex> adj) {
			this.adj = adj;
		}

		public Vertex(String name) {
			super();
			this.name = name;
		}

		public Integer getDist() {
			return dist;
		}

		public void setDist(Integer dist) {
			this.dist = dist;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

/**
  * 无权最短路径
  * 
  * @param s 起点
  */
public void unweight(Vertex s) {
	Queue<Vertex> q = new LinkedList<Vertex>();
	for (Vertex x : graph) {
	 //每个节点的初始最短路径为Integer的最大值，表示未访问
	  x.setDist(Integer.MAX_VALUE);
	}
	s.dist = 0;
	q.add(s);
	while (!q.isEmpty()) {
		Vertex v = q.poll();
	  if (v != null) {
		if (v.getAdj() != null && !v.getAdj().isEmpty()) {
		  for (Vertex w : v.getAdj()) {
			if (w.getDist() == Integer.MAX_VALUE) {//每条边只访问一次
				w.setDist(v.getDist()+1);
				w.setPath(v);
				q.add(w);
			}
		  }
	    }
     }
   }
}
	public static void main(String[] args) {
		List<Vertex> graph=new ArrayList<Vertex>();
		Vertex v1= new Vertex("1");
		Vertex v20=new Vertex("20");
		Vertex v21=new Vertex("21");
		Vertex v22=new Vertex("22");
		Vertex v30=new Vertex("30");
		Vertex v31=new Vertex("31");
		Vertex v32=new Vertex("32");
		Vertex v4= new Vertex("4");
		List<Vertex>adjV1=new ArrayList<Vertex>();
		adjV1.add(v20);
		adjV1.add(v21);
		adjV1.add(v22);
		v1.setAdj(adjV1);
		List<Vertex>adjV20=new ArrayList<Vertex>();
		adjV20.add(v30);
		adjV20.add(v21);
		v20.setAdj(adjV20);
		List<Vertex>adjV21=new ArrayList<Vertex>();
		adjV21.add(v31);
		v21.setAdj(adjV21);
		List<Vertex>adjV22=new ArrayList<Vertex>();
		adjV22.add(v32);
		v22.setAdj(adjV22);
		List<Vertex>adjV30=new ArrayList<Vertex>();
		List<Vertex>adjV31=new ArrayList<Vertex>();
		List<Vertex>adjV32=new ArrayList<Vertex>();
		adjV30.add(v31);
		adjV31.add(v4);
		adjV32.add(v31);
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
		UnweightedShortPath unWeightShortPath=new UnweightedShortPath();
		unWeightShortPath.setGraph(graph);
		unWeightShortPath.unweight(v1);
		for(Vertex v:unWeightShortPath.getGraph()){
			if(v.getPath()!=null){
				System.out.println(v.getPath().getName()+"-"+v.getName()+" cost:"+v.getDist());
			}else{
				System.out.println(v.getName()+"-"+v.getName()+" cost:"+v.getDist());
			}
		}
	}
	public List<Vertex> getGraph() {
		return graph;
	}
	public void setGraph(List<Vertex> graph) {
		this.graph = graph;
	}
	
}
