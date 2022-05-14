package com.aircraftwar.android.application;

import com.aircraftwar.android.aircraft.AbstractAircraft;
import com.aircraftwar.android.aircraft.HeroAircraft;
import com.aircraftwar.android.aircraft.MobEnemy;
import com.aircraftwar.android.application.ImageManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MainGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background;
    public static final int viewportWidth = 512;
    public static final int viewportHeight = 768;
    private OrthographicCamera camera;
    private Viewport viewport;
    private float backgroundTop;


    private HeroAircraft heroAircraft;
    private Array<AbstractAircraft> enemyAircrafts;
    private final int enemyMaxNumber = 5;
    private long lastEnemyGen = 0;
    private long enemyGenDuration = 1000000000;


    @Override
    public void create() {
        //Initialize
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,viewportWidth,viewportHeight);
        viewport = new ExtendViewport(viewportWidth,viewportHeight, camera);
        background = ImageManager.BACKGROUND_IMAGE;
        backgroundTop = viewportHeight;
        heroAircraft = new HeroAircraft(0, 0, 0, 0, 100);
        heroAircraft.setLocation(viewportWidth / 2 - heroAircraft.getWidth() / 2, 0);
        enemyAircrafts = new Array<>();
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawBackground();
        drawAircraft();
        batch.end();

        //Enemy Generate
        if (enemyAircrafts.size < enemyMaxNumber && TimeUtils.nanoTime() - lastEnemyGen > enemyGenDuration) {
            lastEnemyGen = TimeUtils.nanoTime();
            enemyAircrafts.add(
                    new MobEnemy(
                            MathUtils.random((float) 0, (float) (viewportWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth() / 2)),
                            MathUtils.random((float) (viewportHeight * 0.8), (float) viewportHeight),
                            0, 200, 10));
        }

        //HeroAircraft Move
        aircraftMove();

        //Crash check
        crashCheck();

    }

    @Override
    public void dispose() {
        background.dispose();
        batch.dispose();
    }


    private void drawBackground() {
        //Draw Background
        batch.draw(background, 0, backgroundTop - background.getHeight());
        batch.draw(background, 0, backgroundTop);

        //Background Scrolling
        backgroundTop -= 100 *  Gdx.graphics.getDeltaTime();
        if (backgroundTop <= 0) {
            backgroundTop = viewportHeight;
        }
    }

    private void drawAircraft() {
        //Draw Enemy
        for (Iterator<AbstractAircraft> iterator = enemyAircrafts.iterator(); iterator.hasNext();) {
            AbstractAircraft enemy = iterator.next();
            if (!enemy.notValid()) {
                batch.draw(enemy.getImage(), enemy.getLocationX(), enemy.getLocationY(), enemy.getWidth(), enemy.getHeight());
            } else {
                iterator.remove();
            }
        }

        //Draw Aircraft
        batch.draw(heroAircraft.getImage(), heroAircraft.getLocationX(), heroAircraft.getLocationY(), heroAircraft.getWidth(), heroAircraft.getHeight());
    }

    private void crashCheck(){
        for(AbstractAircraft enemy :enemyAircrafts){
            if(enemy.crash(heroAircraft)){
                //TODO: Game End
            }
        }
    }


    private void aircraftMove() {
        for (AbstractAircraft enemy : enemyAircrafts) {
            enemy.forward();
        }
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            heroAircraft.setLocation(touchPos.x - heroAircraft.getWidth() / 2, touchPos.y - heroAircraft.getWidth() / 2);
        }
    }

}