package graphSudoku;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import graphSudoku.Edge;
import graphSudoku.Graph;
import graphSudoku.GraphAdjListImpl;
import graphSudoku.Vertex;

import graphSudoku.Metadata;

public class FileIO {

	//return numberofVertices for adjacency Matrix and 0 for adjList graph implementation
	public static Metadata GraphToCreate(String filename){
		
		int numberOfvertexes = 0, numberOfEdges = 0, totalEdges = 0;
		int[] colorInitial = new int[9];
		
		File initialFile = new File(filename);
		String line = "";
		int lineNumber = -1;
		
		try {
			 
			 BufferedReader reader = new BufferedReader(new FileReader(initialFile));
			 line = reader.readLine();
			 lineNumber++;

			 while (line != null){
				 //add vertexes
				 if (lineNumber == 0) { //first line
					 String[] edgs = line.split(" ");
					 numberOfvertexes = Integer.parseInt(edgs[0]);
					 totalEdges = Integer.parseInt(edgs[1]);
					 System.out.println("totalEdges - " + totalEdges);
					// numberOfvertexes = Integer.parseInt(line);
					for (int i = 0; i < numberOfvertexes; i++){
						line = reader.readLine();
						lineNumber++;
					}
				 }
				 //add edges
				 else{
				 	if(lineNumber == 28) {
						String[] colors = line.split(" ");
						for (int i = 0; i < colors.length; i++) {
							int x = Integer.parseInt(colors[i]);
							colorInitial[i] = x;
							// System.out.print(" colorInitial " + colorInitial[i]);
						}
					}
					 // System.out.println("numberOfEdges - " + numberOfEdges);
					 numberOfEdges++;
					 line = reader.readLine();
					 lineNumber++;
				 }
				 
			 }
			 reader.close();
		 
		 }
		 catch (Exception ex){
			 System.out.println(ex.getMessage());
		 }
		
		//check if it a complete graph or almost a complete graphSudoku. The graph with adj matrix is preferred
		/*int threashold = 1;
		System.out.println("numberOfEdges - " + numberOfEdges + " numberOfvertexes - " + numberOfvertexes);
		if (2*numberOfEdges > (numberOfvertexes*(numberOfvertexes-1) - threashold))
			return numberOfvertexes;
		else
			return 0;*/
		Metadata md = new Metadata(numberOfvertexes,totalEdges, colorInitial);
		/*md.vertices = numberOfvertexes;
		md.nodes = numberOfEdges;
		md.colors = colorInitial.clone();*/

		return md;
	}
	
	public static Graph readDataAndCreateGraph(String filename, Graph G, int[] initialColors){
			
			String vertexProperty = "value";
			
			ArrayList<Vertex> vList = new ArrayList<>();
			
			 File initialFile = new File(filename);
			 String line = "";
			 int lineNumber = -1;
			 try {
			 
				 BufferedReader reader = new BufferedReader(new FileReader(initialFile));
				 line = reader.readLine();
				 lineNumber++;
				 while (line != null){
					 System.out.println("Processing line - " + line + " Line Number - " + lineNumber);
					 //add vertexes
					 if (lineNumber == 0) { //first line
						// int vertexes = Integer.parseInt(line);
						 String[] verts = line.split(" ");
						 int vertexes = Integer.parseInt(verts[0]);
						while (vertexes > 0){
							line = reader.readLine();
							Vertex v = new Vertex();
							v.addProp(vertexProperty, line.trim());
							if(initialColors[lineNumber] != 0) {
								v.color = initialColors[lineNumber];
							}
							vList.add(v);
							G.addNewVertex(v);
							vertexes--;
							lineNumber++;
						}
						line = reader.readLine();
						lineNumber++;
					 }
					 //add edges
					 else{
					 	if(lineNumber != 28) {
							String[] edgs = line.split(" ");
							String v1 = edgs[0];
							String v2 = edgs[1];
							Edge e = new Edge();
							// System.out.println("Processing vertexes size - " + vList.size());
							for (int i = 0; i < vList.size(); i++) {
							 /*System.out.println("comparing vList.get(i).props.get(vertexProperty)  - "
									 + vList.get(i).props.get(vertexProperty) + " - " + v1 +
									 " - " + Objects.equals(vList.get(i).props.get(vertexProperty),v1));
							 System.out.println("comparing vList.get(i).props.get(vertexProperty)  - "
									 + vList.get(i).props.get(vertexProperty) + " - " + v2 +
									 " - " + Objects.equals(vList.get(i).props.get(vertexProperty),v2));*/
								if (Objects.equals(vList.get(i).props.get(vertexProperty), v1)) {
									e.setStartVertex(vList.get(i));
								}
								if (Objects.equals(vList.get(i).props.get(vertexProperty), v2)) {
									e.setEndVertex(vList.get(i));
								}
							}
							G.addNewEdge(e);
							line = reader.readLine();
							lineNumber++;
						} else {
							lineNumber++;
						}
					 }

					 
				 }
				 reader.close();
			 
			 }
			 catch (Exception ex){
				 System.out.println(ex.getMessage());
			 }
			 
			//return graph
			return G;
	}
	
