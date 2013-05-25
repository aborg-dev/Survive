package survive.client.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import survive.client.SurviveClient;
import survive.client.World;
import survive.common.world.gameobject.GameObject;
import survive.common.world.gameobject.Character;

import java.util.logging.Logger;

public class GameScreen extends SurviveScreen {
	private final static Logger LOGGER = Logger.getLogger(SurviveClient.class.getName());

	public GameScreen(SurviveClient surviveClient) {
		super(surviveClient);
	}

	@Override
	protected void update(float delta) {
	}

	@Override
	protected void draw() {
		SpriteBatch batch = stage.getSpriteBatch();
		if (surviveClient.getWorld() != null) {
			World world = surviveClient.getWorld();
			batch.begin();
			String text = world.getWidth() + ":" + world.getHeight() + " gameObjects: " + world.getGameObjects().size();
			surviveClient.fonts.gameFont.draw(batch, text, 50, 100);

			for (GameObject gameObject : world.getGameObjects().values()) {
				if (gameObject instanceof Character) {
					Character character = (Character)gameObject;
					surviveClient.fonts.gameFont.draw(batch, "@", character.getPosition().x, character.getPosition().y);
				}
			}
			batch.end();
		}
	}

	@Override
	protected void receive(Object object) {
		if (object instanceof GameObject) {
			surviveClient.getWorld().addGameObject((GameObject)object);
			LOGGER.info("Game object received");
		}
	}
}
