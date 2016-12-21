package chapter9.smallestpath;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Dijkstra算法求单源头最短路径
 * 
 * @author wenl
 *
 */
public class WeightShortPath {
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
		boolean known;

		public Vertex getPath() {
			return path;
		}

		public List<AdjVertex> getAdj() {
			return adj;
		}

		public void setAdj(List<AdjVertex> adj) {
			this.adj = adj;
		}

		public boolean isKnown() {
			return known;
		}

		public void setKnown(boolean known) {
			this.known = known;
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
	 * 著名的dijkstra算法 解决单源最短路径（权无负值）
	 * 
	 * @param s
	 *            起点
	 */
	public void dijkstra(Vertex s) {
		for (Vertex v : graph) {// 初始默认所有顶点未被访问
			v.setDist(Integer.MAX_VALUE);
			v.known = false;
		}
		s.dist = 0;// 声明起点的距离为0
		PriorityQueue<Vertex> priorityQueue = new PriorityQueue<Vertex>();
		// 将s放入优先队列
		priorityQueue.add(s);
		while (!priorityQueue.isEmpty()) {// 知道所有顶点的最短路径都已知并且优先队列为空
			Vertex v = priorityQueue.poll();// 取出未知节点中最短路径最小的节点
			if (v != null) {
				if (!v.known) {
					v.known = true;// 声明该节点已知
					if (v.getAdj() != null && !v.getAdj().isEmpty()) {
						// 如 dv+cvw<=dw 则更新临接点的最短路径，并且更新值放入到优先队列中
						for (AdjVertex adjW : v.getAdj()) {
							if (!adjW.getW().known) {
								if (v.dist + adjW.cvw < adjW.getW().getDist()) {
									adjW.getW().setDist(v.dist + adjW.cvw);
									adjW.getW().setPath(v);
									priorityQueue.add(adjW.getW());
								}
							}
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
		adjV1.add(new AdjVertex(v21, 3));
		adjV1.add(new AdjVertex(v22, 1));
		v1.setAdj(adjV1);
		List<AdjVertex> adjV20 = new ArrayList<AdjVertex>();
		adjV20.add(new AdjVertex(v30, 1));
		adjV20.add(new AdjVertex(v21, 1));
		v20.setAdj(adjV20);
		List<AdjVertex> adjV21 = new ArrayList<AdjVertex>();
		adjV21.add(new AdjVertex(v31, 5));
		v21.setAdj(adjV21);
		List<AdjVertex> adjV22 = new ArrayList<AdjVertex>();
		adjV22.add(new AdjVertex(v32, 2));
		v22.setAdj(adjV22);
		List<AdjVertex> adjV30 = new ArrayList<AdjVertex>();
		List<AdjVertex> adjV31 = new ArrayList<AdjVertex>();
		List<AdjVertex> adjV32 = new ArrayList<AdjVertex>();
		adjV30.add(new AdjVertex(v31, 1));
		adjV31.add(new AdjVertex(v4, 1));
		adjV32.add(new AdjVertex(v31, 1));
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
		WeightShortPath shortPath = new WeightShortPath();
		shortPath.setGraph(graph);
		shortPath.dijkstra(v1);
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
