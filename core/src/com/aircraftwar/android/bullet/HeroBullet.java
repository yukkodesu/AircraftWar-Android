package com.aircraftwar.android.bullet;

import com.aircraftwar.android.application.ImageManager;

public class HeroBullet extends AbstractBullet{

    public HeroBullet(float locationX, float locationY, float speedX, float speedY, int power, ImageManager imageManager) {
        super(locationX, locationY, speedX, speedY, power,imageManager);
    }
}
