


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CrapsDisplayInfoPanel extends JPanel {

	private int[][] gameData; // stores craps game data wins / losses for turns 1 - 20+
	private double averageLength; // stores average length of a craps game
	private double[] winsAndLosses; // stores percentage of wins and losses
	private int gamesPlayed; // stores the number of games played
	private Font titleFont = new Font("Comic Sans MS", Font.BOLD, 20);
	private Font valueFont = new Font("Comic Sans MS", Font.PLAIN, 12);
	public CrapsDisplayInfoPanel(int[][] gameData, double averageLength, double[] winsAndLosses, int gamesPlayed) {
		this.gameData = gameData;
		this.averageLength = averageLength;
		this.winsAndLosses = winsAndLosses;
		this.gamesPlayed = gamesPlayed;
	}
	
	
	
	public void displayData (Graphics g, int width, int height) {
		
		String[] columns = {"Number of Turns", "Games Ending In a Win", "Games Ending In a Loss" }; 
		
		g.setColor(Color.CYAN);
		g.setFont(titleFont);
		
	     // draw column titles	
		for (int x = 0; x < columns.length; x++) {
					
			g.drawString(columns[x], ((width / 15) + 4 * x * (width / 15)) , (height / 20) );
			
	    }
		
		g.setFont(valueFont);
	
		for (int x = 0; x < gameData[0].length; x++) { // begin for B
			
			
			// draw turns
			if (x < (gameData[0].length - 2)) {
				// draw turn data
				g.drawString(((Integer)(x+ 1)).toString(), (width / 14)  , ((height / 13) + x * (height / 30) ));
				g.drawString(((Integer)gameData[0][(x+ 1)]).toString(), (width / 3)  , ((height / 13) + x * (height / 30) ));
				g.drawString(((Integer)gameData[1][(x+ 1)]).toString(), (int)(width / 1.5)  , ((height / 13) + x * (height / 30) ));
				// draw bottom row borders
				if (x > 0) g.drawLine((width / 15), ((height / 13) + x * (int)(height / 29.9) )   ,(width - (width / 6)) , ((height / 13) + x * (int)(height / 29.9)) );
				else g.drawLine((width / 15), (int)(height / 12.25) ,(width - (width / 6)) , (int)(height / 12.25) ); 
				
			} else if (x == (gameData[0].length - 1) ) {
				g.drawString("20+", (width / 14)  , (int)((height / 22) + x * (height / 30) ));
				// draw turn data
				g.drawString( ((Integer)gameData[0][(gameData[0].length - 1)]).toString(), (width / 3)  , (int)((height / 22) + x * (height / 30) ));
				g.drawString( ((Integer)gameData[1][(gameData[1].length - 1)]).toString(), (int)(width / 1.5)  , (int)((height / 22) + x * (height / 30) ));
			}
			
			
			
			
			
					
		} // end for B

		// draw horizontal table border
		g.drawLine((width / 15), (height / 17), (width - (width / 6)), (height / 17));
		g.drawLine((width / 15), (int)(height - height / 3.75), (width - (width / 6)), (int)(height - height / 3.75));
		
		// draw vertical table border
		
		g.drawLine((width / 15), (height / 17), (width / 15), (int)(height - height / 3.75) );
		g.drawLine((width - (width / 6)), (height / 17), (width - (width / 6)), (int)(height - height / 3.75));
		
		// draw column separator lines
		
		g.drawLine((int)(width / 3.5), (height / 17), (int)(width / 3.5), (int)(height - height / 3.75) );
		g.drawLine((int)(width - (width / 2.4)), (height / 17), (int)(width - (width / 2.4)), (int)(height - height / 3.75));
		
		
		// display game statistics
		g.setFont(titleFont);
		g.drawString("Games Played: " + this.gamesPlayed, (width / 8), (height - (height / 6)));
		g.drawString(String.format("Games Won: %.2f%%", winsAndLosses[0]), (int)(width / 2.3), (height - (height / 6)));
		g.drawString(String.format("Games Lost: %.2f%%", winsAndLosses[1]), (int)(width / 1.4), (height - (height / 6)));
		g.drawString(String.format("Average Length of a Game: %.2f Turns", averageLength), (int)(width / 3 ), (height - (height / 12)));
	} // end displayData
	
	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
			
		displayData(g, width, height); // displays table with Craps data
				
		
	}
	
	
	
}
