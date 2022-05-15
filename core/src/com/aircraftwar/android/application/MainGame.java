package com.aircraftwar.android.application;

import com.aircraftwar.android.aircraft.AbstractAircraft;
import com.aircraftwar.android.aircraft.EliteEnemy;
import com.aircraftwar.android.aircraft.HeroAircraft;
import com.aircraftwar.android.bullet.AbstractBullet;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;

public class MainGame extends ApplicationAdapter {
    private int score = 0;
    private BitmapFont font;
    private SpriteBatch batch;
    private Texture background;
    public static final int viewportWidth = 512;
    public static final int viewportHeight = 768;
    private OrthographicCamera camera;
    private Viewport viewport;
    private float backgroundTop;


    private HeroAircraft heroAircraft;
    private Array<AbstractAircraft> enemyAircrafts;
    private Array<AbstractBullet> heroBullets;
    private Array<AbstractBullet> enemyBullets;
    private final int enemyMaxNumber = 5;
    private long lastEnemyGenTime = 0;
    private long enemyGenDuration = 1000000000;
    private long heroLastShootGenTime = 0;
    private long heroShootGenDuration = 500000000;
    private long eliteLastShootGenTime = 0;
    private long eliteShootGenDuration = 500000000;



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
        heroBullets = new Array<>();
        enemyBullets = new Array<>();
        font = new BitmapFont();


    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawBackground();
        drawObject();
        font.draw(batch, Integer.toString(score),0,viewportHeight-20);
        batch.end();




        //Enemy Generate
        if (enemyAircrafts.size < enemyMaxNumber && TimeUtils.nanoTime() - lastEnemyGenTime > enemyGenDuration) {
            lastEnemyGenTime = TimeUtils.nanoTime();
            enemyAircrafts.add(
                    new EliteEnemy(
                            MathUtils.random((float) 0, (float) (viewportWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth() / 2)),
                            MathUtils.random((float) (viewportHeight * 0.8), (float) viewportHeight),
                            0, 200, 10));
        }

        shootAction();

        //HeroAircraft Move
        objectMove();

        //Crash check
        crashCheck();

    }

    @Override
    public void dispose() {
        background.dispose();
        batch.dispose();
    }

    //shoot
    private void shootAction() {
        //heroaircraft shoot
        if (TimeUtils.nanoTime() - heroLastShootGenTime > heroShootGenDuration) {
            heroLastShootGenTime = TimeUtils.nanoTime();
            heroBullets.addAll(heroAircraft.shoot());
        }

        //elite enemy shoot
        if (TimeUtils.nanoTime() - eliteLastShootGenTime > eliteShootGenDuration) {
            eliteLastShootGenTime = TimeUtils.nanoTime();
            for(AbstractAircraft enemy:enemyAircrafts) {
                if(enemy instanceof EliteEnemy) {
                    enemyBullets.addAll(enemy.shoot());
                }
            }
        }

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

    private void drawObject() {
        //Draw Enemy
        for (Iterator<AbstractAircraft> iterator = enemyAircrafts.iterator(); iterator.hasNext();) {
            AbstractAircraft enemy = iterator.next();
            if (!enemy.notValid()) {
                batch.draw(enemy.getImage(), enemy.getLocationX(), enemy.getLocationY(), enemy.getWidth(), enemy.getHeight());
            } else {
                iterator.remove();
            }
        }
        //Draw Bullets
        for (Array.ArrayIterator<AbstractBullet> iterator = heroBullets.iterator(); iterator.hasNext();) {
            AbstractBullet bullet = iterator.next();
            if (!bullet.notValid()) {
                batch.draw(bullet.getImage(), bullet.getLocationX(), bullet.getLocationY(), bullet.getWidth(), bullet.getHeight());
            } else {
                iterator.remove();
            }
        }
        for (Array.ArrayIterator<AbstractBullet> iterator = enemyBullets.iterator(); iterator.hasNext();) {
            AbstractBullet bullet = iterator.next();
            if (!bullet.notValid()) {
                batch.draw(bullet.getImage(), bullet.getLocationX(), bullet.getLocationY(), bullet.getWidth(), bullet.getHeight());
            } else {
                iterator.remove();
            }
        }

        //Draw Aircraft
        batch.draw(heroAircraft.getImage(), heroAircraft.getLocationX(), heroAircraft.getLocationY(), heroAircraft.getWidth(), heroAircraft.getHeight());
    }

    private void crashCheck(){
        //check whether hero crashes enemy
        for(AbstractAircraft enemy :enemyAircrafts){
            if(enemy.notValid()) {
                continue;
            }
            if(enemy.crash(heroAircraft)){
//                Gdx.app.exit();
                //TODO
            }
        }

        //check whether hero bullets hit enemy
        for(AbstractBullet bullet : heroBullets) {
            if(bullet.notValid()) {
                continue;
            }
            for(AbstractAircraft enemy:enemyAircrafts) {
                if(enemy.notValid()) {
                    continue;
                }
                if(enemy.crash(bullet)) {
                    enemy.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if(enemy.notValid()) {
                        score += 10;
                    }
                }
            }
        }

    }


    private void objectMove() {
        for (AbstractAircraft enemy : enemyAircrafts) {
            enemy.forward();
        }
        for (AbstractBullet bullet: heroBullets) {
            bullet.forward();
        }
        for (AbstractBullet bullet: enemyBullets) {
            bullet.forward();
        }
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            heroAircraft.setLocation(touchPos.x - heroAircraft.getWidth() / 2, touchPos.y - heroAircraft.getWidth() / 2);
        }
    }

}