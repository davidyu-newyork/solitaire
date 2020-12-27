/**
* The <code>CardStack</code> class implements a instance of CardStack
* objects.
*    
*Recitation number: 08
* @author D.J.Y David Justin Yu
*    e-mail: david.yu@stonybrook.edu
*    Stony Brook ID:111922653
**/
package homework3_cse214;

import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

public class CardStack extends Stack<Card> {
	private char type; // type of stack such as waste, tableu,foundation, stock
	private Stack<Card> stack; // where our stack is

	/**
	 * default constructor for our cardStack
	 */
	public CardStack() {
		super();
		type = ' ';
		stack = new Stack<Card>();
	}

	/**
	 * constructor for our CardStack, it sets what type of stack it is and generates
	 * a stack
	 * @param type
	 */
	public CardStack(char type) {
		super();
		this.type = type;
		stack = new Stack<Card>();

	}

	/**
	 * getter method for type of stack
	 * 
	 * @return type
	 */
	public char getType() {
		return type;
	}

	/**
	 * method that shuffles our stack(deck), using Collections
	 */
	public void shuffle() {
		Collections.shuffle(stack);
	}

	/**
	 * (non-Javadoc)
	 * method we use to "push" a Card onto a stack
	 * @return Card
	 * @see java.util.Stack#push(java.lang.Object)
	 */
	public Card push(Card item) {
		return stack.push(item);
	}

	/**
	 * (non-Javadoc)
	 * method we use to "pop" the top of stack off
	 * @return Card
	 * @see java.util.Stack#pop()
	 */
	@Override
	public Card pop() {
		return stack.pop();
	}
	/**
	 * (non-Javadoc)
	 * method that we use to see if stack is empty
	 * @return true or false
	 * @see java.util.Vector#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return stack.isEmpty();
	}

	/**
	 * (non-Javadoc)
	 * method that we use to check how big the stack is
	 * @return int , how big stack is
	 * @see java.util.Vector#size()
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return stack.size();
	}

	/**
	 * (non-Javadoc)
	 * method that we use to "peek" at top of stack
	 * @see java.util.Stack#peek()
	 */
	@Override
	public Card peek() {
		try {
			return stack.peek();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * method that clears our stack
	 * @see java.util.Vector#clear()
	 */
	@Override
	public void clear() {
		stack.clear();
	}

	/**
	 * method that iterates through the stack depending on type, and prints it out
	 */
	public void printStack() {
		Iterator<Card> iterator = stack.iterator();

		switch (type) {
		case 's':
			if (stack.size() > 0) {
				System.out.print(stack.peek());

			} else
				System.out.print("[  ]");

			break;
		case 'w':
			if (stack.size() > 0) {
				System.out.print(stack.peek().toString());

			} else
				System.out.print("[  ]");

			break;

		case 't':
			if (stack.size() > 0) {
				while (iterator.hasNext()) {
					System.out.print(iterator.next().toString() + " ");
				}
			}

			break;

		case 'f':
			if (stack.size() > 0) {
				System.out.print(stack.peek().toString());
			} else
				System.out.print("[  ]");

			break;

		default:
			while (iterator.hasNext()) {
				System.out.print(iterator.next().toString() + " ");
			}
			break;
		}
	}

}
