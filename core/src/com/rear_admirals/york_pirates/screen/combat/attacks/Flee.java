package com.rear_admirals.york_pirates.screen.combat.attacks;

import com.rear_admirals.york_pirates.Ship;

import java.util.Random;

public class Flee extends Attack {

    //Constructor
    protected Flee() {
        this.name = "FLEE";
        this.desc = "Attempt to escape enemy.";
    }

    // Flee requires a custom doAttack function and as such has its own class.
    @Override
    public int doAttack(Ship attacker, Ship defender) {
        Random ran = new Random();
        int fleeSuccess = ran.nextInt(101);
        if (fleeSuccess >= 30) {
            return 1;
        } else {
            return 0;
        }
    }

    public static final Flee attackFlee = new Flee();
}
