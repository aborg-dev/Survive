package survive.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import survive.client.screens.MainMenu;

public class SurviveClient extends Game {
	public final int WIDTH;
	public final int HEIGHT;

	public SurviveClient(int WIDTH, int HEIGHT) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
	}

	public void changeScreen(Screen screen) {
		Screen currentScreen = getScreen();
		if (currentScreen != null) {
			currentScreen.hide();
		}
		setScreen(screen);
		screen.show();
	}

	@Override
	public void create() {
		changeScreen(new MainMenu(this));
	}
}
