package survive.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Timer;
import survive.client.SurviveClient;

public class MainMenu extends SurviveScreen {
	private static final String fontName = "Main/data/fonts/CarnevaleeFreakshow.ttf";
	private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
	private BitmapFont menuFont = generator.generateFont(32);

	private TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
	private TextButton playButton;

	private TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
	private TextField loginField;

	public MainMenu(SurviveClient surviveClient) {
		super(surviveClient);

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
		textFieldStyle.cursor = whiteImage.getDrawable();
		textFieldStyle.selection = whiteImage.getDrawable();
		loginField = new TextField("Enter", textFieldStyle);
		loginField.setCursorPosition(2);
		//loginField.

//		ClickListener

		stage.addActor(playButton);
		stage.addActor(loginField);

		class MyTask extends Timer.Task {
			@Override
			public void run() {
				System.out.println("Run!");
			}
		}
		MyTask task = new MyTask();
		float repeatInitialTime = 1.0f;
		float repeatTime = 64.0f;
		Timer.schedule(task, repeatInitialTime, repeatTime);
	}

	@Override
	public void update(float delta) {
		stage.act(delta);
		loginField.setPosition(-loginField.getWidth() / 2, 0.0f);
		stage.getCamera().position.set(0.0f, 0.0f, 0.0f);
		stage.getCamera().update();
	}

	@Override
	public void draw() {
		stage.draw();
	}


}
