package assessment.model;

/**
 * <h1>PPA assignment 13 </h1> <br>
 * Computer Science <br>
 * Year 1 
 * <p>
 * This is the Bean class, which represents the bean in the game. It implements the piece as it has properties of a piece (e.g.
 * it can be moved onto a boat)
 * 
 * @author Wonjoon Seol (k1631098), Yuji Xing (k1630591)
 */

public class Bean implements Piece{
	private String name;
	
	public Bean(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
