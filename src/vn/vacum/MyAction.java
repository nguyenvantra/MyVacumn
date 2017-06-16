package vn.vacum;
import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;


public class MyAction extends DynamicAction {
	public static final Action TURN_LEFT = new MyAction("Left");
	public static final Action TURN_RIGHT = new MyAction("Right");
	public static final Action GO_FORWARD = new MyAction("forward");
	public static final Action SUCK = new MyAction("Suck");
	public static final Action GO_HOME = new MyAction("go home");
	public static final Action RADA = new MyAction("rada");
	public MyAction(String name) {
		super(name);
	}
}
