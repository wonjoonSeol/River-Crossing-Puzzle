package preliminaries;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cw5.Coordinates;

/**
 * This inner class runs the appropriate methods for interacting with the components
 * MVC - CONTROLLER
 */
class Controller implements ChangeListener, ActionListener{			
	
	private Plane plane;
	private RunwayModel runway;
	private View gui;
	private int seconds = 0;
	private int conseqSec = 0;
	
	public Controller(Plane plane, RunwayModel runway){
		this.plane = plane;
		this.runway = runway;
	}
	
	/**
	 * This method overrides stateChanged method from interface ChangeListener 
	 * {@inheritDoc}
	 * 
	 * @return void
	 */
	@Override
	public void stateChanged(ChangeEvent e){						// Listen for changes in the value in a components (here sliders)
		JSlider slider = (JSlider) e.getSource();
		if (slider.getOrientation() == 0){						// if the source is from horizontal slider
			plane.setX(slider.getValue());				// Set approproriate value to the x coordinate of the plane
		} else if (slider.getOrientation() == 1){
			plane.setSpeed(slider.getValue());	
		}
	}

	/**
	 * This method overrides actionPerformed method from interface ActionListener 
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e){		// checks for action happened (here button)
			gui.dispose(); 					// dispose current JFrame 
			new Thread (new Driver()).start();		// start new thread
	}
	
	public void setGUI(View view){
		this.gui = view;
	}
	
	public void initialiseTakeOff(){
		while(plane.getY() < runway.getHeight()) {
			try {
				gui.jtaAppend("Seconds: " + seconds +"\n");
				gui.jtaAppend(plane.toString() + "\n");
				Thread.sleep(1000);							// Thread sleeps for 1 second
				
				seconds += 1;

				if (plane.isFastEnough()) {
					conseqSec += 1;							// if plane is fast enough, add 1 to conseqSec
				} else {
					conseqSec = 0;							// otherwise reset to 0
				}
				
				if (plane.isGoingUp(conseqSec)) {			// if conseqSec is more than time for elevation
					plane.addElevation();					// add elevation by 1
				}
				 plane.addY(plane.getSpeed());
				 //gui.setLabelUpdate();						// update speed, elevation status label

			} catch (InterruptedException e) {
				 System.out.println(e);
			}
		}
	
		gui.jtaAppend("Seconds: " + seconds +"\n");
		gui.jtaAppend(plane.toString() + "\n"); 	// reporting the final position of the plane (consistent to brief example)
		
		if (plane.isTakenOff() && runway.contains(plane) && plane.isFastEnough()) { // checks the case when plane suddenly lowers the speed right before the take off.
			gui.jtaAppend("Plane in air");
			
		} else {
			gui.jtaAppend("Take off failed");
		}
	}
}