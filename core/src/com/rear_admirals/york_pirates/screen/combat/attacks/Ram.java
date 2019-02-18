package com.rear_admirals.york_pirates.screen.combat.attacks;

import com.rear_admirals.york_pirates.Ship;

public class Ram extends Attack {

	//Constructor
	/**Constructs ram attack type
	 * @param accMultiplier The amount it multiplies the attacker's accuracy
	 * @param name The name of the attack* @param accMultiplier The amount it multiplies the player's accuracy by
	 * @param accPercent The move's accuracy percentage
	 * @param desc A description of the atack
	 * @param dmgMultiplier The amount it multiplies the player's damage by
	 * @param skipMove If it skips a move or not
	 */
	protected Ram(String name, String desc, int dmgMultiplier, double accMultiplier, boolean skipMove, int accPercent) {
		super(name, desc, dmgMultiplier, accMultiplier, skipMove, accPercent);
	}

	// Ram requires a custom doAttack function and as such has its own class.
	/**Performs the ram attack as a custom attack
	 * @param attacker Attacking ship object and its statistics
	 * @param defender Defending ship object and its statistics*/
	@Override
	public int doAttack(Ship attacker, Ship defender) {
		if ( doesHit(attacker.getAccuracy(), this.accPercent) ) {
			this.damage = attacker.getAttack()*this.dmgMultiplier;
			defender.damage(this.damage);
			attacker.damage(this.damage/2);
			return this.damage;
		}
		return 0;
	}

	public static Attack attackRam = new Ram("Ram","Ram your ship into your enemy, causing damage to both of you.", 4, 1, false, 85);
}
