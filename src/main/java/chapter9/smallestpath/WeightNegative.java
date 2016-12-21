package chapter9.smallestpath;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * 解决带有负值得图
 * 
 * @author Administrator
 */
public class WeightNegative {

	public List<Vertex> graph;

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
		Integer dist;// 到该节点的路径
		String name;
		List<AdjVertex> adj;
		Vertex path;// 最短路径上连接到该节点的父节点
		boolean isInQueue;

		public boolean isInQueue() {
			return isInQueue;
		}

		public void setInQueue(boolean isInQueue) {
			this.isInQueue = isInQueue;
		}

		public Vertex getPath() {
			return path;
		}

		public List<AdjVertex> getAdj() {
			return adj;
		}

		public void setAdj(List<AdjVertex> adj) {
			this.adj = adj;
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

	/**
	 * 有权有负值最短路径
	 * 借助广度优先搜素
	 * @param s 起点
	 */
	public void weightNegative(Vertex s) {
		Queue<Vertex> q = new LinkedList<Vertex>();
		for (Vertex v : graph) {
			v.dist = Integer.MAX_VALUE;
			v.isInQueue = false;
		}
		s.dist = 0;
		s.isInQueue = true;
		q.add(s);
		while (!q.isEmpty()) {
			Vertex v = q.poll();
			v.isInQueue = false;
			if (v.getAdj() != null && !v.getAdj().isEmpty()) {
				for (AdjVertex wadj : v.getAdj()) {
					if (wadj.cvw + v.dist < wadj.getW().dist) {
						wadj.getW().setDist(wadj.cvw + v.dist);
						wadj.getW().setPath(v);
						if (!wadj.getW().isInQueue) {
							wadj.getW().isInQueue = false;
							q.add(wadj.getW());
						}
					}
				}
			}
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
		adjV1.add(new AdjVertex(v21, -2));
		adjV1.add(new AdjVertex(v22, 1));
		v1.setAdj(adjV1);
		List<AdjVertex> adjV20 = new ArrayList<AdjVertex>();
		adjV20.add(new AdjVertex(v30, -1));
		adjV20.add(new AdjVertex(v21, 1));
		v20.setAdj(adjV20);
		List<AdjVertex> adjV21 = new ArrayList<AdjVertex>();
		adjV21.add(new AdjVertex(v31, 5));
		v21.setAdj(adjV21);
		List<AdjVertex> adjV22 = new ArrayList<AdjVertex>();
		adjV22.add(new AdjVertex(v32, -2));
		v22.setAdj(adjV22);
		List<AdjVertex> adjV30 = new ArrayList<AdjVertex>();
		List<AdjVertex> adjV31 = new ArrayList<AdjVertex>();
		List<AdjVertex> adjV32 = new ArrayList<AdjVertex>();
		adjV30.add(new AdjVertex(v31, 1));
		adjV31.add(new AdjVertex(v4, 1));
		adjV32.add(new AdjVertex(v31, 2));
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
		WeightNegative shortPath = new WeightNegative();
		shortPath.setGraph(graph);
		shortPath.weightNegative(v1);
		for (Vertex v : shortPath.getGraph()) {
			if (v.getPath() != null) {
				System.out.println(v.getPath().getName() + "-" + v.getName() + " cost:" + v.getDist());
			} else {
				System.out.println(v.getName() + "-" + v.getName() + " cost:" + v.getDist());
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
