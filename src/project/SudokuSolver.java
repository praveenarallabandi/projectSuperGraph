
import graphSudoku.Graph;
import graphSudoku.GraphAdjListImpl;
import graphSudoku.GraphColoringBackTracking;
import graphSudoku.GraphColoringBackTrackingForSudoku;
import graphSudoku.Colors;
import graphSudoku.FileIO;

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
