package com.rear_admirals.york_pirates.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rear_admirals.york_pirates.PirateGame;

public abstract class BaseScreen implements Screen {
    protected Stage mainStage;
    protected Stage uiStage;

    protected PirateGame pirateGame;

    protected final int viewwidth = 1920;
    protected final int viewheight = 1080;

    //Constructor
    /**Initiates the base screen
     * @param game The PirateGame type instance
     */
    public BaseScreen(PirateGame game){
        this.pirateGame = game;
        this.mainStage = new Stage(new FitViewport(this.viewwidth, this.viewheight));
        this.uiStage = new Stage(new FitViewport(this.viewwidth, this.viewheight));
    }

    /**Draws the stages
     * @param delta
     */
    public void render (float delta) {
        this.uiStage.act(delta);
        this.mainStage.act(delta);
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.mainStage.draw();
        this.uiStage.draw();
    }

    /**disposes of the stages*/
    @Override
    public void dispose () {
        this.mainStage.dispose();
        this.uiStage.dispose();
    }

    //resizes the screen
    /**Re-sizes the screen
     * @param height The new height
     * @param width The new width
     */
    public void resize(int width, int height) {
        this.uiStage.getViewport().update(width, height);
        this.mainStage.getViewport().update(width, height);
    }

    //The following exist only to satisfy the Screen interface
    public void show() { /**empty*/   }
    public void pause() { /**empty*/  }
    public void resume() { /**empty*/ }
    public void update(float delta) { /**empty*/  }
    public void hide() { /**empty*/   }
}

