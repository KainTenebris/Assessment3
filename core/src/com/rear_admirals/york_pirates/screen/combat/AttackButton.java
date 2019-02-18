package com.rear_admirals.york_pirates.screen.combat;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.rear_admirals.york_pirates.screen.combat.attacks.Attack;

public class AttackButton extends TextButton {
    private String name;
    private String desc;
    private Attack attack;

    //Constructor
    /**Initialises the attack buttons
     * @param attack The attack type
     * @param skin The font of the button
     */
    public AttackButton(Attack attack, Skin skin){
        super(attack.getName(), skin);
        this.attack = attack;
        this.name = attack.getName();
        this.desc = attack.getDesc();
    }

    //Constructor
    /**Initialises the attack buttons for combat
     * @param skin The font of the button
     * @param attack The attack class
     * @param type The type of attack it is
     */
    public AttackButton(Attack attack, Skin skin, String type){
        super(attack.getName(), skin, type);
        this.attack = attack;
        this.name = attack.getName();
        this.desc = attack.getDesc();
    }

    //Getters
    /**Returns the name if the attack
     * @return name
     */
    public String getName() {
        return this.name; }
    /**Returns the description of the attack
     * @return desc This is a string description of the attack
     */
    public String getDesc() {
        return this.desc; }
    /**Returns the attack type of the object
     * @return attack
     */
    public Attack getAttack() {
        return this.attack; }
}
