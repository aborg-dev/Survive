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
        SurviveServer server = new SurviveServer();
        server.run();
    }
}
