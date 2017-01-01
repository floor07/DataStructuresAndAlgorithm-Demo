package chapter9.maxflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 * DAG(有向无圈赋权图)最大网络流
 * @author wenl
 */
public class MaxWebFlow {
	
	List<Vertex> graph;//初始为原图，后为剩余图
	Map<String, Vertex> maxFlowGraph;//最大网络流结果图

	public static class Vertex implements Comparable<Vertex> {
		Integer dist;// 到该节点的路径
		String name;//节点名称，唯一标识
		Map<Vertex, Integer> adj=new HashMap<Vertex, Integer>();//临接表
		Vertex path;// 最短路径上连接到该节点的父节点
		boolean known;//是否找到最短路径

		@Override
		public String toString() {
			return "Vertex [name=" + name + ", adj=" + adj + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Vertex other = (Vertex) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		public Map<Vertex, Integer> getAdj() {
			return adj;
		}

		public void setAdj(Map<Vertex, Integer> adj) {
			this.adj = adj;
		}

		public Vertex getPath() {
			return path;
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
	 * 修改后的dijkstra，计算从起点到终点的最短路径，
	 * 与dijkstra算法一致，仅当终点的最短路径已知后就结束
	 * @param start 开始节点
	 * @param end   结束节点
	 */
	private void dijkstra(Vertex start, Vertex end) {
		for (Vertex v : graph) {
			v.setDist(Integer.MAX_VALUE);
			v.known = false;
		}
		start.dist = 0;
		PriorityQueue<Vertex> priorityQueue = new PriorityQueue<Vertex>();
		priorityQueue.add(start);
		while (!priorityQueue.isEmpty()) {
			Vertex v = priorityQueue.poll();
			if (v != null) {
				if (!v.known) {
					v.known = true;
					if (v.getAdj() != null && !v.getAdj().isEmpty()) {
						for (Entry<Vertex, Integer> adjEntry : v.getAdj().entrySet()) {
							Vertex w = adjEntry.getKey();
							Integer cvw = adjEntry.getValue();
							if (!w.known) {
								if (v.dist + cvw < w.getDist()) {
									w.setDist(v.dist + cvw);
									w.setPath(v);
									priorityQueue.add(w);

								}
							}
						}
					}
				} else {
					if (v.equals(end)) {
						return;
					}
				}
			}
		}
	}

	/**
	 * 获取从起点到终点的最大网络流
	 * @param start 起点
	 * @param end   终点
	 * @return 从起点到终点的最大网络流
	 */
	public  Integer getMaxFlow(Vertex start,Vertex end){
		int maxFlow=0;
		while (true) {
			dijkstra(start, end);//找到从起点到终点的最短路径
			if(end.dist==Integer.MAX_VALUE){
				break;
			}
			printPath(end);//打印路径便于观察
			int currentMaxFlow = getCurrentMaxFlow(end);//得到当前路径的最大网络流
			//修建原图
			changeGraphWeightAndSetMaxFlow(end, currentMaxFlow, maxFlowGraph);
			maxFlow+=currentMaxFlow;//记录最大网络流
			//打印当前最大网络流图，以便追踪是否正确
			System.out.println();
			for (Vertex v : maxFlowGraph.values()) {
				System.out.println(v);
			}
		}
		return maxFlow;
	}
	/**
	 * 获取当前最短路径的最大流
	 * @param end 终点
	 * @return 从起点到终点的当前最大流
	 */
	private  Integer getCurrentMaxFlow(Vertex end) {
		if (end.getPath() != null) {
			Integer parentcwv = 0;
			if (end.getPath() != null) {
				parentcwv = getCurrentMaxFlow(end.getPath());
			}
			Integer cwv = end.getPath().getAdj().get(end);
			return Math.min(cwv, parentcwv);
		} else {
			return Integer.MAX_VALUE;
		}

	}

	/**
	 * 为网络流图赋值，修剪原图
	 * 如果原图中有一条边修剪后权为0，去掉该边
	 * @param end 终点
	 * @param currentMaxFlow 当前最大流
	 * @param maxFlowGraph 网络流结果图
	 */
	public  void changeGraphWeightAndSetMaxFlow(Vertex end, Integer currentMaxFlow, Map<String, Vertex> maxFlowGraph) {
		if (end.getPath() != null) {
			int oldCwv = end.getPath().getAdj().get(end);
			if (oldCwv - currentMaxFlow > 0) {
				end.getPath().getAdj().put(end, oldCwv - currentMaxFlow);
			} else if (oldCwv - currentMaxFlow == 0) {
				end.getPath().getAdj().remove(end);
			}
			String startName=end.getPath().name;
			Integer oldcvw=maxFlowGraph.get(startName).adj.get(maxFlowGraph.get(end.name));
			maxFlowGraph.get(startName).adj.put(maxFlowGraph.get(end.name), oldcvw+currentMaxFlow);
			maxFlowGraph.get(end.name).setPath(maxFlowGraph.get(startName));
			changeGraphWeightAndSetMaxFlow(end.getPath(), currentMaxFlow, maxFlowGraph);
		}
	}

	/**
	 * 打印从起点到终点的路径
	 * @param end
	 */
	private  void printPath(Vertex end) {
		if (end.getPath() != null) {
			printPath(end.getPath());
			System.out.print(" to ");
		}
		System.out.print(end.getName());
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
		Map<Vertex, Integer> adjV1 = new HashMap<Vertex, Integer>();

		adjV1.put(v20, 1);
		adjV1.put(v21, 3);
		adjV1.put(v22, 1);
		v1.setAdj(adjV1);
		Map<Vertex, Integer> adjV20 = new HashMap<Vertex, Integer>();
		adjV20.put(v30, 1);
		adjV20.put(v21, 1);
		v20.setAdj(adjV20);
		Map<Vertex, Integer> adjV21 = new HashMap<Vertex, Integer>();
		adjV21.put(v31, 5);
		v21.setAdj(adjV21);
		Map<Vertex, Integer> adjV22 = new HashMap<Vertex, Integer>();
		adjV22.put(v32, 2);
		v22.setAdj(adjV22);
		Map<Vertex, Integer> adjV30 = new HashMap<Vertex, Integer>();
		Map<Vertex, Integer> adjV31 = new HashMap<Vertex, Integer>();
		Map<Vertex, Integer> adjV32 = new HashMap<Vertex, Integer>();
		adjV30.put(v31, 1);
		adjV31.put(v4, 1);
		adjV32.put(v31, 1);
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
		MaxWebFlow maxWebFlow = new MaxWebFlow();
		maxWebFlow.setGraph(graph);
		// 找到从起始点到终点的一个最短路径
		Vertex start=v1;
		Vertex end=v21;
		System.out.println("找到最大流:"+maxWebFlow.getMaxFlow(start, end));
	}
	
	public List<Vertex> getGraph() {
		return graph;
	}

	public void setGraph(List<Vertex> graph) {
		this.graph = graph;
		this.maxFlowGraph = new HashMap<String, Vertex>();
		for (Vertex v : graph) {
			Vertex newv = new Vertex(v.getName());
			if (v.getAdj() != null && !v.getAdj().isEmpty()) {
				Map<Vertex, Integer> newAdj = new HashMap<Vertex, Integer>();
				for (Vertex w : v.getAdj().keySet()) {
					newAdj.put(new Vertex(w.getName()), 0);
				}
				newv.setAdj(newAdj);
			}
			this.maxFlowGraph.put(newv.name, newv);
		}
	}
}
