package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Tube {
    public static final int TUBE_WIDTH = 52;
    private static final int FLUCT = 130;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 120;
    private Texture topTube, bottomTube;
    private Vector2 pos_top_tube, pos_bot_tube;
    private Rectangle bounds_top, bounds_bot, bounds_gap;
    private Random rand;

    public Tube(float x) {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();

        pos_top_tube = new Vector2(x, rand.nextInt(FLUCT) + TUBE_GAP + LOWEST_OPENING);
        pos_bot_tube = new Vector2(x, pos_top_tube.y - TUBE_GAP - bottomTube.getHeight());

        bounds_top = new Rectangle(pos_top_tube.x, pos_top_tube.y, topTube.getWidth(), topTube.getHeight());
        bounds_bot = new Rectangle(pos_bot_tube.x, pos_bot_tube.y, bottomTube.getWidth(), bottomTube.getHeight());
        bounds_gap = new Rectangle(pos_bot_tube.x + bottomTube.getWidth(), pos_bot_tube.y + bottomTube.getHeight(), 0, 800 - topTube.getHeight() - bottomTube.getHeight());
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPos_top_tube() {
        return pos_top_tube;
    }

    public Vector2 getPos_bot_tube() {
        return pos_bot_tube;
    }

    public void reposition(float x){
        pos_top_tube.set(x, rand.nextInt(FLUCT) + TUBE_GAP + LOWEST_OPENING);
        pos_bot_tube.set(x, pos_top_tube.y - TUBE_GAP - bottomTube.getHeight());
        bounds_top.setPosition(pos_top_tube.x, pos_top_tube.y);
        bounds_bot.setPosition(pos_bot_tube.x, pos_bot_tube.y);
        bounds_gap.setPosition(pos_bot_tube.x + bottomTube.getWidth(), pos_bot_tube.y + bottomTube.getHeight());
    }

    public boolean collides(Rectangle player){
        return player.overlaps(bounds_top)||player.overlaps(bounds_bot);
    }

    public boolean passes(Rectangle player){
        return player.overlaps(bounds_gap);
    }

    public void dispose(){
        topTube.dispose(); bottomTube.dispose();
    }
}
