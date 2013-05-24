package survive.common.utils;

/**
 * User: iiotep9huy
 * Date: 5/24/13
 * Time: 5:15 PM
 * Project: Survive
 */
public class Position {
	public Position() {
		this(0, 0);
	}

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int x, y;
}
