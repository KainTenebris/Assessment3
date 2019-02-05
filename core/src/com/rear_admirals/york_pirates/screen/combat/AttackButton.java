package com.rear_admirals.york_pirates.screen.combat;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.rear_admirals.york_pirates.screen.combat.attacks.Attack;

public class AttackButton extends TextButton {
    private String name;
    private String desc;
    private Attack attack;

    //Constructor
    public AttackButton(Attack attack, Skin skin){
        super(attack.getName(), skin);
        this.attack = attack;
        this.name = attack.getName();
        this.desc = attack.getDesc();
    }

    //Constructor
    public AttackButton(Attack attack, Skin skin, String type){
        super(attack.getName(), skin, type);
        this.attack = attack;
        this.name = attack.getName();
        this.desc = attack.getDesc();
    }

    //Getters
    public String getName() { return this.name; }
    public String getDesc() { return this.desc; }
    public Attack getAttack() { return this.attack; }
}
