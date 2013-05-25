package survive.client;

import com.badlogic.gdx.Game;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import survive.client.screens.Fonts;
import survive.client.screens.GameScreen;
import survive.client.screens.LoginScreen;
import survive.client.screens.MainMenu;
import survive.common.network.*;
import survive.common.world.WorldConstrains;

import java.io.IOException;
import java.util.logging.Logger;

public class SurviveClient extends Game {
	private final static Logger LOGGER = Logger.getLogger(SurviveClient.class.getName());

	public int WIDTH;
	public int HEIGHT;

	public Fonts fonts;

	public MainMenu mainMenu;
	public LoginScreen loginScreen;
	public GameScreen gameScreen;

	private static final String serverAddress = "localhost";
	private static final int TCP_PORT = Network.port;
	private static final int TIMEOUT = 5000;
	private Client client;

	private World world;

	public SurviveClient(int WIDTH, int HEIGHT) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;

		client = new Client();
		client.start();
		LOGGER.info("Client created");

		Network.register(client);
		LOGGER.info("Network registration succeeded");

		client.addListener(new Listener() {
			@Override
			public void connected(Connection connection) {
				LOGGER.info("Connected to server");
			}

			@Override
			public void disconnected(Connection connection) {
				LOGGER.info("Disconnected from server");
			}

			@Override
			public void received(Connection connection, Object object) {
				super.received(connection, object);
				LOGGER.info("Received object " + object.getClass().getSimpleName());
				forwardMessage(object);
			}
		});
	}

	public void sendMessage(Object object) {
		client.sendTCP(object);
	}

	private void forwardMessage(Object object) {
		if (object instanceof LoginResponse) {
			loginScreen.pushMessage(object);
		}
		if (object instanceof WorldConstrains) {
			loginScreen.pushMessage(object);
		}
		if (object instanceof PlayerInfo) {
			loginScreen.pushMessage(object);
		}
		if (object instanceof AddGameObject) {
			gameScreen.pushMessage(object);
		}
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public World getWorld() {
		return world;
	}

	public boolean login(String name) {
		if (!client.isConnected()) {
			try {
				LOGGER.info("Connecting to " + serverAddress + ":" + String.valueOf(TCP_PORT));
				client.connect(TIMEOUT, serverAddress, TCP_PORT);
				LOGGER.info("Connection established");
			} catch (IOException e) {
				LOGGER.info("Connection refused");
				return false;
			}
		}

		LOGGER.info("Connection is ok");

		LOGGER.info("Trying to login. Name: " + name);
		client.sendTCP(new Login(name));

		return true;
	}

	@Override
	public void create() {
		fonts = new Fonts();

		mainMenu = new MainMenu(this);
		loginScreen = new LoginScreen(this);
		gameScreen = new GameScreen(this);

		setScreen(mainMenu);
		mainMenu.show();
	}
}