	public static Graph readDataAndCreateGraphFromEdges(String filename, Graph G){
		
		String vertexProperty = "value";
		
		ArrayList<Vertex> vList = new ArrayList<>();
		HashMap<String, Integer> vertexes = new HashMap<>();
		
		 File initialFile = new File(filename);
		 String line = "";
		 int lineNumber = -1;
		 try {
		 
			 BufferedReader reader = new BufferedReader(new FileReader(initialFile));
			 line = reader.readLine();
			 lineNumber++;
			 while (line != null){
				 
				 //add vertexes
				
				 String[] edgs = line.split(" ");
				 String v1 = edgs[0];
				 String v2 = edgs[1];
				 
				 if (!vertexes.containsKey(v1)) {
					 Vertex v = new Vertex();
					 v.addProp(vertexProperty, v1.trim());
					 vList.add(v);
					 G.addNewVertex(v);
					 vertexes.put(v1, vList.size()-1);
				 }
				 if (!vertexes.containsKey(v2)) {
					 Vertex v = new Vertex();
					 v.addProp(vertexProperty, v2.trim());
					 vList.add(v);
					 G.addNewVertex(v);
					 vertexes.put(v2, vList.size()-1);
				 }
				 
				 Edge e = new Edge();
				 
				 Vertex first = vList.get(vertexes.get(v1));
				 Vertex second = vList.get(vertexes.get(v2));
				 e.setStartVertex(first);
				 e.setEndVertex(second);
				 G.addNewEdge(e);
				 line = reader.readLine();
				 lineNumber++;
//				 System.out.println(lineNumber);
			 }
			
			 reader.close();
		 
		 }
		 catch (Exception ex){
			 System.out.println(ex.getMessage());
		 }
		 
		//return graph
		return G;
}
	
