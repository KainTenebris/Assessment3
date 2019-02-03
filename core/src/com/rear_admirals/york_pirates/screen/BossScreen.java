package com.rear_admirals.york_pirates.screen;

import com.rear_admirals.york_pirates.College;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Ship;
import com.rear_admirals.york_pirates.screen.combat.CombatScreen;

import static com.rear_admirals.york_pirates.ShipType.Warship;

public class BossScreen extends CombatScreen {

    public BossScreen(PirateGame game) {
        super(game, new Ship(Warship, "YSJ Admiral", new College("YSJ")));
    }

    public void update(float delta) {

    }

    public void render(float delta) {
        super.render(delta);

    }

}
