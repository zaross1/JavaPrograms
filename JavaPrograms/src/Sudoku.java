import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;


public class Sudoku { // Sudoku application
	
	static int[][][] boards; // stores starting sudoku board values
	// populates the Board, which consists of JTextFields, with starting values
	public static void setRandomGame(int[][][] boards, ArrayList<ArrayList> board  ) { 
		
		int randomNum = (int)(Math.random() * boards.length); // used to select random starting values
		
		for (int x = 0; x < boards[0].length; x++) { // begin loop A
			
			for (int y = 0; y < boards[0][0].length; y++) { // begin loop B
				
				if (boards[randomNum][x][y] != 0) { // begin If C
					// if the Sudoku board value is not 0, sets JTextField with that value
					((JTextField) board.get(x).get(y)).setForeground(Color.BLUE);
					((JTextField)board.get(x).get(y)).setText(String.format("%d", boards[randomNum][x][y]));
					((JTextField) board.get(x).get(y)).setEditable(false);
					
				} // end If C
			} // end loop B
			
			
		} // end loop A
		
	} // end setRandomGame function

	public static void main(String[] args) {
		
		int counter = 0; // keeps track of where in the Sudoku starting values we are
		boards = new int[100][9][9]; // stores the starting Sudoku values 
		
		try {
			
			Scanner file = new Scanner(new File("C:/Users/nighteyes/workspace/Craps/resources/sudokuData.txt"));
			    
			for (int x = 0; x < boards.length; x++) {
				// each line of the text file is one complete sudoku starter game.
				String[] game = file.nextLine().split("");
				counter = 0;
				
				for (int y = 0; y < boards[0].length; y++) {
					
					for (int z = 0; z < boards[0][0].length; z++) {
						// the starter games are put into boards for convenient access
						boards[x][y][z] = Integer.parseInt(game[counter]);	
						counter += 1;
					}					
				}			
			}
			
			file.close();
		
		} catch (Exception ex) {
			
			System.out.println("error" + ex.getMessage());
			
		}
		
	
		
		
		
		int[][] sudokuBoard1 = {{5, 3, 4, 6, 7, 8, 9, 1, 2}, // initial test board for the assignment
			    {6, 7, 2, 1, 9, 5, 3, 4, 8},
			    {1, 9, 8, 3, 4, 2, 5, 6, 7},
			    {8, 5, 9, 7, 6, 1, 4, 2, 3},
			    {4, 2, 6, 8, 5, 3, 7, 9, 1},
			    {7, 1, 3, 9, 2, 4, 8, 5, 6},
			    {9, 6, 1, 5, 3, 7, 2, 8, 4},
			    {2, 8, 7, 4, 1, 9, 6, 3, 5},
			    {3, 4, 5, 2, 8, 6, 1, 7, 9}
        	};
		
		// System.out.println(checkSudokuSolution(sudokuBoard1, 3 ));
		int temp = sudokuBoard1[2][3];
		sudokuBoard1[2][3] = sudokuBoard1[5][6];
		sudokuBoard1[5][6] = temp;
		// System.out.println(checkSudokuSolution(sudokuBoard1, 3 ));
		
		// configure JFrame, and the JPanel where the game will be played
		JFrame frame = new JFrame();
		JPanel panel = new SudokuPanel();
		panel.setBackground(Color.BLACK);
		frame.setSize(800, 800);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Sudoku");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		
		
		
		
	}

	private static boolean checkSudokuSolution(int[][] grid, int subSquareSize) { // checks solution
		
		final int size = grid.length;
		
		if (!checkValues(grid, 1, size)) return false; // check that all values are valid
		
		for (int row = 0; row < size; row++) { // check that rows are valid
			
			if (!checkRow(grid, row)) return false;	
		}
		for (int row = 0; row < size; row++) { // check that columns are valid
			if (!checkColumn(grid, row)) return false;	
		}
		// check that each grid is valid
		for (int baseRow = 0; baseRow < size; baseRow += subSquareSize) { // begin loop A
		   for (int baseCol = 0; baseCol < size; baseCol += subSquareSize) { // begin loop B
		      if (!checkSquare(grid, baseRow, baseCol, subSquareSize)){ // begin if
		    	  return false;
		 } // end if
		 
		 } // end loop B
		 } // end loop A
				
		return true; // the Sudoku board is a valid solution, so return true
	}

	private static boolean checkSquare(int[][] grid, int baseRow, int baseCol, int subSquareSize) {
		// found is a list of the values, of which each subSquare can only have one copy.
		boolean[] found = new boolean[subSquareSize * subSquareSize];
		// iterates through subsquares, checking for duplicate values
		for(int row = baseRow; row < subSquareSize; row++) {
			
			for (int col = baseCol; col < subSquareSize; col++) {
				
				int index = grid[row][col] - 1;
				// if duplicate value is found, it returns false, otherwise returns true
				if (!found[index]) found[index] = true; 
				else return false;			
			}
			
		}
		
		return true;
	}

	private static boolean checkColumn(int[][] grid, int col) {
		
		final int size = grid[0].length; // column size
		
		boolean[] found = new boolean[size]; // array of values
		
		for (int row = 0; row < grid.length; row++) { // loops through rows
			
			int index = grid[row][col] - 1; // index of current value
			
			if (!found[index]) found[index] = true; // first time value occurs, sets it to true
			else return false; // value has occurred twice, so invalid solution, returns false
			
		}
		
		return true; // returns true on valid column
	}