	public static Graph readSudokuAndCreateGraph(String filename, Graph G){
	
		String vertexProperty = "value";
		
		//ArrayList<Vertex> vList = new ArrayList<>();
		int[][] sudokuPuzzle = new int[9][9];
		//for the location and clues that we have in sudoku
		HashMap<Integer, Vertex> vertices = new HashMap<>();
	
		 File initialFile = new File(filename);
		 String line = "";
		 int count = 0, col = 0;
		 try {
		 
			 BufferedReader reader = new BufferedReader(new FileReader(initialFile));
			 line = reader.readLine();
			 
			 while (line != null){
			 	 while (col != 9){
					 if (line.charAt(col) == '.'){
				 		 Vertex v = new Vertex();
				 		 int c = count%9;
				 		 int r = count/9;
				 		 sudokuPuzzle[r][c] = -1;
				 	 }
				 	 else{
				 		 int c = count%9;
				 		 int r = count/9;
				 		 sudokuPuzzle[r][c] = Character.getNumericValue(line.charAt(col));
				 	 }
				 	 count++; col++;
				 }
			 	 if (col == 9)
			 		 col = 0;
			 	 line = reader.readLine();
			 }
			 
			 reader.close();
			 
			 //create vertices
			 for (int i = 0; i< 9; i++){
				 int row = i*9;
				 for (int j = 0; j<9;j++){
					  Vertex v = new Vertex();
					  count = row + j;
					  v.addProp(vertexProperty, String.valueOf(count));
					  v.color = sudokuPuzzle[i][j];
					  if (sudokuPuzzle[i][j] != -1)
						  v.addProp("fixed", "1");
					  else 
						  v.addProp("fixed", "0");
					  vertices.put(count, v);
					  G.addNewVertex(v);
				 }
			 }
			 
			 //add row vice edges
			 for(int row = 0; row<9;row++){
				 for (int i = 0; i< 9; i++){
					 for (int j = i+1; j<9;j++){
						  int ind1 = row*9+i;
						  int ind2 = row*9+j;
						  Edge e = new Edge();
						  e.setStartVertex(vertices.get(ind1));
						  e.setEndVertex(vertices.get(ind2));
						  G.addNewEdge(e);
					 }
				 }
			 }
			 
			
			//add col vice edges
			 for (int i = 0; i< 9; i++){
				 for (int j = 0; j<9;j++){
					 for(col = j+1; col<9;col++){
						  int ind1 = j*9+i;
						  int ind2 = col*9+i;
						  Edge e = new Edge();
						  e.setStartVertex(vertices.get(ind1));
						  e.setEndVertex(vertices.get(ind2));
						  G.addNewEdge(e);
					 }
				 }
			 }
			 
			 
//			 //for the 3*3 box
			 for (int row = 0; row < 9; row+=3){
				 for(col = 0; col <9; col+=3){
					 
					 ArrayList<Integer> pos = new ArrayList<>();
					 int start = row*9+col;
					 pos.add((row+1)*9+col+1);
					 pos.add((row+1)*9+col+2);
					 pos.add((row+2)*9+col+1);
					 pos.add((row+2)*9+col+2);
					 
					 addEdgesToGraph(G, start, pos, vertices);
					 
					 start = row*9+col+1;
					 pos.clear();
					 pos.add((row+1)*9+col);
					 pos.add((row+1)*9+col+2);
					 pos.add((row+2)*9+col);
					 pos.add((row+2)*9+col+2);
					 
					 addEdgesToGraph(G, start, pos, vertices);
					 
					 start = row*9+col+2;
					 pos.clear();
					 pos.add((row+1)*9+col);
					 pos.add((row+1)*9+col+1);
					 pos.add((row+2)*9+col);
					 pos.add((row+2)*9+col+1);
					 
					 addEdgesToGraph(G, start, pos, vertices);
					 
					 start = (row+1)*9+col;
					 pos.clear();
					 pos.add((row+2)*9+col+1);
					 pos.add((row+2)*9+col+2);
					 
					 addEdgesToGraph(G, start, pos, vertices);
					 
					 start = (row+1)*9+col+1;
					 pos.clear();
					 pos.add((row+2)*9+col);
					 pos.add((row+2)*9+col+2);
					 
					 addEdgesToGraph(G, start, pos, vertices);
					 
					 start = (row+1)*9+col+2;
					 pos.clear();
					 pos.add((row+2)*9+col);
					 pos.add((row+2)*9+col+1);
					 
					 addEdgesToGraph(G, start, pos, vertices);
				 }
			 }
		 }
		 catch (Exception ex){
			 System.out.println(ex.getMessage());
		 }
		
		
		return G;
	}
	
	public static void addEdgesToGraph(Graph G, int startPosition, ArrayList<Integer> pos, HashMap<Integer, Vertex> vertices){
		
		for (int i: pos){
			Edge e =  new Edge();
		    e.setStartVertex(vertices.get(startPosition));
		    e.setEndVertex(vertices.get(i));
		    G.addNewEdge(e);
		}
				
	}
	
}
