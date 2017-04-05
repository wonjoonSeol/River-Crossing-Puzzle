package assessment.model;

import java.util.Observable;

/**
 * <h1>PPA assignment 13 </h1> <br>
 * Computer Science <br>
 * Year 1 
 * <p>
 * The player class is used to store the player's score and it contains methods to get and modify the player score.
 * 
 * @author Wonjoon Seol (k1631098), Yuji Xing (k1630591)
 */
public class Player extends Observable {

	private int score;
	private String name;
	
	public Player(String name) {
		 /**
		  * Constructor initialises score of player
		  */
		score = 0;
		this.name = name;
	}
	/**
	 * When the following method is called, the player's Score is decremented, and the observer 
	 * is notified in order to change the score board in view.
	 *
	 */
	public void subtractScore() {
		score--;
		setChanged();
		notifyObservers();
	}
	/**
	 * This method gets the score of the player
	 * @return int value of score
	 */
	public int getScore(){
		return score;
	}
	
	public void reset() {
		score = 0;
		setChanged();
		notifyObservers();
	}
}
