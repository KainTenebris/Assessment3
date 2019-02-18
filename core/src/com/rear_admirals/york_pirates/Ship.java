package com.rear_admirals.york_pirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rear_admirals.york_pirates.base.PhysicsActor;


public class Ship extends PhysicsActor {
	private String name;
	private int attack;
	private int defence;
	private int accuracy;
	private int health;
	private ShipType type;
	private int healthMax;
	private Texture sailingTexture;
	private College college;
	private boolean isBoss = false;

	//For testing purposes only. Use of this constructor in-game WILL cause errors.
// 	@Deprecated
// 	public Ship(){
// 		this.name = "DEBUG SHIP";
// 		this.attack = 5;
// 		this.defence = 5;
// 		this.accuracy = 5;
// 		this.healthMax = defence*20;
// 		this.health = healthMax;
// 		this.college = Derwent;
// 	}
	
	//Constructor
	/**Initialises the ship and all its characteristics such as attack, defence, accuracy, college, type and health
	 * @param college This is the college the ship belongs to
	 * @param type This is the type of ship that is created
	 */
	public Ship(ShipType type, College college) {
		this.name = college.getName() + " " + type.getName();
		this.attack = type.getAttack();
		this.defence = type.getDefence();
		this.accuracy = type.getAccuracy();
		this.healthMax = type.getHealth() + this.defence;
		this.health = healthMax;
		this.college = college;
		this.type = type;
		this.sailingTexture = new Texture(Gdx.files.internal("ship (1).png"));
		setupShip();
	}

	//Constructor
	/**Initialises the ship and all its characteristics such as attack, defence, accuracy, college, type and health.
	 * @param type This is the type of ship created
	 * @param college This is the college the ship belongs to
	 * @param texturePath This is the sailing texture for the graphics
	 */
	public Ship(ShipType type, College college, String texturePath) {
		this.name = college.getName() + " " + type.getName();
		this.attack = type.getAttack();
		this.defence = type.getDefence();
		this.accuracy = type.getAccuracy();
		this.healthMax = type.getHealth() + this.defence;
		this.health = healthMax;
		this.college = college;
		this.type = type;
		this.sailingTexture = new Texture(Gdx.files.internal(texturePath));
		setupShip();
	}

	//Constructor
	/**Initialises a ship with a name, type and college, taking these three elements as parameters
	 * @param college This is the college the ship belongs to
	 * @param name This is the name of the ship
	 * @param type The type of ship*/
	public Ship(ShipType type, String name, College college) {
		this(type, college);
		this.name = name;
	}

	//Constructor
	/**Initialises the ship and all its characteristics such as attack, defence, accuracy, college, type and health.
	 * @param type This is the type of ship
	 * @param name This is the name of the ship
	 * @param college This is the college the  ship belongs to
	 * @param accuracy The ship's accuracy
	 * @param attack The ship's attack stat
	 * @param defence The ship's defence stat
	 * @param isBoss If the boss is dead or not
	 * This takes attack, defence, accuracy, type, college, name and is boss as parameters*/
	public Ship(int attack, int defence, int accuracy, ShipType type, College college, String name, boolean isBoss) {
		this.attack=attack;
		this.defence = defence;
		this.accuracy = accuracy;
		this.type = type;
		this.name = name;
		this.healthMax = type.getHealth() + defence;
		this.college = college;
		this.health = healthMax;
		this.sailingTexture = new Texture(Gdx.files.internal("ship (1).png"));
		this.isBoss = isBoss;
		setupShip();
	}

	public Ship(int attack, int defence, int accuracy, ShipType type, College college, String name, int health, int healthMax) {
		this.attack = attack;
		this.defence = defence;
		this.accuracy = accuracy;
		this.type = type;
		this.name = name;
		this.healthMax = healthMax;
		this.college = college;
		this.health = health;
		this.sailingTexture = new Texture(Gdx.files.internal("ship (1).png"));
		setupShip();
	}

