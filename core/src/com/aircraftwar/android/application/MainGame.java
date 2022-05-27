package com.aircraftwar.android.application;

import com.aircraftwar.android.aircraft.AbstractAircraft;
import com.aircraftwar.android.aircraft.BossEnemy;
import com.aircraftwar.android.aircraft.EliteEnemy;
import com.aircraftwar.android.aircraft.HeroAircraft;
import com.aircraftwar.android.aircraft.MobEnemy;
import com.aircraftwar.android.aircraft.shootstrategy.SprayShoot;
import com.aircraftwar.android.basic.AbstractFlyingObject;
import com.aircraftwar.android.bullet.AbstractBullet;
import com.aircraftwar.android.prop.PropBlood;
import com.aircraftwar.android.prop.PropBomb;
import com.aircraftwar.android.prop.PropBullet;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;
import java.util.Random;
import java.util.TimerTask;

public class MainGame extends ApplicationAdapter {

    public MainGame(CommunicationInterface communicationInterface){
        this.communicationInterface = communicationInterface;
    }

    private CommunicationInterface communicationInterface;
    private SpriteBatch batch;
    private Texture background;
    public static final int viewportWidth = 512;
    public static final int viewportHeight = 768;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont font24;
    private float backgroundTop;

    private int score = 0;
    private int heroSpeed = 1000;
    private HeroAircraft heroAircraft;
    private Array<AbstractAircraft> enemyAircrafts;
    private Array<AbstractBullet> heroBullets;
    private Array<AbstractBullet> enemyBullets;
    private Array<AbstractFlyingObject> props;
    /**
     * the number of boss ever existed
     */
    private int bossNumber = 0;
    private boolean bossExisting = false;
    private int bossThreshold = 200;
    private final int enemyMaxNumber = 5;
    private final int eliteRate = 1;
    private long lastEnemyGenTime = 0;
    private long enemyGenDuration = 1000000000;
    private long heroLastShootGenTime = 0;
    private long heroShootGenDuration = 500000000;
    private long eliteLastShootGenTime = 0;
    private long eliteShootGenDuration = 500000000;
    private long bossLastShootGenTime = 0;
    private long bossShootGenDuration = 500000000;

    private Sound bomb_Explosion;
    private Sound bullet_Hit;
    private Sound game_Over;
    private Sound get_Supply;
    private Music bgm;
    private Music bgm_Boss;
    private Timer timer;

    @Override
    public void create() {
        //Initialize
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewportWidth, viewportHeight);
        viewport = new ExtendViewport(viewportWidth, viewportHeight, camera);
        background = ImageManager.BACKGROUND_IMAGE;
        backgroundTop = viewportHeight;
        heroAircraft = new HeroAircraft(0, 0, 0, 0, 100);
        heroAircraft.setLocation(viewportWidth / 2 - heroAircraft.getWidth() / 2, 0);
        enemyAircrafts = new Array<>();
        heroBullets = new Array<>();
        enemyBullets = new Array<>();
        props = new Array<>();
        bomb_Explosion = Gdx.audio.newSound(Gdx.files.internal("musics/bomb_explosion.wav"));
        bullet_Hit = Gdx.audio.newSound(Gdx.files.internal("musics/bullet_hit.wav"));
        game_Over = Gdx.audio.newSound(Gdx.files.internal("musics/game_over.wav"));
        get_Supply = Gdx.audio.newSound(Gdx.files.internal("musics/get_supply.wav"));
        bgm = Gdx.audio.newMusic(Gdx.files.internal("musics/bgm.wav"));
        bgm_Boss = Gdx.audio.newMusic(Gdx.files.internal("musics/bgm_boss.wav"));
        timer = new Timer();

        bgm.setLooping(true);
        bgm.setLooping(true);
        bgm.play();

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Inter-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.valueOf("e03131");
        font24 = fontGenerator.generateFont(parameter);
        fontGenerator.dispose();
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawBackground();
        drawObject();
        font24.draw(batch, "SCORE: " + Integer.toString(score) + "\nLIFE:"+Integer.toString(heroAircraft.getHp()), 0, viewportHeight - 10);
        batch.end();


        //Enemy Generate
        enemyGenerate();

        shootAction();

        //HeroAircraft Move
        objectMove();

