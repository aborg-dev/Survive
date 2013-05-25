package survive.client.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import survive.client.SurviveClient;
import survive.client.World;
import survive.common.network.AddGameObject;
import survive.common.world.gameobject.Character;
import survive.common.world.gameobject.GameObject;

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

			StringBuilder stringBuilder = new StringBuilder("Game objects are:");

			for (GameObject gameObject : world.getGameObjects().values()) {
				if (gameObject instanceof Character) {
					Character character = (Character)gameObject;
					surviveClient.fonts.gameFont.draw(batch, "@", character.getPosition().x, character.getPosition().y);
					stringBuilder.append(" (" + character.getPosition().x + ", " + character.getPosition().y + ")");
				}
			}
			surviveClient.fonts.gameFont.draw(batch, stringBuilder.toString(), 50, 130);
			batch.end();
		}
	}

	@Override
	protected void receive(Object object) {
		if (object instanceof AddGameObject) {
			AddGameObject addGameObject = (AddGameObject)object;
			surviveClient.getWorld().addGameObject(addGameObject.gameObject);
			LOGGER.info("Game object received");
		}
	}
}