	//sets up the ship for sailing
	/**Sets up the ship for sailing by setting the width and height of the boat as well as where
	 * it starts off and the speed it can go at */
	public void setupShip(){
		this.setWidth(this.sailingTexture.getWidth());
		this.setHeight(this.sailingTexture.getHeight());
		this.setOriginCentre();
		this.setMaxSpeed(200);
		this.setDeceleration(20);
		this.setEllipseBoundary();
	}
	
	//Getters
	/**Returns the ship's college*
	 * @return college
	 */
	public College getCollege() {
		return college; }
	/**Returns the maximum health
	 * @retun healthmax
	 */
	public int getHealthMax() {
		return healthMax; }
	/**Returns the ship name
	 * @return name
	 */
	public String getName() {
		return name; }
	/**Returns the ship's attack
	 * @return attack
	 */
	public int getAttack() {
		return attack; }
	/**Returns the ship's defence
	 * @return defence
	 */
	public int getDefence() {
		return defence; }
	/**Returns the ship's accuracy
	 * @return accuracy
	 */
	public int getAccuracy() {
		return accuracy; }
	/**Returns the ship's health
	 * @return health
	 */
	public int getHealth() {
		return health; }
	/**Returns the type of the ship
	 * @return type
	 */
	public String getType() {
		return type.getName(); }
	/**Returns the sailing texture
	 * @return sailingTexture
	 */
	public Texture getSailingTexture() {
		return this.sailingTexture; }
	/**Initiated and returns the isboss boolean
	 * @return isBoss
	 */
	public boolean getIsBoss() {
		return this.isBoss; }
	
	//Setters
<<<<<<< Updated upstream
	public void setCollege(College college) { this.college = college; }
	public void setAttack(int attack) { this.attack = attack; }
	public void setName(String name) { this.name = name; }
	public void setAccuracy(int accuracy) { this.accuracy = accuracy; }
	public void setHealth(int health) { this.health = health; }
	public void setHealthMax(int health) { this.healthMax = health; }
	public void setType(ShipType type) { this.type = type; }
	public void damage(int amt) { health = health - amt; }
=======
	/**Sets the college of the ship
	 * @param college This is the ship's college
	 */
	public void setCollege(College college) {
		this.college = college; }
	/**Sets the attack of the ship
	 * @param attack  An integer variable for the attack
	 */
	public void setAttack(int attack) {
		this.attack = attack; }
	/**Sets the name of the ship.
	 * @param name The name of the ship
	 */
	public void setName(String name) {
		this.name = name; }
	/**Sets the accuracy of the ship
	 * @param accuracy An integer variable as accuracy
	 */
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy; }
	/**Sets the health of the ship
	 * @param health An integer variable as health
	 */
	public void setHealth(int health) {
		this.health = health; }
	/**Sets the type of the ship
	 * @param type The type of the ship
	 */
	public void setType(ShipType type) {
		this.type = type; }
	/**Reduces the health of the ship depending on the amount of damage the ship received
	 * @param amt An integer variable for the amount of damage received
	 */
	public void damage(int amt) {
		health = health - amt; }
	/**Sets the defence of the ship as well as the max health
	 * @param defence An integer variable for the ship's defence
	 */
>>>>>>> Stashed changes
	public void setDefence(int defence) {
        	this.defence = defence;
        	this.healthMax = type.getHealth() + defence;
    	}

	//controls movement of ship
	/**Enables the player to move the ship by changing the direction the ship is facing using the left and right input keys
	 * @param dt The time the key is pressed for
	 */
	public void playerMove(float dt) {		this.setAccelerationXY(0,0);
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			this.rotateBy(90 * dt);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			this.rotateBy(-90 * dt );
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)){
			this.setAnchor(false);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			this.setAnchor(true);
		}
	}

	//draws batch
/**Draws the batch
 * @param alpha
 * @param batch
 */
	@Override
	public void draw(Batch batch, float alpha){
		batch.setColor(1,1,1,alpha);
		batch.draw(new TextureRegion(sailingTexture),getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,1,getRotation());
	}
}
