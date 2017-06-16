package vn.vacum;

import java.util.ArrayList;

import aima.core.agent.Action;
import aima.core.agent.impl.NoOpAction;
import aima.core.util.datastructure.XYLocation;
import vn.vacum.MyAgent.HeadDirection;

public class FindPathGoHome {

	public static ArrayList<Action> findPath(MyAgent agent,
			XYLocation locOfAgent) {
		ArrayList<Action> actions = new ArrayList<>();
		Node[]fringe = new Node[100000];
		Node start = new Node(NoOpAction.NO_OP, locOfAgent,
				agent.getHeadDirection(), new Node());
		fringe[0] = start;
		int index = 0, in = 1;
		while(true){
			Node newNode = fringe[index];
			if(isLocationHome(newNode)){
				System.out.println(newNode.getLocation().toString());
				actions = returnSolution(newNode);
				break;
			}else{
				Node[] lists = listNodeNear(newNode);
			//	System.out.println("----------------");
				for (int i = 0; i < lists.length; i++) {
					if (lists[i] == null) {continue;}
					if (isLocationOutsiteMap(lists[i]) &&
							isSubNodeDifferentParentNodeofParent(lists[i])) {
					//	System.out.println(lists[i].getLocation().toString());
						fringe[in] = lists[i];
						in++;
					}
				}
				index++;
			}
		}
		for(Action a : actions){
		//	System.out.println("--->" + a.toString());
		}
		return actions;
	}
	public static ArrayList<Action> returnSolution(Node node) {
		ArrayList<Action> actions = new ArrayList<>();
		while (true) {
			actions.add(node.getAction());
			System.out.println(node.getAction());
			if (node.getAction() == NoOpAction.NO_OP) {
				break;
			}
			node = node.getParentNode();
		}
		return actions;
	}
	
	public static boolean isLocationHome(Node node){
		int x = node.getLocation().getXCoOrdinate();
		int y = node.getLocation().getYCoOrdinate();
		if ( x==1 && y == 1) {
			return true;
		}
		return false;
	}
	public static boolean isLocationOutsiteMap(Node node){
		int x = node.getLocation().getXCoOrdinate();
		int y = node.getLocation().getYCoOrdinate();
		if ( x >= 1 && y >= 1) {
			return true;
		}
		return false;
	}
//	public ArrayList<Action> solutionBFS(Node node){
//		
//	}
	
	public static Node[] listNodeNear(Node parent) {
		Node[] node = new Node[3];
		switch (parent.getlState()) {
		case North:
			node[0] = new Node(MyAction.GO_FORWARD, parent.getLocation()
					.locationAt(XYLocation.Direction.West),
					HeadDirection.North, parent);
			node[1] = new Node(MyAction.TURN_LEFT, parent.getLocation()
					.locationAt(XYLocation.Direction.North),
					HeadDirection.West, parent);
			node[2] = new Node(MyAction.TURN_RIGHT, parent.getLocation()
					.locationAt(XYLocation.Direction.South),
					HeadDirection.East, parent);
			break;
		case East:
			node[0] = new Node(MyAction.GO_FORWARD, parent.getLocation()
					.locationAt(XYLocation.Direction.South),
					HeadDirection.East, parent);
			node[1] = new Node(MyAction.TURN_LEFT, parent.getLocation()
					.locationAt(XYLocation.Direction.West),
					HeadDirection.North, parent);
			node[2] = new Node(MyAction.TURN_RIGHT, parent.getLocation()
					.locationAt(XYLocation.Direction.East),
					HeadDirection.South, parent);
			break;
		case South:
			node[0] = new Node(MyAction.GO_FORWARD, parent.getLocation()
					.locationAt(XYLocation.Direction.East),
					HeadDirection.South, parent);
			node[1] = new Node(MyAction.TURN_LEFT, parent.getLocation()
					.locationAt(XYLocation.Direction.South),
					HeadDirection.East, parent);
			node[2] = new Node(MyAction.TURN_RIGHT, parent.getLocation()
					.locationAt(XYLocation.Direction.North),
					HeadDirection.West, parent);
			break;
		case West:
			node[0] = new Node(MyAction.GO_FORWARD, parent.getLocation()
					.locationAt(XYLocation.Direction.North),
					HeadDirection.West, parent);
			node[1] = new Node(MyAction.TURN_LEFT, parent.getLocation()
					.locationAt(XYLocation.Direction.East),
					HeadDirection.South, parent);
			node[2] = new Node(MyAction.TURN_RIGHT, parent.getLocation()
					.locationAt(XYLocation.Direction.West),
					HeadDirection.North, parent);
			break;
		}
		return node;
	}
	public static boolean isSubNodeDifferentParentNodeofParent(Node node) {
		if(node.equals(node.getParentNode().getParentNode())){
			return false; 
		}                  
		return true; 
	}  
}
