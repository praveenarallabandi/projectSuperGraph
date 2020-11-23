package graphSudoku;
import java.util.ArrayList;

import graphSudoku.Graph;
import graphSudoku.Vertex;

public class Colors {
	private static String[] colors = {"1", "2", "3"};
	public static String getColor(int index){
		return colors[index%colors.length];
	}
	
	public static void printSudoku(Graph G){
		
		ArrayList<Vertex> vertices = G.getVertices();

		for(int i =0; i< 9; i++){
			
			for (int j =0; j < 9; j++){
				/*if (j%3==0)
					System.out.print(",");*/
				System.out.print(vertices.get(i*9+j).color);
				System.out.print(",");
			}
			//System.out.print("|");
			System.out.println();
			
		}
		
	}
	
	public static int maximumColorsAvailable(){
		return colors.length;
	}
	
}
