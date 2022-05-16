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
    private static final Map<String, Texture> CLASSNAME_IMAGE_MAP = new HashMap<>();
    public static Texture HERO_IMAGE = new Texture("hero.png");
    public static Texture HERO_BULLET_IMAGE = new Texture("bullet_hero.png");
    public static Texture ENEMY_BULLET_IMAGE = new Texture("bullet_enemy.png");
    public static Texture MOB_ENEMY_IMAGE = new Texture("mob.png");
    public static Texture BOSS_ENEMY_IMAGE = new Texture("boss.png");
    public static Texture ELITE_ENEMY_IMAGE = new Texture("elite.png");
    public static Texture BOMB_DROP_IMAGE = new Texture("prop_bomb.png");
    public static Texture BLOOD_DROP_IMAGE = new Texture("prop_blood.png");
    public static Texture BULLET_DROP_IMAGE = new Texture("prop_bullet.png");
    public static Texture BACKGROUND_IMAGE1 = new Texture("bg.jpg");
    public static Texture BACKGROUND_IMAGE2 = new Texture("bg2.jpg");
    public static Texture BACKGROUND_IMAGE3 = new Texture("bg3.jpg");
    public static Texture BACKGROUND_IMAGE4 = new Texture("bg4.jpg");
    public static Texture BACKGROUND_IMAGE5 = new Texture("bg5.jpg");
    public static Texture BACKGROUND_IMAGE = BACKGROUND_IMAGE1;

    static {
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

    public static Texture get(String className) {
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static Texture get(Object obj) {
        if (obj == null) {
            return null;
        }
        return get(obj.getClass().getName());
    }

    public static void setBackgroundImage(Texture backgroundImage) {
        synchronized (BACKGROUND_IMAGE) {
            BACKGROUND_IMAGE = backgroundImage;
        }
    }
}