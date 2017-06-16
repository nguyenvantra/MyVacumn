package vn.vacum;

import aima.core.agent.Action;
import aima.core.util.datastructure.XYLocation;
import vn.vacum.MyAgent.HeadDirection;

public class Node {
	Action action;
	XYLocation location;
	HeadDirection lState;
	Node parentNode;
	public Node(){}
	public Node(Action action, XYLocation location, HeadDirection lState, Node parentNode) {
		this.parentNode = parentNode;
		this.action = action;
		this.location = location;
		this.lState = lState;
	}
	
	public Node getParentNode() {
		return parentNode;
	}
	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public XYLocation getLocation() {
		return location;
	}
	public void setLocation(XYLocation location) {
		this.location = location;
	}

	public HeadDirection getlState() {
		return lState;
	}

	public void setlState(HeadDirection lState) {
		this.lState = lState;
	}
	
}
