
import graph.Graph;
import graph.GraphAdjListImpl;
import graph.GraphColoringBackTracking;
import graph.GraphColoringBackTrackingForSudoku;
import graph.Colors;
import graph.FileIO;

public class SudokuSolver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "Files/Sudoku";
		Graph G = FileIO.readSudokuAndCreateGraph(path, new GraphAdjListImpl());
		G.setGraphColoringTechnique(new GraphColoringBackTrackingForSudoku());
		G.colorGraph();
		Colors.printSudoku(G);
	}
	
}
