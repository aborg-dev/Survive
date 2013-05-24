import com.esotericsoftware.minlog.Log;
import survive.server.SurviveServer;

public class DesktopServerStarter {
	public static void main(String[] args) {
		Log.set(Log.LEVEL_DEBUG);
		SurviveServer server = new SurviveServer();
		try {
			server.run();
		} catch (Exception ex) {
			Log.error("Failed to start server.");
			ex.printStackTrace();
		}
	}
}
