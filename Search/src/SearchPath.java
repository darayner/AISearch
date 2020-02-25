import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/* 
 * version 0.8.
 * Last modified 02/24/2020 @ 1:22pm.
 * 
 * Program implementing:
 * 1. Breadth-first search
 * 2. Depth-first search
 * 3. A* star search
 * 
 * Testing the following:
 * 1. Cost of path to goal.
 * 2. Number of nodes expanded.
 * 3. Maximum number of nodes held in memory.
 * 4. Runtime in milliseconds
 * 5. Path to goal (row, column) --> (row, column) ...
 * 
 */

public class SearchPath {
	
	private static Node[][] grid; //store Nodes on grid respective to their coordinate positions.
	private static Node start;
	private static Node goal;
	
	public static void main(String[] args){
		readFile("map.txt");
		LinkedList<Node>[][] nodeAdjList = createAdjList();
		
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[i].length; j++){
				System.out.print(grid[i][j].getValue() + " ");
			}
			System.out.println("");
		}
		
		BFS bsf = new BFS(nodeAdjList);
		List<Node> bPath = bsf.bfsearch(start, goal);
		System.out.println("--BFS Path--");
		for (Node node : bPath) { 		      
	           System.out.print("(" + node.getRow() + ", " + node.getCol() + "), "); 		
	    }
		System.out.println();
		IDS ids = new IDS(nodeAdjList);
		List<Node> iPath = ids.idSearch(start, goal);
		System.out.println("--IDS Path--");
		for (Node node : iPath) { 		      
	           System.out.print("(" + node.getRow() + ", " + node.getCol() + "), "); 		
	    }
		System.out.println();
		AStar ast = new AStar(nodeAdjList);
		List<Node> aPath = ast.aStarSearch(start, goal);
		System.out.println("--A* Path--");
		for (Node node : aPath) { 		      
	           System.out.print("(" + node.getRow() + ", " + node.getCol() + "), "); 		
	    }
	
	}
	/* 
	 * Reads provided input file. 
	 * Creates grid based on size (file line 1).
	 * Stores start and end goal nodes (file lines 2 and 3).
	 * Creates grid of Nodes with coordinates and their values. (file lines > 3)
	 * Handle Exceptions:
	 * 
	 */
	public static void readFile(String filename){
		
		try {
			int[] sizeOfMap = new int[2];
			int[] startPos = new int[2];
			int[] goalPos = new int[2];
			
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			int lineNum = 0;
			
			while((line = br.readLine()) != null){ 
				String[] temp = line.split(" ");
				for(int j = 0; j < temp.length; j++){
					if(lineNum == 0){
						sizeOfMap[j] = Integer.parseInt(temp[j]);
						if(j == sizeOfMap.length-1){
							grid = new Node[sizeOfMap[j-1]][sizeOfMap[j]];
						}
					}
					else if(lineNum == 1){
						startPos[j] = Integer.parseInt(temp[j]);
					}
					else if(lineNum == 2){
						goalPos[j] = Integer.parseInt(temp[j]);
					}
					else{
						grid[lineNum-3][j] = new Node(lineNum-3, j, Integer.parseInt(temp[j]));
					}
				}
				lineNum++;
			}
			br.close();
			start = grid[startPos[0]][startPos[1]];
		    goal = grid[goalPos[0]][goalPos[1]];
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Creates node adjacency list for all Nodes on grid.
	 * This allows for each node position to correspond to all allowable next moves.
	 * Checks Node position bounds up, right, left, down respectively.
	 * Example:
	 * Node[i][j]] -> [Node[i+1][j], Node[i][j+1],....]
	 * 
	 * 1 2 3 
	 * 3 0 4
	 * 1 4 5
	 * 
	 * 1 -> [2, 3]
	 * 2 -> [3, 1, 0]
	 * 3 -> [2, 4]
	 * 3 -> [1, 0, 1]
	 * 0 -> [2, 4, 3, 4]
	 * .
	 * .
	 * .
	 * 5 -> [4, 4]
	 */
	
	public static LinkedList<Node>[][] createAdjList(){
		LinkedList<Node> nodeAdjList[][] = new LinkedList[grid.length][grid[0].length];
		
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[i].length; j++){
				nodeAdjList[i][j] = new LinkedList<Node>(); 
			}
		}
		
		for(int i = 0; i < nodeAdjList.length; i++){
			int up = i - 1;
			int down = i + 1;
			for(int j = 0; j < nodeAdjList[i].length; j++){
				int left = j - 1;
				int right = j + 1;
				if(up >= 0){
					nodeAdjList[i][j].add(grid[up][j]);
				}
				if(right <= nodeAdjList[i].length - 1){
					nodeAdjList[i][j].add(grid[i][right]);
				}
				if(down <= nodeAdjList.length - 1){
					nodeAdjList[i][j].add(grid[down][j]);
				}
				if(left >= 0){
					nodeAdjList[i][j].add(grid[i][left]);
				}
			}
		}
		return nodeAdjList;
	
	}
	
}
