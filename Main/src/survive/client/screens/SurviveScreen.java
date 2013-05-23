package survive.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import survive.client.SurviveClient;

public abstract class SurviveScreen implements Screen {
	protected final SurviveClient surviveClient;
	protected final Stage stage;
	protected int width;
	protected int height;

	abstract public void update(float delta);

	abstract public void draw();

	public SurviveScreen(SurviveClient surviveClient) {
		this.surviveClient = surviveClient;
		stage = new Stage(surviveClient.WIDTH, surviveClient.HEIGHT, true);
	}

	@Override
	public final void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		update(delta);
		draw();
	}

	@Override
	public final void resize(int width, int height) {
		this.width = width;
		this.height = height;
		stage.setViewport(width, height, false);
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
