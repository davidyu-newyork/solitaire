/**
* The <code>Card</code> class implements a instance of Card
* objects.
*    
*Recitation number: 08
* @author D.J.Y David Justin Yu
*    e-mail: david.yu@stonybrook.edu
*    Stony Brook ID:111922653
**/
package homework3_cse214;

public class Card {
	
	private int suit, value;
	private boolean isFaceUp;
	
	
	private String values[] = {" ","A","2","3","4","5","6","7","8","9","10","J","Q","K"};
	private char suits[]    = {' ', '\u2666', '\u2663','\u2665', '\u2660'};
	/**
	 * default constructor
	 */
	public Card() {
		suit = 0;
		value = 0;
		isFaceUp = false;
	}
	/**
	 * constructor for our card, setting suit, value, and whether or not it's faceup
	 * @param suit
	 * @param value
	 * @param isFaceUp
	 */
	public Card(int suit, int value, boolean isFaceUp) {
		super();
		this.suit = suit;
		this.value = value;
		this.isFaceUp = isFaceUp;
	}


	/**
	 * getter method for suit
	 * @return suit 
	 */
	public int getSuit() {
		return suit;
	}
	/**
	 * setter method for suit
	 * @param suit , this is what we set suit to
	 */
	public void setSuit(int suit) {
		this.suit = suit;
	}
	/**
	 * getter method for value
	 * @return value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * setter method for value
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * getter method for isFaceUp, true or false
	 * @return isFaceUp
	 */
	public boolean isFaceUp() {
		return isFaceUp;
	}
	/**
	 * setter method for faceUp
	 * @param faceUp
	 */
	public void setFaceUp(boolean faceUp) {
		isFaceUp = faceUp;
	}
	/**
	 * method which we use to determine what color a card is
	 * @return true or false, whether it's red or not
	 */
	public boolean isRed() {
		if(suit == 1 || suit == 3) return true;
		else 
			return false;
	}
	/** (non-Javadoc)
	 * this method returns string representation of card
	 * @return String
	 * @see java.lang.Object#toString()
	 */
	
	public String toString() {
		if (isFaceUp) {
			return "[" + values[value]+suits[suit] + "]";
			
		}
		else		 	
			return "[XX]";
	}


}
