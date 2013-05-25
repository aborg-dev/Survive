package survive.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import survive.client.SurviveClient;
import survive.client.World;
import survive.common.network.AddGameObject;
import survive.common.network.SetDirection;
import survive.common.network.SetMovement;
import survive.common.network.TestPackage;
import survive.common.utils.Direction;
import survive.common.utils.Position;
import survive.common.world.gameobject.Character;
import survive.common.world.gameobject.GameObject;
import survive.common.world.gameobject.Player;

import java.util.logging.Logger;

public class GameScreen extends SurviveScreen {
	private final static Logger LOGGER = Logger.getLogger(SurviveClient.class.getName());

	private Position playerPosition;
	private boolean isMoving = false;
	private int horizontal = 0;
	private int vertical = 0;

	public GameScreen(SurviveClient surviveClient) {
		super(surviveClient);
	}

	@Override
	protected void update(float delta) {
		int horizontal = 0;
		int vertical = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			vertical -= 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			vertical += 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			horizontal -= 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			horizontal += 1;
		}

		if (horizontal == 0 && vertical == 0) {
			if (isMoving) {
				sendMessage(new SetMovement(false));
			}
			isMoving = false;
			this.horizontal = 0;
			this.vertical = 0;
		} else {
			if (!isMoving) {
				sendMessage(new SetMovement(true));
			}
			isMoving = true;
			if (horizontal != this.horizontal || vertical != this.vertical) {
				sendMessage(new SetDirection(new Direction(horizontal, vertical)));
			}
			this.horizontal = horizontal;
			this.vertical = vertical;
		}

		if (playerPosition == null) {
			int playerId = surviveClient.getWorld().getPlayerInfo().id;
			GameObject playerObject = surviveClient.getWorld().getGameObjects().get(playerId);
			if (playerObject != null) {
				LOGGER.info("Player found!");
				Player player = (Player)playerObject;
				playerPosition = player.getPosition();
	//			stage.getCamera().position.set(playerPosition.x, playerPosition.y, 0.0f);
			} else {
				LOGGER.info("Player NOT found =((");
			}
		}
//		stage.getCamera().position.set(0.0f, 0.0f, 0.0f);
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
					if (playerPosition == null) {
						surviveClient.fonts.gameFont.draw(batch, "@", character.getPosition().x, character.getPosition().y);
						stringBuilder.append(" (" + character.getPosition().x + ", " + character.getPosition().y + ")");
					} else {
						Position result = new Position();
						result.x = character.getPosition().x - playerPosition.x + width / 2;
						result.y = character.getPosition().y - playerPosition.y + height / 2;
						surviveClient.fonts.gameFont.draw(batch, "@", result.x, result.y);
						stringBuilder.append(" (" + result.x + ", " + result.y + ")");
					}
				}
			}
			surviveClient.fonts.gameFont.draw(batch, stringBuilder.toString(), 50, 130);
			Vector3 cameraPosition = stage.getCamera().position;
			String cameraInfo = "Camera is at: (" + cameraPosition.x + ", " + cameraPosition.y + ")";
			surviveClient.fonts.gameFont.draw(batch, cameraInfo, 50, 160);

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
