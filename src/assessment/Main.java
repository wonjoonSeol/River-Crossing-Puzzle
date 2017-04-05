/**
 * 
 */
package assessment;

import java.io.IOException;

import assessment.controller.AIsolver;
import assessment.controller.Controller;
import assessment.model.Bean;
import assessment.model.Boat;
import assessment.model.Farmer;
import assessment.model.Fox;
import assessment.model.Goose;
import assessment.model.Land;
import assessment.model.Player;
import assessment.view.AIFrame;
import assessment.view.PuzzleFrame;

/**
 * <h1>PPA assignment 13 </h1> <br>
 * Computer Science <br>
 * Year 1 
 * <p>
 * This class is the main class
 * 
 * @author Wonjoon Seol (k1631098), Yuji Xing (k1630591)
 */
public class Main {

	public static void main(String[] args) throws IOException {
		Fox fox = new Fox("Fox");
		Goose goose = new Goose("Goose");
		Farmer farmer = new Farmer("Farmer");
		Bean bean = new Bean("Bean");
		Player player = new Player("Player");
		
		Land leftSide = new Land();
		Land rightSide = new Land();
		Boat boat = new Boat(rightSide, "Boat");
		
		rightSide.addPiece(fox);
		rightSide.addPiece(goose);
		rightSide.addPiece(bean);
		rightSide.addPiece(farmer);
		rightSide.addPiece(boat);

		Controller controller = new Controller(boat, farmer, fox, goose, leftSide, rightSide, bean, player);
		AIsolver aiSolver = new AIsolver(boat, farmer, fox, goose, leftSide, rightSide, bean, player);
		PuzzleFrame view = new PuzzleFrame(controller, fox, goose, bean, farmer, boat, leftSide, rightSide, player, aiSolver);
		AIFrame aiView = new AIFrame(controller);
		
		/** 
		 * Each of the pieces of the game are created, placed in the right side of the river bank, and loaded to the controller and view.
		 * 
		 * The View is linked to the controller and Observers are used to link between the model and view
		 */
		
		controller.addViewer(view, aiView);
		aiSolver.addViewer(view, aiView);
		rightSide.addObserver(view);
		leftSide.addObserver(view);
		boat.addObserver(view);
		controller.addAI(aiSolver); 
		view.setVisible(true);
		aiView.setVisible(true);

	}

}
