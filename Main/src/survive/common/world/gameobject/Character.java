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
	public Character() {
	}

	public Character(int id) {
		super(id);
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Position getPosition() {
		return position;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public void update(float delta) {
		if (isMoving) {
			position.x += direction.angleCos * delta;
			position.y += direction.angleSin * delta;
		}
	}

	boolean isMoving;
	Position position;
	Direction direction;
}
