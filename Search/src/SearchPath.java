import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/* 
 * version 1.0.
 * Last modified 02/26/2020 @ 5:00pm.
 * Authors:
 * David Rayner
 * Andrew Munoz
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
 * 4. Runtime in milliseconds.
 * 5. Path to goal (row, column), (row, column) ...
 * 
 */

public class SearchPath {

	private static Node[][] grid; //store Nodes on grid respective to their coordinate positions
	private static Node start;
	private static Node goal;
	final static int capPathLength = 5;
	
	public static void main(String[] args){
		
		LinkedList<Node>[][] nodeAdjList;
		
		/*
		if(args.length > 0){
			String fileName = args[0];
			String searchType = args[1];
			File file = new File(fileName);
			
			if(file.exists() && (searchType.equals("BFS") || searchType.equals("IDS") || searchType.equals("AST"))){
				readFile(fileName);
				nodeAdjList = createAdjList();
				runSearch(searchType, nodeAdjList);
			}
			else{
				System.out.println("File does not exist or invalid search type!");
				System.exit(0);
			}
			
		}
		else{
			System.out.println("Two arguments expected recieved: " + args.length);
			System.exit(0);
		}
		*/
		
		String file = "mapTestF.txt";
		readFile(file);
		nodeAdjList = createAdjList();
		
		runSearch("BFS", nodeAdjList);
		runSearch("IDS", nodeAdjList);
		runSearch("AST", nodeAdjList);
		
	}
	
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
        catch(Exception e) {
        	System.out.print(e);
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
		@SuppressWarnings("unchecked")
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
	
	/*
	 * Runs the desired search type.
	 * Prints:
	 * Node path.
	 * Total path cost.
	 * Maximum Number of nodes held in memory.
	 * Number of nodes expanded.
	 * Runtime in milliseconds.
	 * 
	 */
	public static void runSearch(String searchType, LinkedList<Node>[][] nodeAdjList){
		long startTime, duration = 0;
		int maxNodesHeld = 0;
		int expandedNodes = 0;
		double timeout = System.currentTimeMillis() + 180000;
		List<Node> path = null;
		
		if(searchType.equals("BFS")){ 
			BFS bSF = new BFS(nodeAdjList);
			startTime = System.nanoTime();
			path = bSF.bfsearch(start, goal, timeout);
			duration = System.nanoTime() - startTime;
			expandedNodes = bSF.getExpandedNodes();
			maxNodesHeld = bSF.getmaxNodesHeld();
			
		}
		
		else if(searchType.equals("IDS")){
			IDS iDS = new IDS(nodeAdjList);
			startTime = System.nanoTime();
			path = iDS.idSearch(start, goal, timeout);
			duration = System.nanoTime() - startTime;
			expandedNodes = iDS.getExpandedNodes();
			maxNodesHeld = iDS.getmaxNodesHeld();
		}
		
		else if(searchType.equals("AST")){
			AStar aST = new AStar(nodeAdjList);
			startTime = System.nanoTime();
			path = aST.aStarSearch(start, goal, timeout);
			duration = System.nanoTime() - startTime;
			expandedNodes = aST.getExpandedNodes();
			maxNodesHeld = aST.getmaxNodesHeld();
		}
		
		System.out.println("--" + searchType + " Search" + "--");
		System.out.print("Path: ");
		int pathCost = 0 - start.getValue();
		int pathLength = 0;
		
		if(path.isEmpty()){
			pathCost = -1;
			System.out.print("No path found to goal!");
		}
		
		for(Node node : path){
			System.out.print("(" + node.getRow() + ", " + node.getCol() + ")");
			if(pathLength < path.size()-1)
				System.out.print(" -> ");
			pathCost += node.getValue();
			pathLength +=1;
			if (pathLength%capPathLength == 0) {
				System.out.println();
				System.out.print("      ");
			}

		}
		
		
		System.out.println();
		System.out.println("Path Cost: " + pathCost);
		System.out.println("Maximum number of nodes held in memory: " + maxNodesHeld);
		System.out.println("Number of nodes expanded: "+ expandedNodes);
		System.out.printf("Runtime milliseconds: %.4f %n%n", (duration * .000001)); // convert to milliseconds
	}
	
}
