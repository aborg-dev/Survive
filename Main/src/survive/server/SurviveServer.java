package survive.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import survive.common.network.*;
import survive.common.world.gameobject.Character;
import survive.common.world.gameobject.GameObject;
import survive.common.world.gameobject.Player;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class SurviveServer {
	private final static Logger LOGGER = Logger.getLogger(SurviveServer.class.getName());

	private Server server;
	private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<String, User>();
	private World world;
	private final float updateDelta = 50;
	private final int sleepTime = 2000;

	public SurviveServer() {
	}

	public void run() throws IOException {
		server = new Server() {
			protected Connection newConnection() {
				return new UserConnection();
			}
		};
		Network.register(server);

		// Read world params from config
		world = new World();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					world.update(updateDelta);
					String name = null;
					if (!users.isEmpty()) {
						name = users.keys().nextElement();
					}
					if (name != null) {
						LOGGER.info("Sample name " + name);
						for (GameObject gameObject : world.getGameObjectsForPlayer(users.keys().nextElement())) {
							LOGGER.info("Sending " + gameObject.getClass().getSimpleName());
							if (gameObject instanceof Character) {
								CharacterPositionChange positionChange = new CharacterPositionChange();
								Character character = (Character) gameObject;
								positionChange.id = character.getId();
								positionChange.position = character.getPosition();
								server.sendToAllTCP(positionChange);
							}
						}
					}
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		server.addListener(new Listener() {
			@Override
			public void connected(Connection connection) {
				LOGGER.info("User connected");
				connection.sendTCP(new Login("aaa"));
			}

			@Override
			public void disconnected(Connection c) {
				UserConnection connection = (UserConnection) c;
				String userName = connection.getUserName();
				if (userName != null) {
					if (users.containsKey(userName)) {
						loggedOut(userName);
					}
				}
				LOGGER.info("User disconnected");
			}

			@Override
			public void received(Connection c, Object object) {
				UserConnection connection = (UserConnection) c;

				LOGGER.fine("Received a message " + object.getClass().getSimpleName());

				if (object instanceof Login) {
					final Login login = (Login) object;

					if (!isValidName(login.name)) {
						connection.sendTCP(LoginResponse.INCORRECT_NAME);
						connection.close();
					}

					if (connection.getUserName() != null) {
						connection.sendTCP(LoginResponse.YOU_ARE_ALREADY_LOGGED_IN);
						return;
					}

					if (users.containsKey(login.name)) {
						connection.sendTCP(LoginResponse.NAME_IS_ALREADY_IN_USE);
						return;
					}

					loggedIn(connection, login);
					connection.sendTCP(LoginResponse.SUCCESS);
					return;
				}

				if (object instanceof SetMovement) {
					if (connection.userName == null) {
						connection.close();
						return;
					}

					world.setPlayerMovement(connection.userName, ((SetMovement) object).isMoving);
					return;
				}

				if (object instanceof SetDirection) {
					if (connection.userName == null) {
						connection.close();
						return;
					}

					world.setPlayerDirection(connection.userName, ((SetDirection) object).direction);
				}
			}
		});

		server.bind(Network.port);
		server.start();
		LOGGER.info("Server started at port " + Network.port);
	}

	private void addUser(User user) {
		users.put(user.getName(), user);
		LOGGER.fine("User " + user.getName() + " added");
	}

	private void loggedIn(UserConnection connection, Login login) {
		String name = login.name;
		connection.setUserName(login.name);
		User user = new User(name);
		addUser(user);

		Player player = world.addPlayer(name);
		sendWorldInfo(connection);
		AddGameObject addPlayerGameObject = new AddGameObject(player);
		server.sendToAllTCP(addPlayerGameObject);

		LOGGER.info("User " + name + " logged in.");
	}

	void sendWorldInfo(UserConnection connection) {
		String name = connection.userName;

		connection.sendTCP(world.getWorldConstrains());

		for (GameObject gameObject : world.getGameObjectsForPlayer(name)) {
			AddGameObject addGameObject = new AddGameObject(gameObject);
			connection.sendTCP(addGameObject);
		}
	}

	private void loggedOut(String name) {
		users.remove(name);
		int playerId = world.getPlayerId(name);
		RemoveGameObject removeGameObject = new RemoveGameObject(playerId);
		server.sendToAllTCP(removeGameObject);
		LOGGER.info("User " + name + " logged out");
	}

	private boolean isValidName(String name) {
		if (name == null) {
			return false;
		}
		name = name.trim();
		if (name.length() == 0) {
			return false;
		}
		return true;
	}

	private static class UserConnection extends Connection {
		private String getUserName() {
			return userName;
		}

		private void setUserName(String userName) {
			this.userName = userName;
		}

		String userName;
	}
}
