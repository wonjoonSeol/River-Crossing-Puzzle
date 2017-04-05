package assessment.model;

/**
 * <h1>PPA assignment 13 </h1> <br>
 * Computer Science <br>
 * Year 1 
 * <p>
 * This is the Goose class, which represents the Goose in the game. It implements the piece as it has properties of a piece (e.g.
 * it can be moved onto a boat)
 * The Geese is also a predator, it can eat Beans, so when the eat method recieves an instance of Beans as piece parameter, then 
 * the method will return true, indicating that the Geese can eat the Beans..
 * 
 * @author Wonjoon Seol (k1631098), Yuji Xing (k1630591)
 */
public class Goose implements Piece, Predator{
	
	private String name;
	
	public Goose(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public boolean eats(Piece piece) {
		if (piece instanceof Bean) return true;
		return false;
	}

}
