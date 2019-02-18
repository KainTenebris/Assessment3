package com.rear_admirals.york_pirates.screen.combat.attacks;

import com.rear_admirals.york_pirates.Ship;

public class GrapeShot extends Attack {

    //Constructor
    /**Constructs Grape Shot attack type
     *@param accMultiplier The amount it multiplies the attacker's accuracy
     * @param name The name of the attack* @param accMultiplier The amount it multiplies the player's accuracy by
     * @param accPercent The move's accuracy percentage
     * @param desc A description of the atack
     * @param dmgMultiplier The amount it multiplies the player's damage by
     * @param skipMove If it skips a move or not   */
    public GrapeShot(String name, String desc, int dmgMultiplier, double accMultiplier, boolean skipMove, int accPercent) {
        super(name, desc, dmgMultiplier, accMultiplier, skipMove, accPercent);
    }

    // Grapeshot requires a custom doAttack function and as such has its own class.
    /**Performs the grape shot attack as a custom attack
     * @param attacker Attacking ship object and its statistics
     * @param defender Defending ship object and its statistics
     */
    @Override
    public int doAttack(Ship attacker, Ship defender) {
        this.damage = 0;
        for (int i = 0; i < 4; i++) { // Fires 4 shots.
            if (doesHit(attacker.getAccuracy(), this.accPercent)) {
                this.damage += attacker.getAttack() * this.dmgMultiplier; // Landed shots do half as much damage as a swivel shot.
            }
        }
        defender.damage(this.damage);
        return this.damage;
    }

    public static Attack attackGrape = new GrapeShot("Grape Shot","Fire a cluster of smaller cannonballs at the enemy.",1,1,false, 75);
}

