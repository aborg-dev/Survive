package survive.common.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import survive.common.utils.Direction;
import survive.common.utils.Position;
import survive.common.world.WorldConstrains;
import survive.common.world.gameobject.NPC;
import survive.common.world.gameobject.Player;

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
		kryo.register(Position.class);
		kryo.register(Direction.class);
		kryo.register(NPC.class);
		kryo.register(AddGameObject.class);
		kryo.register(SetMovement.class);
		kryo.register(SetDirection.class);
		kryo.register(RemoveGameObject.class);
		kryo.register(LoginResponse.class);
		kryo.register(WorldConstrains.class);
		kryo.register(CharacterPositionChange.class);
	}
}
