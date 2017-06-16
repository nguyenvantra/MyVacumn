package vn.vacum;
import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.EnvironmentState;
import aima.core.agent.EnvironmentView;
import aima.core.util.datastructure.XYLocation;


public class MyEnvironmentView implements EnvironmentView{

	@Override
	public void notify(String msg) {
		// TODO Auto-generated method stub
		System.out.println("Message: " + msg);
	}

	@Override
	public void agentAdded(Agent agent, EnvironmentState resultingState) {
		// TODO Auto-generated method stub
		printConsole(resultingState, agent);
	}

	@Override
	public void agentActed(Agent agent, Action action,
			EnvironmentState resultingState) {
		    System.out.println("Agent acted: " + action.toString());
			printConsole(resultingState, agent);
		
	}
//	public void printConsole(EnvironmentState envir, Agent a){
//		MyEnvironmentState envState = (MyEnvironmentState) envir;
//		System.out.println(envState.isGoHome());
//		for(int i = 0; i < envState.width ;i++){
//			for(int j = 0; j < envState.height ;j++){
//				XYLocation loc = new XYLocation(i, j);
//	            if(envState.isBlocked(loc)){
//	            	System.out.print("▄ ");
//	            }else if (envState.isAgent(loc)){
//	            	System.out.print(((MyAgent) a).toString());
//	            }else if (envState.getLocationState(loc) == LocationState.DIRTY){
//	            	System.out.print("▒ ");
//	            }else if (envState.getLocationState(loc) == LocationState.CLEAN){
//	            	System.out.print("  ");
//	            }
//			}System.out.println();
//		}
//	}
	public void printConsole(EnvironmentState envir, Agent a){
		MyEnvironmentState envState = (MyEnvironmentState) envir;
		for(int i = 0; i < envState.width ;i++){
			for(int j = 0; j < envState.height ;j++){
				XYLocation loc = new XYLocation(i, j);
	            if(envState.isBlocked(loc)){
	            	System.out.print("x ");
	            }else if (envState.isAgent(loc)){
	            	System.out.print(((MyAgent) a).toString());
	            }else if (envState.isDirty(loc)){
	            	System.out.print("* ");
	            }else if (envState.isClean(loc)){
	            	System.out.print("  ");
	            }
			}System.out.println();
		}
	}
}
