package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.base.BaseScreen;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Player;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.*;

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

    private Integer gold_available;
    private Integer bet_amount;

    private TextButton higher;
    private TextButton lower;
    private TextButton quater_bet;
    private TextButton half_bet;
    private TextButton three_quater_bet;
    private TextButton full_bet;
    private TextButton textBox;

    public MiniGameScreen(final PirateGame main){
        super(main);
        this.player = main.getPlayer();
        this.gold_available = player.getGold();
        this.bet_amount = 0;
        //for testing purposes
//        this.gold_available = 1000;

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
        ArrayList<String> cards = new ArrayList();
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
//        cards.add("card_11.png");
//        cards.add("card_12.png");
//        cards.add("card_13.png");



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
        Integer quater = this.gold_available/4;
        Integer half = this.gold_available/2;
        Integer three_quater = 3*this.gold_available/4;



        //buttons
        //creates buttons for guessing result
        lower = new TextButton("Lower", pirateGame.getSkin());
        higher = new TextButton("Higher", pirateGame.getSkin());

        //creates buttons for choosing bets
        quater_bet = new TextButton(quater.toString(), pirateGame.getSkin());
        half_bet = new TextButton(Integer.toString(half), pirateGame.getSkin());
        three_quater_bet = new TextButton(three_quater.toString(), pirateGame.getSkin());
        full_bet = new TextButton(this.gold_available.toString(), pirateGame.getSkin());

        //creates button to leave game
        TextButton quit = new TextButton("Back To Main Game", pirateGame.getSkin());
        textBox = new TextButton("", pirateGame.getSkin());

        //listeners for the buttons
        buttonListener(quater_bet, 0.25);
        buttonListener(half_bet, 0.5);
        buttonListener(three_quater_bet, 0.75);
        buttonListener(full_bet, 1);

        //listener to quit minigame
        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("quitting");
                player.setGold(gold_available);
                //dispose of minigame or build new sailing screen
                pirateGame.setScreen(pirateGame.getSailingScene());
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
        Table gold_table = new Table();
        Table screen_bottom = new Table();

        //create table to choose bets from
        bet_table.row();
        bet_table.add(quater_bet).uniform().width(viewwidth/5).padRight(button_pad_right);
        bet_table.add(half_bet).uniform().width(viewwidth/5);
        bet_table.row().padTop(button_pad_bottom);
        bet_table.add(three_quater_bet).uniform().width(viewwidth/5).padRight(button_pad_right);
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
        card_table_front.row().padBottom(400);
        card_table_front.add(card1).uniform().padRight(button_pad_right);
        card_table_front.add(card2).uniform().padRight(button_pad_right);
        card_table_front.add(card3).uniform().padRight(button_pad_right);
        card_table_front.add(card4).uniform();
        card_table_front.row();
        card1.setVisible(false);
        card2.setVisible(false);
        card3.setVisible(false);
        card4.setVisible(false);
//        textBox.setVisible(false);
        contain.setActor(card_table_front);

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
        uiStage.addActor(higher_lower_table);

        //makes everything interactable
        Gdx.input.setInputProcessor(uiStage);
    }

    private void turn(Integer card){
        switch (card){
            case 1:     System.out.println(card1_val+":"+card2_val);
                        card1.setVisible(true);
                        break;
            case 2:     System.out.println(card2_val+":"+card3_val);
                        card2.setVisible(true);
                        break;
            case 3:     System.out.println(card3_val+":"+card4_val);
                        card3.setVisible(true);
                        break;
            case 4:     System.out.println(card4_val);
                        card4.setVisible(true);
                        break;
            default:    throw new IllegalArgumentException("not valid card");
        }
    }

    private void play(){
        stage = 1;
        System.out.println(card1_val+":"+card2_val+":"+card3_val+":"+card4_val);
        update(bet_amount);
        bet_table.setVisible(false); //couldn't press buttons. dunno if that was cos you couldn't or I was clicking in the wrong place
        higher_lower_table.setVisible(true);
        buttonListener(higher, true);
        buttonListener(lower,false);
        turn(stage);
    }

    public void update(float delta){
        bet_num_label.setText(bet_amount.toString());
        gold_num_label.setText(gold_available.toString());

        Integer quater = this.gold_available/4;
        Integer half = this.gold_available/2;
        Integer three_quater = 3*this.gold_available/4;

        quater_bet.setText(quater.toString());
        half_bet.setText(half.toString());
        three_quater_bet.setText(three_quater.toString());
        full_bet.setText(this.gold_available.toString());
    }

    private void buttonListener(TextButton button, final Boolean high){
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switch (stage){
                    case 1:     turn(stage+1);
                                System.out.println(stage);
                                if (high == (card2_val>card1_val)){
                                    System.out.println("yup");
                                } else{
                                    player.setGold(gold_available);
                                    pirateGame.setScreen(new MiniGameScreen(pirateGame));
                                    dispose();
                                }
                                stage+=1;
                                break;
                    case 2:     turn(stage+1);
                                System.out.println(stage);
                                if (high == (card3_val>card2_val)){
                                    System.out.println("yup");
                                } else{
                                    player.setGold(gold_available);
                                    pirateGame.setScreen(new MiniGameScreen(pirateGame));
                                    dispose();
                                }
                                stage+=1;
                                break;
                    case 3:     turn(stage+1);
                                System.out.println(stage);
                                stage = 1;
                                if (high == (card4_val>card3_val)){
                                    System.out.println("yup");
                                    gold_available += 2*bet_amount;
                                } else{
                                }
                                higher_lower_table.setVisible(false);
                                bet_table.setVisible(true);
                                bet_amount = 0;
                                update(bet_amount);
                                //doesn't let you play a second game for some reason
                                //imma just kill teh entire thing an reload the screen
                                //do this a better way if time
                                player.setGold(gold_available);
                                pirateGame.setScreen(new MiniGameScreen(pirateGame));
                                dispose();
                                break;
                    default:    throw new IllegalArgumentException("not valid stage");
                }
            }
        });
    }

    private void buttonListener(TextButton button, final double percent){
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                double temp = gold_available*percent;
                System.out.println(percent);
                System.out.println(temp);
                bet_amount = (int)temp;
                gold_available -= bet_amount;
                System.out.println("clicked button: "+bet_amount);
                play();
            }
        });
    }

}
