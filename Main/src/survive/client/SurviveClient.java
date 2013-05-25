package survive.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import survive.client.screens.MainMenu;
import survive.common.network.Login;
import survive.common.network.Network;

import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SurviveClient extends Game {
	private final static Logger LOGGER = Logger.getLogger(SurviveClient.class.getName());

	// Queue for received messages from SurviveClient to translate them to the screen
	private BlockingDeque<Object> messages = new LinkedBlockingDeque<Object>();

	public int WIDTH;
	public int HEIGHT;

	private static final String serverAddress = "localhost";
	private static final int TCP_PORT = Network.port;
	private static final int TIMEOUT = 5000;
	private Client client;

	// It needs for pushing into Listener
	private SurviveClient self = this;
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
				messages.push(object);
				return;
				/*
				LOGGER.fine("Received a message " + object.getClass().getSimpleName());

				if (object instanceof LoginResponse) {
					LoginResponse response = (LoginResponse) object;
					switch (response) {
						case SUCCESS:
							changeScreen(new GameScreen(self));
							LOGGER.info("Logged in");
							break;
						case INCORRECT_NAME:
							LOGGER.info("Incorrect name");
							break;
						case YOU_ARE_ALREADY_LOGGED_IN:
							LOGGER.info("You are already logged in");
							break;
						case NAME_IS_ALREADY_IN_USE:
							LOGGER.info("Name is already in use");
							break;
					}
				}

				if (object instanceof WorldConstrains) {
					WorldConstrains constrains = (WorldConstrains) object;
					world = new World(constrains);
				}  */
			}
		});

		try {
			LOGGER.info("Connecting to " + serverAddress + ":" + String.valueOf(TCP_PORT));
			client.connect(TIMEOUT, serverAddress, TCP_PORT);
			LOGGER.info("Connection established");
		} catch (IOException e) {
			LOGGER.info("Connection refused");
			e.printStackTrace();
		}

	}

	public Object pollMessage() {
		return messages.poll();
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
		LOGGER.info("Trying to login. Name: " + name);
		client.sendTCP(new Login(name));
	}

	@Override
	public void create() {
		changeScreen(new MainMenu(this));
	}

	public static void main(String[] args) {
		LOGGER.setLevel(Level.FINE);
		new SurviveClient(640, 480);
	}
}
