package vn.vacum;

import aima.core.agent.impl.DynamicPercept;
import aima.core.util.datastructure.XYLocation;

public class MyPercept extends DynamicPercept {
	
	private XYLocation agentLocation;
	boolean isGoHome, isObjectNear, isWall;
	private LocationState state;

	public MyPercept(XYLocation agentLocation, LocationState state) {
		this.agentLocation = agentLocation;
		this.state = state;
	}

	public boolean isWall() {
		return isWall;
	}

	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}

	public void setGoHome(boolean isGoHome) {
		this.isGoHome = isGoHome;
	}

	public void setObjectNear(boolean isObjectNear) {
		this.isObjectNear = isObjectNear;
	}

	public XYLocation getAgentLocation() {
		return agentLocation;
	}

	public LocationState getState() {
		return state;
	}

	public boolean isGoHome() {
		return isGoHome;
	}

	public boolean isObjectNear() {
		return isObjectNear;
	}
}
