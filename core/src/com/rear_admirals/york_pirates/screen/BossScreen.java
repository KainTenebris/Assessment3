package com.rear_admirals.york_pirates.screen;

import com.rear_admirals.york_pirates.College;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Ship;
import com.rear_admirals.york_pirates.screen.combat.CombatScreen;

import static com.rear_admirals.york_pirates.ShipType.Warship;

//Just in case we want to change things independently of other screens
//music/backgrounds etc
public class BossScreen extends CombatScreen {

    //Constructor
    public BossScreen(PirateGame game) {
        super(game, new Ship(Warship, "YSJ Admiral", new College("YSJ")));
    }
}
