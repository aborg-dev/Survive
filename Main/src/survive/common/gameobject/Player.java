package survive.common.gameobject;

/**
 * Created with IntelliJ IDEA.
 * User: iiotep9huy
 * Date: 5/24/13
 * Time: 2:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class Player extends Character {
	public Player(int id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	String name;
}
