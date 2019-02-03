package com.rear_admirals.york_pirates.screen.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.screen.MainMenu;
import com.rear_admirals.york_pirates.screen.combat.attacks.*;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Player;
import com.rear_admirals.york_pirates.base.BaseScreen;
import com.rear_admirals.york_pirates.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import static com.rear_admirals.york_pirates.ShipType.Warship;

public class CombatScreen extends BaseScreen {

    // screen layout variables
    private float button_pad_bottom;
    private float button_pad_right;

    // Labels changed throughout the scene
    public Label descriptionLabel;
    private Label playerHPLabel;
    private Label enemyHPLabel;

    // Health bars of both ships
    private ProgressBar playerHP;
    private ProgressBar enemyHP;

    // Image textures and images for the various stages
    private Texture bg_texture;
    private Texture wood_texture;
    private Image background;
    private Image background_wood;

    public Player player;
    public Ship enemy;

    // Control the layout of the stage
    private Table completeAttackTable;
    private Table attackTable;
    private Table rootTable;
    private Table descriptionTable;
    private Container<Table> tableContainer;

    // Written text box
    private TextButton textBox;

    // Variables used in handling combat
    private Stack<Attack> combatStack;
    private static List<Attack> enemyAttacks;
    private Attack currentAttack;
    private BattleEvent queuedCombatEvent;

    // Variables used in text animation
    private float delayTime = 0;
    private boolean textAnimation = false;
    private int animationIndex = 0;
    private String displayText = "";

