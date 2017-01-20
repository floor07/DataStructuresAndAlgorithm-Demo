package chapter9.mst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 利用不相交集，无向图的解决最小生成树
 * 
 * @author wenl
 */
public class Kruskal {
	Map<Integer, Vertex> graph;

public List<Edge> kruskal() {
	List<Edge> result = new ArrayList<Edge>();
	int vertexSize = graph.values().size();
	int acceptedEdge = 0;
	//以点的数量构建不相交集合
	DisjSets disjSets = new DisjSets(vertexSize);
	//以边的权构建堆
	PriorityQueue<Edge> priorityQueue = new PriorityQueue<Edge>(getEdges());
	while (acceptedEdge < vertexSize - 1&&!priorityQueue.isEmpty()) {
		Edge e = priorityQueue.poll();
		int disv = disjSets.find(e.vnum);
		int disw = disjSets.find(e.wnum);
		if (disv != disw) {
			//两个顶点不在一颗树上，合并两个顶点
				acceptedEdge++;
				disjSets.union(disv, disw);
				result.add(e);
			}
	}
	return result;
}

	public Map<Integer, Vertex> getGraph() {
		return graph;
	}

	public void setGraph(Map<Integer, Vertex> graph) {
		this.graph = graph;
	}

	public List<Edge> getEdges() {
		List<Edge> edges = new ArrayList<Edge>();
		for (Vertex v : graph.values()) {
			if (v.adj != null && !v.adj.isEmpty()) {
				for (AdjVertex adjw : v.adj) {
					Edge edge = new Edge(v.num, adjw.w.num, adjw.cvw);
					edges.add(edge);
				}
			}
		}
		return edges;
	}

	public static class Edge implements Comparable<Edge> {
		Integer vnum;// 从0开始
		Integer wnum;
		Integer cvw;

		@Override
		public String toString() {
			return "Edge [vnum=" + vnum + ", wnum=" + wnum + ", cvw=" + cvw + "]";
		}

		public Edge(Integer vid, Integer wid, Integer cvw) {
			this.vnum = vid;
			this.wnum = wid;
			this.cvw = cvw;
		}

		@Override
		public int compareTo(Edge o) {
			return this.cvw.compareTo(o.cvw);
		}

	}

	public static class Vertex {
		String name;
		Integer num;
		List<AdjVertex> adj;
		boolean know;

		public Vertex(String name, Integer num) {
			this.name = name;
			this.num = num;
		}

		public List<AdjVertex> getAdj() {
			return adj;
		}

		public void setAdj(List<AdjVertex> adj) {
			this.adj = adj;
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

	public static void main(String[] args) {
		Map<Integer, Vertex> graph = new HashMap<Integer, Vertex>();
		Vertex v1 = new Vertex("1", 0);
		Vertex v20 = new Vertex("20", 1);
		Vertex v21 = new Vertex("21", 2);
		Vertex v22 = new Vertex("22", 3);
		Vertex v30 = new Vertex("30", 4);
		Vertex v31 = new Vertex("31", 5);
		Vertex v32 = new Vertex("32", 6);
		Vertex v4 = new Vertex("4", 7);
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
		graph.put(v1.num, v1);
		graph.put(v20.num, v20);
		graph.put(v21.num, v21);
		graph.put(v22.num, v22);
		graph.put(v30.num, v30);
		graph.put(v31.num, v31);
		graph.put(v32.num, v32);
		graph.put(v4.num, v4);
		Kruskal kruskal = new Kruskal();
		kruskal.setGraph(graph);
		PriorityQueue<Edge> priorityQueue = new PriorityQueue<Edge>(kruskal.getEdges());
		while (!priorityQueue.isEmpty()) {
			Edge e=priorityQueue.poll();
			System.out.println(graph.get(e.vnum).name + ":" + graph.get(e.wnum).name + " weight:" + e.cvw);
		}
		System.out.println("================================================");
		List<Edge> edge = kruskal.kruskal();
		System.out.println("================================================");
		for (Edge e : edge) {
			System.out.println(graph.get(e.vnum).name + ":" + graph.get(e.wnum).name + " weight:" + e.cvw);
		}
	}
}
