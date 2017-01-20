package chapter9.mst;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;




/**
 * Prim算法解决最小生成树 无向图
 * @author Administrator
 */
public class Prim {
	public List<Vertex> graph;
	
	public void prim(Vertex start){
		//初始声明所有顶点均不在树上
		for(Vertex v:graph){
			v.isInTree=false;
			v.dist=Integer.MAX_VALUE;
		}
		start.dist = 0;// 声明起点的距离为0
		//以顶点的最短距离构建堆
		PriorityQueue<Vertex> priorityQueue = new PriorityQueue<Vertex>();
		priorityQueue.add(start);
		while(!priorityQueue.isEmpty()){
			Vertex v=priorityQueue.poll();
			if(v!=null){
				if(!v.isInTree){//取出的顶点不在树上
					//1. 声明顶点在树上
					v.isInTree=true;
					if(v.adj!=null&&!v.adj.isEmpty()){
						for(AdjVertex adjw:v.adj){
							//更新临接表中 最短距离有变化的顶点，并将该顶点加入到优先队列
							if(adjw.cvw<adjw.w.dist){
								adjw.w.setDist(adjw.cvw);
								adjw.w.setPath(v);
								priorityQueue.add(adjw.w);
							}
						}
					}
				}
			}
		}
	}
	
	public static class AdjVertex {
		Vertex w;
		public int cvw;// vw边的权

		public AdjVertex(Vertex w, int cvw) {
			super();
			this.w = w;
			this.cvw = cvw;
		}

		public Vertex getW() {
			return w;
		}

		public void setW(Vertex w) {
			this.w = w;
		}

		public int getCvw() {
			return cvw;
		}

		public void setCvw(int cvw) {
			this.cvw = cvw;
		}

	}

	public static class Vertex implements Comparable<Vertex> {
		Integer dist;// 最小生产树对应边的权
		String name;
		List<AdjVertex> adj;
		Vertex path;// 最短路径上连接到该节点的父节点
		boolean isInTree;

		public Vertex getPath() {
			return path;
		}

		public List<AdjVertex> getAdj() {
			return adj;
		}

		public void setAdj(List<AdjVertex> adj) {
			this.adj = adj;
		}

		public boolean isInTree() {
			return isInTree;
		}

		public void setInTree(boolean isInTree) {
			this.isInTree = isInTree;
		}

		public void setPath(Vertex path) {
			this.path = path;
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

		@Override
		public int compareTo(Vertex o) {
			return this.dist.compareTo(o.dist);
		}

	}
	public static void main(String[] args) {
		List<Vertex> graph = new ArrayList<Vertex>();
		Vertex v1 = new Vertex("1");
		Vertex v20 = new Vertex("20");
		Vertex v21 = new Vertex("21");
		Vertex v22 = new Vertex("22");
		Vertex v30 = new Vertex("30");
		Vertex v31 = new Vertex("31");
		Vertex v32 = new Vertex("32");
		Vertex v4 = new Vertex("4");
		List<AdjVertex> adjV1 = new ArrayList<AdjVertex>();
		adjV1.add(new AdjVertex(v20, 1));
		adjV1.add(new AdjVertex(v21, 3));
		adjV1.add(new AdjVertex(v22, 1));
		v1.setAdj(adjV1);
		List<AdjVertex> adjV20 = new ArrayList<AdjVertex>();
		adjV20.add(new AdjVertex(v30, 1));
		adjV20.add(new AdjVertex(v21, 1));
		adjV20.add(new AdjVertex(v1, 1));
		v20.setAdj(adjV20);
		List<AdjVertex> adjV21 = new ArrayList<AdjVertex>();
		adjV21.add(new AdjVertex(v31, 5));
		adjV21.add(new AdjVertex(v20, 1));
		adjV21.add(new AdjVertex(v1, 3));
		v21.setAdj(adjV21);
		List<AdjVertex> adjV22 = new ArrayList<AdjVertex>();
		adjV22.add(new AdjVertex(v32, 2));
		adjV22.add(new AdjVertex(v1, 1));
		v22.setAdj(adjV22);
		List<AdjVertex> adjV30 = new ArrayList<AdjVertex>();
		List<AdjVertex> adjV31 = new ArrayList<AdjVertex>();
		List<AdjVertex> adjV32 = new ArrayList<AdjVertex>();
		adjV30.add(new AdjVertex(v31, 1));
		adjV30.add(new AdjVertex(v20, 1));
		
		adjV31.add(new AdjVertex(v4, 1));
		adjV31.add(new AdjVertex(v30, 1));
		adjV31.add(new AdjVertex(v21, 5));
		adjV31.add(new AdjVertex(v32, 1));
		
		adjV32.add(new AdjVertex(v31, 1));
		adjV32.add(new AdjVertex(v22, 2));
		
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
		Prim prim = new Prim();
		prim.setGraph(graph);
		prim.prim(v1);
		for (Vertex v : prim.getGraph()) {
			if (v.getPath() != null) {
				System.out.println(v.getPath().getName() + " son：" + v.getName() + " weight:" + v.getDist());
			} else {
				System.out.println( v.getName() + " weight:" + v.getDist());
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
