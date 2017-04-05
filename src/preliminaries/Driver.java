package preliminaries;

import cw5.Coordinates;

/**
 * <h1>PPA assignment 12 </h1> <br>
 * Computer Science <br>
 * Year 1 
 * <p>
 * This class is a driver class
 * 
 * @author Sadi Ashraful(k1631026), Wonjoon Seol (k1631098)
 */
public class Driver implements Runnable {			// Implement Runnable interface to override run() method to make a thread

    public static Thread t;
    public static boolean flag;

	public static void main(String[] args) {	
		t = (new Thread (new Driver()));
		t.start();											// start new thread t, which would run the method run()
	}
	
	@Override
	public void run() {
		Plane plane = new Plane(0, 10, 0, 10, new Coordinates(5, 0), 10, 5, 5);
		RunwayModel runway = new RunwayModel(new Runway(10, 100));

		Controller controller = new Controller(plane, runway);
		View view = new View(controller, plane);
		controller.setGUI(view);
		view.setVisible(true);								// make JFrame visible	
		
		plane.addObserver(view);

		controller.initialiseTakeOff();
	}
}
