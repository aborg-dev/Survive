package survive.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import survive.client.SurviveClient;

public abstract class SurviveScreen implements Screen {
	protected final SurviveClient surviveClient;
	protected final Stage stage;
	protected int width;
	protected int height;

	protected static final String fontName = "Main/data/fonts/CarnevaleeFreakshow.ttf";
	protected FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
	protected BitmapFont menuFont = generator.generateFont(32);
	protected BitmapFont gameFont = generator.generateFont(28);

	abstract protected void update(float delta);

	abstract protected void draw();

	abstract protected void receive(Object object);

	public SurviveScreen(SurviveClient surviveClient) {
		this.surviveClient = surviveClient;
		stage = new Stage(surviveClient.WIDTH, surviveClient.HEIGHT, true);
	}

	protected final void changeScreen(Screen screen) {
		Screen currentScreen = surviveClient.getScreen();
		if (currentScreen != null) {
			currentScreen.hide();
		}
		surviveClient.setScreen(screen);
		screen.show();
	}

	@Override
	public final void render(float delta) {
		Object message;
		while ((message = surviveClient.pollMessage()) != null) {
			receive(message);
		}
		update(delta);
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		draw();
	}

	@Override
	public final void resize(int width, int height) {
		this.width = width;
		this.height = height;
		stage.setViewport(width, height, false);
		surviveClient.WIDTH = width;
		surviveClient.HEIGHT = height;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
