package survive.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import survive.client.SurviveClient;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class SurviveScreen implements Screen {
	protected final SurviveClient surviveClient;
	protected final Stage stage;
	protected int width;
	protected int height;

	// Queue for received messages from SurviveClient to translate them to the screen
	private BlockingDeque<Object> messages = new LinkedBlockingDeque<Object>();

	abstract protected void update(float delta);

	abstract protected void draw();

	abstract protected void receive(Object object);

	public SurviveScreen(SurviveClient surviveClient) {
		this.surviveClient = surviveClient;
		stage = new Stage(surviveClient.WIDTH, surviveClient.HEIGHT, true);
	}

	protected final void changeScreen(Screen screen) {
		surviveClient.setScreen(screen);
	}

	public final void pushMessage(Object object) {
		messages.push(object);
	}

	protected final Object pollMessage() {
		return messages.poll();
	}

	@Override
	public final void render(float delta) {
		Object message;
		while ((message = pollMessage()) != null) {
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
