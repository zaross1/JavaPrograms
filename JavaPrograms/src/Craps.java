
		
		/***********************************************
		* Jason Mortensen
		*  Compilation: javac Craps.java
		*  Execution: java Craps
		*  this is a Craps game program. The user plays at least once, placing a wager, and can no longer play when the user's bank account balance hits 0.
		*  
		*  example Output:
		*  
		*   The player rolled 1 and 6. The sum is: 7
		*   Player has won!
        *   Player Balance: $1450.00
		*
		*
		*  
		*  
		*
		*
		***************************************************/
		import java.util.Random;

		import javax.swing.JOptionPane;

		public class Craps {

			private static final Random randomNumbers = new Random(); // used to generate random numbers to represent dice rolls

			private enum Status {CONTINUE, WON, LOST}; // game states
			
			private static final int SNAKE_EYES = 2;
			private static final int TREY = 3;
			private static final int SEVEN = 7;
			private static final int YO_LEVEN = 11;
			private static final int BOX_CARS = 12;
			// negative / positive prompts
			private static String[] sentences = new String[] {"You can do it!\n", "Go for it!\n", "You're amazing!\n", "Give up!\n", "It's over!\n", "You're terrible!\n"};




			public static int rollDice() { // rolls two dice, prints outcome, returns sum

				int die1 = (randomNumbers.nextInt(6) + 1 );
				int die2 = (randomNumbers.nextInt(6) + 1 );
				int sum = die1 + die2;
				System.out.printf("The player rolled %d and %d. The sum is: %d\n", die1, die2, sum);
				return sum;

			}

			public static String chatter(boolean chatterStatus) { // prints either positive or negative message.
				
				if (!chatterStatus) {

					int random = (int)(Math.random() * 3);

					return sentences[random];

				} else {

					int random = 3 + (int)(Math.random() * 3);
					return sentences[random];
				}


			}


			public static void main(String[] args) {

				int myPoint = 0; // used to store current point for a game
				int bankBalance;  // player's bank account balance, used for wagering
				Status gameStatus; // current status of game (Continue, Won, Lost)
				int sumOfDice; // holds dice 
				int wager; // holds value of user wager
				
				bankBalance = 1000;


				do { // begin do / while A 

					

					wager = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter a wager")); // prompts user for input

				while (wager > bankBalance ) { // begin while A; prompts user to enter valid balance, re-prompts if not valid
					
					wager = Integer.parseInt(JOptionPane.showInputDialog(null, "enter a valid wager:"));
					
				} // end while A

				sumOfDice = rollDice(); // rolls dice, stores sum.



				switch (sumOfDice) { // performs action appropriate to dice roll

				case SEVEN: // 7, 11: user wins, updates bankBalance, displays info
				case YO_LEVEN: 
					gameStatus = Status.WON;  
					System.out.println("Player has won!");
					bankBalance += wager;
					System.out.printf("Player Balance: $%.2f\n\n", (double)bankBalance);
					break;

				case SNAKE_EYES:
				case TREY:
				case BOX_CARS:  // 2, 3, 12: player lost, displays info, updates bankBalance
					gameStatus = Status.LOST;
					System.out.println("Player has lost!");
					bankBalance -= wager;
					System.out.printf("Player Balance: $%.2f\n\n", (double)bankBalance);
					break;

				default: // game continues
					gameStatus = Status.CONTINUE; 
					myPoint = sumOfDice;
					System.out.printf("Point is %d\n", myPoint);
					break;
				}

				while (gameStatus == Status.CONTINUE) { // 

					sumOfDice = rollDice();

					if (sumOfDice == myPoint) {
						gameStatus = Status.WON;



					} else if (sumOfDice == SEVEN) {
						gameStatus = Status.LOST;


					}

					if (gameStatus == Status.LOST) {
						System.out.println("Player loses.");
						bankBalance -= wager;
						System.out.printf("Player Balance: $%.2f\n\n ", (double)bankBalance);
					} else if (gameStatus == Status.WON) {
						System.out.println("Player wins.");
						bankBalance += wager;
						System.out.printf("Player Balance: $%.2f\n\n ", (double)bankBalance);
					}



					if (bankBalance <= 0) {
						System.out.println("You're busted!\n\n");
					}

					if (bankBalance <= 100) { // provides positive / negative prompts
						System.out.println(chatter(true));
					} else {
						System.out.println(chatter(false));
					}

				}
			} while (bankBalance > 0 ); // end Do / while A


			} // end main
		
	} // end Craps class


