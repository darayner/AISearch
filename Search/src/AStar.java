import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStar {
	
	private LinkedList<Node>[][] nodeAdjList; // adjacency node list
	private int expandedNodes = 0;
	private int maxNodesHeld = 0;

	public int getExpandedNodes() {
		return expandedNodes;
	}

	public int getmaxNodesHeld() {
		return maxNodesHeld;
	}
	
	public AStar(LinkedList<Node>[][] nodeAdjList){
		this.nodeAdjList = nodeAdjList;

	}
	
	/*
	 * A* search:
	 * Uses priority queue to sort nodes based on f values.
	 * Computes H, G, and F values of each node.
	 * Checks to see if a node has already been visited
	 * Finds goal node and returns shortest path.
	 * 
	 */
	public List<Node> aStarSearch(Node start, Node goal){
		boolean[][] visited = new boolean[nodeAdjList.length][nodeAdjList[0].length];
		PriorityQueue<Node> queue = new PriorityQueue<Node>(new Node());
		List<Node> path = new ArrayList<>();
		
		queue.add(start); // add starting node f = 0
		start.setHValue(computeManhDist(start, goal)); // set H value
		
		Node current;
		while(queue.size() != 0){
			current = queue.poll();
			visited[current.getRow()][current.getCol()] = true;
			
			if(current == goal){
				 path.add(current);
				  while(current != start){
					  current = current.getParent();
					  path.add(current);
				  }
				 Collections.reverse(path); 
				 break;
			}
			expandedNodes += 1;
			//for each child compute f and g values
			for(Node adjToCurrent : nodeAdjList[current.getRow()][current.getCol()]) {
				if(adjToCurrent.getValue() == 0){
					continue;
				}
				int hValue = computeManhDist(adjToCurrent, goal);
				int gValue = current.getGValue() + adjToCurrent.getValue();
				int fValue = gValue + hValue;
				
				if(visited[adjToCurrent.getRow()][adjToCurrent.getCol()] && (fValue >= adjToCurrent.getFValue())){
					continue;
				}
				if(checkQueue(queue, adjToCurrent, fValue)){
					continue;
				}
				adjToCurrent.setHValue(hValue);
				adjToCurrent.setGValue(gValue);
				adjToCurrent.setFValue(fValue);
				
				adjToCurrent.setParent(current);
				queue.add(adjToCurrent);
			}
		}
		return path;
	}
	/*
	 * Checks priority queue (if adjacent node's f value 
	 * is greater than the already visited node return true.
	 */
	public boolean checkQueue(PriorityQueue<Node> queue, Node adjToCurrent, int fValue){
		for(Node temp: queue){
			if((temp == adjToCurrent) && (fValue >= temp.getFValue())){
				return true;
			}
		}
		return false;
	}
	/*
	 * Compute Manhattan distance to apply to nodes.
	 * Distance from current node to goal. 
	 * Example: 
	 * S--
	 * --G  
	 * Start is 2 horizontal (right) moves away goal and 1 vertical move away (down)
	 * Distance is 3
	 */
	public int computeManhDist(Node current, Node goal){
		return Math.abs(current.getRow() - goal.getRow()) + Math.abs(current.getCol() - goal.getCol());
	}
	
}
