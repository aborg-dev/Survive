package survive.common.world;

/**
 * Created with IntelliJ IDEA.
 * User: iiotep9huy
 * Date: 5/24/13
 * Time: 2:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class WorldConstrains {
	public WorldConstrains() {
	}

	public WorldConstrains(int height, int width) {
		this.height = height;
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	int height, width;
}
