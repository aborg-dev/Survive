package survive.client.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import survive.client.SurviveClient;

import java.util.logging.Logger;

public class MainMenu extends SurviveScreen {
	private final static Logger LOGGER = Logger.getLogger(MainMenu.class.getName());

	private TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
	private TextButton playButton;

	private TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
	private TextField loginField;

	private VerticalGroup menuGroup;

	public MainMenu(final SurviveClient surviveClient) {
		super(surviveClient);

		menuGroup = new VerticalGroup();

		textButtonStyle.font = menuFont;
		textButtonStyle.fontColor = Color.WHITE;
		textButtonStyle.overFontColor = Color.RED;
		textButtonStyle.downFontColor = Color.YELLOW;

		playButton = new TextButton("Play", textButtonStyle);

		Pixmap whitePixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		whitePixmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		whitePixmap.fill();
		Texture whiteTexture = new Texture(whitePixmap);
		Image whiteImage = new Image(whiteTexture);

		textFieldStyle.font = menuFont;
		textFieldStyle.fontColor = Color.WHITE;
		textFieldStyle.messageFontColor = Color.WHITE;
		textFieldStyle.cursor = whiteImage.getDrawable();
		textFieldStyle.selection = whiteImage.getDrawable();
		loginField = new TextField("", textFieldStyle);
		loginField.setMessageText("Enter Login");

		menuGroup.addActor(loginField);
		menuGroup.addActor(playButton);

		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				LOGGER.info("Clicked at (" + x + ", " + y + ")");
				surviveClient.loginScreen.setLoginName(loginField.getText());
				changeScreen(surviveClient.loginScreen);
				LOGGER.info("Login sent");
			}
		});

		menuGroup.setTransform(true);
		menuGroup.setAlignment(Align.left);
		menuGroup.pack();
		stage.addActor(menuGroup);
	}

	@Override
	protected void update(float delta) {
		stage.act(delta);
		menuGroup.setPosition(-menuGroup.getWidth() / 2, -menuGroup.getHeight() / 2);
		stage.getCamera().position.set(0.0f, 0.0f, 0.0f);
		stage.getCamera().update();
	}

	@Override
	protected void draw() {
		stage.draw();
	}

	@Override
	protected void receive(Object object) {
	}
}
