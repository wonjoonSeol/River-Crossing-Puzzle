package assessment.model;

import java.util.ArrayList;
import java.util.Observable;

/**
 * <h1>PPA assignment 13 </h1> <br>
 * Computer Science <br>
 * Year 1 
 * <p>
 * This class is used for representing the Land, and its properties
 * MVC - MODEL
 * @author Wonjoon Seol (k1631098), Yuji Xing (k1630591)
 */
public class Land extends Observable{
	
	private ArrayList<Piece> pieces;
	private boolean isGameOver;
	
	public Land() {
		pieces = new ArrayList<Piece>();
	}
	/**
	 * When a Land object is initialised, the array would be created
	 * 
	 * The following methods are the add piece and remove piece, used to move pieces from the boat to the land and vice versa
	 * The Observers are notified of the change so that the pieces can move onto or off the land in the view.
	 */
	public void addPiece(Piece piece) {
		pieces.add(piece);
//		setChanged();
//		notifyObservers("pieceAdded");
	}
	
	public void removePiece(Piece piece) {
		pieces.remove(piece); 
	}
	
	public ArrayList<Piece> getPieces() {
		return pieces;
	}
	
	/**
	 * Some of the game rules are added to this class, the following method checks if the farmer is present,
	 * if farmer is not present, it checks if there is a predator and a prey (which the predator can eat)
	 * and if there is, then the game over flag is true, and the observers are notified
	 */
	public void initiateNaturalBehaviour(){
		if (!isFarmerPresent()) {
			for (int i = 0; i < pieces.size(); i++) {
				for (int j = 0; j < pieces.size(); j++) {
					if (pieces.size() == 2 && pieces.get(i) instanceof Predator && ((Predator) pieces.get(i)).eats(pieces.get(j))) {
						pieces.remove(j);
						isGameOver = true;
						setChanged();
						notifyObservers("GameOver");
					}
				}
			}
		}
	}
	
	public boolean predictNaturalBehaviour(){
		for (int i = 0; i < pieces.size(); i++) {
			for (int j = 0; j < pieces.size(); j++) {
				if (pieces.get(i) instanceof Predator && ((Predator) pieces.get(i)).eats(pieces.get(j))) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isFarmerPresent() {
		for (Piece piece : pieces) {
			if (piece instanceof Farmer) {
				return true;
			}
			
			if (piece instanceof Boat) {
				if (((Boat) piece).isContainFarmer()) return true;
			}
		}
		return false;
	}
	/**
	 * The following method checks if the land the pieces, which is part of the requirement to win the game. 
	 * @param pieces 
	 * @return boolean Whether or not the piece is in the array
	 */
	public boolean contains(Piece piece){
		for (Piece animal : pieces) {
			if (animal.equals(piece)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isGameOver() {
		return isGameOver;
	}

	public int getPiecesSize() {
		return pieces.size();
	}
	
	public void reset() {
		pieces.clear();
		isGameOver = false;
	}

}
