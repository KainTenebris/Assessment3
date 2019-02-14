package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rear_admirals.york_pirates.College;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.base.BaseScreen;
import com.rear_admirals.york_pirates.screen.combat.CombatScreen;
import com.rear_admirals.york_pirates.Ship;

import java.util.Iterator;
import java.util.Random;

import static com.rear_admirals.york_pirates.PirateGame.*;
import static com.rear_admirals.york_pirates.ShipType.*;
import static com.rear_admirals.york_pirates.College.*;

public class MainMenu extends BaseScreen {
    private Stage stage;
    private float screen_width;
    private float screen_height;
    
    //Constructor
    public MainMenu(final PirateGame pirateGame){
        super(pirateGame);

        //Labels
        //create labels
        Label title = new Label("York Pirates!", pirateGame.getSkin(), "title");
        
        //set alignment of labels
        title.setAlignment(Align.center);

        //TextButtons
        //create TextButtons
        TextButton play = new TextButton("New Game", pirateGame.getSkin()); // Starts sailing mode.
        TextButton continueGame = new TextButton("Continue Game", pirateGame.getSkin());
        TextButton quickplay = new TextButton("Quick Play", pirateGame.getSkin());
        TextButton quit = new TextButton("Quit", pirateGame.getSkin());
        
        //adds listeners to buttons
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Preferences prefs = Gdx.app.getPreferences("pirategamesave");
                prefs.clear();
                prefs.flush();
                pirateGame.setScreen(pirateGame.getSailingScene());
                dispose();
            }
        });
        continueGame.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {
               pirateGame.load();
               pirateGame.setScreen(pirateGame.getSailingScene());
               dispose();
           }
        });
        quickplay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Ship newPlayerShip = new Ship(Warship, pirateGame.getPlayer().getPlayerShip().getCollege());
                pirateGame.getPlayer().setPlayerShip(newPlayerShip);
                Iterator iter = colleges.values().iterator();
                int size = colleges.size();
                for(int i = 0; i < size/2; i++) {
                    College college = (College) iter.next();
                    College playersCollege = pirateGame.getPlayer().getPlayerShip().getCollege();
                    if(college != playersCollege) {
                        college.setBossDead(true);
                        playersCollege.addAlly(college);
                    }
                }
                pirateGame.setSailingScene(new SailingScreen(pirateGame));
                pirateGame.setScreen(pirateGame.getSailingScene());
                dispose();
            }
        });
        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               Gdx.app.exit();
            }
        });

        //Tables
        //create tables
        Table table = new Table();
        
        //adds to tables
        table.add(title).padBottom(viewwidth/20).width(viewwidth/2);
        table.row();
        table.add(continueGame).uniform().padBottom(viewheight/40).size(viewwidth/2,viewheight/10);
        table.row(); // Ends the current row
        table.add(play).uniform().padBottom(viewheight/40).size(viewwidth/2,viewheight/10);
        table.row();
        table.add(quickplay).uniform().padBottom(viewheight/40).fill();
        table.row();
        table.add(quit).uniform().fill().padBottom(viewheight/40);
        
        //Stages
        Container<Table> tableContainer = new Container<Table>();
        tableContainer.setFillParent(true);
        tableContainer.setPosition(0,0);
        tableContainer.align(Align.center);

        this.stage = new Stage(new FitViewport(1920,1080));

        this.screen_width = stage.getWidth();
        this.screen_height = stage.getHeight();

        tableContainer.setActor(table);
        stage.addActor(tableContainer);

        Gdx.input.setInputProcessor(stage);
    }

    //creates the screen
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act();
    }

    //resizes screen
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
    }

    //disposes of screen
    @Override
    public void dispose() { stage.dispose(); }
}