        //Crash check
        crashCheck();

    }

    //Enemy Generate
    private void enemyGenerate() {
        if (enemyAircrafts.size < enemyMaxNumber && TimeUtils.nanoTime() - lastEnemyGenTime > enemyGenDuration) {
            lastEnemyGenTime = TimeUtils.nanoTime();
            if (!bossExisting) {
                if (MathUtils.random(1, 5) <= eliteRate) {
                    enemyAircrafts.add(
                            new EliteEnemy(
                                    MathUtils.random((float) 0, (float) (viewportWidth - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                                    MathUtils.random((float) (viewportHeight * 0.8), (float) viewportHeight),
                                    100, 100, 20));
                } else {
                    enemyAircrafts.add(
                            new MobEnemy(
                                    MathUtils.random((float) 0, (float) (viewportWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                                    MathUtils.random((float) (viewportHeight * 0.8), (float) viewportHeight),
                                    0, 200, 10));
                }
            }
            if (score / bossThreshold > bossNumber && bossExisting == false) {
                bossNumber++;
                bossExisting = true;
                enemyAircrafts.add(
                        new BossEnemy(
                                MathUtils.random((float) 0, (float) (viewportWidth - ImageManager.BOSS_ENEMY_IMAGE.getWidth())),
                                MathUtils.random((float) (viewportHeight * 0.95), (float) viewportHeight) - ImageManager.BOSS_ENEMY_IMAGE.getHeight() / 2,
                                100, 0, 100));
                bgm.pause();
                bgm_Boss.setPosition(0);
                bgm_Boss.play();
            }
        }
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
            for (AbstractAircraft enemy : enemyAircrafts) {
                if (enemy instanceof EliteEnemy) {
                    enemyBullets.addAll(enemy.shoot());
                }
            }
        }

        //boss shoot
        if (TimeUtils.nanoTime() - bossLastShootGenTime > bossShootGenDuration) {
            bossLastShootGenTime = TimeUtils.nanoTime();
            for (AbstractAircraft enemy : enemyAircrafts) {
                if (enemy instanceof BossEnemy) {
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
        backgroundTop -= 100 * Gdx.graphics.getDeltaTime();
        if (backgroundTop <= 0) {
            backgroundTop = viewportHeight;
        }
    }

    private void drawObject() {

        //Draw Bullets
        for (Array.ArrayIterator<AbstractBullet> iterator = heroBullets.iterator(); iterator.hasNext(); ) {
            AbstractBullet bullet = iterator.next();
            if (!bullet.notValid()) {
                batch.draw(bullet.getImage(), bullet.getLocationX(), bullet.getLocationY(), bullet.getWidth(), bullet.getHeight());
            } else {
                iterator.remove();
            }
        }
        for (Array.ArrayIterator<AbstractBullet> iterator = enemyBullets.iterator(); iterator.hasNext(); ) {
            AbstractBullet bullet = iterator.next();
            if (!bullet.notValid()) {
                batch.draw(bullet.getImage(), bullet.getLocationX(), bullet.getLocationY(), bullet.getWidth(), bullet.getHeight());
            } else {
                iterator.remove();
            }
        }
        //Draw Enemy
        for (Iterator<AbstractAircraft> iterator = enemyAircrafts.iterator(); iterator.hasNext(); ) {
            AbstractAircraft enemy = iterator.next();
            if (!enemy.notValid()) {
                batch.draw(enemy.getImage(), enemy.getLocationX(), enemy.getLocationY(), enemy.getWidth(), enemy.getHeight());
            } else {
                iterator.remove();
            }
        }

        //Draw Hero
        batch.draw(heroAircraft.getImage(), heroAircraft.getLocationX(), heroAircraft.getLocationY(), heroAircraft.getWidth(), heroAircraft.getHeight());

        //Draw Props
        for (Iterator<AbstractFlyingObject> iterator = props.iterator(); iterator.hasNext(); ) {
            AbstractFlyingObject prop = iterator.next();
            if (!prop.notValid()) {
                batch.draw(prop.getImage(), prop.getLocationX(), prop.getLocationY(), prop.getWidth(), prop.getHeight());
            } else {
                iterator.remove();
            }
        }
    }

    private void crashCheck() {
        //check whether hero crashes enemy
        for (AbstractAircraft enemy : enemyAircrafts) {
            if (enemy.notValid()) {
                continue;
            }
            if (enemy.crash(heroAircraft)) {
                // TODO
                communicationInterface.goRankListActivityAndGetName(score);
                Gdx.app.exit();
            }
        }

        //check whether hero bullets hit enemy
        for (AbstractBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemy : enemyAircrafts) {
                if (enemy.notValid()) {
                    continue;
                }
                if (enemy.crash(bullet)) {
                    enemy.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    bullet_Hit.play();
                    if (enemy.notValid()) {
                        if (enemy instanceof EliteEnemy) {
                            score += 20;
                            propGeneration(enemy);
                        } else if (enemy instanceof MobEnemy) {
                            score += 10;
                        } else if (enemy instanceof BossEnemy) {
                            score += 50;
                            bossExisting = false;
                            propGeneration(enemy);
                            bgm_Boss.pause();
                            bgm.setPosition(0);
                            bgm.play();
                        }
                    }
                }
            }
        }

        //check whether hero hits props
        for (AbstractFlyingObject prop : props) {
            if (!prop.notValid()) {
                if (heroAircraft.crash(prop)) {
                    if (prop instanceof PropBlood) {
                        get_Supply.play();
                        prop.vanish();
                        heroAircraft.increaseHp(20);
                    } else if (prop instanceof PropBullet) {
                        prop.vanish();
                        timer.clear();
                        get_Supply.play();
                        heroAircraft.setShootNum(4);
                        timer.scheduleTask(new Timer.Task() {
                            @Override
                            public void run() {
                                heroAircraft.setShootNum(1);
                            }
                        }, 10, 1);
                    } else if (prop instanceof PropBomb) {
                        prop.vanish();
                        bomb_Explosion.play();
                        for (Iterator<AbstractAircraft> iterator = enemyAircrafts.iterator(); iterator.hasNext(); ) {
                            AbstractAircraft item = iterator.next();
                            if (!(item instanceof BossEnemy)) {
                                item.vanish();
                            }
                        }

                        for (Iterator<AbstractBullet> iterator = enemyBullets.iterator(); iterator.hasNext(); ) {
                            AbstractBullet item = iterator.next();
                            item.vanish();
                        }
                    }
                }
            }
        }
    }


    private void objectMove() {
        for (AbstractAircraft enemy : enemyAircrafts) {
            enemy.forward();
        }
        for (AbstractBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (AbstractBullet bullet : enemyBullets) {
            bullet.forward();
        }
        for (AbstractFlyingObject prop : props) {
            prop.forward();
        }
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            float deltaX = heroAircraft.getLocationX() - (touchPos.x - heroAircraft.getWidth() / 2);
            float deltaY = heroAircraft.getLocationY() - (touchPos.y - heroAircraft.getHeight() / 2);
            if (Math.abs(deltaX) < heroAircraft.getWidth() / 4 && Math.abs(deltaY) < heroAircraft.getHeight() / 4) {
                heroAircraft.setLocation(touchPos.x - heroAircraft.getWidth() / 2, touchPos.y - heroAircraft.getWidth() / 2);
            } else {
                float speedXRate = (float) Math.sqrt(deltaX * deltaX / (deltaX * deltaX + deltaY * deltaY));
                float speedYRate = (float) Math.sqrt(deltaY * deltaY / (deltaX * deltaX + deltaY * deltaY));
                if (deltaX > 0) {
                    heroAircraft.setSpeedX(-speedXRate * heroSpeed);
                } else {
                    heroAircraft.setSpeedX(speedXRate * heroSpeed);
                }
                if (deltaY > 0) {
                    heroAircraft.setSpeedY(speedYRate * heroSpeed);
                } else {
                    heroAircraft.setSpeedY(-speedYRate * heroSpeed);
                }
                if (Math.abs(deltaX) > heroAircraft.getWidth() / 4 || Math.abs(deltaY) > heroAircraft.getHeight() / 4) {
                    heroAircraft.forward();

                }
            }

        }
    }

    private void propGeneration(AbstractAircraft aircraft) {
//        int i = MathUtils.random(0, 9);
//        if (i == 0) {
//            props.add(new PropBlood(
//                    aircraft.getLocationX(),
//                    aircraft.getLocationY(),
//                    0, 200));
//        } else if (i == 1) {
//            props.add(new PropBomb(
//                    aircraft.getLocationX(),
//                    aircraft.getLocationY(),
//                    0, 200));
//        } else if (i == 2) {
//            props.add(new PropBullet(
//                    aircraft.getLocationX(),
//                    aircraft.getLocationY(),
//                    0, 200));
//        }
        props.add(new PropBullet(
                aircraft.getLocationX(),
                aircraft.getLocationY(),
                0, 200));
    }
}