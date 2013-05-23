import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import survive.client.SurviveClient;

/**
 * Created with IntelliJ IDEA.
 * User: iiotep9huy
 * Date: 5/21/13
 * Time: 11:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class DesktopClientStarter {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Survive";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 480;
		new LwjglApplication(new SurviveClient(), cfg);
	}
}
