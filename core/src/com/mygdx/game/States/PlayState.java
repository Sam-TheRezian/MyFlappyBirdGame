package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Sprites.Bird;
import com.mygdx.game.Sprites.Tube;

public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 gp1, gp2;

    public static double score;

    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(20, 300);
        cam.setToOrtho(false, com.mygdx.game.MyFlappyDemo.WIDTH/2, com.mygdx.game.MyFlappyDemo.HEIGHT/2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        gp1 = new Vector2(cam.position.x - cam.viewportWidth/2, GROUND_Y_OFFSET);
        gp2 = new Vector2((cam.position.x - cam.viewportWidth/2) + ground.getWidth(), GROUND_Y_OFFSET);

        score = 0;

        tubes = new Array<Tube>();
        for(int i = 1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;

        for(int i=0; i < tubes.size; i++){
            Tube tube = tubes.get(i);
            if(cam.position.x - (cam.viewportWidth/2) > tube.getPos_top_tube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPos_top_tube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if(tube.collides(bird.getBounds_bird()))
                gsm.set(new GameOverState(gsm, score));

            if(tube.passes(bird.getBounds_bird()))
                {score++; break;}

        }

        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET)
            gsm.set(new GameOverState(gsm, score));
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth/2), 0);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);
        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPos_top_tube().x, tube.getPos_top_tube().y);
            sb.draw(tube.getBottomTube(), tube.getPos_bot_tube().x, tube.getPos_bot_tube().y);
        }
        sb.draw(ground, gp1.x, gp1.y);
        sb.draw(ground, gp2.x, gp2.y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for (Tube tube : tubes)
            tube.dispose();
        System.out.println("Play state disposed");
    }

    private void updateGround(){
        if (cam.position.x - (cam.viewportWidth/2) > gp1.x + ground.getWidth())
            gp1.add(ground.getWidth() * 2, 0);
        if(cam.position.x - (cam.viewportWidth/2) > gp2.x + ground.getWidth())
            gp2.add(ground.getWidth() * 2, 0);
    }
}
