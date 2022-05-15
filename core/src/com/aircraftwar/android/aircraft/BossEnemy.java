package com.aircraftwar.android.aircraft;

import com.aircraftwar.android.aircraft.shootstrategy.EnemyShoot;

public class BossEnemy extends AbstractAircraft{
    public BossEnemy(float locationX, float locationY, float speedX, float speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        shootStrategy = new EnemyShoot();
    }
}
