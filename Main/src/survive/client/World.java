package survive.client;

import survive.common.world.WorldConstrains;

import java.util.logging.Logger;

public class World {
	private final static Logger LOGGER = Logger.getLogger(World.class.getName());

	private int width;
	private int height;

	public World(WorldConstrains constrains) {
		width = constrains.getWidth();
		height = constrains.getHeight();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
