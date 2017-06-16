package vn.vacum;
import aima.core.agent.impl.AbstractAgent;


public class MyAgent extends AbstractAgent{
	HeadDirection headDirection;
	public enum HeadDirection{
		North, South, East, West
	}
	public MyAgent() {
		super(new MyAgentProgram());
	}
	
	public void setHeadDirection(HeadDirection dir){
		this.headDirection = dir;
	}
	public HeadDirection getHeadDirection(){
		return headDirection;
	}
	public String toString(){
		String display = null;
		switch (getHeadDirection()) {
		case North:
			display =  "▲ ";
			break;
		case South:
			display = "▼ ";
			break;
		case East:
			display = "► ";
			break;
		case West:
			display = "◄ ";
			break;
		default:
			break;
		}
		return display;
	}
}
