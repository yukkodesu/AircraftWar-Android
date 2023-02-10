package com.aircraftwar.android.aircraft;

import com.aircraftwar.android.application.ImageManager;
import com.aircraftwar.android.bullet.AbstractBullet;
import com.badlogic.gdx.utils.Array;

public class MobEnemy extends AbstractAircraft{
    public MobEnemy(float locationX, float locationY, int speedX, int speedY, int hp, ImageManager imageManager) {
        super(locationX, locationY, speedX, speedY, hp, imageManager);
    }


}
