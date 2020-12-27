/**
* The <code>Stackotaire</code> class implements a instance of Stackotaire
* objects. This is the "main" class for this homework, which has the game loop
*    
*Recitation number: 08
* @author D.J.Y David Justin Yu
*    e-mail: david.yu@stonybrook.edu
*    Stony Brook ID:111922653
**/
package homework3_cse214;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

public class Stackotaire{
	private Card[] allCards = new Card[52];//array of all cards that we use to check if user has won
	private CardStack main = new CardStack(' ');//main "deck" that we create
	private CardStack stock = new CardStack('s');//stock stack where we draw from
	private CardStack F1 = new CardStack('f'); //All these F"n" are the foundation stacks
	private CardStack F2 = new CardStack('f');
	private CardStack F3 = new CardStack('f');
	private CardStack F4 = new CardStack('f');//end of foundation stacks
	private CardStack T1 = new CardStack('t');//tableu stacks
	private CardStack T2 = new CardStack('t');
	private CardStack T3 = new CardStack('t');
	private CardStack T4 = new CardStack('t');
	private CardStack T5 = new CardStack('t');
	private CardStack T6 = new CardStack('t');
	private CardStack T7 = new CardStack('t');//end of tableu stacks
	private CardStack waste = new CardStack('w');//waste stack
	private CardStack[] tableuArray = {T1,T2,T3,T4,T5,T6,T7};//array with all tableu stacks
	private CardStack[] foundationArray = {F1,F2,F3,F4};//array with all foundation stacks
	private Scanner input = new Scanner(System.in);//what we use to take inputs (scanner)
	private String[] tokens;//tokens is array of user inputs which we split
	