    public CombatScreen(final PirateGame pirateGame, Ship enemy){
        // Calls superclass BaseScreen
        super(pirateGame);

        // This constructor also replaces the create function that a stage would typically have.
        this.pirateGame = pirateGame;
        this.player = pirateGame.getPlayer();
        this.enemy = enemy;

        if(this.enemy.getType().equals("Warship")) {
            this.player.getPlayerShip().setHealth(this.player.getPlayerShip().getHealthMax());
        }

        // Load the skin for this screen
        pirateGame.setSkin(new Skin(Gdx.files.internal("flat-earth-ui.json")));

        combatStack = new Stack();

        // Sets size constants for the scene depending on viewport, also sets button padding constants for use in tables
        button_pad_bottom = viewheight/24f;
        button_pad_right = viewwidth/32f;

        // Instantiate the image textures for use within the scene as backgrounds.
        bg_texture = new Texture("water_texture_sky.png");
        background = new Image(bg_texture);
        background.setSize(viewwidth, viewheight);

        wood_texture = new Texture("wood_vertical_board_texture.png");
        background_wood = new Image(wood_texture);
        background_wood.setSize(viewwidth, viewheight);

        // Create a Container which takes up the whole screen (used for layout purposes)
        tableContainer = new Container<Table>();
        tableContainer.setFillParent(true);
        tableContainer.setPosition(0,0);
        tableContainer.align(Align.bottom);

        // Instantiate some different tables used throughout scene
        rootTable = new Table();
        descriptionTable = new Table();
        attackTable = new Table();

        // Instantiate both the ships for the battle
        CombatShip myShip = new CombatShip("ship1.png", viewwidth/3);
        CombatShip enemyShip = new CombatShip("ship2.png",viewwidth/3);

        Label shipName = new Label(player.getPlayerShip().getName(),pirateGame.getSkin(), "default_black");
        playerHP = new ProgressBar(0, player.getPlayerShip().getHealthMax(),0.1f,false,pirateGame.getSkin());
        playerHPLabel = new Label(player.getPlayerShip().getHealth()+"/" + player.getPlayerShip().getHealthMax(), pirateGame.getSkin());

        playerHP.getStyle().background.setMinHeight(playerHP.getPrefHeight()*2); //Setting vertical size of progress slider (Class implementation is slightly weird)
        playerHP.getStyle().knobBefore.setMinHeight(playerHP.getPrefHeight());

        Label enemyName = new Label(enemy.getName(), pirateGame.getSkin(),"default_black");
        enemyHP = new ProgressBar(0, enemy.getHealthMax(),0.1f,false,pirateGame.getSkin());
        enemyHPLabel = new Label(enemy.getHealth()+"/" + enemy.getHealthMax(), pirateGame.getSkin());

        playerHP.setValue(player.getPlayerShip().getHealthMax());
        enemyHP.setValue(enemy.getHealthMax());

        Table playerHPTable = new Table();
        Table enemyHPTable = new Table();

        playerHPTable.add(playerHPLabel).padRight(viewwidth/36f);
        playerHPTable.add(playerHP).width(viewwidth/5);

        enemyHPTable.add(enemyHPLabel).padRight(viewwidth/36f);
        enemyHPTable.add(enemyHP).width(viewwidth/5);

        Label screenTitle = new Label("Combat Mode", pirateGame.getSkin(),"title_black");
        screenTitle.setAlignment(Align.center);

        textBox = new TextButton("You encountered a "+enemy.getCollege().getName()+" "+enemy.getType()+"!", pirateGame.getSkin());
        textBox.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (textAnimation) {
                    textAnimation = false;
                    textBox.setText(displayText);
                } else {
                    textBox.setText("");
                    updateHP();
                    combatHandler(queuedCombatEvent);
                }
            }
        });

        // Control combat
        this.queuedCombatEvent = BattleEvent.NONE;
        currentAttack = null;

        // Instantiation of the combat buttons. Attack and Flee are default attacks, the rest can be modified within player class.
        final AttackButton button1 = new AttackButton(Attack.attackMain, pirateGame.getSkin());
        buttonListener(button1);
        final AttackButton button2 = new AttackButton(player.attacks.get(0), pirateGame.getSkin());
        buttonListener(button2);
        final AttackButton button3 = new AttackButton(player.attacks.get(1), pirateGame.getSkin());
        buttonListener(button3);
        final AttackButton button4 = new AttackButton(player.attacks.get(2), pirateGame.getSkin());
        buttonListener(button4);
        final AttackButton fleeButton = new AttackButton(Flee.attackFlee, pirateGame.getSkin(), "red");
        buttonListener(fleeButton);

        descriptionLabel = new Label("What would you like to do?", pirateGame.getSkin());
        descriptionLabel.setWrap(true);
        descriptionLabel.setAlignment(Align.center);

        descriptionTable.center();
        descriptionTable.add(descriptionLabel).uniform().pad(0,button_pad_right,0,button_pad_right).size(viewwidth/2 - button_pad_right*2, viewheight/12).top();
        descriptionTable.row();
        descriptionTable.add(fleeButton).uniform();

        attackTable.row();
        attackTable.add(button1).uniform().width(viewwidth/5).padRight(button_pad_right);
        attackTable.add(button2).uniform().width(viewwidth/5);
        attackTable.row().padTop(button_pad_bottom);
        attackTable.add(button3).uniform().width(viewwidth/5).padRight(button_pad_right);
        attackTable.add(button4).uniform().width(viewwidth/5);

        rootTable.row().width(viewwidth*0.8f);
        rootTable.add(screenTitle).colspan(2);
        rootTable.row();
        rootTable.add(shipName);
        rootTable.add(enemyName);
        rootTable.row().fillX();
        rootTable.add(myShip);
        rootTable.add(enemyShip);
        rootTable.row();
        rootTable.add(playerHPTable);
        rootTable.add(enemyHPTable);
        rootTable.row();
        rootTable.add(textBox).colspan(2).fillX().height(viewheight/9f).pad(viewheight/12,0,viewheight/12,0);
        tableContainer.setActor(rootTable);

        completeAttackTable = new Table();
        completeAttackTable.setFillParent(true);
        completeAttackTable.align(Align.bottom);
        completeAttackTable.row().expandX().padBottom(viewheight/18f);
        completeAttackTable.add(descriptionTable).width(viewwidth/2);
        completeAttackTable.add(attackTable).width(viewwidth/2);

        background_wood.setVisible(false);
        completeAttackTable.setVisible(false);
        mainStage.addActor(background_wood);
        mainStage.addActor(completeAttackTable);

        uiStage.addActor(background);
        uiStage.addActor(tableContainer);

        // Setup Enemy attacks - may need to be modified if you want to draw attacks from enemy's class
        enemyAttacks = new ArrayList<Attack>();
        enemyAttacks.add(Attack.attackMain);
        enemyAttacks.add(GrapeShot.attackGrape);
        enemyAttacks.add(Attack.attackSwivel);

        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void update(float delta){ }

	@Override
	public void render (float delta) {
	    Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        uiStage.draw();
        uiStage.act();
        mainStage.draw();
        mainStage.act();
        labelAnimationUpdate(delta);
    }
	
	@Override
	public void dispose () {
        uiStage.dispose();
        mainStage.dispose();
        bg_texture.dispose();
	}


    public void toggleAttackStage(){
        // This method toggles the visibility of the players attack moves and changes input processor to relevant stage
        if (background_wood.isVisible()) {
            background_wood.setVisible(false);
            completeAttackTable.setVisible(false);
            Gdx.input.setInputProcessor(uiStage);
        } else {
            background_wood.setVisible(true);
            completeAttackTable.setVisible(true);
            Gdx.input.setInputProcessor(mainStage);
        }
    }

    // combat Handler
    //  This function handles the ship combat using BattleEvent enum type
    public void combatHandler(BattleEvent status){

        if (!combatStack.empty()){
            currentAttack = combatStack.pop();
        }

        switch(status) {
            case NONE:
                toggleAttackStage();
                break;
            case PLAYER_MOVE:
                toggleAttackStage();
                textBox.setStyle(pirateGame.getSkin().get("default", TextButton.TextButtonStyle.class));
                if (currentAttack.isSkipMoveStatus()) {
                    currentAttack.setSkipMoveStatus(false);
                    combatStack.push(currentAttack);
                    dialog("Charging attack " + currentAttack.getName(), BattleEvent.ENEMY_MOVE);
                } else if (currentAttack.getName() == "FLEE") {
                    if (currentAttack.doAttack(player.getPlayerShip(), enemy) == 1) {
                        dialog("Flee successful!", BattleEvent.PLAYER_FLEES);
                    } else {
                        dialog("Flee failed.", BattleEvent.ENEMY_MOVE);
                    }
                } else {
                    int damage = currentAttack.doAttack(player.getPlayerShip(), enemy); // Calls the attack function on the player and stores damage output
                    // This selection statement returns Special Charge attacks to normal state
                    if (currentAttack.isSkipMove()) {
                        currentAttack.setSkipMoveStatus(true);
                    }

                    if (damage == 0) {
                        dialog("Attack Missed", BattleEvent.ENEMY_MOVE);
                    } else {
                        if (player.getPlayerShip().getHealth() <= 0) {
                            dialog("You dealt " + damage + " with " + currentAttack.getName() + "!", BattleEvent.PLAYER_DIES);
                        } else if (enemy.getHealth() <= 0) {
                            dialog("You dealt " + damage + " with " + currentAttack.getName() + "!", BattleEvent.ENEMY_DIES);
                        } else{
                            dialog("You dealt " + damage + " with " + currentAttack.getName() + "!", BattleEvent.ENEMY_MOVE);
                        }
                    }
                }
                break;
            case ENEMY_MOVE:
                textBox.setStyle(pirateGame.getSkin().get("red", TextButton.TextButtonStyle.class));
                Random ran = new Random();
                Attack enemyAttack = enemyAttacks.get(ran.nextInt(3));
                int damage = enemyAttack.doAttack(enemy, player.getPlayerShip());
                String message;
                if (damage == 0){
                    message = "Enemies " + enemyAttack.getName() + " missed.";
                } else {
                    message = "Enemy "+enemy.getName()+ " dealt " + damage + " with " + enemyAttack.getName()+ "!";
                }

                if (player.getPlayerShip().getHealth() <= 0) {
                    dialog("Enemies " + enemyAttack.getName() + " hit you for "+ damage, BattleEvent.PLAYER_DIES);
                } else if (enemy.getHealth() <= 0) {
                    dialog("Enemies " + enemyAttack.getName() + " hit you for "+ damage, BattleEvent.ENEMY_DIES);
                } else {
                    if (currentAttack.isSkipMove() != currentAttack.isSkipMoveStatus()){
                        dialog(message, BattleEvent.PLAYER_MOVE);
                    } else {
                        dialog(message, BattleEvent.NONE);
                    }
                }
                break;
            case PLAYER_DIES:
                textBox.setStyle(pirateGame.getSkin().get("red", TextButton.TextButtonStyle.class));
                dialog("YOU HAVE DIED. Final Score: " + pirateGame.getPlayer().getPoints(), BattleEvent.SCENE_RETURN);
                break;
            case ENEMY_DIES:
                textBox.setStyle(pirateGame.getSkin().get("default", TextButton.TextButtonStyle.class));
                player.addGold(20);
                player.addPoints(20);
                dialog("Congratulations, you have defeated Enemy " + enemy.getName(), BattleEvent.SCENE_RETURN);
                if (enemy.getIsBoss() == true) {
                    enemy.getCollege().setBossDead(true);
                    this.player.getPlayerShip().getCollege().addAlly(this.enemy.getCollege());
                }
                break;
            case PLAYER_FLEES:
                textBox.setStyle(pirateGame.getSkin().get("red", TextButton.TextButtonStyle.class));
                player.addPoints(-5);
                combatHandler(BattleEvent.SCENE_RETURN);
                break;
            case SCENE_RETURN:
                if(player.getPlayerShip().getHealth() <= 0) {
                    while(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {

                    }
                    pirateGame.reset();
                    pirateGame.setScreen(new MainMenu(pirateGame));
                }
                enemy.setVisible(false);
                player.getPlayerShip().setSpeed(0);
                player.getPlayerShip().setAccelerationXY(0,0);
                player.getPlayerShip().setAnchor(true);
                toggleAttackStage();
                pirateGame.setScreen(pirateGame.getSailingScene());
                break;
        }
    }

    // Button Listener Classes - creates a hover listener for any button passed through

    public void buttonListener(final AttackButton button){
        button.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                descriptionLabel.setText(button.getDesc());
            };

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                descriptionLabel.setText("What would you like to do?");
            };

            @Override
            public void clicked(InputEvent event, float x, float y) {
                combatStack.push(button.getAttack());
                combatHandler(BattleEvent.PLAYER_MOVE);
            }
        });
    }

    public void buttonListener(final AttackButton button, final String message){
        button.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                descriptionLabel.setText(button.getDesc());
            };

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                descriptionLabel.setText("Choose an option");
            };

            @Override
            public void clicked(InputEvent event, float x, float y) {
                button.setText(message);
            }
        });
    }

    // This method updates the player HP bar and text values
    public void updateHP(){
        enemyHP.setAnimateDuration(1);
        playerHP.setAnimateDuration(1);

        if (enemy.getHealth() <= 0){
            enemy.setHealth(0);
        }

        if (player.getPlayerShip().getHealth() <= 0){
            player.getPlayerShip().setHealth(0);
        }

        enemyHPLabel.setText(enemy.getHealth()+"/"+enemy.getHealthMax());
        enemyHP.setValue(enemy.getHealth());

        playerHPLabel.setText(player.getPlayerShip().getHealth()+"/" + player.getPlayerShip().getHealthMax());
        playerHP.setValue(player.getPlayerShip().getHealth());
    }

    // Updates and displays text box
    public void dialog(String message, final BattleEvent nextEvent){
        queuedCombatEvent = nextEvent;

        if (background_wood.isVisible()){
            toggleAttackStage();
        }

        displayText = message;
        animationIndex = 0;
        textAnimation = true;
    }

    // This method controls the animation of the dialog label
    public void labelAnimationUpdate(float dt){
        if (textAnimation) {
            delayTime += dt;

            if (animationIndex > displayText.length()){
                textAnimation = false;
            }

            if (delayTime >= 0.05f){
                textBox.setText(displayText.substring(0,animationIndex));
                animationIndex++;
                delayTime = 0;
            }
        }
    }
}

