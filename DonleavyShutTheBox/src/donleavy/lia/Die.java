package donleavy.lia;

import java.util.Random;

/***
 * Class to represent a single Die
 */
public class Die {

	Random rand = new Random(); // Class wide variable

	private int value;
	private int numSides;

	/***
	 * Constructor for a Die - default to 6 sides
	 */
	public Die() {
		numSides = 6;
		value = roll();
	}

	/***
	 * Constructor for a Die - any size of die
	 * 
	 * @param n number of sides
	 */
	Die(int n) {
		numSides = n;
		value = roll();
	}

	/***
	 * Randomly assigns a new value to the die
	 * 
	 * @return value on top of the die
	 */
	public int roll() {
		value = rand.nextInt(numSides) + 1;
		return value;
	}
	/***
	 * Returns the value of the chosen tile.
	 * 
	 * @return the value of the tile.
	 */
	public int getValue() {
		return value;
	}
	
	/***
	 * Sets the number of sides on a dice.
	 * 
	 * @param n number of sides on a dice.
	 */
	public void setSides(int n) {
		numSides = n;
	}

	/***
	 * Create a string representation of the Die object
	 * 
	 * @return a string that says how many sides the die has and what it/s current value is.
	 */
	@Override
	public String toString() {
		String message = "Die has " + numSides + " sides and the current value is " + value;
		return message;
	}
}