	/**
	 * main method where we instiantiate an instance of Stackotaire and start the loop
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("commands - draw, restart,move f1 f2, moven f1 f2 n,quit");
		Stackotaire game = new Stackotaire();
		game.gameLoop();
	}
	
	/**
	 * clears all stacks, then creates new deck, then shuffles and distrubutes cards
	 */
	public void refresh() {
		main.clear();
		stock.clear();
		F1.clear(); 
		F2.clear(); 
		F3.clear(); 
		F4.clear();
		T1.clear();
		T2.clear();
		T3.clear();
		T4.clear();
		T5.clear();
		T6.clear();
		T7.clear();
		waste.clear();
		createDeck();
		main.shuffle();
		cardsToStacks();
		
	}
	/**
	 * main game loop, where we print out the board, take inputs, and according to input execute certain method
	 */
	public void gameLoop() {
		boolean flag = true;
		refresh();
		while(flag) {
			setTopFaceUp();
			printBoard();
			takeInput();
			if (checkWin()) {
				String yesNo = input.nextLine();
				yesNo = yesNo.toLowerCase();
				System.out.println("you won! replay? yes or no");
				if (yesNo.equals("yes")) {
					refresh();
					
				}
				else {
					System.out.println("terminating program");
					System.exit(0);
				}
				
			}
	
		}
	}
	/**
	 * method where we take user input and split it into an array, where we can see what action the user wants
	 */
	public void takeInput() {
		System.out.println("enter ur move:");
		String userInput = input.nextLine();
		userInput = userInput.toLowerCase();
		tokens = userInput.split("[ ]+");
		
		switch (tokens[0]) {
		case "move":
			if(move(tokens[1],tokens[2]))
				System.out.println("move successful");
			else System.out.println("move was invalid");
			break;
		case "moven":
			if(moveN(tokens[1], tokens[2], tokens[3]))
				System.out.println("move successful");
			else System.out.println("move invalid");
			break;
		case "draw":
			draw();
			break;
		case "restart":
			System.out.println("restart? : enter yes or no");

			userInput = input.nextLine();
			userInput = userInput.toLowerCase();
			if (userInput.equals("yes")) {
				System.out.println("restarting!");
				refresh();
			}
			else System.out.println("returning to game");
			break;
		case "quit":
			System.out.println("exiting game u lose");
			System.exit(0);
			break;
		default:
			System.out.println("invalid input");
			break;
		}
	}
	/**
	 * method where we move a card from stock to waste pile(drawing a carD)
	 * @return true or false, depending if there is cards to draw
	 */
	public boolean draw() {
		try {
			waste.push(stock.pop());
			waste.peek().setFaceUp(true);
			return true;

			
		} catch (Exception e) {
			while(waste.size()>0) {
				stock.push(waste.pop());
				stock.peek().setFaceUp(false);
			}
			waste.push(stock.pop());
			waste.peek().setFaceUp(true);
		
		}
		return false;
	}
	/**
	 * method where we see if the move is valid and the cards are acceptable suit and value
	 * @param x , stack where we want to move card from
	 * @param y , stack where we want to move card to
	 * @return true or false, depending on if move is a valid move
	 */
	public boolean checkValidValue(CardStack x, CardStack y) {
		if(x.size()==0) return false;
		if (y.getType()=='f') {
			if (x.peek().getValue()==1 && y.size()==0) {
				return true;
				
			}
			if (y.size() > 0 && x.peek().getSuit() == y.peek().getSuit() ) {
				return true;
				
			}
			return false;
			
		}
		
		if (y.getType() =='t') {
			if (y.size()== 0 && x.peek().getValue() ==13) {
				return true;
			}
			if(y.size()>0&& x.peek().getValue()+1 == y.peek().getValue() && x.peek().isRed() != y.peek().isRed()) {
				return true;
			}
			return false;
			
		}
		
		return false;
		
	}
	/**
	 * method where user wants to move multiple cards at once
	 * @param p1 ,stack where we want to move card from
	 * @param p2 , stack where we want to move card to
	 * @param num , how many cards we want to move
	 * @return true or false, depending if move was successful
	 */
	public boolean moveN(String p1, String p2, String num) {
		char one = p1.charAt(0);
		int oneN = Character.getNumericValue(p1.charAt(1))-1;
		char two = p2.charAt(0);
		int twoN = Character.getNumericValue(p2.charAt(1)) -1;
		int num1 = 0;
		try {
			num1 = Integer.parseInt(num);
			System.out.println("this is num1:"+num1);
			
		} catch (Exception e) {
			return false;
		}	
		if (one == 't') {
			switch (two) {
			
			case 't':
				CardStack tempStack = new CardStack();
				if (tableuArray[oneN].size()>0 && tableuArray[twoN].size()>0) {
					for (int i = 0; i < num1; i++) {
						tempStack.push(tableuArray[oneN].pop());
				
					}
					if (tempStack.peek().getValue()+1 == tableuArray[twoN].peek().getValue()&&
							tempStack.peek().isRed() != tableuArray[twoN].peek().isRed()) {
						for (int i = 0; i < num1; i++) {
							tableuArray[twoN].push(tempStack.pop());
							
						}
						return true;

						
					}
					else {
						for (int i = 0; i < num1; i++) {
							tableuArray[oneN].push(tempStack.pop());
					
						}
						return false;
						
					}
				}
				if (tableuArray[twoN].size() == 0) {
					for (int i = 0; i < num1; i++) {
						tableuArray[twoN].push(tempStack.pop());
						
					}
					if (tempStack.peek().getValue()==13) {
						for (int i = 0; i < num1; i++) {
							tableuArray[twoN].push(tempStack.pop());
							
						}
						
					}
					else {
						for (int i = 0; i < num1; i++) {
							tableuArray[oneN].push(tempStack.pop());
					
						}
					}

					return true;
				} 
									
				break;
			default:
				break;
			}
	
		}
		return false;
	}
	/**
	 * method where we want to move one card to another stack
	 * @param p1 ,stack where we want to move card from
	 * @param p2 ,stack where we want to move card to
	 * @return true or false, depending if move was successful
	 */
	public boolean move(String p1, String p2) {

		char one = p1.charAt(0);
		int oneN = Character.getNumericValue(p1.charAt(1))-1;
		char two = p2.charAt(0);
		int twoN = Character.getNumericValue(p2.charAt(1)) -1;
		if (one =='w' ) {
			switch (two) {
			case 'f':
				if (checkValidValue(waste, foundationArray[twoN])) {
					foundationArray[twoN].push(waste.pop());
					return true;
				}
				
				break;
			case 't':
				if (checkValidValue(waste, tableuArray[twoN])) {
					tableuArray[twoN].push(waste.pop());
					return true;
				}
				break;
			default:
				break;
			}
			
			
		}
		if (one =='f' ) {
			switch (two) {
			case 't':
				if (checkValidValue(foundationArray[oneN], tableuArray[twoN])) {
					tableuArray[twoN].push(foundationArray[oneN].pop());
					return true;
				}
				break;
			default:
				break;
			}
				
		}
		if (one == 't') {
			switch (two) {
			case 'f':
				if (checkValidValue(tableuArray[oneN], foundationArray[twoN])) {
					foundationArray[twoN].push(tableuArray[oneN].pop());
					return true;
				}
				
				break;
			case 't':
				if (checkValidValue(tableuArray[oneN], tableuArray[twoN])) {
					tableuArray[twoN].push(tableuArray[oneN].pop());
					return true;
				}
				break;
			default:
				break;
			}
				
		}
		
		return false;
	}
	/**
	 * method where we print out entire board
	 * we will loop through each stack and print the cards using CardStack method printStack()
	 */
	public void printBoard() {
		int tempN = 1;
		for (int i = 0; i < 4; i++) {
			if (foundationArray[i].size() >0 ) {
				foundationArray[i].printStack();
				tempN++;
			} else {
				System.out.print("[F"+tempN+"]");
				tempN++;

			}	
			
		}
		System.out.printf("%7s","");
		if (waste.size() >0 ) {
			System.out.print("W1");
			waste.printStack();			
		} else {
			System.out.print("W1  [   ]");

		}
		if (stock.size() >0 ) {
			stock.printStack();	
			System.out.print(" " +stock.size());
		} else {
			System.out.print("[ ]" + 0);

		}
		System.out.println();


		for (int i = 6; i >= 0; i--) {
			int temp = i+1;
			System.out.print("T" + temp + "-" );
			if(tableuArray[i].size()>0) {
				tableuArray[i].printStack();
				
			}
			else {
				for (int j = 0; j < i; j++) {
					System.out.print("[  ]");
					
				}
				
			}
			System.out.println();

		}
		
	}
	/**
	 * method where we check if player has won, if all cards are faceup it means player has won
	 * @return true or false
	 */
	public boolean checkWin() {
		for (int i = 0; i < allCards.length; i++) {
			if (!allCards[i].isFaceUp()) {
				return false;			
			}
			
		}
		return true;
	}
	/**
	 * method where we set the top cards of stacks to face up
	 */
	public void setTopFaceUp() {
		int counter = 1;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < counter; j++) {
				if (tableuArray[i].size()>0) {
					tableuArray[i].peek().setFaceUp(true);
				}			
			}
	
		}
		
	}
	/**
	 * method where we distrubute cards from deck to all the stacks in specific number depending on type of stack
	 */
	public void cardsToStacks(){
		int counter = 1;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < counter; j++) {
				tableuArray[i].push(main.pop());
				
			}
			counter++;
			
		}
		for (int i = 0; i < 24; i++) {
			stock.push(main.pop());
		
		}
		
	}
	/**
	 * method where we create 52 cards and put them in a main "deck", aswell as an array to hold all cards
	 * so we can easily loop through them and check if user has won
	 */
	public void createDeck() {
		int counter = 0;
		for (int i = 1; i < 14; i++) {
			for (int j = 1; j < 5; j++) {
				
				Card temp = new Card(j, i, false);
				main.push(temp);
				allCards[counter] = temp;
				counter++;
				
			}
			
		}
	}

}
