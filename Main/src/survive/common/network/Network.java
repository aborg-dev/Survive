package survive.common.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import survive.common.gameobject.Player;

/**
 * Created with IntelliJ IDEA.
 * User: iiotep9huy
 * Date: 5/24/13
 * Time: 1:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class Network {
	static public final int port = 55455;

	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Login.class);
		kryo.register(Character.class);
		kryo.register(Player.class);
		kryo.register(AddGameObject.class);
		kryo.register(RemoveGameObject.class);
		kryo.register(LoginResponse.class);
	}
}
