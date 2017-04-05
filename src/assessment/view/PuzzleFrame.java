package assessment.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import assessment.controller.AIsolver;
import assessment.controller.Controller;
import assessment.model.Boat;
import assessment.model.Land;
import assessment.model.Piece;
import assessment.model.Player;

/**
 * <h1>PPA assignment 13 </h1> <br>
 * Computer Science <br>
 * Year 1 
 * <p>
 * This class is the view of the game 
 * 
 * @author Wonjoon Seol (k1631098), Yuji Xing (k1630591)
 */
public class PuzzleFrame extends JFrame implements Observer {

	private AIsolver aisolver;
	private Piece bean;
	private Piece farmer;
	private Piece fox;
	private Piece goose;
	private Land leftSide;
	private Land rightSide;
	private Player player;
	private Piece boat;
	private boolean isAIOn;
	
	private Controller controller;
	private JLabel riverLabel;
	private JLabel leftLabel;
	private JLabel rightLabel;
	private JPanel statusLabel;
	
	private JButton[] arrowButtons;
	private JLabel[] arrowLabels;
	
	private int boatX;
	private int boatY;
	
	public PuzzleFrame(Controller controller, Piece fox, Piece goose, Piece bean, Piece farmer, Piece boat, Land leftSide, Land rightSide, Player player, AIsolver aisolver) throws IOException {
		super("Fox, Goose and Bag of Beans");
		this.controller = controller;
		this.fox = fox;
		this.goose = goose;
		this.bean = bean;
		this.farmer = farmer;
		this.boat = boat;
		this.leftSide = leftSide;
		this.rightSide = rightSide;
		this.player = player;
		this.aisolver = aisolver;
		/**
		 * The constructor initialises the pieces for the GUI of the game.
		 * @param bean
		 * @param fox
		 * @param goose
		 * @param farmer
		 * @param boat
		 * @param leftSide
		 * @param rightSide
		 * @param player
		 */
		boatX = 730;
		boatY = 0;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		initStatusWidgets();
		new imageWidgets();
		pack();
	}
	
	private void initStatusWidgets() {
		
		statusLabel = new JPanel();
		statusLabel.setLayout(new FlowLayout());
		
		arrowButtons = new JButton[10];
		arrowLabels = new JLabel[5];
		
		arrowLabels[0] = new JLabel("Boat:");
		arrowLabels[1] = new JLabel("Fox:");
		arrowLabels[2] = new JLabel("Goose:");
		arrowLabels[3] = new JLabel("Beans:");
		arrowLabels[4] = new JLabel("Farmer:");
		
		for (int i = 0; i < arrowLabels.length; i++) {
			arrowButtons[2*i] = new JButton("<");
			arrowButtons[2*i].setName(Integer.toString(2*i));
			arrowButtons[2*i].addActionListener(controller);
			arrowButtons[2*i+1] = new JButton(">");			
			arrowButtons[2*i+1].setName(Integer.toString(2*i+1));
			arrowButtons[2*i+1].addActionListener(controller);			
			
			statusLabel.add(arrowLabels[i]);
			statusLabel.add(arrowButtons[2*i]);
			statusLabel.add(arrowButtons[2*i+1]);	
		}
		add(statusLabel, BorderLayout.PAGE_END);
	}
	
	class imageWidgets {
		
		private BufferedImage farmerImage;
		private BufferedImage foxImage;
		private BufferedImage beanImage;
		private BufferedImage boatImage;
		private BufferedImage gooseImage;
		
		private ImageIcon grassIcon;
		private ImageIcon riverIcon;
		
		public imageWidgets() throws IOException {
			initImages();
			initCentreWidgets();	
			initLeftWidgets();
			initRightWidgets();
		}
		/**
		 * This method reads files, and loads images onto the bufferedimages, 
		 * @throws IOException if reading a local image file that was no longer available.
		 */
		private void initImages() throws IOException {
			farmerImage = ImageIO.read(new File("resources/farmer.png"));
			foxImage = ImageIO.read(new File("resources/fox.png"));
			beanImage = ImageIO.read(new File("resources/beans.png"));
			boatImage = ImageIO.read(new File("resources/boat.png"));
			gooseImage = ImageIO.read(new File("resources/goose.png"));
			grassIcon = new ImageIcon("resources/grass.png");
			riverIcon = new ImageIcon("resources/water.png");
		}
		
