package com.rear_admirals.york_pirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
	
	//no constructor. Instead use the default constructor from the super, Game()
	
	//Getters
	/**Returns the skin which is the font for the the in-game labels
	 * @reutrn skin This is the font of the game
	 */
	public Skin getSkin() { return this.skin; }
	/**Initialises and returns the player
	 * @return player Returns the player
	 */
	public Player getPlayer() { return this.player; }
	/**Initialises and returns the sailing scene
	 * @return sailingScene*/
	public Screen getSailingScene() { return this.sailingScene; }
	
	//Setters
	/**Initialises the skin (label font)
	 *@param skin This is the label font
	 */
	public void setSkin(Skin skin) { this.skin = skin; }
	/** Initialises and sets the sailing scene
	 * @param screen This is the sailing screen image
	 */
	public void setSailingScene(Screen screen) { this.sailingScene = screen; }

	/**
	* Calls the reset and then sets the screen to the Main menu of the pirate game
	* Called when starting the game
	*/
	public void create(){
		reset();
		setScreen(new MainMenu(this));
	}

	//Basically a constructor. Called when starting the game and when returning to the main menu.
	/**Resets the game to initial conditions.It initialises the spriteBatch that draws the sprites on the screen,
	 the font for the labels and the standard libgdx font. This also includes creating the map initialising 5 colleges and
	 3 departments and putting them on the map
	 */
	public void reset() {
		this.skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));
		this.font = new BitmapFont();
		this.batch = new SpriteBatch();
		
		//creates Maps to store Departments and Colleges
		departments = new HashMap<String, Department>();
		colleges = new HashMap<String, College>();

		//puts Departments in map
		departments.put("Chemistry", new Department("Chemistry", Department.Stat.Attack, this));
		departments.put("Physics", new Department("Physics", Department.Stat.Defence, this));
		departments.put("Computer Science", new Department("Computer Science", Department.Stat.Minigame, this));

		//puts Colleges in map
		colleges.put("Derwent", new College("Derwent"));
		colleges.put("Vanbrugh", new College("Vanbrugh"));
		colleges.put("James", new College("James"));
		colleges.put("Constantine", new College("Constantine"));
		colleges.put("Goodricke", new College("Goodricke"));

		this.player = new Player();
		this.sailingScene = new SailingScreen(this);
	}

	//clears screen
	/**This clears the screen by disposing of the sprites and labels*/
	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	//draws screen
	/**This draws the screen*/
	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	/*
		Things to save/load:
		player gold
		player points
		ship name;
		ship attack;
		ship defence;
		ship accuracy;
		ship health;
		ship type (new type with other values);
		ship healthMax;
		ships college name (just use colleges.get() on load);
		ship position
		allied colleges
	*/
	public void save() {
		Preferences prefs = Gdx.app.getPreferences("pirategamesave");

		prefs.putInteger("gold", player.getGold());
		prefs.putInteger("points", player.getPoints());
		Ship playerShip = player.getPlayerShip();
		prefs.putString("ship name", playerShip.getName());
		prefs.putInteger("ship attack", playerShip.getAttack());
		prefs.putInteger("ship defence", playerShip.getDefence());
		prefs.putInteger("ship accuracy", playerShip.getAccuracy());
		prefs.putInteger("ship health", playerShip.getHealth());
		prefs.putInteger("ship healthMax", playerShip.getHealthMax());
		prefs.putFloat("shipX", playerShip.getX());
		prefs.putFloat("shipY", playerShip.getY());
		String allies = playerShip.getCollege().getName();
		for(College college : playerShip.getCollege().getAlly()) {
			allies += ";" + college.getName();// college1;college2;college3
		}
		prefs.putString("allies", allies);
		prefs.flush();
	}

	public void load() {
		Preferences prefs = Gdx.app.getPreferences("pirategamesave");
		Player defaultPlayer = new Player();
		Ship defaultShip = defaultPlayer.getPlayerShip();
		Ship playerShip = player.getPlayerShip();

		player.setGold(prefs.getInteger("gold", defaultPlayer.getGold()));
		player.setPoints(prefs.getInteger("points", defaultPlayer.getPoints()));

		playerShip.setAttack(prefs.getInteger("ship attack", defaultShip.getAttack()));
		playerShip.setDefence(prefs.getInteger("ship defence", defaultShip.getDefence()));
		playerShip.setHealthMax(prefs.getInteger("ship healthMax", defaultShip.getHealthMax()));
		playerShip.setAccuracy(prefs.getInteger("ship accuracy", defaultShip.getAccuracy()));
		playerShip.setCollege(colleges.get(prefs.getString("college", defaultShip.getCollege().getName())));
		playerShip.setName(prefs.getString("ship name", defaultShip.getName()));
		playerShip.setHealth(prefs.getInteger("ship health", defaultShip.getHealth()));
		playerShip.setPosition(500, 500);
		playerShip.setPosition(prefs.getFloat("shipX", playerShip.getX()), prefs.getFloat("shipY", playerShip.getY()));
		String sAllies = prefs.getString("allies", "MISSING");
		if(!sAllies.equals("MISSING")) {
			ArrayList<College> allies = playerShip.getCollege().getAlly();
			for(String collegeName : sAllies.split(";")) {
				College college = colleges.get(collegeName);
				if(!allies.contains(college)) {
					playerShip.getCollege().addAlly(college);
				}
			}
		}
	}
}
