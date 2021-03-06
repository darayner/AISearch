import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class IDS {

private LinkedList<Node>[][] nodeAdjList;
private int expandedNodes = 0;
private int maxNodesHeld = 0;
private int tempMaxNodes = 0;

	public int getExpandedNodes() {
		return expandedNodes;
	}

	public int getmaxNodesHeld() {
		return maxNodesHeld;
	}

	public IDS(LinkedList<Node>[][] nodeAdjList){
		this.nodeAdjList = nodeAdjList;

	}
	/*
	 * Recursively check each depth.
	 * Use BFS to check visit adjacent nodes at depth.
	 * If goal found return true, otherwise depth reached (false).
	 * 
	 */
	public boolean depthLimSearch(Node current, Node goal, int depth, boolean[][] visited, List<Node> path){
		visited[current.getRow()][current.getCol()] = true; // mark each visited node
		if(current == goal){
			return true;
		}
		
		if(depth <= 0){
			return false;
		}
		expandedNodes += 1;
		for(Node adjToCurrent : nodeAdjList[current.getRow()][current.getCol()]){ //visit all adjacent nodes
			if(adjToCurrent.getValue() == 0 || visited[adjToCurrent.getRow()][adjToCurrent.getCol()]){
				continue;
			}
			else if(depthLimSearch(adjToCurrent, goal, depth - 1, visited, path)){ // call stack decrement depth
				adjToCurrent.setParent(current);
				tempMaxNodes += 1;
				return true;
			}
		}
		return false;
	}
	
	/*
	 * IDS search:
	 * Call depth limited search to go up to depth.
	 * Gradually increment depth if goal not found at depth.
	 * Returns path.
	 */
	public List<Node> idSearch(Node start, Node goal, double timeout){
		int depth = 0;
		List<Node> path = new ArrayList<>();
		int maxDepth = nodeAdjList.length * nodeAdjList[0].length;
		while(true){ // until goal is found keep incrementing depth
			boolean[][] visited = new boolean[nodeAdjList.length][nodeAdjList[0].length]; // keep track of visited (no cycles)
			
			if(System.currentTimeMillis() >= timeout){ // timeout
				System.out.println("Exceeded 3 minute time out!");
				System.exit(0);
			}
			
			if(depthLimSearch(start, goal, depth, visited, path)){ // goal found
				path.add(goal);
				while(goal != start){
					goal = goal.getParent();
					path.add(goal);
				}
				Collections.reverse(path);
				if(tempMaxNodes > maxNodesHeld){
					maxNodesHeld = tempMaxNodes;
				}
				break;
			}
			else if(depth >= maxDepth) {
				if(tempMaxNodes > maxNodesHeld){
					maxNodesHeld = tempMaxNodes;
				}
				break;
			}
			else{
				depth += 1; // increment depth
				if(tempMaxNodes > maxNodesHeld){
					maxNodesHeld = tempMaxNodes;
				}
				tempMaxNodes = 0;
			}
		}
		return path;
	}
	
	
}
