
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
		System.out.println("<------------Input Sudoku----------->");
		Colors.printSudoku(G);
		System.out.println("<------------Ouput Sudoku----------->");
		G.colorGraph();
		Colors.printSudoku(G);
	}
	
}
