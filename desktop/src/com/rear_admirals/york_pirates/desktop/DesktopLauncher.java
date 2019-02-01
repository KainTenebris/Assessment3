package com.rear_admirals.york_pirates.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rear_admirals.york_pirates.PirateGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = true;
		config.title = "York Pirates!";
		new LwjglApplication(new PirateGame(), config);
	}
}
