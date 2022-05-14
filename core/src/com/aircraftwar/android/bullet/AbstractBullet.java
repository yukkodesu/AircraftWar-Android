package com.aircraftwar.android.bullet;

import com.aircraftwar.android.aircraft.AbstractAircraft;
import com.aircraftwar.android.basic.AbstractFlyingObject;

public class AbstractBullet extends AbstractFlyingObject {

    private int power = 10;

    public AbstractBullet(float locationX, float locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY);
        this.power = power;
    }
}
