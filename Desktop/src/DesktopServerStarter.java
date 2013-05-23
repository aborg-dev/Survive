import com.esotericsoftware.minlog.Log;
import survive.server.SurviveServer;

/**
 * Created with IntelliJ IDEA.
 * User: iiotep9huy
 * Date: 5/21/13
 * Time: 11:45 PM
 * To change this template use File | Settings | File Templates.
 */
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
