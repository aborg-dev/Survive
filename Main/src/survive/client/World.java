package survive.client;

import survive.common.network.PlayerInfo;
import survive.common.world.WorldConstrains;
import survive.common.world.gameobject.GameObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class World {
	private final static Logger LOGGER = Logger.getLogger(World.class.getName());

	private Map<Integer, GameObject> gameObjects = new HashMap<Integer, GameObject>();
	private PlayerInfo playerInfo;

	private int width;
	private int height;

	public World(WorldConstrains constrains) {
		width = constrains.getWidth();
		height = constrains.getHeight();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void addGameObject(GameObject gameObject) {
		gameObjects.put(gameObject.getId(), gameObject);
		LOGGER.info("GameObject with id " + gameObject.getId() + " added to the world");
	}

	public Map<Integer, GameObject> getGameObjects() {
		return gameObjects;
	}

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public void setPlayerInfo(PlayerInfo playerInfo) {
		this.playerInfo = playerInfo;
	}
}
