package survive.common.world.gameobject;

import survive.common.utils.Direction;
import survive.common.utils.Position;

/**
 * Created with IntelliJ IDEA.
 * User: iiotep9huy
 * Date: 5/24/13
 * Time: 2:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class Character extends GameObject {
	public Character(int id) {
		super(id);
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	Position position;
	Direction direction;
}
