import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;


/*
 * Class to integrate breadth-first search.
 * Provided nodeAdjList.
 * 
 */
public class BFS {
	
	private LinkedList<Node>[][] nodeAdjList;
	
	public BFS(LinkedList<Node>[][] nodeAdjList){
		this.nodeAdjList = nodeAdjList;

	}
	
	/*
	 * Breadth-first search.
	 * Expands all adjacent nodes.
	 * do until goal is reached or queue is empty:
	 * Keep track of current (top of queue).
	 * Look at current's neighbors (adjacent Nodes).
	 * Place current neighbor Nodes on queue mark as visited.
	 * 
	 */
	public List<Node> bfsearch(Node start, Node goal){
		boolean[][] visited = new boolean[nodeAdjList.length][nodeAdjList[0].length];
		Queue<Node> queue = new LinkedList<>();
		List<Node> path = new ArrayList<>();
		
		visited[start.getRow()][start.getCol()] = true;
		queue.add(start);
		
		Node current;
		while(queue.size() !=  0){
			current = queue.poll();
			
			if(current == goal){  // goal found return path
			  path.add(current);
			  while(current != start){
				  current = current.getParent();
				  path.add(current);
			  }
			  Collections.reverse(path); 
			  break;
			}
			
			for(Node adjToCurrent : nodeAdjList[current.getRow()][current.getCol()]) {
			   if(!visited[adjToCurrent.getRow()][adjToCurrent.getCol()]  && adjToCurrent.getValue() != 0) {
				   visited[adjToCurrent.getRow()][adjToCurrent.getCol()] = true;
				   adjToCurrent.setParent(current);
				   queue.add(adjToCurrent);
			   }
			}
		}
		return path;
	}
	
	
}
