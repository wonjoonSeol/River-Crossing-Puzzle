package assessment.model;

/**
 * <h1>PPA assignment 13 </h1> <br>
 * Computer Science <br>
 * Year 1 
 * <p>
 * This interface defines characteristics of predators
 * 
 * @author Wonjoon Seol (k1631098), Yuji Xing (k1630591)
 */
public interface Predator {
	/**
	 * This method returns true if the predator eats the piece passed to it as parameter, returns false if it doesnt eat the piece.
	 * @param Piece
	 * @return boolean whether predator eats the piece 
	 */
	public abstract boolean eats(Piece piece);
		
}
