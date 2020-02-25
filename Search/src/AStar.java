import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStar {
	
	private LinkedList<Node>[][] nodeAdjList;
	
	public AStar(LinkedList<Node>[][] nodeAdjList){
		this.nodeAdjList = nodeAdjList;

	}
	
	public void aStarSearch(Node start, Node goal){
		boolean[][] visited = new boolean[nodeAdjList.length][nodeAdjList[0].length];
		PriorityQueue<Node> queue = new PriorityQueue<Node>(new Node());
		
		queue.add(start); // add starting node f = 0
		start.setHValue(computeManhDist(start, goal)); // set H value
		
		Node current;
		while(queue.size() != 0){
			current = queue.poll();
			System.out.print("(" + current.getRow() + ", " + current.getCol() + ")" + "[" + current.getFValue() + "] -->");
			visited[current.getRow()][current.getCol()] = true;
			
			if(current == goal){
				break;
			}
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
				queue.add(adjToCurrent);
			}
		}
	}

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
