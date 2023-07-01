package com.massacre.box2d.tutorial;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.massacre.box2d.tutorial.loader.B2dAssetManager;
import com.massacre.box2d.tutorial.views.*;

public class Box2DTutorial extends Game {



	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;

	private AppPreferences appPreferences;
	public B2dAssetManager assMan = new B2dAssetManager();

	public static final  int MENU = 0;
	public static final int PREFERENCES = 1;
	public static final int APPLICATION = 2;
	public static final int ENDGAME = 3;

	private Music playingSong;

	@Override
	public void create() {
		loadingScreen = new LoadingScreen(this);
		appPreferences = new AppPreferences();
		setScreen(loadingScreen);
		// tells our asset manger that we want to load the images set in loadImages method
		assMan.queueAddMusic();
		// tells the asset manager to load the images and wait until finished loading.
		assMan.manager.finishLoading();
		// loads the 2 sounds we use
		playingSong = assMan.manager.get("music/Rolemusic-pl4y1ng.mp3");

		playingSong.play();
	}
	public AppPreferences getPreferences(){
		return appPreferences;
	}

	public void changeScreen(int screen) {
		switch (screen) {
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if(mainScreen == null) mainScreen = new MainScreen(this);
				setScreen(mainScreen);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				setScreen(endScreen);
				break;
		}
	}

	@Override
	public void dispose() {
		playingSong.dispose();
		assMan.manager.dispose();
	}
}
