package algo;

// Arup Guha
// 8/24/2011
// Uses Edmunds-Karp Algorithm to calculate Maximum Flow in a Flow Network.

import java.util.*;

import models.Graph;

class Edge {

	private int capacity;
	private int flow;

	public Edge(int cap) {
		capacity = cap;
		flow = 0;
	}

	public int maxPushForward() {
		return capacity - flow;
	}

	public int maxPushBackward() {
		return flow;
	}

	public boolean pushForward(int moreflow) {

		// We can't push through this much flow.
		if (flow+moreflow > capacity)
			return false;

		// Push through.
		flow += moreflow;
		return true;
	}

	public boolean pushBack(int lessflow) {

		// Not enough to push back on.
		if (flow < lessflow)
			return false;

		flow -= lessflow;
		return true;
	}
}

class direction {

	public int prev;
	public boolean forward;

	public direction(int node, boolean dir) {
		prev = node;
		forward = dir;
	}

	public String toString() {
		int k=prev+1;
		if (forward)
			return "" + k + "->";
		else
			return "" + k + "<-";
	}
}

public class networkflow {
	
	private final static boolean DEBUG = false;
	private final static boolean PRINTPATH = true;

	private Edge[][] adjMat;
	private int source;
	private int dest;
	private String output;
	
	// All positive entries in flows should represent valid flows
	// between vertices. All other entries must be 0 or negative.
	public networkflow(int[][] flows, int start, int end) {

		output="";
		source = start;
		dest = end;
		adjMat = new Edge[flows.length][flows.length];

		for (int i=0; i<flows.length; i++) {
			for (int j=0; j<flows[i].length; j++) {

				// Fill in this flow.
				if (flows[i][j] > 0)
					adjMat[i][j] = new Edge(flows[i][j]);
				else
					adjMat[i][j] = null;
			}
		}
	}

	public ArrayList<direction> findAugmentingPath() {

		// This will store the previous node visited in the BFS.
		direction[] prev = new direction[adjMat.length];
		boolean[] inQueue = new boolean[adjMat.length];
		for (int i=0; i<inQueue.length; i++)
			inQueue[i] = false;

		// The source has no previous node.
		prev[source] = new direction(-1, true);

		LinkedList<Integer> bfs_queue = new LinkedList<Integer>();
		bfs_queue.offer(new Integer(source));
		inQueue[source] = true;

		// Our BFS will go until we clear out the queue.
		while (bfs_queue.size() > 0) {

			// Add all the new neighbors of the current node.
			Integer next = bfs_queue.poll();
			if (DEBUG) System.out.println("Searching " + next);

			// Find all neighbors and add into the queue. These are forward edges.
			for (int i=0; i<adjMat.length; i++)
				if (!inQueue[i] && adjMat[next][i] != null && adjMat[next][i].maxPushForward() > 0) {
					bfs_queue.offer(new Integer(i));
					inQueue[i] = true;
					prev[i] = new direction(next, true);
				}

			// Now look for back edges.
			for (int i=0; i<adjMat.length; i++)
				if (!inQueue[i] && adjMat[i][next] != null && adjMat[i][next].maxPushBackward() > 0) {
					bfs_queue.offer(new Integer(i));
					inQueue[i] = true;
					prev[i] = new direction(next, false);
				}
		}

		// No augmenting path found.
		if (!inQueue[dest])
			return null;

		ArrayList<direction> path = new ArrayList<direction>();

		direction place = prev[dest];

		direction dummy = new direction(dest, true);
		path.add(dummy);

		// Build the path backwards.
		while (place.prev != -1) {
			path.add(place);
			place = prev[place.prev];
		}

		// Reverse it now.
		Collections.reverse(path);

		return path;
	}

	// Run the Max Flow Algorithm here.
	public int getMaxFlow() {

		int flow = 0;

		ArrayList<direction> nextpath = findAugmentingPath();
		output+="Maximum Flow Algorithim..\n";
		if (DEBUG || PRINTPATH) {
			System.out.println("Found one augmenting path.");
			output+="Found one augmenting path.\n";
			for (int i=0; i<nextpath.size(); i++)
				{
				System.out.print(nextpath.get(i)+" ");
				output+=nextpath.get(i)+" ";
				}
			output+="\n";
			System.out.println();
		}
		
		// Loop until there are no more augmenting paths.
		while (nextpath != null) {

			// Check what the best flow through this path is.
			int this_flow = Integer.MAX_VALUE;
			for (int i=0; i<nextpath.size()-1; i++) {

				if (nextpath.get(i).forward) {
					this_flow = Math.min(this_flow, adjMat[nextpath.get(i).prev][nextpath.get(i+1).prev].maxPushForward());
				}
				else {
					this_flow = Math.min(this_flow, adjMat[nextpath.get(i+1).prev][nextpath.get(i).prev].maxPushBackward());
				}
			}

			// Now, put this flow through.
			for (int i=0; i<nextpath.size()-1; i++) {

				if (nextpath.get(i).forward) {
					adjMat[nextpath.get(i).prev][nextpath.get(i+1).prev].pushForward(this_flow);
				}
				else {
					adjMat[nextpath.get(i+1).prev][nextpath.get(i).prev].pushBack(this_flow);
				}
			}

			// Add this flow in and then get the next path.
			if (DEBUG || PRINTPATH) { 
				System.out.println("Adding "+this_flow);
				output+="Adding "+this_flow+"\n";
			}
			flow += this_flow;
			nextpath = findAugmentingPath();
			if (nextpath != null && (DEBUG || PRINTPATH)) {

				System.out.println("Found another augmenting path.");
				output+="Found another augmenting path.\n";
				for (int i=0; i<nextpath.size(); i++) {
					System.out.print(nextpath.get(i)+" ");
					output+=nextpath.get(i)+" ";
				}
				output+="\n";
				System.out.println();
			}

		}

		return flow;
	}
	public String getOutput() {
		return output;
	}
	public static int [][]main(Graph g) {
			System.out.println(g.getEdges().size());
			System.out.println(g.getEdges());

		int[][] graph = new int[g.getEdges().size()][g.getEdges().size()];
		for (int i=0; i<g.getEdges().size(); i++)
		{
			graph[g.getEdges().get(i).getFrom()-1][g.getEdges().get(i).getTo()-1]=g.getEdges().get(i).getWeight();
			graph[g.getEdges().get(i).getTo()-1][g.getEdges().get(i).getFrom()-1]=g.getEdges().get(i).getWeight();

		}
		
		return graph;

	}

}