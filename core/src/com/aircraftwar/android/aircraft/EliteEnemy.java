package com.aircraftwar.android.aircraft;

import com.aircraftwar.android.aircraft.shootstrategy.EnemyShoot;
import com.aircraftwar.android.application.ImageManager;

public class EliteEnemy extends AbstractAircraft{
    public EliteEnemy(float locationX, float locationY, float speedX, float speedY, int hp, ImageManager imageManager) {
        super(locationX, locationY, speedX, speedY, hp, imageManager);
        shootStrategy = new EnemyShoot();
    }
}
