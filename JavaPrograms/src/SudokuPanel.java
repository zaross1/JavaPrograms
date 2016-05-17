import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;

public class SudokuPanel extends JPanel { // this is the panel where the Sudoku game is played
	
	
	private ArrayList<ArrayList> board = new ArrayList<>(); // the board stores the JTextField boxes which constitute the board
	private int boardSize = 9; // length / width of board
	private Font numberFont = new Font("SansSerif", Font.BOLD, 20 ); // font used for numbers
	private String statusText = ""; // text informing user of correct or incorrect solution
	private int score = 0; // keeps track of users score. 10 points per successful game.
	private boolean hasScored = false; // used to keep track of whether a player has scored for a particular board
	
	
	public SudokuPanel() { 
		// I used MigLayout for the initial configuration
		setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][][][][grow][grow][grow][][][][][]", "[][][][][][][][][][]"));
		// imageIcon for use on button
		ImageIcon newGameIcon = new ImageIcon(createImage("C:/Users/nighteyes/workspace/Craps/resources/newGameIcon.jpg"));
		JButton newGameButton = new JButton("", newGameIcon); // this button displays a new board for the user
		add(newGameButton, "pos 87.5% 5%, wmax 75px,hmax 60px,aligny bottom"); // adds button to panel
    	newGameButton.addMouseListener(new MouseAdapter() { // 
    		@Override
    		public void mouseClicked(MouseEvent e) { // event handler for mouse click
    			
    			statusText = ""; // resets status text
    			
    			hasScored = false; // resets scored status for board 
    			
    			Sudoku.clearBoard(board); // clears the board
    			
    			Sudoku.setRandomGame(Sudoku.boards, board); // displays a new board
    			repaint(); // redraws
    		}
    	});
    	
    	// the check solution button checks user's attempt, and updates relevant info
    	ImageIcon checkSolutionIcon = new ImageIcon(createImage("C:/Users/nighteyes/workspace/Craps/resources/checkSolutionImage.jpg"));
		JButton checkSolutionButton = new JButton("", checkSolutionIcon);
		add(checkSolutionButton, "pos 87.5% 15%, wmax 70px,hmax 45px,aligny bottom");
    	checkSolutionButton.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			
    			if (Sudoku.checkSudokuSolution(board, 3)) {
    				
    				statusText = "Congratulations! Your Solution is correct!";
    				hasScored = true;
    				score += 10; 
    				
    			} else {
    				
    				statusText = "Sorry, your solution is incorrect.";
    				
    			}
    			repaint();
    			
    		}
    	});
		
		
		// board is configured for the first time -- the JTextFields are created
		int height = 5;
		for (int row = 0; row < boardSize; row++) { // begin loop A
			
				board.add(new ArrayList()); // new row 
				int initialWidth = 20; // horizontal position of starting textBox
				String location =  ""; // stores setup info for JTextBoxes
			
			for (int col = 0; col < boardSize; col++) { // begin loop B
				// JTextFields are configured, and added to the board.
				board.get(row).add(new JTextField());
			    ((JTextField) board.get(row).get(col)).setColumns(10);
			    ((JTextField) board.get(row).get(col)).setFont(numberFont);
			    ((JTextField) board.get(row).get(col)).setHorizontalAlignment(JTextField.CENTER);
			    location = "pos " + (initialWidth + col * 6) + "% " + height + "%," + "growx," + "wmax 5%," + "hmin 5%";
			    add((JTextField) board.get(row).get(col), location);
			} // end loop B
			
			height = height + 6;
			
		} // end loop A
		
		Sudoku.setRandomGame(Sudoku.boards, board);	// populates JTextFields with Sudoku starting board values		
				
	}
	
	
	
	
	
	public void paintComponent(Graphics g) { // draws game info
		
		super.paintComponent(g);
		
		int width = getWidth();
		int height = getHeight();
		g.setFont(numberFont);
		g.setColor(Color.CYAN);
		g.drawString(statusText, (width / 3), (height - height / 3));
		g.drawString(String.format("Score: %d ", score), (width - width / 8), (int)(height - height / 1.5));	
	}
	
	public static BufferedImage createImage(String img) { // used to turn file into BufferedImage
		  	
	 	BufferedImage myImage = null;
	  try {
		  
		myImage = ImageIO.read(new File(img));
		
	  } catch (IOException e) {
		  System.out.println(e.getMessage());
		 
	  }
	  return myImage;
	}
	
	
	

}
