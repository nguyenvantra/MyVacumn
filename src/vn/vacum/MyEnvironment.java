package vn.vacum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.xml.bind.annotation.XmlList;

import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.EnvironmentObject;
import aima.core.agent.EnvironmentState;
import aima.core.agent.Percept;
import aima.core.agent.impl.AbstractEnvironment;
import aima.core.search.framework.GoalTest;
import aima.core.util.datastructure.XYLocation;

public class MyEnvironment extends AbstractEnvironment {
	private MyEnvironmentState envState = null;
	int width;
	int height;
	boolean isWal = false;

	public MyEnvironment(int width, int height) {
		// TODO Auto-generated constructor stub
		assert (width > 0);
		assert (height > 0);
		this.width = width;
		this.height = height;
		envState = new MyEnvironmentState(width, height);
		init();
	}

	@Override
	public EnvironmentState getCurrentState() {
		// TODO Auto-generated method stub
		return envState;
	}

	@Override
	public EnvironmentState executeAction(Agent agent, Action action) {
		// TODO Auto-generated method stub
		MyAgent myAgent = (MyAgent) agent;
		if (MyAction.SUCK == action) {
			envState.setLocationState(LocationState.CLEAN,
					envState.getCurrentLocationFor(agent));
			envState.locationDirtytoClean(envState.getCurrentLocationFor(agent));
		} else if (MyAction.GO_FORWARD == action) {
			switch (myAgent.getHeadDirection()) {
			case North:
				moveObject(agent, XYLocation.Direction.West);
				break;
			case East:
				moveObject(agent, XYLocation.Direction.South);
				break;
			case West:
				moveObject(agent, XYLocation.Direction.North);
				break;
			case South:
				moveObject(agent, XYLocation.Direction.East);
				break;
			}
		} else if (MyAction.TURN_LEFT == action) {
			switch (myAgent.getHeadDirection()) {
			case North:
				moveAgent(agent, XYLocation.Direction.North,
						MyAgent.HeadDirection.West);
				break;
			case East:
				moveAgent(agent, XYLocation.Direction.West,
						MyAgent.HeadDirection.North);
				break;
			case West:
				moveAgent(agent, XYLocation.Direction.East,
						MyAgent.HeadDirection.South);
				break;
			case South:
				moveAgent(agent, XYLocation.Direction.South,
						MyAgent.HeadDirection.East);
				break;
			}
		} else if (MyAction.TURN_RIGHT == action) {
			switch (myAgent.getHeadDirection()) {
			case North:
				moveAgent(agent, XYLocation.Direction.South,
						MyAgent.HeadDirection.East);
				break;
			case East:
				moveAgent(agent, XYLocation.Direction.East,
						MyAgent.HeadDirection.South);
				break;
			case West:
				moveAgent(agent, XYLocation.Direction.West,
						MyAgent.HeadDirection.North);
				break;
			case South:
				moveAgent(agent, XYLocation.Direction.North,
						MyAgent.HeadDirection.West);
				break;
			}
		} else if (MyAction.GO_HOME == action) {
			System.out.println("GoHome");
			for (Action a : listActiontoGoHome(agent)) {
				gohome(a);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			agent.setAlive(false);
		}
		return envState;
	}

	public ArrayList<Action> listActiontoGoHome(Agent agent) {
		ArrayList<Action> actions = new ArrayList<>();
		for (Action a : FindPathGoHome.findPath((MyAgent) agent,
				envState.getCurrentLocationFor(agent))) {
			actions.add(0, a);
		}
		return actions;
	}

	public void moveAgent(Agent agent, XYLocation.Direction direction,
			MyAgent.HeadDirection head) {
		moveObject(agent, direction);
		((MyAgent) agent).setHeadDirection(head);
	}

	@Override
	public Percept getPerceptSeenBy(Agent anAgent) {
		// TODO Auto-generated method stub
		XYLocation loc = envState.getCurrentLocationFor(anAgent);
		System.out.println("vị trí agent: " + loc.toString());
		MyPercept percept = new MyPercept(loc, envState.getLocationState(loc));
		percept.setGoHome(envState.isGoHome());
		percept.setObjectNear(isObjectNearGood(anAgent));
		percept.setWall(isWal);
		return percept;
	}

	public boolean isObjectNearGood(Agent anAgent) {
		Set<EnvironmentObject> sets = getObjectsNear(anAgent, 1);
		System.out.println(sets.size());
		Iterator<EnvironmentObject> iterator = sets.iterator();
		while (iterator.hasNext()) {
			XYLocation loc = envState.getCurrentLocationFor(iterator.next());
			//System.out.println("{tọa độ}" + loc.toString());
			if (envState.getLocationState(loc) == LocationState.DIRTY) {
				return true;
			}
		}
		return false;
	}

	public void addObjectToLocation(EnvironmentObject eo, XYLocation loc) {
		moveObjectToAbsoluteLocation(eo, loc);
	}

	public void moveObjectToAbsoluteLocation(EnvironmentObject eo,
			XYLocation loc) {
		// Ensure the object is not already at a location
		envState.moveObjectToAbsoluteLocation(eo, loc);

		// Ensure is added to the environment
		addEnvironmentObject(eo);
	}

	public void locationDirtytoClean(XYLocation loc) {
		// Ensure the object is not already at a location
		envState.locationDirtytoClean(loc);
		// Ensure is added to the environment
	}

	public void moveObject(EnvironmentObject eo, XYLocation.Direction direction) {
		XYLocation presentLocation = envState.getCurrentLocationFor(eo);

		if (null != presentLocation) {
			XYLocation locationToMoveTo = presentLocation.locationAt(direction);
			if (!(envState.isBlocked(locationToMoveTo))) {
				moveObjectToAbsoluteLocation(eo, locationToMoveTo);
				isWal = false;
				System.out.println("vị trí agent di chuyển tới: "
						+ locationToMoveTo.toString());
			} else {
				isWal = true;
				System.out.println("This is Wall");
			}
		}
	}

	public XYLocation getCurrentLocationFor(EnvironmentObject eo) {
		return envState.getCurrentLocationFor(eo);
	}

	public Set<EnvironmentObject> getObjectsAt(XYLocation loc) {
		return envState.getObjectsAt(loc);
	}

	public Set<EnvironmentObject> getObjectsNear(Agent agent, int radius) {
		return envState.getObjectsNear(agent, radius);
	}

	public void init() {

		for (int i = 0; i < envState.width; i++) {
			for (int j = 0; j < envState.height; j++) {
				XYLocation loc = new XYLocation(i, j);
				envState.setLocationState(LocationState.CLEAN, loc);
				envState.moveObjectToAbsoluteLocation(new Clean(), loc);
			}
		}

		for (int i = 0; i < envState.width; i++) {
			XYLocation loc = new XYLocation(i, 0);
			XYLocation loc2 = new XYLocation(i, envState.height - 1);
			envState.moveObjectToAbsoluteLocation(new Wall(), loc);
			envState.moveObjectToAbsoluteLocation(new Wall(), loc2);
		}

		for (int i = 0; i < envState.height; i++) {
			XYLocation loc = new XYLocation(0, i);
			XYLocation loc2 = new XYLocation(envState.width - 1, i);
			envState.moveObjectToAbsoluteLocation(new Wall(), loc);
			envState.moveObjectToAbsoluteLocation(new Wall(), loc2);
		}
		int max_dirty = (envState.width * envState.height) / height;
		while (max_dirty > 0) {
			int x = (int) (Math.random() * envState.width);
			int y = (int) (Math.random() * envState.height);
			XYLocation loc = new XYLocation(x, y);
			if (!envState.isBlocked(loc) && !envState.isDirty(loc)) {
				envState.setLocationState(LocationState.DIRTY, loc);
				envState.moveObjectToAbsoluteLocation(new Dirty(), loc);
				max_dirty--;
			}

		}
	}

	@Override
	public void addAgent(Agent a) {
		addObjectToLocation(a, new XYLocation(1, 1));
		super.addAgent(a);
	}

	public void gohome(Action a) {
		for (Agent agent : agents) {
			if (agent.isAlive()) {
				EnvironmentState es = executeAction(agent, a);
				updateEnvironmentViewsAgentActed(agent, a, es);
			}
		}
	}

	@Override
	public void stepUntilDone() {
		while (!isDone()) {
			step();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
