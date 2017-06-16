package vn.vacum;

public class MyAgentDemo {
	public static void main(String[] args) {
		MyEnvironment en = new MyEnvironment(5, 5);
		MyAgent agent = new MyAgent();
		agent.setHeadDirection(MyAgent.HeadDirection.East);
		en.addEnvironmentView(new MyEnvironmentView());
		en.addAgent(agent);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					en.stepUntilDone();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
