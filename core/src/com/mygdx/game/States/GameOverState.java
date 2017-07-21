package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyFlappyDemo;

public class GameOverState extends State {
    private Texture background, gameover, retry;

    int finalScore = 0;
    String totalScore;
    BitmapFont scoreFont;

    public GameOverState(GameStateManager gsm, double myScore) {
        super(gsm);
        cam.setToOrtho(false, com.mygdx.game.MyFlappyDemo.WIDTH/2, com.mygdx.game.MyFlappyDemo.HEIGHT/2);
        background = new Texture("bg.png");
        gameover = new Texture("gameover.png");
        retry = new Texture("retry_logo.png");

        finalScore = (int)myScore/20;

        totalScore = "SCORE: " + finalScore;
        scoreFont = new BitmapFont();
        scoreFont.setColor(Color.ORANGE);
        scoreFont.getData().setScale(1.5f, 1.5f);

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(gameover, (MyFlappyDemo.WIDTH)/2 - gameover.getWidth()*10/9, (MyFlappyDemo.HEIGHT/2) - gameover.getHeight()*2);
        scoreFont.draw(sb, totalScore, cam.position.x - retry.getWidth()*3/7, cam.position.y + retry.getHeight()*3/2);
        sb.draw(retry, cam.position.x - retry.getWidth()/2, cam.position.y - retry.getHeight()/2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameover.dispose();
        scoreFont.dispose();
    }
}
