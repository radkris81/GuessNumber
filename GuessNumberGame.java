import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class GuessNumberGame {
	
	//Enum to store the possible entries that user can provide.
	public enum UserEntries {YES, READY, HIGHER, LOWER, END};

	private BufferedReader buffReader = null;
	
	public static void main(String[] args) throws Exception{
		
		GuessNumberGame numberGame = new GuessNumberGame();
		
		numberGame.printWelcomeMessage();
		
		boolean beginGame = false;		
		
		while (!beginGame)
		{
			String inputString = numberGame.buffReader.readLine();
			if(UserEntries.READY.toString().equalsIgnoreCase(inputString))
			{
				beginGame = true;
			}
			else
			{
				System.out.println("Please enter a valid Entry. Enter READY to begin the game") ;	
			}
		}
		
		numberGame.performNumberGuessLogic();
		numberGame.closeReader();		

	}
	
	/** Method to print the welcome message
	 * 
	 */
	private void printWelcomeMessage(){
		
		System.out.println("Welcome to the Number Guessing Game!!!! ");
		System.out.println("Please choose a number between 1 - 500 and answer the questions accordingly.");
		System.out.println("Enter 'Ready' when you are ready to play");
		
	}
	
	/**
	 * Method to perform the core guessing logic.
	 */
	private void performNumberGuessLogic(){
		String inputString;
		boolean gameOver = false;
		List<Integer> guessedNumbersList = new ArrayList<Integer>();
		int number = 500;		
		int numberOfGuess = 0;
		int maxAllowedNumber = 500;
		int previousLowestNumber = 0;
		int previousHighestNumber = 500;		
		String lastCheck = null;
		
		
		System.out.println("Is the number you guessed " + number );
		
		// Array list to maintain the numbers that were guessed at the appropriate indexes.
		guessedNumbersList.add(numberOfGuess, number);
		
				
		while (!gameOver)
		{
			try
			{
				inputString = buffReader.readLine();	
				if(UserEntries.LOWER.toString().equalsIgnoreCase(inputString))
				{
					numberOfGuess++;
					previousHighestNumber = number;
					 
					//last check string is used to track the previous answer from the user.
					if(lastCheck != null){
						
						//If the current answer from user is LOWER 
						//and the previous answer is HIGHER, then the new number will be the addition of the 
						//previously guessed number two indexes before and the average of the last two guessed numbers
						// if the previous answer is LOWER, then it will be the average of the previous lowest guessed number
						//and the current number
						if(UserEntries.HIGHER.toString().equalsIgnoreCase(lastCheck))
						{
							number = guessedNumbersList.get(numberOfGuess - 2) + (guessedNumbersList.get(numberOfGuess-1) - guessedNumbersList.get(numberOfGuess-2))/2;
						}
						else if (UserEntries.LOWER.toString().equalsIgnoreCase(lastCheck))
						{
							number = (previousLowestNumber + number)/2;
						}
						
						else
						{
							number = number/2;
						}
					}
					else
					{
						number = number/2;
					}
					guessedNumbersList.add(numberOfGuess, number);
					lastCheck = UserEntries.LOWER.toString();
					System.out.println("The number you guessed is " + number);	
					
				}
				else if (UserEntries.HIGHER.toString().equalsIgnoreCase(inputString))
				{	
					numberOfGuess++;
					previousLowestNumber = number;
					if(lastCheck != null)
					{
						//If the current answer from user is HIGHER 
						//and the previous answer is LOWER, then the new number will be the addition of the 
						//last guessed number and the average of the last two guessed numbers
						//if the previous answer is LOWER, then it will be the average of the previous highest guessed number
						//and the current number
						if(UserEntries.LOWER.toString().equalsIgnoreCase(lastCheck))
						{
							number = guessedNumbersList.get(numberOfGuess - 1) + (guessedNumbersList.get(numberOfGuess-2) - guessedNumbersList.get(numberOfGuess - 1))/2;
						}
						else if (UserEntries.HIGHER.toString().equalsIgnoreCase(lastCheck))
						{
							number = (previousHighestNumber + number)/2;
						}
						else
						{
							//following logic is in place to ensure that the guessed number never exceeds the maximum allowed number
							int newNumber = number + (number/2);
							if (newNumber > maxAllowedNumber)
							{
								number = (number + maxAllowedNumber)/2;
							}
							else
							{
								number = newNumber;
							}			
						}
					}
					else
					{
						number = number + (number/2);
					}
					
					guessedNumbersList.add(numberOfGuess, number);
					lastCheck = UserEntries.HIGHER.toString();
					System.out.println("The number you guessed is " + number);		
				}
				else if (UserEntries.YES.toString().equalsIgnoreCase(inputString))
				{
					System.out.println("The number you guessed is " + number + " and the number of guesses that the program took is " + guessedNumbersList.size());	
					gameOver = true;	
					
				}
				else if (UserEntries.END.toString().equalsIgnoreCase(inputString))
				{
					System.out.println("User requested to end the game after " + numberOfGuess + " guesses") ;	
					gameOver = true;	
					
				}
				else
				{
					System.out.println("Please enter a valid Entry. Entries can be one of the following: YES or READY or HIGHER or LOWER or END") ;					
				}
			}
			catch(IOException e)
			{
				System.out.println("Problem reading input");
			}
			
		}
		
		
	}
	
	public GuessNumberGame() {
		try 
		{
			buffReader = new BufferedReader(new InputStreamReader(System.in));
		} 
		catch (Exception e) 
		{
			System.out.println ("Error while creating a BufferedReader: " + e.getMessage());
		}
	}
	
	public void closeReader() {
	 	try 
	 	{
	 		buffReader.close();
	 	} 
	 	catch (Exception e) 
	 	{
	 		System.out.println ("Error while closing the buffered reader");
	 	}		
	}
	
}
