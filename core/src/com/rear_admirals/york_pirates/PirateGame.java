package com.rear_admirals.york_pirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rear_admirals.york_pirates.screen.MainMenu;
import com.rear_admirals.york_pirates.screen.SailingScreen;

import java.util.ArrayList;
import java.util.HashMap;

public class PirateGame extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    private Skin skin;
    private Player player;
	private Screen sailingScene;
	public Screen beforeMinigameScreen;
	public static HashMap<String, Department> departments;
	public static HashMap<String, College> colleges;

	public void create(){
		reset();
        setScreen(new MainMenu(this));
	}

	public void reset() {
		Gdx.graphics.setTitle("York Pirates!");
		this.skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font.
		font = new BitmapFont();

		departments = new HashMap<String, Department>();
		colleges = new HashMap<String, College>();

		departments.put("Chemistry", new Department("Chemistry", "Attack", this));
		departments.put("Physics", new Department("Physics", "minigame", this));
//        departments.put("Computer Science", new Department("Computer Science", "minigame", this));

		colleges.put("Derwent", new College("Derwent"));
		colleges.put("Vanbrugh", new College("Vanbrugh"));
		colleges.put("James", new College("James"));
//        colleges.put("College1", new College("College1"));
//        colleges.put("College2", new College("College2"));

		player = new Player();
		this.sailingScene = new SailingScreen(this);
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	public Skin getSkin() { return this.skin; }

	public void setSkin(Skin skin) { this.skin = skin; }

	public Player getPlayer() { return this.player; }

	public Screen getSailingScene() { return this.sailingScene; }

	public void setSailingScene(Screen screen) { this.sailingScene = screen; }
}
