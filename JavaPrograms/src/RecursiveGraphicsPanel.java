import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


import javax.swing.JPanel;

public class RecursiveGraphicsPanel extends JPanel {
	private static int[][] colors; // colors for the displayed Hs or Squares.
	private int n; // levels of Hs or Squares to draw
	private int drawType; // if 1, Squares are drawn, if 0, Hs
	
	
	public static void draw(Graphics g, int n, double sz, double x, double y, int drawType)
	 {	
		 // if n is 0 the recursion stops	
		 if (n == 0) return;
		 
		 // a color is selected at index n of the colors array
		 Color myColor = new Color(colors[n][0], colors[n][1], colors[n][2]);	
		 g.setColor(myColor);
		 
		 // locations used to draw Hs and Squares
		 double x0 = x - sz / 2, x1 = x + sz/2;
		 double y0 = y - sz / 2, y1 = y + sz/2;
		 
		 if (drawType == 0) {
		 	
			 
			 // draw the 3 line segments of the H
			 g.drawLine((int)x0, (int)y, (int)x1, (int)y);
			 g.drawLine((int)x0, (int)y0, (int)x0, (int)y1);
			 g.drawLine((int)x1, (int)y0, (int)x1, (int)y1); 
			 // recursively draw 4 half-size
			 // H-trees of order n-1
			  draw(g, n-1, sz/2, x0, y0, drawType);
			  draw(g, n-1, sz/2, x0, y1, drawType);
			  draw(g, n-1, sz/2, x1, y0, drawType);
			  draw(g, n-1, sz/2, x1, y1, drawType); 	
			
		 } else {
			 // draws a rectangle
			 
			 // I found this method of changing the stroke on StackOverflow - no idea if it's optimal.
			 Graphics2D g2 = (Graphics2D) g; // downcast in order to get access to setStroke    
			 g2.setStroke(new BasicStroke(10)); // makes the Square borders 10 pixels thick
			 g.drawRect((int)x0, (int)y0, (int)sz, (int)sz); // draws a rectangle
			 
			 
			 // recursive rectangle drawing
			 
			 draw(g, n - 1, sz / 2, x0, y0, drawType);
			 draw(g, n - 1, sz / 2, x0 + sz, y0, drawType);
			 draw(g, n - 1, sz / 2, x0, y0 + sz, drawType);
			 draw(g, n - 1, sz / 2, x0 + sz, y0 + sz, drawType);
			 
			 
		 } // end else
		 
		 
			 
	 } 	// end draw()
	
	
	
	
	
	public RecursiveGraphicsPanel(int n, int drawType) {
		
		this.n = n;
		this.drawType = drawType;
		
		// generates array of random colors used to color Squares / Hs
		
		this.colors = new int[n + 1][3];
		
		for (int k = 0; k <= n; k++) {
			
			for (int z = 0; z < colors[0].length; z++ ) {
				
				colors[k][z] = 1 + (int)(Math.random() * 255); 
			}
			
		}
		
		
	}
	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		int x = getWidth();
		int y = getHeight();
		
		
		// draws squares or Hs
		draw(g, n, x / 2, x / 2, y / 2, this.drawType);
	}
	
	
	

}
