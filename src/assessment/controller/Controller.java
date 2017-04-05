package assessment.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

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
 * This class is the controller 
 * MVC - Controller
 * @author Wonjoon Seol (k1631098), Yuji Xing (k1630591)
 */

public class Controller implements ActionListener {
	private Boat boat;
	private Farmer farmer;
	private Fox fox;
	private Goose goose;
	private Land leftLand;
	private Land rightLand;
	private Bean bean;
	private Player player;
	private PuzzleFrame viewer;
	private AIFrame aiViewer;
	private AIsolver aiSolver;
		
	public Controller(Boat boat, Farmer farmer, Fox fox, Goose goose, Land leftLand, Land rightLand, Bean bean, Player player) {
		this.boat = boat;
		this.farmer = farmer;
		this.goose = goose;
		this.leftLand = leftLand;
		this.rightLand = rightLand;
		this.bean = bean;
		this.player = player;
		this.fox = fox;
	}
	
	/**
	 * The following is used for the control bar at the bottom of the main frame, each time a button is pressed, the gameover flag is checked,
	 * the GameWon flag is checked, and then if both is false, methods are called to move the boat, run game's rules checks, and subtract player score.
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton jButton = (JButton)e.getSource();
		
		if (!leftLand.isGameOver() && !rightLand.isGameOver() && leftLand.getPiecesSize() != 5) {
			if (jButton.getName().equals("0")) {
				if (boat.move(leftLand)) {
					player.subtractScore();
					viewer.initiateLeftSailAnimation();
					boat.update();
					rightLand.initiateNaturalBehaviour();
				}
			} else if (jButton.getName().equals("1")) {
				if (boat.move(rightLand)) {
					player.subtractScore();
					viewer.initiateRightSailAnimation();
					boat.update();
					leftLand.initiateNaturalBehaviour();
				}
				
			} else if (jButton.getName().equals("2")) {
				if (rightLand.equals(boat.getLocation()) && rightLand.contains(fox)) {
					boat.enterBoat(fox);
				} else if (leftLand.equals(boat.getLocation()) && boat.contains(fox)){
					boat.leaveBoat(fox);
				}	
				
			} else if (jButton.getName().equals("3")) {
				if (leftLand.equals(boat.getLocation()) && leftLand.contains(fox)) {
					boat.enterBoat(fox);
				} else if (rightLand.equals(boat.getLocation()) && boat.contains(fox)){
					boat.leaveBoat(fox);
				}	
				
			} else if (jButton.getName().equals("4")) {
				if (rightLand.equals(boat.getLocation()) && rightLand.contains(goose)) {
					boat.enterBoat(goose);
				} else if (leftLand.equals(boat.getLocation()) && boat.contains(goose)){
					boat.leaveBoat(goose);
				}	
				
			} else if (jButton.getName().equals("5")) {
				if (leftLand.equals(boat.getLocation()) && leftLand.contains(goose)) {
					boat.enterBoat(goose);
				} else if (rightLand.equals(boat.getLocation()) && boat.contains(goose)){
					boat.leaveBoat(goose);
				}	

			} else if (jButton.getName().equals("6")) {
				if (rightLand.equals(boat.getLocation()) && rightLand.contains(bean)) {
					boat.enterBoat(bean);
				} else if (leftLand.equals(boat.getLocation()) && boat.contains(bean)){
					boat.leaveBoat(bean);
				}	
				
			} else if (jButton.getName().equals("7")) {
				if (leftLand.equals(boat.getLocation()) && leftLand.contains(bean)) {
					boat.enterBoat(bean);
				} else if (rightLand.equals(boat.getLocation()) && boat.contains(bean)){
					boat.leaveBoat(bean);
				}	;
			} else if (jButton.getName().equals("8")) {
				if (rightLand.equals(boat.getLocation()) && rightLand.contains(farmer)) {
					boat.enterBoat(farmer);
				} else if (leftLand.equals(boat.getLocation()) && boat.contains(farmer)){
					boat.leaveBoat(farmer);
				}	
				
			} else if (jButton.getName().equals("9")) {
				if (leftLand.equals(boat.getLocation()) && leftLand.contains(farmer)) {
					boat.enterBoat(farmer);
				} else if (rightLand.equals(boat.getLocation()) && boat.contains(farmer)){
					boat.leaveBoat(farmer);
				}	
				
			} else if (jButton.getName().equals("AIbutton")) {
				aiViewer.jtaAppend("Overtaking a human player ... \n A.I. operating");
				aiSolver.reset();
				aiSolver.run();
			}
		}
	}
	
	public void addAI(AIsolver aiSolver) {
		this.aiSolver = aiSolver;
	}
	
	public void addViewer(PuzzleFrame viewer, AIFrame aiViewer) {
		this.viewer = viewer;
		this.aiViewer = aiViewer;
	}
}