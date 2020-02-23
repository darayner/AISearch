import java.util.LinkedList;

public class IDS {

private LinkedList<Node>[][] nodeAdjList;
	
	public IDS(LinkedList<Node>[][] nodeAdjList){
		this.nodeAdjList = nodeAdjList;

	}
	/*
	 * Recursively check each depth.
	 * Use BFS to check visit adjacent nodes at depth.
	 * If goal found return true, otherwise depth reached (false).
	 * 
	 */
	public boolean depthLimSearch(Node current, Node goal, int depth, boolean[][] visited){
		visited[current.getRow()][current.getCol()] = true; // mark each visited node
		
		System.out.print("(" + current.getRow() + ", " + current.getCol() + ")" + current.getValue());
		System.out.print(" --> ");
		
		if(current == goal){
			System.out.println("Found goal at: " + "(" +current.getRow() + "," + current.getCol() + ")");
			return true;
		}
		
		if(depth <= 0){
			return false;
		}
		
		for(Node adjToCurrent : nodeAdjList[current.getRow()][current.getCol()]){ //visit all adjacent nodes
			if(adjToCurrent.getValue() == 0 || visited[adjToCurrent.getRow()][adjToCurrent.getCol()]){
				continue;
			}
			else if(depthLimSearch(adjToCurrent, goal, depth - 1, visited)){ // call stack decrement depth
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Call depth limited search to go up to depth
	 * Gradually increment depth if goal not found at depth
	 * 
	 */
	public void idSearch(Node start, Node goal){
		int depth = 0;
		System.out.println("-- IDS path -- (coordinates)value ");
		
		while(true){ // until goal is found keep incrementing depth
			System.out.println("Depth: " + depth);
			boolean[][] visited = new boolean[nodeAdjList.length][nodeAdjList[0].length]; // keep track of visited (no cycles)
			
			if(depthLimSearch(start, goal, depth, visited)){
				break;
			}
			else{
				System.out.println();
				depth += 1;
			}
		}
		
	}
	
	
}
