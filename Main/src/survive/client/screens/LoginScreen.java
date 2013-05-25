package survive.client.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import survive.client.SurviveClient;
import survive.client.World;
import survive.common.network.LoginResponse;
import survive.common.world.WorldConstrains;

import java.util.logging.Logger;

public class LoginScreen extends SurviveScreen {
	private final static Logger LOGGER = Logger.getLogger(MainMenu.class.getName());

	private String loginName = "";

	private Label.LabelStyle style = new Label.LabelStyle();
	private Label loginLabel;

	public LoginScreen(SurviveClient surviveClient) {
		super(surviveClient);

		style.font = menuFont;
		style.fontColor = Color.WHITE;
		loginLabel = new Label("Login...", style);
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Override
	public void show() {
		super.show();
		surviveClient.login(loginName);
	}

	@Override
	protected void update(float delta) {
	}

	@Override
	protected void draw() {
	}

	@Override
	protected void receive(Object object) {
		LOGGER.fine("Received a message " + object.getClass().getSimpleName());

		if (object instanceof LoginResponse) {
			LoginResponse response = (LoginResponse) object;
			switch (response) {
				case SUCCESS:
					changeScreen(surviveClient.gameScreen);
					LOGGER.info("Logged in");
					break;
				case INCORRECT_NAME:
					changeScreen(surviveClient.mainMenu);
					LOGGER.info("Incorrect name");
					break;
			}
		}

		if (object instanceof WorldConstrains) {
			WorldConstrains constrains = (WorldConstrains) object;
			surviveClient.setWorld(new World(constrains));
		}
	}
}
