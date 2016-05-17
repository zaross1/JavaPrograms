/***********************************************
*  Jason Mortensen
*  Compilation: javac CrapsGame.java
*  Execution: java CrapsGame
*   
*  this is a Craps game program. A dialog box pops up, and the user enters the number of times the simulated craps games should be run. 
*  After all the games have been run, some relevant statistics are calculated, and then displayed in a JPanel. 
*
***************************************************/
import java.awt.Color;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class CrapsGame {
	 

	private static final Random randomNumbers = new Random(); // random number generator

	private enum Status {CONTINUE, WON, LOST}; 

	private static final int SNAKE_EYES = 2;
	private static final int TREY = 3;
	private static final int SEVEN = 7;
	private static final int YO_LEVEN = 11;
	private static final int BOX_CARS = 12;






	public static int rollDice() {

		// simulates rolling two dice and summing their total
		
		int die1 = (randomNumbers.nextInt(6) + 1 );
		int die2 = (randomNumbers.nextInt(6) + 1 );
		int sum = die1 + die2;
		return sum;

	}

	public static String chatter(boolean chatterStatus) {
		// used to display motivational messages for the player
		String[] sentences = new String[] {"You can do it!\n", "Go for it!\n", "You're amazing!\n", "Give up!\n", "It's over!\n", "You're terrible!\n"};


		if (!chatterStatus) {

			int random = (int)(Math.random() * 3);

			return sentences[random];

		} else {

			int random = 3 + (int)(Math.random() * 3);
			return sentences[random];
		}


	}
	
	
	
	public static double[] calculateWinLossPercent(int[][] gameData, int totalGames) { 
		// calculates percentage of wins and losses
		
		double[] percentagesWinLoss = new double[2]; // stores percentage of wins versus losses
		
		int wins = 0; // stores number of wins
		
		for (int x = 0; x < gameData[0].length; x++) { // calculates number of wins		
			wins += gameData[0][x];
				
		}
		
		percentagesWinLoss[0] =  100 *((double) wins / totalGames); // calculates percentage of wins
		percentagesWinLoss[1] = 100 - percentagesWinLoss[0];  // calculates percentage of losses
		
		return percentagesWinLoss;
	}
	
	
	public static double calculateAverageLength(int[][] gameData, int totalGames) {
	
		/*  
		
		computes the average length of a Craps game -- the number of turns before the game is over. 
		This is done by:
		
			N = turn that game ended on    // this is the weight
			O = this is the number of times a game ended on turn N
			Sum = (for all N while N is 20 or less: Sum += (O*N)))  // I also add 25 * O, when N is 21   
			
			Average = Sum / totalGames
		
		*/
							
		double averageGameLength = 0; // stores average game length
		double weightedValues = 0.0; // stores weighted values for calculating average game length
		// I looked at the distribution of 20+ turns, and the median is in the twenties.
		double twentyPlusWeight = 25; // For our craps game, because we don't care about excessive precision, I'm just going use 25 as the weight in the calculation.
		
		
		for (int x = 1; x < (gameData[0].length); x++) {
			// computes weightedValues
			if (x < (gameData[0].length - 1)) weightedValues += ( x * (gameData[0][x] + gameData[1][x]));   
			else weightedValues += (twentyPlusWeight * (gameData[0][x] + gameData[1][x]));  	
        }
		
		averageGameLength = weightedValues / totalGames; // computes average
		
		return averageGameLength;
	}
	
	
	


	public static void main(String[] args) {




		// 7 or 11 player wins
		// 2, 3, 12, house wins, player loses
		// other than winning or losing: 4, 5, 6, 8, 9, 10: repeat
		// the point is 4, 5, 6, 8, 9, 10
		// to win, after first roll, then you have to get your point
		// but if you get 7, you lose





		int myPoint; // used in craps game
		Status gameStatus; // keeps track of game status
		int sumOfDice; // rolls two dice
		int numberOfGames = 0; // store number of games to be played
		int turnCounter; // keeps track of the number of turns in a given game
		int[][] gameData = new int[2][22]; // turn frequency, wins: index 0; losses, turn frequency: index 1
		double[] winsAndLosses = new double[2]; // stores percent of wins and losses
		double averageLength = 0; // stores average length of a game of craps (average number of turns before the game ends)
		
		do { // prompts the user to enter the number of times the craps game will be played.
			
			try {
		        numberOfGames = Integer.parseInt(JOptionPane.showInputDialog("Enter number of Craps games to play(1 - 10,000,000)"));	
			} catch (Exception ex) { // if there's an exception, its caught and printed
				System.out.println(ex.getMessage());
			}
			
		} while ((numberOfGames < 1) || (numberOfGames > 10000000)); // the user will repeatedly be prompted unless they enter a value in this range
		
		

		for (int x = 0; x < numberOfGames; x++ ) { // plays the craps game equal to numberOfGames

			myPoint = 0; // resets point to 0
			turnCounter = 1; // resets turnCounter to 1
			sumOfDice = rollDice(); // rolls two dice, return their sum
	
	
	
			switch (sumOfDice) { // win, loses, or continue, depending on sum
	
			case SEVEN:
			case YO_LEVEN:
				gameStatus = Status.WON;
				gameData[0][turnCounter]++; // a first turn win, so the first turn victory counter is incremented
				
				break;
	
			case SNAKE_EYES:
			case TREY:
			case BOX_CARS:
				gameStatus = Status.LOST;
				gameData[1][turnCounter]++; // a first turn loss, so the first turn loss counter is incremented
						
				break;
	
			default:
				gameStatus = Status.CONTINUE;
				myPoint = sumOfDice; // assigns point sum of dice
				break;
			}
			
			
			
	
			while (gameStatus == Status.CONTINUE) { // continues the game of Craps until the game is won or lost.
				
				turnCounter++;  // each roll of the dice constitutes a turn, so turn is incremented to next turn.
				
				sumOfDice = rollDice();
	
				if (sumOfDice == myPoint) {
					gameStatus = Status.WON;
					try { // attempts to increment wins.
						
						gameData[0][turnCounter]++;
						
					} catch (ArrayIndexOutOfBoundsException ex) { // if turncounter exceeds the index of the victory array, the last element is incremented, which keeps track of 20+
						gameData[0][gameData[0].length - 1]++;
						
					}
					
					
	
				} else if (sumOfDice == SEVEN) {
					gameStatus = Status.LOST;
					
					try { // attempts to increment losses
						
						gameData[1][turnCounter]++;
						
					} catch (ArrayIndexOutOfBoundsException ex) { // if turnCounter exceeds last index of loss array, the last index of loss array is incremented.  
						gameData[1][gameData[1].length - 1]++;
						
					}
	
				}
	
				
				
			} // end while
		
			
			} // end for
			
			
			averageLength = calculateAverageLength(gameData, numberOfGames);
			winsAndLosses = calculateWinLossPercent(gameData, numberOfGames);
			
			
			
			// creates Jframe and adds a panel to it, which will display Craps data
			JFrame frame = new JFrame();
			JPanel panel = new CrapsDisplayInfoPanel(gameData, averageLength, winsAndLosses, numberOfGames);
			panel.setBackground(Color.BLACK);
			frame.setSize(1000, 800);
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			frame.setTitle("Craps Simulation Game Data");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(panel);
			
			
			
	} // end main
} // end Craps class

