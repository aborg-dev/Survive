package survive.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import survive.client.SurviveClient;

import java.util.logging.Logger;

public class MainMenu extends SurviveScreen {
	private final static Logger LOGGER = Logger.getLogger(MainMenu.class.getName());

	private static final String fontName = "Main/data/fonts/CarnevaleeFreakshow.ttf";
	private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
	private BitmapFont menuFont = generator.generateFont(32);

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
		playButton.setPosition(-menuFont.getBounds(playButton.getText()).width / 2, -menuFont.getBounds(playButton.getText()).height);

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
				LOGGER.info("Clecked");
				surviveClient.login(loginField.getText());
				LOGGER.info("Login sent");
			}
		});

		stage.addActor(menuGroup);
	}

	@Override
	public void update(float delta) {
		stage.act(delta);

		/*if (loginField.getMessageText().equals("")) {
			loginField.setPosition(-loginField.getStyle().font.getBounds(loginField.getText()).width / 2, 0.0f);
		} else {
			loginField.setPosition(-loginField.getStyle().font.getBounds(loginField.getMessageText()).width / 2, 0.0f);
		}*/

		stage.getCamera().position.set(0.0f, 0.0f, 0.0f);
		stage.getCamera().update();
	}

	@Override
	public void draw() {
		stage.draw();
	}
}
