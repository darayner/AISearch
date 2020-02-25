import java.util.Comparator;

/*
 * Class to keep track of nodes on grid.
 * Stores coordinates and values.
 * Values:
 * Edge cost to node 
 * Cost of heuristic if applicable
 * Cost of path from start to node
 */
public class Node implements Comparator<Node>{
	private int row; // x location of grid
	private int col; // y location of grid
	private int value; // edge cost to reach node
	private int gValue; // cost from source node
	private int hValue; // The Manhattan distance cost of node
	private int fValue; // The total path cost of the node from the start
	private Node parent; // Get parent of node used for retracing path

	public Node(int row, int col, int value){
		this.row = row;
		this.col = col;
		this.value = value;
	}
	
	public Node() {
		
	}
	
	public void setParent(Node parent){
		this.parent = parent;
	}

	public void setRow(int row){
		this.row = row;
	}
	
	public void setCol(int col){
		this.col = col;
	}
	
	public void setValue(int value){
		this.value = value;
	}
	
	public void setHValue(int hValue){
		this.hValue = hValue;
	}
	
	public void setFValue(int fValue) {
		this.fValue = fValue;
	}
	
	public void setGValue(int gValue) {
		this.gValue = gValue;
	}
	
	public Node getParent(){
		return this.parent;
	}
	
	public int getRow(){
		return this.row;
	}
	
	public int getCol(){
		return this.col;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public int getGValue() {
		return this.gValue;
	}

	public int getHValue(){
		return this.hValue;
	}
	
	public int getFValue() {
		return this.fValue;
	}
	/*
	 * Compares node f values
	 * Used for A* priority queue
	 * 
	 */
	@Override
	public int compare(Node a, Node b) {
		if(a.fValue > b.fValue){
			return 1;
		}
		else if(a.fValue < b.fValue){
			return -1;
		}
		else{
			return 0;
		}
	}

	
}
