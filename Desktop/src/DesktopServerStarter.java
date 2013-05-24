import survive.server.SurviveServer;

public class DesktopServerStarter {
	public static void main(String[] args) {
		SurviveServer server = new SurviveServer();
		try {
			server.run();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
