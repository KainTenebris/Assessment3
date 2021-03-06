package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Player;
import com.rear_admirals.york_pirates.base.BaseScreen;

import java.util.ArrayList;
import java.util.Random;

public class MiniGameScreen extends BaseScreen{
    private Player player;

    private Image card1;
    private Image card2;
    private Image card3;
    private Image card4;

    private Integer card1_val;
    private Integer card2_val;
    private Integer card3_val;
    private Integer card4_val;
    private Integer stage;

    private Label gold_num_label;
    private Label bet_num_label;

    private Table bet_table;
    private Table higher_lower_table;
    private Table gold_table;

    private Integer gold_available;
    private Integer bet_amount;

    private TextButton higher;
    private TextButton lower;
    private TextButton quarter_bet;
    private TextButton half_bet;
    private TextButton three_quarter_bet;
    private TextButton full_bet;
    private TextButton textBox;
    private TextButton winLose;

    /**
    Initialises all of the textures, buttons, initial card values, gets the required gold values from Player,
    and any other initialisations required for the MiniGame.
    **/
    public MiniGameScreen(final PirateGame main){
        super(main);
        this.player = main.getPlayer();
        this.gold_available = player.getGold();
        this.bet_amount = 0;

        //numbers for use
        float button_pad_bottom = viewheight / 24f;
        float button_pad_right = viewwidth / 32f;

        //textures and Images
        //texture and image for background
        Texture bg_texture = new Texture("table.png");
        Image background = new Image(bg_texture);
        background.setSize(viewwidth, viewheight);

        //texture and image for menu
        Texture wood_texture = new Texture("wood_vertical_board_texture.png");
        Image background_wood = new Image(wood_texture);
        background_wood.setSize(viewwidth, viewheight);

        //array of textures for the card fronts
        ArrayList<String> cards = new ArrayList<String>();
        cards.add("card_1.png");
        cards.add("card_2.png");
        cards.add("card_3.png");
        cards.add("card_4.png");
        cards.add("card_5.png");
        cards.add("card_6.png");
        cards.add("card_7.png");
        cards.add("card_8.png");
        cards.add("card_9.png");
        cards.add("card_10.png");
        cards.add("card_11.png");
        cards.add("card_12.png");
        cards.add("card_13.png");

        //pick 4 random different card textures and turn them into images
        //picks card values
        Random randint = new Random();
        card1_val = randint.nextInt(cards.size());
        card2_val = randint.nextInt(cards.size());
        card3_val = randint.nextInt(cards.size());
        card4_val = randint.nextInt(cards.size());

        //ensures card values are different
        while (card2_val.equals(card1_val)){
            card2_val = randint.nextInt(cards.size());
        }
        while ((card3_val.equals(card1_val))||(card3_val.equals(card2_val))){
            card3_val = randint.nextInt(cards.size());
        }
        while ((card4_val.equals(card1_val))||(card4_val.equals(card2_val))||(card4_val.equals(card3_val))){
            card4_val = randint.nextInt(cards.size());
        }

        //fetches card images
        card1 = new Image(new Texture(cards.get(card1_val)));
        card2 = new Image(new Texture(cards.get(card2_val)));
        card3 = new Image(new Texture(cards.get(card3_val)));
        card4 = new Image(new Texture(cards.get(card4_val)));

        //sets card size
        card1.setSize(360,504);
        card2.setSize(360,504);
        card3.setSize(360,504);
        card4.setSize(360,504);

        //calculating numbers for possible bets
        int quarter = this.gold_available/4;
        int half = this.gold_available/2;
        int three_quarter = 3*this.gold_available/4;

        //buttons
        //creates buttons for guessing result
        lower = new TextButton("Lower", pirateGame.getSkin());
        higher = new TextButton("Higher", pirateGame.getSkin());

        //creates buttons for choosing bets
        quarter_bet = new TextButton("" + quarter, pirateGame.getSkin());
        half_bet = new TextButton("" + half, pirateGame.getSkin());
        three_quarter_bet = new TextButton("" + three_quarter, pirateGame.getSkin());
        full_bet = new TextButton(this.gold_available.toString(), pirateGame.getSkin());

        //creates button to leave game
        TextButton quit = new TextButton("Back To Main Game", pirateGame.getSkin());
        textBox = new TextButton("", pirateGame.getSkin());

        //creates button to win/lose
        winLose = new TextButton("", pirateGame.getSkin());

        //listeners for the buttons
        buttonListener(quarter_bet, 0.25);
        buttonListener(half_bet, 0.5);
        buttonListener(three_quarter_bet, 0.75);
        buttonListener(full_bet, 1);

        //listener to quit minigame
        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.setGold(gold_available);
                pirateGame.beforeMinigameScreen.resume();
                pirateGame.setScreen(pirateGame.beforeMinigameScreen);
                dispose();
            }
        });

        //listener to win/lose
        winLose.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                textBox.setText("");
                pirateGame.setScreen(new MiniGameScreen(pirateGame));
            }
        });

        //Labels
        //create Labels for gold and bet
        Label gold_label = new Label("Available Gold: ", pirateGame.getSkin());
        Label bet_label = new Label("Gold Bet: ", pirateGame.getSkin());
        gold_num_label = new Label(this.gold_available.toString(), pirateGame.getSkin());
        bet_num_label = new Label(this.bet_amount.toString(), pirateGame.getSkin());



        //tables
        //creates containers for the cards to be in
        Container<Table> contain = new Container<Table>();
        contain.setFillParent(true);
        contain.setPosition(0,0);
        contain.align(Align.bottom);

        //Instantiate the tables
        Table card_table_front = new Table();
        Table card_table_back = new Table();
        bet_table = new Table();
        higher_lower_table = new Table();
        gold_table = new Table();
        Table screen_bottom = new Table();
        Table win_lose_table = new Table();

        //create table to choose bets from
        bet_table.row();
        bet_table.add(quarter_bet).uniform().width(viewwidth/5).padRight(button_pad_right);
        bet_table.add(half_bet).uniform().width(viewwidth/5);
        bet_table.row().padTop(button_pad_bottom);
        bet_table.add(three_quarter_bet).uniform().width(viewwidth/5).padRight(button_pad_right);
        bet_table.add(full_bet).uniform().width(viewwidth/5);

        //create table to choose higher/lower from
        higher_lower_table.row().padBottom(viewheight/18f);
        higher_lower_table.add(new Label("", pirateGame.getSkin())).width(viewwidth/2);
        higher_lower_table.add(lower).uniform().width(viewwidth/5).padRight(button_pad_right);
        higher_lower_table.add(higher).uniform().width(viewwidth/5);
        higher_lower_table.row().padBottom(button_pad_bottom);
        higher_lower_table.add(new Label("", pirateGame.getSkin()));
        higher_lower_table.row().padBottom(button_pad_bottom);
        higher_lower_table.add(new Label("", pirateGame.getSkin()));
        higher_lower_table.row();
        higher_lower_table.add(new Label("", pirateGame.getSkin()));
        higher_lower_table.align(Align.left);
        higher_lower_table.setVisible(false);

        //create table for the cards to be displayed in
        card_table_front.row().padBottom(100);
        card_table_front.add(card1).uniform().padRight(button_pad_right);
        card_table_front.add(card2).uniform().padRight(button_pad_right);
        card_table_front.add(card3).uniform().padRight(button_pad_right);
        card_table_front.add(card4).uniform();
        card_table_front.row();
        card1.setVisible(false);
        card2.setVisible(false);
        card3.setVisible(false);
        card4.setVisible(false);
        winLose.setVisible(false);
        contain.setActor(card_table_front);

        //create win_lose_table
        win_lose_table.row();
        card_table_front.add(winLose).colspan(4).fillX().height(viewheight/9f).pad(viewheight/12,0,viewheight/12,0);
        winLose.setVisible(false);

        //create table for gold and bet
        gold_table.row();
        gold_table.add(gold_label).uniform().width(viewwidth/5);
        gold_table.add(gold_num_label).uniform();
        gold_table.row();
        gold_table.add(bet_label).uniform().width(viewwidth/5);
        gold_table.add(bet_num_label).uniform();
        gold_table.row();
        gold_table.add(quit).uniform().width(viewwidth/5);

        //create table for bottom of screen
        screen_bottom.row().padBottom(viewheight/18f);
        screen_bottom.add(gold_table).width(viewwidth/2);
        screen_bottom.add(bet_table);
        screen_bottom.align(Align.bottomLeft);

        //makes everything visible
        mainStage.addActor(background);
        mainStage.addActor(background_wood);

        uiStage.addActor(contain);
        uiStage.addActor(screen_bottom);
        uiStage.addActor(win_lose_table);
        uiStage.addActor(higher_lower_table);

        //makes everything interactable
        Gdx.input.setInputProcessor(uiStage);
    }

    /**
    * Makes the specified card visible after pressing higher/lower.
    * @param card The specified card to reveal.
    **/
    private void turn(Integer card){
        switch (card){
            case 1:     card1.setVisible(true);
                        break;
            case 2:     card2.setVisible(true);
                        break;
            case 3:     card3.setVisible(true);
                        break;
            case 4:     card4.setVisible(true);
                        break;
            default:    throw new IllegalArgumentException("not valid card");
        }
    }

    /*
    * Reveals the first card and starts the game.
    * Called after selecting an amount to bet.
    */
    private void play(){
        stage = 1;
        update(bet_amount);
        bet_table.setVisible(false);
        higher_lower_table.setVisible(true);
        buttonListener(higher, true);
        buttonListener(lower,false);
        turn(stage);
    }

    /**
    * Updates values of bet_amount and gold_available on the screen and buttons
    **/
    public void update(float delta){
        bet_num_label.setText(bet_amount.toString());
        gold_num_label.setText(gold_available.toString());

        int quarter = this.gold_available/4;
        int half = this.gold_available/2;
        int three_quarter = 3*this.gold_available/4;

        quarter_bet.setText("" + quarter);
        half_bet.setText("" + half);
        three_quarter_bet.setText("" + three_quarter);
        full_bet.setText("" + this.gold_available);
    }

    /**
    * Adds a ClickListener to the specified higher/lower button to turn the next card and advance the game.
    * @param button The button to add a ClickListener to.
    * @param high True if the button is the higher button, false otherwise.
    * @throws IllegalArgumentException if the button is used after it should be possible to - i.e. if all 4 cards are revealed.
    **/
    private void buttonListener(TextButton button, final Boolean high){
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switch (stage){
                    case 1:     turn(stage+1);
                                if (high == (card2_val>card1_val)){
                                    stage+=1;
                                } else{
                                    player.setGold(gold_available);
                                    winLose.setText("You Lose, better luck next time");
                                    gold_table.setVisible(false);
                                    higher_lower_table.setVisible(false);
                                    winLose.setVisible(true);
                                }
                                break;
                    case 2:     turn(stage+1);
                                if (high == (card3_val>card2_val)){
                                    stage+=1;
                                } else{
                                    player.setGold(gold_available);
                                    winLose.setText("You Lose, better luck next time");
                                    gold_table.setVisible(false);
                                    higher_lower_table.setVisible(false);
                                    winLose.setVisible(true);
                                }
                                break;
                    case 3:     turn(stage+1);
                                if (high == (card4_val>card3_val)){
                                    gold_available += 2*bet_amount;
                                    winLose.setText("You Win! Congratulations!");
                                } else {
                                    winLose.setText("You Lose, better luck next time");
                                }
                                player.setGold(gold_available);
                                bet_amount = 0;
                                gold_table.setVisible(false);
                                higher_lower_table.setVisible(false);
                                winLose.setVisible(true);
                                break;
                    default:    throw new IllegalArgumentException("not valid stage");
                }
            }
        });
    }

    /**
    * Adds a ClickListener to the bet buttons to start the game/take the required money from the player.
    * @param button The button to add the ClickListener to.
    * @param percent The percent of the player's gold to be played with.
    **/
    private void buttonListener(TextButton button, final double percent){
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                double temp = gold_available*percent;
                bet_amount = (int)temp;
                gold_available -= bet_amount;
                play();
            }
        });
    }

}
