package assessment.model;

import java.util.Observable;

/**
 * <h1>PPA assignment 13 </h1> <br>
 * Computer Science <br>
 * Year 1 
 * <p>
 * This class is used for representing the Boat 
 * 
 * MVC - MODEL
 * 
 * @author Wonjoon Seol (k1631098), Yuji Xing (k1630591)
 */

public class Boat extends Observable implements Piece {

	private Piece[] passengers;
	private Land location;
	private String name;
		

	/**
	 * Within the boat, an array is used to represent the space available for the boat's passengers.
	 * @param land
	 * 
	 */
	
	public Boat(Land land, String name){
		passengers = new Piece[2];
		location = land;
		this.name = name;
	}
	
	/**
	 * This method checks for the amount of space left on the  boat, in order to check if a piece can enter the boat
	 * @param piece refers to the piece which the user has appointed to enter the boat
	 * @return boolean whether the piece was able to succesfully enter the boat
	 */
	public boolean enterBoat(Piece piece){
		if (contains(piece)) return false;
		for (int i = 0; i < passengers.length; i++) {
			if (passengers[i] == null) {
				passengers[i] = piece;
				location.removePiece(piece);
				setChanged();
				notifyObservers();
				return true;
			}
		}
		return false;
	}
	
	 /** 
	 * The following method lets a piece leave the boat.
	 * It checks for the passenger on the boat, and then if the passenger is present, it removes it from the boat and adds it to the
	 * target location, also notifies observer about the change.
	 * 
	 * 
	 * @param piece refers to the piece to leave the boat
	 * @return temp contrains the piece that leaves the boat
	 */
	
	public Piece leaveBoat(Piece piece){
		for (int i = 0; i < passengers.length; i++) {
			if (passengers[i] != null && passengers[i].equals(piece)) {
				Piece temp = passengers[i];
				passengers[i] = null;
				location.addPiece(piece);
				
				setChanged();
				notifyObservers("leaved");
				
				return temp;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * This method moves the boat from one location to the other.
	 * It first checks if the passengers contain a Farmer, which allows the boat to move.
	 * @param location
	 * @return boolean Whether the boat has moved or not
	 */
	
	public boolean move(Land location) {
		if (!this.location.equals(location) && passengers[0] instanceof Farmer || passengers[1] instanceof Farmer) {
			this.location.removePiece(this);
			this.location = location;
			this.location.addPiece(this);
			return true;
		}
		return false;
	}

	public boolean contains(Piece piece) {
		for (Piece element : passengers) {
			if (element != null && element.equals(piece)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isContainFarmer() {
		for (Piece element : passengers) {
			if (element != null && element instanceof Farmer) return true;
		}
		return false;
	}
	
	public Land getLocation() {
		return location;
	}
	
	public boolean firstSlotContains(Piece piece) {
		if (passengers[0] != null) return passengers[0].equals(piece);
		return false;
	}

	public boolean secondSlotContains(Piece piece) {
		if (passengers[1] != null) return passengers[1].equals(piece);
		return false;
	}
	
	public void update() {
		setChanged();
		notifyObservers();
	}
	
	public void reset(Land land) {
		location = land;
		passengers[0] = null;
		passengers[1] = null;
//		setChanged();
//		notifyObservers("boatReset");	
	}

	@Override
	public String getName() {
		return name;
	}
	
}
