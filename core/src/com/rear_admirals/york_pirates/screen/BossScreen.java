package com.rear_admirals.york_pirates.screen;

import com.rear_admirals.york_pirates.College;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Ship;
import com.rear_admirals.york_pirates.screen.combat.CombatScreen;

import static com.rear_admirals.york_pirates.ShipType.Warship;

//just in case want to change how a boss would appear
//music/backgrounds etc
public class BossScreen extends CombatScreen {

    //Constructor
    public BossScreen(PirateGame game) {
        super(game, new Ship(Warship, "YSJ Admiral", new College("YSJ")));
    }
}
