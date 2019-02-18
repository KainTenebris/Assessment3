package com.rear_admirals.york_pirates.screen.combat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.rear_admirals.york_pirates.base.BaseActor;

public class CombatShip extends BaseActor {
    private float ship_size;
    private Texture texture;

    //Constructor
    /**Initialises the Combat ship with a texture, size and bounds
     * @param ship_size The size of the ship
     * @param shipFile A string for the texture of the ship
     */
    public CombatShip(String shipFile, float ship_size){
        this.texture = new Texture(shipFile);
        this.ship_size = ship_size;
        this.setBounds(getX(), getY(), ship_size,ship_size);
    }

    //draws the ship
    /**Draws the ship
     * @param batch
     * @param alpha
     */
    @Override
    public void draw(Batch batch, float alpha){
        batch.setColor(1,1,1, alpha);
        batch.draw(texture, getX(), getY(), ship_size,ship_size);
    }
}
