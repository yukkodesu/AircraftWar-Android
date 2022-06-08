package com.aircraftwar.android.aircraft.shootstrategy;

import com.aircraftwar.android.aircraft.AbstractAircraft;
import com.aircraftwar.android.bullet.AbstractBullet;
import com.badlogic.gdx.utils.Array;

public interface ShootStrategy {
    Array<AbstractBullet> shoot(AbstractAircraft abstractAircraft);
}
