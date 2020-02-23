import java.util.LinkedList;

public class IDS {

private LinkedList<Node>[][] nodeAdjList;
	
	public IDS(LinkedList<Node>[][] nodeAdjList){
		this.nodeAdjList = nodeAdjList;

	}
	
	public boolean depthLimSearch(Node current, Node goal, int depth){
		System.out.print("(" + current.getRow() + ", " + current.getCol() + ")" + current.getValue());
		
		if(current == goal){
			return true;
		}
		System.out.print(" --> ");
		
		if(depth == 0){
			return false;
		}
		
		for(Node adjToCurrent : nodeAdjList[current.getRow()][current.getCol()]){ //visit all adjacent nodes
			if(depthLimSearch(adjToCurrent, goal, depth - 1)){ // call stack decrement depth
				return true;
			}
		}
		return false;
	}
	
	public void idSearch(Node start, Node goal){
		int depth = 0;
		System.out.println("-- IDS path -- (coordinates)value ");
		
		while(true){ // until goal is found keep incrementing depth
			if(depthLimSearch(start, goal, depth)){
				break;
			}
			else{
				depth += 1;
			}
		}
		
	}
	
	
}