		private void initCentreWidgets() {
			riverLabel = new JLabel() {
			
				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					
					g.drawImage(boatImage, boatX, boatY, null);
					updatePassengers(g);
				}
				
				private void updatePassengers(Graphics g) {
					int firstSlotX = boatX;
					int secondSlotX = boatX + 150;
					int slotY = boatY + 300;
					
					if (((Boat) boat).firstSlotContains(goose)) {
						g.drawImage(gooseImage, firstSlotX, slotY, null); 
					}
					if (((Boat) boat).firstSlotContains(fox)) {
						g.drawImage(foxImage, firstSlotX, slotY, null);
					}
					if (((Boat) boat).firstSlotContains(bean)) {
						g.drawImage(beanImage, firstSlotX, slotY, null); 
					}
					if (((Boat) boat).firstSlotContains(farmer)) {
						g.drawImage(farmerImage, firstSlotX, slotY, null); 
					}
					
					if (((Boat) boat).secondSlotContains(goose)) {
						g.drawImage(gooseImage, secondSlotX, slotY, null); 
					}
					if (((Boat) boat).secondSlotContains(fox)) {
						g.drawImage(foxImage, secondSlotX, slotY, null); 
					}
					if (((Boat) boat).secondSlotContains(bean)) {
						g.drawImage(beanImage, secondSlotX, slotY, null); 
					}
					if (((Boat) boat).secondSlotContains(farmer)) {
						g.drawImage(farmerImage, secondSlotX, slotY, null); 
					}
				}
			};
			riverLabel.setIcon(riverIcon);
			add(riverLabel, BorderLayout.CENTER);
		}

		private void initLeftWidgets() {
		
			leftLabel = new JLabel() {

				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					
					if (leftSide.contains(fox)) {
						g.drawImage(foxImage, 20, 20, null);
					}				
					
					if (leftSide.contains(goose)) {
						g.drawImage(gooseImage, 20, 210, null);
					}	
					
					if (leftSide.contains(bean)) {
						g.drawImage(beanImage, 50, 390, null);
					}	
					
					if (leftSide.contains(farmer)) {
						g.drawImage(farmerImage, 15, 500, null);
					}
					
				}
			};
					
			leftLabel.setIcon(grassIcon);
			leftLabel.setPreferredSize(new Dimension(200, 50));
			add(leftLabel, BorderLayout.LINE_START);
		}
			
		private void initRightWidgets() {

			rightLabel = new JLabel() {
	
				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					
					if (rightSide.contains(fox)) {
						g.drawImage(foxImage, 20, 30, null);
					}				
					if (rightSide.contains(goose)) {
						g.drawImage(gooseImage, 20, 210, null);
					}	
					if (rightSide.contains(bean)) {
						g.drawImage(beanImage, 50, 390, null);
					}	
					if (rightSide.contains(farmer)) {
						g.drawImage(farmerImage, 15, 500, null);
					}
				}
			};
			
			rightLabel.setIcon(grassIcon);
			rightLabel.setPreferredSize(new Dimension(200, 50));
			add(rightLabel, BorderLayout.LINE_END);
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		riverLabel.repaint();
		leftLabel.repaint();
		rightLabel.repaint();
		
		if (leftSide.isGameOver() || rightSide.isGameOver()) {
			setTitle("Game over: Predator ate prey.");
			if (!isAIOn) {
				int result = JOptionPane.showOptionDialog(this, "Game Over, Retry?", "Notification", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION, null, new String[] {"Cancel", "Reset"}, JOptionPane.NO_OPTION);
				if (result == JOptionPane.NO_OPTION) {
					aisolver.hardReset();
				}
			}
		} else if (leftSide.getPiecesSize() == 5) {
			setTitle("Success ! Your score : " + player.getScore());
			if (!isAIOn) {
				int result = JOptionPane.showOptionDialog(this, "Congrats! You have won the game!", "Notification", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[] {"Cancel", "Reset"}, JOptionPane.NO_OPTION);
				if (result == JOptionPane.NO_OPTION) {
					aisolver.hardReset();
				}
			}
		
		} else {
			setTitle("Your current Score: " + player.getScore());
		}
		riverLabel.repaint();
		leftLabel.repaint();
		rightLabel.repaint();
	}

	public boolean initiateRightSailAnimation() {
		int rightSideX = 730;
		int rightSideY2 = 300;
		
		initiateSailAnimation(rightSideX, rightSideY2, rightSide);
		return true;
	}
	
	public boolean initiateLeftSailAnimation() {
		int leftSideX = 0;
		int leftSideY2 = 300;
		
		initiateSailAnimation(leftSideX, leftSideY2, leftSide);
		return true;
	}
	
	/**
	 * This method initiate animation in which to send a boat to the given destination
	 *
     * @param destiX int variable coordinate of X to be sailed
	 * @param destiY int variable coordinate of Y to be sailed
	 * @param land Land Land to arrive
	 */	
	private void initiateSailAnimation(int destiX, int destiY, Land land) {
		new SwingWorker<Void, Void>() {
		@Override
		protected Void doInBackground() throws Exception {
			
			int yCoordinate;
			
			if (land.getPiecesSize() > 2) {
				yCoordinate = destiY; 
			} else {
				yCoordinate = 0;
			}
			
			while (boatX != destiX || boatY != yCoordinate) {
				
				if (destiX < boatX) boatX -= 1;
				if (destiX > boatX) boatX += 1;
				if (yCoordinate > boatY) boatY += 1;
				if (yCoordinate < boatY) boatY -= 1;

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				riverLabel.repaint();
			}
			return null;
			}
		}.execute();
	}
	
	public void setAI(boolean flag) {
		isAIOn = flag;
	}

	public void boatPositionReset(){
			boatX = 730;
			boatY = 0;	
	}
}
