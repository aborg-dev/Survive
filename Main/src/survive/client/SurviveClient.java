package survive.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import survive.client.screens.GameScreen;
import survive.client.screens.MainMenu;
import survive.common.network.Login;
import survive.common.network.LoginResponse;
import survive.common.network.Network;
import survive.common.world.WorldConstrains;

import java.io.IOException;
import java.util.logging.Logger;

public class SurviveClient extends Game {
	private final static Logger LOGGER = Logger.getLogger(SurviveClient.class.getName());

	public int WIDTH;
	public int HEIGHT;

	private static final String serverAddress = "localhost";
	private static final int TCP_PORT = Network.port;
	private Client client;

	// It needs for pushing into Listener
	private SurviveClient self;
	private World world;

	public SurviveClient(int WIDTH, int HEIGHT) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;

		client = new Client();
		client.start();
		LOGGER.info("Client created");

		Network.register(client);
		LOGGER.info("Network registration succeeded");

		try {
			LOGGER.info("Connection to " + serverAddress + ":" + String.valueOf(TCP_PORT));
			client.connect(5000, serverAddress, TCP_PORT);
			LOGGER.info("Connection established");
		} catch (IOException e) {
			LOGGER.info("Connection refused");
			e.printStackTrace();
		}

		client.addListener(new Listener() {
			@Override
			public void disconnected(Connection connection) {
				super.disconnected(connection);
			}

			@Override
			public void received(Connection connection, Object object) {
				super.received(connection, object);

				LOGGER.info("Received a message");

				if (object instanceof LoginResponse) {
					LoginResponse response = (LoginResponse) object;
					switch (response) {
						case SUCCESS:
							changeScreen(new GameScreen(self));
							LOGGER.info("Logged in");
							break;
						case INCORRECT_NAME:
							break;
						case YOU_ARE_ALREADY_LOGGED_IN:
							break;
						case NAME_IS_ALREADY_IN_USE:
							break;
					}
				}

				if (object instanceof WorldConstrains) {
					WorldConstrains constrains = (WorldConstrains) object;
					world = new World(constrains);
				}
			}
		});
	}

	public void changeScreen(Screen screen) {
		Screen currentScreen = getScreen();
		if (currentScreen != null) {
			currentScreen.hide();
		}
		setScreen(screen);
		screen.show();
	}

	public void login(String name) {
		client.sendTCP(new Login(name));
	}

	@Override
	public void create() {
		changeScreen(new MainMenu(this));
	}
}
