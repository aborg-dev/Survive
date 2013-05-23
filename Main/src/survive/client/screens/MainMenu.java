package survive.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import survive.client.SurviveClient;

public class MainMenu extends SurviveScreen {
	private static final String fontName = "Main/data/fonts/CarnevaleeFreakshow.ttf";
	private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
	private TextButton button;

	public MainMenu(SurviveClient surviveClient) {
		super(surviveClient);

		TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
		style.font = generator.generateFont(32);
		style.fontColor = Color.WHITE;
		style.overFontColor = Color.RED;
		style.downFontColor = Color.YELLOW;

		button = new TextButton("Hello!", style);
		button.setTransform(true);

		stage.addActor(button);
	}

	@Override
	public void update(float delta) {
		stage.act(delta);
		BitmapFont.TextBounds bounds = button.getStyle().font.getBounds(button.getText());
		stage.getCamera().position.set(button.getX() + bounds.width / 2, button.getY() + bounds.height / 2, 0f);
		stage.getCamera().update();
	}

	@Override
	public void draw() {
		stage.draw();
	}
}
