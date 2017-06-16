package vn.vacum;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import aima.core.agent.Agent;
import aima.core.agent.EnvironmentObject;
import aima.core.agent.EnvironmentState;
import aima.core.util.datastructure.XYLocation;

public class MyEnvironmentState implements EnvironmentState {
	int width;
	int height;

	private Map<XYLocation, Set<EnvironmentObject>> objsAtLocation = new LinkedHashMap<XYLocation, Set<EnvironmentObject>>();
	private Map<XYLocation, LocationState> stateAtLocation = new LinkedHashMap<XYLocation, LocationState>();
	public MyEnvironmentState(int width, int height) {
		this.width = width;
		this.height = height;
		for (int h = 1; h <= height; h++) {
			for (int w = 1; w <= width; w++) {
				objsAtLocation.put(new XYLocation(h, w),
						new LinkedHashSet<EnvironmentObject>());
			}
		}
	}
	
	public void moveObjectToAbsoluteLocation(EnvironmentObject eo,
			XYLocation loc) {
		// Ensure is not already at another location
		for (Set<EnvironmentObject> eos : objsAtLocation.values()) {
			if (eos.remove(eo)) {
				break; // Should only every be at 1 location
			}
		}
		// Add it to the location specified
		getObjectsAt(loc).add(eo);
	}
	
	public void locationDirtytoClean(XYLocation loc){
		for (EnvironmentObject eos : getObjectsAt(loc)) {
			if (eos instanceof Dirty) {
				System.out.println(eos.toString());
				objsAtLocation.get(loc).remove(eos);break;
			}
		}
		getObjectsAt(loc).add(new Clean());
	}
	
	public Set<EnvironmentObject> getObjectsAt(XYLocation loc) {
		Set<EnvironmentObject> objectsAt = objsAtLocation.get(loc);
		if (null == objectsAt) {
			// Always ensure an empty Set is returned
			objectsAt = new LinkedHashSet<EnvironmentObject>();
			objsAtLocation.put(loc, objectsAt);
		}
		return objectsAt;
	}

	public XYLocation getCurrentLocationFor(EnvironmentObject eo) {
		for (XYLocation loc : objsAtLocation.keySet()) {
			if (objsAtLocation.get(loc).contains(eo)) {
				return loc;
			}
		}
		return null;
	}

	public Set<EnvironmentObject> getObjectsNear(Agent agent, int radius) {
		Set<EnvironmentObject> objsNear = new LinkedHashSet<EnvironmentObject>();

		XYLocation agentLocation = getCurrentLocationFor(agent);
		for (XYLocation loc : objsAtLocation.keySet()) {
			if (withinRadius(radius, agentLocation, loc)) {
				objsNear.addAll(objsAtLocation.get(loc));
			}
		}
		// Ensure the 'agent' is not included in the Set of
		// objects near
		objsNear.remove(agent);
		return objsNear;
	}
	public void setLocationState(LocationState state, XYLocation loc){
		stateAtLocation.put(loc, state);
	}
	public LocationState getLocationState(XYLocation loc){
		return stateAtLocation.get(loc);
	}
	@Override
	public String toString() {
		return "MyEnvironmentState:" + objsAtLocation.toString();
	}

	//
	// PRIVATE METHODS
	//
	private boolean withinRadius(int radius, XYLocation agentLocation,
			XYLocation objectLocation) {
		int xdifference = agentLocation.getXCoOrdinate()
				- objectLocation.getXCoOrdinate();
		int ydifference = agentLocation.getYCoOrdinate()
				- objectLocation.getYCoOrdinate();
		return Math.sqrt((xdifference * xdifference)
				+ (ydifference * ydifference)) <= radius;
	}
	
	public boolean isAgent(XYLocation loc){
		for (EnvironmentObject eo : getObjectsAt(loc)) {
			if (eo instanceof MyAgent) {
				return true;
			}
		}
		return false;
	}
	public boolean isBlocked(XYLocation loc) {
		for (EnvironmentObject eo : getObjectsAt(loc)) {
			if (eo instanceof Wall) {
				return true;
			}
		}
		return false;
	}
	public boolean isClean(XYLocation loc) {
		for (EnvironmentObject eo : getObjectsAt(loc)) {
			if (eo instanceof Clean) {
				return true;
			}
		}
		return false;
	}
	public boolean isDirty(XYLocation loc) {
		for (EnvironmentObject eo : getObjectsAt(loc)) {
			if (eo instanceof Dirty) {
				return true;
			}
		}
		return false;
	}

	public boolean isGoHome() {
		// TODO Auto-generated method stub
		Set<XYLocation> states = stateAtLocation.keySet();
		Iterator<XYLocation> iterator = states.iterator();
		while(iterator.hasNext()){
			if(getLocationState(iterator.next()) == LocationState.DIRTY){
				return false;
			}
		}
		return true;
	}
}
