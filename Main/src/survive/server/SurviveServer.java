package survive.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import survive.common.gameobject.GameObject;
import survive.common.gameobject.Player;
import survive.common.network.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: iiotep9huy
 * Date: 5/21/13
 * Time: 11:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class SurviveServer {
    private Server server;
    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<String, User>();
    private World world;

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

        server.addListener(new Listener() {
            @Override
            public void disconnected(Connection c) {
                UserConnection connection = (UserConnection) c;
                String userName = connection.getUserName();
                if (users.containsKey(userName)) {
                    loggedOut(userName);
                }
            }

            @Override
            public void received(Connection c, Object object) {
                UserConnection connection = (UserConnection) c;

                if (object instanceof Login) {
                    final Login login = (Login) object;

                    if (!isValidName(login.name)) {
                        connection.close();
                        return; // Send incorrectNameResponse
                    }

                    if (connection.getUserName() != null) {
                        return; // Send alreadyLoggedInResponse
                    }

                    if (users.containsKey(login.name)) {
                        return; // Send userWithThisLoginIsAlreadyLoggedInResponse
                    }

                    loggedIn(connection, login);
                    return;
                }
            }
        });

        server.bind(Network.port);
        server.start();
        Log.info("Server started at port " + Network.port);
    }

    private void addUser(User user) {
        users.put(user.getName(), user);
        Log.debug("User " + user.getName() + " added.");
    }

    private void loggedIn(UserConnection connection, Login login) {
        String name = login.name;
        connection.setUserName(login.name);
        User user = new User(name);
        addUser(user);
        Player player = world.addPlayer(name);

        for (GameObject gameObject : world.getPlayerGameObjects(name)) {
            AddGameObject addGameObject = new AddGameObject(gameObject);
            connection.sendTCP(addGameObject);
        }

        AddGameObject addPlayerGameObject = new AddGameObject(player);
        server.sendToAllTCP(addPlayerGameObject);
        Log.info("User " + name + " logged in.");
    }

    private void loggedOut(String name) {
        users.remove(name);
        int playerId = world.getPlayerId(name);
        RemoveGameObject removeGameObject = new RemoveGameObject(playerId);
        server.sendToAllTCP(removeGameObject);
        Log.info("User " + name + " logged out.");
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
