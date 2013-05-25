package survive.common.world.gameobject;

import survive.common.utils.Direction;

/**
 * Created with IntelliJ IDEA.
 * User: iiotep9huy
 * Date: 5/24/13
 * Time: 2:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class Player extends Character {
	public Player() {
	}

	public Player(int id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMovement(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	String name;
}
