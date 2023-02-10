package com.aircraftwar.android.application;

import com.aircraftwar.android.aircraft.BossEnemy;
import com.aircraftwar.android.aircraft.EliteEnemy;
import com.aircraftwar.android.aircraft.HeroAircraft;
import com.aircraftwar.android.aircraft.MobEnemy;
import com.aircraftwar.android.bullet.EnemyBullet;
import com.aircraftwar.android.bullet.HeroBullet;
import com.aircraftwar.android.prop.PropBlood;
import com.aircraftwar.android.prop.PropBomb;
import com.aircraftwar.android.prop.PropBullet;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class ImageManager {
    private final Map<String, Texture> CLASSNAME_IMAGE_MAP;
    public Texture HERO_IMAGE;
    public Texture HERO_BULLET_IMAGE;
    public Texture ENEMY_BULLET_IMAGE;
    public Texture MOB_ENEMY_IMAGE;
    public Texture BOSS_ENEMY_IMAGE;
    public Texture ELITE_ENEMY_IMAGE;
    public Texture BOMB_DROP_IMAGE;
    public Texture BLOOD_DROP_IMAGE;
    public Texture BULLET_DROP_IMAGE;
    public Texture BACKGROUND_IMAGE1;
    public Texture BACKGROUND_IMAGE2;
    public Texture BACKGROUND_IMAGE3;
    public Texture BACKGROUND_IMAGE4;
    public Texture BACKGROUND_IMAGE5;
    public Texture BACKGROUND_IMAGE;

    public ImageManager() {
        CLASSNAME_IMAGE_MAP = new HashMap<>();
        HERO_IMAGE = new Texture("images/hero.png");
        HERO_BULLET_IMAGE = new Texture("images/bullet_hero.png");
        ENEMY_BULLET_IMAGE = new Texture("images/bullet_enemy.png");
        MOB_ENEMY_IMAGE = new Texture("images/mob.png");
        BOSS_ENEMY_IMAGE = new Texture("images/boss.png");
        ELITE_ENEMY_IMAGE = new Texture("images/elite.png");
        BOMB_DROP_IMAGE = new Texture("images/prop_bomb.png");
        BLOOD_DROP_IMAGE = new Texture("images/prop_blood.png");
        BULLET_DROP_IMAGE = new Texture("images/prop_bullet.png");
        BACKGROUND_IMAGE1 = new Texture("images/bg.jpg");
        BACKGROUND_IMAGE2 = new Texture("images/bg2.jpg");
        BACKGROUND_IMAGE3 = new Texture("images/bg3.jpg");
        BACKGROUND_IMAGE4 = new Texture("images/bg4.jpg");
        BACKGROUND_IMAGE5 = new Texture("images/bg5.jpg");
        BACKGROUND_IMAGE = BACKGROUND_IMAGE1;
        CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
        CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(PropBomb.class.getName(), BOMB_DROP_IMAGE);
        CLASSNAME_IMAGE_MAP.put(PropBullet.class.getName(), BULLET_DROP_IMAGE);
        CLASSNAME_IMAGE_MAP.put(PropBlood.class.getName(), BLOOD_DROP_IMAGE);
    }

    public Texture get(String className) {
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public Texture get(Object obj) {
        if (obj == null) {
            return null;
        }
        return get(obj.getClass().getName());
    }

    public void setBackgroundImage(Texture backgroundImage) {
        synchronized (BACKGROUND_IMAGE) {
            BACKGROUND_IMAGE = backgroundImage;
        }
    }

    public void dipose() {
        HERO_IMAGE.dispose();
        HERO_BULLET_IMAGE.dispose();
        ENEMY_BULLET_IMAGE.dispose();
        MOB_ENEMY_IMAGE.dispose();
        BOSS_ENEMY_IMAGE.dispose();
        ELITE_ENEMY_IMAGE.dispose();
        BOMB_DROP_IMAGE.dispose();
        BLOOD_DROP_IMAGE.dispose();
        BULLET_DROP_IMAGE.dispose();
        BACKGROUND_IMAGE1.dispose();
        BACKGROUND_IMAGE2.dispose();
        BACKGROUND_IMAGE3.dispose();
        BACKGROUND_IMAGE4.dispose();
        BACKGROUND_IMAGE5.dispose();
        BACKGROUND_IMAGE.dispose();
    }
}