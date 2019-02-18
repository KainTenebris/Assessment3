package com.rear_admirals.york_pirates;

import com.badlogic.gdx.graphics.Texture;
/**Sets the variables all shipTypes require such as an attack, defence, accuracy, health, texture and a name*/
public class ShipType {
	private int attack;
	private int defence;
	private int accuracy;
	private int health;
	private String name;
	private Texture texture;

	//Constructor
	/**Initialises the shipType
	 * @param defence The ship's defence
	 * @param health The ship's health
	 * @param accuracy The ship's accuracy
	 * @param name The name of the ship
	 * @param attack The attack of the ship
	 */
	public ShipType (String name, int attack, int defence, int accuracy, int health) {
		this.name = name;
		this.attack = attack;
		this.defence = defence;
		this.accuracy = accuracy;
		this.health = health;
	}

	//Getters
	/**Returns shipType name
	 * @return name
	 */
	public String getName() {
		return name; }
	/**Returns shipType attack
	 * @return attack
	 */
	public int getAttack() {
		return attack; }
	/**Returns shipType defence
	 * @return defence
	 */
	public int getDefence() {
		return defence; }
	/**Returns shipType accuracy
	 * @return accuracy
	 */
	public int getAccuracy() {
		return accuracy; }
	/**Return shipType health
	 * @return health
	 */
	public int getHealth() {
		return health; }

	// Static Ship Types go here
//	public static ShipType Sloop = new ShipType("Sloop", 4, 4, 7, 80);
	public static ShipType Brig = new ShipType("Brig", 5, 5, 80, 100);
	public static ShipType Player = new ShipType("Player", 5, 5, 95, 200);
	public static ShipType Galleon = new ShipType("Galleon", 10, 50, 80, 200);
	public static ShipType Warship = new ShipType("Warship", 20, 300, 90, 200);
	public static ShipType Debug = new ShipType("Debug", 10000, 10000, 100, 10000);
}
