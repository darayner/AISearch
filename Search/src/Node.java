import java.util.Comparator;

/*
 * Class to keep track of nodes on grid
 * Stores coordinates and values
 * 
 */
public class Node implements Comparator<Node>{
	private int row;
	private int col;
	private int value;

	public Node(int row, int col, int value){
		this.row = row;
		this.col = col;
		this.value = value;
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
	
	public int getRow(){
		return this.row;
	}
	
	public int getCol(){
		return this.col;
	}
	
	public int getValue(){
		return this.value;
	}

	@Override
	public int compare(Node a, Node b) {
		if(a.value > b.value){
			return 1;
		}
		else if(a.value> b.value){
			return -1;
		}
		else{
			return 0;
		}
	}
	
	
	
}
