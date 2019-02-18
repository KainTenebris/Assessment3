package com.rear_admirals.york_pirates.screen.combat.attacks;

import com.rear_admirals.york_pirates.Ship;

import java.util.Random;


public class Attack {
	protected String name;
	protected String desc;
	protected int damage;
	protected int dmgMultiplier;
	protected double accMultiplier;
	protected boolean skipMoveStatus;
	protected boolean skipMove;
	protected int accPercent;

	// Generic constructor. Creates simple broadside attack.
	/**Initailises the broadside attack with a name, description, damage multiplier, accuracy multiplier as some of the characteristics*/
	protected Attack() {
		this.name = "Broadside";
		this.desc = "Fire a broadside at your enemy.";
		this.dmgMultiplier = 3;
		this.accMultiplier = 1;
		this.skipMove = false;
		this.skipMoveStatus = false;
	}

	// Custom constructor. Can be used to create any attack which applies a multiple of the attacker's damage
	// to the defender. Can also take a turn to charge and have custom accuracy.
	/**Initialises a generic attack. Can be used to create any attack which applies a multiple of the attacker's damage to the defender.
	 *Can also take a turn to charge and have custom accuracy.
	 * @param name The name of the attack
	 * @param accMultiplier The amount it multiplies the player's accuracy by
	 * @param accPercent The move's accuracy percentage
	 * @param desc A description of the atack
	 * @param dmgMultiplier The amount it multiplies the player's damage by
	 * @param skipMove If it skips a move or not
	 */

	protected Attack(String name, String desc, int dmgMultiplier, double accMultiplier, boolean skipMove, int accPercent) {
		this.name = name;
		this.desc = desc;
		this.dmgMultiplier = dmgMultiplier;
		this.accMultiplier = accMultiplier;
		this.skipMove = skipMove;
		this.skipMoveStatus = skipMove;
		this.accPercent = accPercent;
	}
	
	//Getters
	/**Returns name of attack
	 * @return name
	 */
	public String getName() {
		return this.name; }
	/**Returns attack description
	 * @return desc
	 */
	public String getDesc() {
		return this.desc; }
	/**Returns if the move will skip
	 * @return skipmove
	 */
	public boolean isSkipMove() {
		return this.skipMove; }
	/**Returns the status of the move skip
	 * @return skipMoveStatus
	 */
	public boolean isSkipMoveStatus() {
		return this.skipMoveStatus; }
	
	//Setters
	/**Sets the skipMoveStatus
	 * @param skipMoveStatus
	 */
	public void setSkipMoveStatus(boolean skipMoveStatus) {
		this.skipMoveStatus = skipMoveStatus; }

	// New function used to check if an attack hits the enemy.
	/**Function to check if the attack hits the enemy
	 * @param accPercent move's accuracy percentage
	 * @param shipAcc Ship's accuracy percentage
	 */
	protected boolean doesHit( int shipAcc, int accPercent) {
		Random ran = new Random();
		double random = ran.nextDouble();
		if ((double) accPercent/100.0 * (double) shipAcc/100.0 > random){
			return true;
		} else{
			return false;
		}
	}

	// Function called to actually perform the attack.
	/**Performs the attack and calculates and returns damage
	 * @param attacker The attacking ship's statistics
	 * @param defender The defending ship's statistics
	 */
	public int doAttack(Ship attacker, Ship defender) {
		if ( doesHit(attacker.getAccuracy(), this.accPercent) ) {
			this.damage = attacker.getAttack() * this.dmgMultiplier;
			defender.damage(this.damage);
			return this.damage;
		}
		return 0;
	}

	// attacks to be used in the game are defined here.
	public static Attack attackMain = new Attack("Broadside","Normal cannons. Fire a broadside at your enemy.",3,2,false,90);
	public static Attack attackSwivel = new Attack("Swivel","Lightweight cannons. High accuracy, low damage attack.",2,3,false,95);
	public static Attack attackBoard = new Attack("Board","Board enemy ship. Charges attack over a turn, medium - high damage and very high accuracy", 4,2,true,100);
}
