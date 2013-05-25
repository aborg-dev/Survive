package survive.common.network;

import survive.common.world.gameobject.GameObject;

/**
 * Created with IntelliJ IDEA.
 * User: iiotep9huy
 * Date: 5/24/13
 * Time: 2:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class AddGameObject {
	public AddGameObject() {
	}

	public AddGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}

	GameObject gameObject;
}