	private static boolean checkRow(int[][] grid, int row) {
		final int size = grid[0].length;
		 boolean[] found = new boolean[size];
		 for (int col = 0; col < size; col++) { // begin loop A
		 // set found[x - 1] to be true if we find x in the row
		 int index = grid[row][col] - 1;
		 
		 if (!found[index]) {
			 found[index] = true;
		 } else {
		 // found it twice, so return false
		 return false;
		 }
		 
		 } // end loop A
		 // didn't find any number twice, so return true
		 return true; 
	}

	private static boolean checkValues(int[][] grid, int min, int max) {
		
		for (int[] row : grid) { // loops through rows
			
			for (int colValue : row) { // loops through column values
				// if a value is invalid, failed sudoku solution, so returns false
				if ((colValue < min) || (colValue > max)) return false; 				
			}			
		}		
		return true; // returns true if the sudoku board has no invalid values
	}

	public static void clearBoard(ArrayList<ArrayList> board) {
		for (int x = 0; x < board.size(); x++) { // begin loop A
			for (int y = 0; y < board.get(0).size(); y++) {
				
				((JTextField) board.get(x).get(y)).setText("");
				((JTextField) board.get(x).get(y)).setForeground(Color.BLACK);
				((JTextField) board.get(x).get(y)).setEditable(true);
			}
			
			
		} // end loop A
	
	} // end method clearBoard
	
	
	// ArrayList methods.
	
static boolean checkSudokuSolution(ArrayList<ArrayList> grid, int subSquareSize) {
		
		final int size = grid.size();
		
		if (!checkValues(grid, 1, size)) return false; // check that all values are valid
		
		for (int row = 0; row < size; row++) { // check that rows are valid
			
			if (!checkRow(grid, row)) return false;	
		}
		for (int row = 0; row < size; row++) { // check that columns are valid
			if (!checkColumn(grid, row)) return false;	
		}
		// check that each grid is valid
		for (int baseRow = 0; baseRow < size; baseRow += subSquareSize) { // begin loop A
		   for (int baseCol = 0; baseCol < size; baseCol += subSquareSize) { // begin loop B
		      if (!checkSquare(grid, baseRow, baseCol, subSquareSize)){ // begin if
		    	  return false;
		 } // end if
		 
		 } // end loop B
		 } // end loop A
				
		return true; // the Sudoku board is a valid solution, so return true
	}

	// the methods below are the same as above - overloaded so I can use either ArrayList<ArrayList> or int[][]

	private static boolean checkSquare(ArrayList<ArrayList> grid, int baseRow, int baseCol, int subSquareSize) {
		
		boolean[] found = new boolean[subSquareSize * subSquareSize];
		
		for(int row = baseRow; row < subSquareSize; row++) {
			
			for (int col = baseCol; col < subSquareSize; col++) {
			
				 String index =  ((JTextComponent) grid.get(row).get(col)).getText();
				 int myIndex = Integer.parseInt(index) - 1; 
				if (!found[myIndex]) found[myIndex] = true;
				else return false;
				
				
			}
			
			
			
		}
		
		
		
		
		return true;
	}

	private static boolean checkColumn(ArrayList<ArrayList> grid, int col) {
		
		final int size = grid.size(); // column size
		
		boolean[] found = new boolean[size]; // array of values
		
		for (int row = 0; row < grid.size(); row++) { // loops through rows
			
			String index = ((JTextComponent) grid.get(row).get(col)).getText(); // index of current value
			int myIndex = Integer.parseInt(index) - 1;
			if (!found[myIndex]) found[myIndex] = true; // first time value occurs, sets it to true
			else return false; // value has occurred twice, so invalid solution, returns false
			
		}
		
		return true; // returns true on valid column
	}

	private static boolean checkRow(ArrayList<ArrayList> grid, int row) {
		final int size = grid.size();
		 boolean[] found = new boolean[size];
		 for (int col = 0; col < size; col++) { // begin loop A
		 // set found[x - 1] to be true if we find x in the row
		 String index = ((JTextComponent) grid.get(row).get(col)).getText();
		 int myIndex = Integer.parseInt(index) - 1;
		 if (!found[myIndex]) {
			 found[myIndex] = true;
		 } else {
		 // found it twice, so return false
		 return false;
		 }
		 
		 } // end loop A
		 // didn't find any number twice, so return true
		 return true; 
	}

	private static boolean checkValues(ArrayList<ArrayList> grid, int min, int max) {
		
		for (ArrayList row : grid) { // begin loop A
			
			for (int x = 0; x < row.size(); x++) { // begin loop B
				// if a value is invalid, failed sudoku solution, so returns false
				String col = ((JTextComponent) row.get(x)).getText();
				try { // some values might not be numbers, so I try it first
					int colValue = Integer.parseInt(col);
				    if ((colValue < min) || (colValue > max)) return false; 				
				} catch (Exception ex) { // if there's an error using the number parse method, returns false.
					return false;
				}
			
			} // end of loop B		
		} // end loop A
		return true; 
		}	// end of function            
	
		
} // end of class
		