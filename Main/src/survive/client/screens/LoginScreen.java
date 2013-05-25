package survive.client.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import survive.client.SurviveClient;
import survive.client.World;
import survive.common.network.LoginResponse;
import survive.common.network.PlayerInfo;
import survive.common.world.WorldConstrains;

import java.util.logging.Logger;

public class LoginScreen extends SurviveScreen {
	private final static Logger LOGGER = Logger.getLogger(MainMenu.class.getName());

	private String loginName = "";
	private boolean needLogin = false;

	private Label.LabelStyle style = new Label.LabelStyle();
	private Label loginLabel;

	private boolean worldConstrainsReady = false;
	private boolean playerInfoReady = false;

	public LoginScreen(SurviveClient surviveClient) {
		super(surviveClient);

		style.font = surviveClient.fonts.menuFont;
		style.fontColor = Color.WHITE;
		loginLabel = new Label("Login...", style);
		loginLabel.setPosition(-loginLabel.getWidth() / 2, -loginLabel.getHeight() / 2);

		stage.addActor(loginLabel);
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setNeedLogin(boolean needLogin) {
		this.needLogin = needLogin;
	}

	@Override
	public void show() {
		super.show();
		// KOCTbIJIb
		worldConstrainsReady = false;
		playerInfoReady = false;
		if (needLogin) {
			needLogin = false;
			LOGGER.info("Call login method of SurviveClient");
			if (!surviveClient.login(loginName)) {
				changeScreen(surviveClient.mainMenu);
			}
		}
	}

	@Override
	protected void update(float delta) {
		stage.getCamera().position.set(0.0f, 0.0f, 0.0f);
		if (worldConstrainsReady && playerInfoReady) {
			worldConstrainsReady = false;
			playerInfoReady = false;
			changeScreen(surviveClient.gameScreen);
		}
	}

	@Override
	protected void draw() {
		stage.draw();
	}

	@Override
	protected void receive(Object object) {
		LOGGER.fine("Received a message " + object.getClass().getSimpleName());

		if (object instanceof LoginResponse) {
			LoginResponse response = (LoginResponse) object;
			switch (response) {
				case SUCCESS:
					LOGGER.info("Logged in");
					break;
				case INCORRECT_NAME:
					changeScreen(surviveClient.mainMenu);
					LOGGER.info("Incorrect name");
					break;
				case YOU_ARE_ALREADY_LOGGED_IN:
					changeScreen(surviveClient.mainMenu);
					LOGGER.info("You are already logged in");
					break;
				case NAME_IS_ALREADY_IN_USE:
					changeScreen(surviveClient.mainMenu);
					LOGGER.info("This name is already in use");
					break;
			}
		}

		if (object instanceof WorldConstrains) {
			LOGGER.info("Setting world constrains");
			worldConstrainsReady = true;
			WorldConstrains constrains = (WorldConstrains) object;
			surviveClient.setWorld(new World(constrains));
		}

		if (object instanceof PlayerInfo) {
			LOGGER.info("Setting player info");
			playerInfoReady = true;
			PlayerInfo playerInfo = (PlayerInfo)object;
			World world = surviveClient.getWorld();
			world.setPlayerInfo(playerInfo);
		}
	}
}
