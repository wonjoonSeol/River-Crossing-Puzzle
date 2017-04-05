package assessment.controller;

import java.util.ArrayList;

import javax.swing.SwingWorker;

import assessment.model.Bean;
import assessment.model.Boat;
import assessment.model.Farmer;
import assessment.model.Fox;
import assessment.model.Goose;
import assessment.model.Land;
import assessment.model.Piece;
import assessment.model.Player;
import assessment.view.AIFrame;
import assessment.view.PuzzleFrame;

/**
 * <h1>PPA assignment 13 </h1> <br>
 * Computer Science <br>
 * Year 1 
 * <p>
 * This class is A.I. solver using random moves
 * 
 * @author Wonjoon Seol (k1631098), Yuji Xing (k1630591)
 */
public class AIsolver {

	private AIFrame aiView;
	private PuzzleFrame view;
	private Boat boat;
	private Farmer farmer;
	private Fox fox;
	private Goose goose;
	private Land leftLand;
	private Land rightLand;
	private Bean bean;
	private Player player;
	private ArrayList<Piece> leftList;
	private ArrayList<Piece> rightList;
	private ArrayList<Integer> badNumbers;
	private ArrayList<Integer> solution;
	private String string;
	
	public AIsolver(Boat boat, Farmer farmer, Fox fox, Goose goose, Land leftLand, Land rightLand, Bean bean, Player player) {
		this.boat = boat;
		this.farmer = farmer;
		this.fox = fox;
		this.goose = goose;
		this.leftLand = leftLand;
		this.rightLand = rightLand;
		this.bean = bean;
		this.player = player;
		
		leftList = new ArrayList<Piece>();
		rightList = new ArrayList<Piece>();
		solution = new ArrayList<Integer>();
		badNumbers = new ArrayList<Integer>();
		initArrayList();
	}
	
	
	private void initArrayList() {
		for (Piece piece: rightLand.getPieces()) {
			if (!(piece instanceof Boat || piece instanceof Farmer)) {
				rightList.add(piece);
			}
		}
	}
	
	/**
	 * This method checks for last unsuccessful number that failed the game and sets a new random number
	 * @return	int	random number
	 */
	private int removeBadRandomNumbers() {
		int number = rollDice();
		boolean flag = true;
		
		while (flag) {
			for (int bad : badNumbers) {
				if (bad == number) number = rollDice();
			}
			flag = false;
		}
		return number;
	}
	
	/**
	 * This method returns random number up to the size of list where the boat is located
	 * @return	int	random number
	 */
	private int rollDice() {
		if (boat.getLocation().equals(rightLand)) {
			return (int)(Math.random() * (rightList.size()));
		} else {
			return (int)(Math.random() * (leftList.size()));
		}
	}
	
	/**
	 * This method checks number of iteration and gets random number only if solution array in a iteration index does not exist
	 * @param	iteration	number of iterations
	 * @return	int 		either a random number or previous successful number
	 */
	private int solutionGuide(int iteration) {
		try {
			if (solution.get(iteration) == 5) return removeBadRandomNumbers();
			return solution.get(iteration);
		} catch (IndexOutOfBoundsException e) {
			return removeBadRandomNumbers();
		}
	}
	
