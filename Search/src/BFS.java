import java.util.LinkedList;
import java.util.Queue;


/*
 * Class to integrate breadth-first search
 * Provided nodeAdjList
 * 
 */
public class BFS {
	
	private LinkedList<Node>[][] nodeAdjList;
	
	public BFS(LinkedList<Node>[][] nodeAdjList){
		this.nodeAdjList = nodeAdjList;

	}
	
	/*
	 * Breadth-first search 
	 * Expands all adjacent nodes
	 * do until goal is reached or queue is empty:
	 * Keep track of current (top of queue)
	 * Look at current's neighbors (adjacent Nodes)
	 * Place current neighbor Nodes on queue mark as visited
	 * 
	 */
	public void bsfSearch(Node start, Node goal){
		boolean[][] visited = new boolean[nodeAdjList.length][nodeAdjList[0].length];
		Queue<Node> queue = new LinkedList<>();
		
		visited[start.getRow()][start.getCol()] = true;
		queue.add(start);
		
		Node current;
		System.out.println("-- BFS path -- (coordinates)value ");
		while(queue.size() !=  0){
			current = queue.poll();
			System.out.print("(" + current.getRow() + ", " + current.getCol() + ")" + current.getValue());
			
			if(current == goal){
				break;
			}
			
			System.out.print(" --> ");
			
			for(Node adjToCurrent : nodeAdjList[current.getRow()][current.getCol()]) {
			   if(!visited[adjToCurrent.getRow()][adjToCurrent.getCol()]  && adjToCurrent.getValue() != 0) {
				   visited[adjToCurrent.getRow()][adjToCurrent.getCol()] = true;
				   queue.add(adjToCurrent);
			   }
			}
		
		}
		
		
	}
	
	
}
