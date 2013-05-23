package survive.server;

import com.esotericsoftware.minlog.Log;
import survive.common.gameobject.GameObject;
import survive.common.gameobject.Player;
import survive.common.gameobject.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: iiotep9huy
 * Date: 5/24/13
 * Time: 2:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class World {
    private ConcurrentHashMap<Integer, GameObject> gameObjects = new ConcurrentHashMap<Integer, GameObject>();
    private ConcurrentHashMap<String, Integer> playerId = new ConcurrentHashMap<String, Integer>();
    private AtomicInteger lastGameObjectId = new AtomicInteger(0);
    private WorldConstrains worldConstrains;
    private Random random = new Random();

    World() {
        worldConstrains = new WorldConstrains(128, 128);
        Log.info("World created.");
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.put(gameObject.getId(), gameObject);
        Log.debug("Game object " + gameObject.getId() + " added.");
    }

    public void putInRandomPosition(Character character) {
        character.x = random.nextInt() % worldConstrains.getHeight();
        character.y = random.nextInt() % worldConstrains.getWidth();
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

    public Iterable<GameObject> getPlayerGameObjects(String playerName) {
        List<GameObject> playerGameObjects = new ArrayList<GameObject>();
        playerGameObjects.addAll(gameObjects.values());
        return playerGameObjects;
    }

    private int getNewId() {
        return lastGameObjectId.getAndAdd(1);
    }

}
