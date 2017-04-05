package preliminaries;

import java.util.Observable;

public class RunwayModel extends Observable {
	private Runway runway;
	
	public RunwayModel(Runway runway) {
		this.runway = runway;
	}
	
	/**
	 * This method checks whether Plane is inside runway
	 * @param plane
	 * @return boolean Returns whether plane x coodrinate is inside the runway
	 */
	public boolean contains(Plane plane){
		return runway.contains(plane.getX(), 0);
	}
	
	public double getHeight() { 
		return runway.getHeight();
	}
}
