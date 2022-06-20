package com.aircraftwar.android.aircraft;

import com.aircraftwar.android.aircraft.shootstrategy.SprayShoot;
import com.aircraftwar.android.application.ImageManager;
import com.aircraftwar.android.bullet.AbstractBullet;
import com.badlogic.gdx.utils.Array;

public class HeroAircraft extends AbstractAircraft{
    public HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, ImageManager imageManager) {
        super(locationX, locationY, speedX, speedY, hp, imageManager);
        this.shootStrategy = new SprayShoot();
    }

}
