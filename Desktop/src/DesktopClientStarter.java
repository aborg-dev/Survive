import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import survive.client.UITest;

public class DesktopClientStarter {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Survive";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 480;
		new LwjglApplication(new UITest(), cfg);
	}
}