	/**
	 * The method run() defines A.I. for this puzzle.
	 */
	public void run() {
		view.setAI(true);
		int iteration = -1;
		while (leftLand.getPiecesSize() != 5 && !leftLand.isGameOver() && !rightLand.isGameOver()) { 	// game end condition
			int randomNum = solutionGuide(iteration); 
			
			if (rightLand.getPiecesSize() == 5 && boat.getLocation().equals(rightLand)) {				// Very first move - move farmer into the boat
				boat.enterBoat(farmer);																	// This is a 'shortcut' to creating an A.I. 
				string += "Farmer has entered boat";													// And improvements can be made by simply checking and removing duplicates moves in the solutions array instead
																										// Something interesting to discuss with T.A. in the interview
			} else if (leftLand.getPiecesSize() == 4 && boat.getLocation().equals(leftLand)) {			// Very last move and the game ends
				boat.leaveBoat(farmer);
				string += "Farmer has left boat";
					
			} else if (boat.getLocation().equals(rightLand)) {											// If the location is right side of the land
				
				boat.enterBoat(rightList.get(randomNum));
				string += rightList.get(randomNum).getName() + " entered boat \n";
					
				boat.move(leftLand);
//				boat.update();
				player.subtractScore();
				
				rightLand.initiateNaturalBehaviour();													// checks for game ending condition
				
				string += "boat is moved to left side \n" + rightList.get(randomNum).getName() + " left boat";
				boat.leaveBoat(rightList.get(randomNum));
				leftList.add(rightList.get(randomNum));
				rightList.remove(randomNum);
				
				if (iteration == solution.size()) {
					solution.add(randomNum);
					badNumbers.clear();																	// added a latest move so the bad number arraylist should be cleared
				} else {
					solution.set(iteration, randomNum);
				}
			} else {

				if (leftList.size() == 1 || (leftList.size() == 2 && !leftLand.predictNaturalBehaviour())) {	// Condition to check whether farmer should return alone.
					boat.move(rightLand);																		// Again improvements can be made from the suggestion above.
//					boat.update();
					
					string += "boat is moved to the right side";
					leftLand.initiateNaturalBehaviour();
					player.subtractScore();

					if (iteration == solution.size()) {
						solution.add(5);
						badNumbers.clear();

					} else {
						solution.set(iteration, 5);
					};

				} else {
					boat.enterBoat(leftList.get(randomNum));
					string += leftList.get(randomNum).getName() + " entered boat \n";
						
					boat.move(rightLand);
					boat.update();

					player.subtractScore();
					leftLand.initiateNaturalBehaviour();
					
					string += "boat is moved to right side \n" + leftList.get(randomNum).getName() + " left boat";
					
					boat.leaveBoat(leftList.get(randomNum));
					rightList.add(leftList.get(randomNum));
					leftList.remove(randomNum);
					
					if (iteration == solution.size()) {
						solution.add(randomNum);
						badNumbers.clear();
					} else {
						solution.set(iteration, randomNum);
					}
				}
			}
			
			iteration++;
			aiView.jtaAppend(string);
			string = "";

			if (leftLand.isGameOver() || rightLand.isGameOver()) {						// when the game is over
				if (solution.size() != 0) {										
					badNumbers.add(solution.get(solution.size() - 1));					// add last successful move performed to the badNumbers
					solution.remove(solution.size() - 1);								// and remove it from the solution arrayList
					reset();															// reset the game
				}
			}	
		}
		view.initiateLeftSailAnimation();
	}
		
	/**
	 * The method reset() resets the game
	 */
	public void reset() {
		string = "";
		aiView.jtaAppend("\nResetting");
		
		boat.reset(rightLand);
		leftLand.reset();
		rightLand.reset();
		leftList.clear();
		rightList.clear();
		player.reset();
		rightLand.addPiece(fox);
		rightLand.addPiece(goose);
		rightLand.addPiece(bean);
		rightLand.addPiece(farmer);
		rightLand.addPiece(boat);
		initArrayList();
		view.boatPositionReset();
		run();
	}
	
	public void hardReset() {
		string = "";
		aiView.jtaAppend("\nResetting");
		
		boat.reset(rightLand);
		leftLand.reset();
		rightLand.reset();
		leftList.clear();
		rightList.clear();
		player.reset();
		rightLand.addPiece(fox);
		rightLand.addPiece(goose);
		rightLand.addPiece(bean);
		rightLand.addPiece(farmer);
		rightLand.addPiece(boat);
		view.boatPositionReset();
	}
	
	public void addViewer(PuzzleFrame viewer, AIFrame aiViewer) {
		this.view = viewer;
		this.aiView = aiViewer;
	}
}
