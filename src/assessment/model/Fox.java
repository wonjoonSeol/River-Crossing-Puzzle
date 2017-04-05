package assessment.model;

/**
 * <h1>PPA assignment 13 </h1> <br>
 * Computer Science <br>
 * Year 1 
 * <p>
 * This is the Fox class, which represents the Fox in the game. It implements the piece as it has properties of a piece (e.g.
 * it can be moved onto a boat)
 * The Fox is also a predator, it can eat Geese, so when the eat method recieves an instance of geese as piece parameter, then 
 * the method will return true, indicating that the fox can eat the geese.
 * 
 * @author Wonjoon Seol (k1631098), Yuji Xing (k1630591)
 */
public class Fox implements Piece, Predator{
	
	private String name;
	
	public Fox(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public boolean eats(Piece piece) {
		if (piece instanceof Goose) {
			return true;
		}
		return false;
	}

}
