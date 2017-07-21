package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {
    private Texture background, play_button, logo;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, com.mygdx.game.MyFlappyDemo.WIDTH/2, com.mygdx.game.MyFlappyDemo.HEIGHT/2);
        logo = new Texture("flappy.jpg");
        background = new Texture("bg.png");
        play_button = new Texture("Button_PlayNow.png");
    }

    @Override
    public void handleInput() {
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
        sb.draw(logo,(com.mygdx.game.MyFlappyDemo.WIDTH)/2 - (logo.getWidth()*12/11), (com.mygdx.game.MyFlappyDemo.HEIGHT)/2 - logo.getHeight());
        sb.draw(play_button, cam.position.x - play_button.getWidth()/2, cam.position.y - play_button.getHeight());
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        logo.dispose();
        play_button.dispose();
        System.out.println("Menu State disposed");
    }
}
