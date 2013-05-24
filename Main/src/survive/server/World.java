package survive.server;

import survive.common.network.SetMovement;
import survive.common.utils.Position;
import survive.common.world.WorldConstrains;
import survive.common.world.gameobject.GameObject;
import survive.common.world.gameobject.Player;
import survive.common.world.gameobject.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: iiotep9huy
 * Date: 5/24/13
 * Time: 2:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class World {
	private final static Logger LOGGER = Logger.getLogger(World.class.getName());

	private ConcurrentHashMap<Integer, GameObject> gameObjects = new ConcurrentHashMap<Integer, GameObject>();
	private ConcurrentHashMap<String, Integer> playerId = new ConcurrentHashMap<String, Integer>();
	private AtomicInteger lastGameObjectId = new AtomicInteger(0);
	private WorldConstrains worldConstrains;
	private Random random = new Random();

	World() {
		worldConstrains = new WorldConstrains(128, 128);
		LOGGER.info("World created.");
	}

	public void addGameObject(GameObject gameObject) {
		gameObjects.put(gameObject.getId(), gameObject);
		LOGGER.fine("Game object " + gameObject.getId() + " added.");
	}

	public void putInRandomPosition(Character character) {
		Position randomPosition = new Position();
		randomPosition.x = random.nextInt() % worldConstrains.getHeight();
		randomPosition.y = random.nextInt() % worldConstrains.getWidth();
		character.setPosition(randomPosition);
	}

	public Player addPlayer(String name) {
		Player player = new Player(getNewId());
		player.setName(name);
		playerId.put(name, player.getId());
		putInRandomPosition(player);
		addGameObject(player);
		return player;
	}

	public int getPlayerId(String playerName) {
		return playerId.get(playerName);
	}

	public Iterable<GameObject> getGameObjectsForPlayer(String playerName) {
		List<GameObject> playerGameObjects = new ArrayList<GameObject>();
		playerGameObjects.addAll(gameObjects.values());
		return playerGameObjects;
	}

	private int getNewId() {
		return lastGameObjectId.getAndIncrement();
	}

	public WorldConstrains getWorldConstrains() {
		return worldConstrains;
	}

	public void update(float delta) {
		for (GameObject gameObject : gameObjects.values()) {
			gameObject.update(delta);
		}
	}

	public void setPlayerMovement(String playerName, SetMovement actionSetMovement) {
		int playerId = getPlayerId(playerName);
		Player player = (Player) gameObjects.get(playerId);
		player.setMovement(actionSetMovement);
	}
}
