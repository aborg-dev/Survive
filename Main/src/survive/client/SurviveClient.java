package survive.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import survive.client.screens.MainMenu;
import survive.common.network.Network;

import java.io.IOException;

public class SurviveClient extends Game {
	public int WIDTH;
	public int HEIGHT;

	public SurviveClient(int WIDTH, int HEIGHT) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;

		Log.set(Log.LEVEL_DEBUG);
		Log.debug("hello");

		Client client = new Client();
		String address = "localhost";
		int TCP_PORT = Network.port;

		client.start();
		try {
			client.connect(5000, address, TCP_PORT);
		} catch (IOException e) {
			System.out.println("Connection refused");
		}
	}

	public void changeScreen(Screen screen) {
		Screen currentScreen = getScreen();
		if (currentScreen != null) {
			currentScreen.hide();
		}
		setScreen(screen);
		screen.show();
	}

	@Override
	public void create() {
		changeScreen(new MainMenu(this));
	}
}
