import java.awt.Color;


/************************************
 *  Jason Mortensen
 *   
 *  Compilation: javac RecursiveGraphics.java
 *  Execution: java RecursiveGraphics
 * 	Custom helper classes: RecursiveGraphicsPanel.java
 * 
 *  In this program the user is prompted to select either squares or H's, as well as the levels to draw at.
 * 	A panel is then displayed with many of the squares or H's drawn recursively.
 *  
 * 
 ************************************/



import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RecursiveGraphics {
	
	
	

	public static void main(String[] args) {
		
		

		// creates Jframe and adds a panel to it, which will display Craps data
		JFrame frame = new JFrame("Recursion Drawing");

		int userInput = 0; // user chooses how many levels of H to draw
		int drawType = -1; // user chooses 1 for squares, 0 for Hs
		
		do {
					try {
						drawType = Integer.parseInt(JOptionPane.showInputDialog("Enter 0 for Hs or 1 for squares: "));
						userInput = Integer.parseInt(JOptionPane.showInputDialog("Enter number of levels: "));
					} catch (Exception e) {
						
					}
		// the user is forced to enter valid values for drawType and userInput to exit loop		
		} while (((userInput < 1) || (userInput > 6)) || ((drawType != 1) && (drawType != 0)) ); 
		
		
		// JPanel, JFrame setup
		JPanel panel = new RecursiveGraphicsPanel(userInput, drawType);
		panel.setBackground(Color.WHITE);
		frame.setSize(800, 800);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Recursive Drawing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		
		
		
		
		

	} // end main

} // end RecursiveGraphics 
