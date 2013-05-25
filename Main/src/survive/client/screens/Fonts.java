package survive.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Fonts {
	private static final String fontName = "Main/data/fonts/CarnevaleeFreakshow.ttf";
	private FreeTypeFontGenerator generator;
	public BitmapFont menuFont;
	public BitmapFont gameFont;

	public Fonts() {
		generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
		menuFont = generator.generateFont(32);
		gameFont = generator.generateFont(28);
	}
}
