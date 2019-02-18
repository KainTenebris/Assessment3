package com.rear_admirals.york_pirates;

import com.rear_admirals.york_pirates.screen.combat.attacks.*;
import com.rear_admirals.york_pirates.screen.combat.attacks.GrapeShot;

import java.util.ArrayList;
import java.util.List;

import static com.rear_admirals.york_pirates.ShipType.*;

public class Player {
	private Ship playerShip;
	private int gold;
	private int points;
	public static List<Attack> attacks = new ArrayList<Attack>();

	//Constructor without a given ship
	/**Initialises the player without a given ship belonging derwent college and with 50 gold*/
	public Player() {
		this.playerShip = new Ship(Player, "Your Ship", PirateGame.colleges.get("Derwent"));
		this.gold = 50;
		this.points = 0;

		attacks.add(Ram.attackRam);
		attacks.add(GrapeShot.attackSwivel);
		attacks.add(Attack.attackBoard);
	}

	//Constructor with a given ship
	/**Initialises the player with 50 gold to start with. Takes a ship as parameters*/
	public Player(Ship ship) {
		this.playerShip = ship;
		this.gold = 50;
		this.points = 0;

		attacks.add(Ram.attackRam);
		attacks.add(Attack.attackSwivel);
		attacks.add(Attack.attackBoard);
    	}

	//Getters
	public Ship getPlayerShip() { return this.playerShip; }
	public int getPoints() { return points; }
	public int getGold() { return gold; }

	//Setters
	/**Initialises the player's ship. Takes 'ship' of type ship as
	 * @param ship This is an object of type ship
	 */
	public void setPlayerShip(Ship ship) {
		this.playerShip = ship; }
	/**Initialises the player's points. Takes the integer variable 'points' as parameters
	 * @param points The number of points the player has
	 */
	public void setPoints(int points) {
		this.points = points; }
	/**Initialises the players gold and sets it to an integer. Takes integer variable 'gold' as parameters
	 * @param gold The amount of gold the player possesses
	 */
	public void setGold(int gold) {
		this.gold = gold; }
	/**Increases the player's points by an integer amount given in the parameters
	 * @param amount The of points the player gains
	 */
	public void addPoints(int amount) {
		points += amount; }
	/**Increases the player's gold by an integer amount given in the parameters
	 * @param amount This is the amount of gold the player gains
	 */
	public void addGold(int amount) {
		gold += amount; }
	
	//allows player to pay gold
	/**Allows the player to pay gold if they posses enough for the item they are attempting to buy and removes the price from their gold amount
	 * @param amount This is the amount of gold the item costs
	 */
	public boolean payGold(int amount){
		if (amount > gold){
			return false;
		}
		else{
			addGold(-amount);
			return true;
        }
    }
}
