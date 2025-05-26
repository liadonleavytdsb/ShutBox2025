package donleavy.lia;

/***
 * This class represents a single tile in shut the box. 
 * Each tile has a value that can be marked as up, down, or selected when a player chooses it.
 */
public class Tile {
	private int value;
	private boolean isDown;
	private boolean selected;
	
	/***
	 * Makes a new tile facing up with a specific value, v.
	 * 
	 * @param v the value of the tile .
	 */
	public Tile(int v) {
		value = v;
		isDown = false;
		selected = false;
	}
	
	/***
	 * Makes the tile be shut.
	 */
	public void shut() {
		isDown = true;
	}
	
	/***
	 * Returns whether or not the specified tile is shut or not.
	 * 
	 * @return true if the tile is shut, false if the tile is not.
	 */
	public boolean isDown() {
		return isDown;
	}
	
	/***
	 * Opens the tile (marks the tile as up).
	 */
	public void open() {
		isDown = false;
	}
	
	/***
	 * Marks the tile as selected.
	 */
	public void select() {
		selected = true;
	}
	
	/***
	 * Deselects the tile.
	 */
	public void deselect() {
		selected = false;
	}
	
	/***
	 * Returns whether or not the tile is currently selected.
	 * 
	 * @return true if the tile is selected, false if not.
	 */
	public boolean isSelected() {
		return selected;
	}
	
	/***
	 * Returns the value of thr tile.
	 * 
	 * @return the tile/s value.
	 */
	public int getValue() {
		return value;
	}
	
	/***
	 * Returns a string version of the tiles, shopwing its value adn whether itis up (U) or down (D).
	 * 
	 * @return a string displaying the tile value and up/down --> "1. U  2. D" etc...
	 */
	@Override
	public String toString() {
		String state = "";
		if (isDown) {
			state = "D";
		}
		else {
			state = "U";
		}
		return "" + value + ":" + state;
	}
	
}
