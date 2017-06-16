package vn.vacum;

import java.util.Random;

import aima.core.agent.Action;
import aima.core.agent.AgentProgram;
import aima.core.agent.Percept;
import aima.core.agent.impl.NoOpAction;

public class MyAgentProgram implements AgentProgram {
	Action[] actions = { MyAction.GO_FORWARD, MyAction.TURN_LEFT,
			MyAction.TURN_RIGHT };

	@Override
	public Action execute(Percept percept) {
		// TODO Auto-generated method stub
		MyPercept vep = (MyPercept) percept;
//		System.out.println(vep.isObjectNear());
		if (vep.isGoHome()) {
			return MyAction.GO_HOME;
		}
		// if(vep.isObjectNear()){
		// return MyAction.RADA;
		// }
		// if status = Dirty then return Suck
		if (vep.getState() == LocationState.DIRTY) {
			return MyAction.SUCK;
			// else if location = A then return Right
		} else if (vep.getState() == LocationState.CLEAN) {
			Random r = new Random();
			Action action;
			if (vep.isWall()) {
				System.out.println("p " + vep.isWall());
				System.out.println("khong di huong nay");
				while (true) {
					action = actions[r.nextInt(3)];
					if (action != MyAction.GO_FORWARD) {
						return action;
					}
				}
			} else {
			//	System.out.println("r " + vep.isWall());
				return actions[r.nextInt(3)];
			}

		}
		// Note: This should not be returned if the
		// environment is correct
		return NoOpAction.NO_OP;
	}

}
