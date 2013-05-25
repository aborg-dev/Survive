package survive.client.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import survive.client.SurviveClient;
import survive.client.World;

public class GameScreen extends SurviveScreen {
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
			String text = world.getWidth() + ":" + world.getHeight();
			surviveClient.fonts.gameFont.draw(batch, text, 50, 100);
			batch.end();
		}
	}

	@Override
	protected void receive(Object object) {
	}
}
