package com.aircraftwar.android.aircraft.shootstrategy;

import com.aircraftwar.android.aircraft.AbstractAircraft;
import com.aircraftwar.android.bullet.AbstractBullet;
import com.aircraftwar.android.bullet.HeroBullet;
import com.badlogic.gdx.utils.Array;

public class SprayShoot implements ShootStrategy{

    private int direction = -1;
    private AbstractBullet bulletTemplate;
    @Override
    public Array<AbstractBullet> shoot(AbstractAircraft abstractAircraft) {
        Array<AbstractBullet> res = new Array<>();
        bulletTemplate =new HeroBullet(0,0,0,0,0);
        int shootnum = abstractAircraft.getShootNum();
        float x = abstractAircraft.getLocationX() + abstractAircraft.getWidth()/2- bulletTemplate.getWidth()/2;
        float y = abstractAircraft.getLocationY() + abstractAircraft.getHeight()-bulletTemplate.getHeight();
        float speedX = 0;
        float speedY = 0;
        int speed = 100;
        AbstractBullet abstractBullet;
        if(shootnum % 2 == 1) {
            for (int i = 0; i < shootnum; i++) {
                if (i % 2 == 1) {
                    speedX += 1.5;
                }
                speedY = (float) (direction * Math.pow(speed * speed - speedX * speedX, 0.5));
                abstractBullet = new HeroBullet(x,
                        y,
                        (float) (Math.pow(-1, i) * speedX),
                        speedY,
                        abstractAircraft.getPower());
                res.add(abstractBullet);
            }
        }
        else {
            speedX = 0.75F;
            for (int i = 0; i < shootnum; i++) {
                if (i % 2 == 0 && i > 1) {
                    speedX += 1.5;
                }
                speedY = (float) (direction * Math.pow(speed * speed - speedX * speedX, 0.5));
                abstractBullet = new HeroBullet(x,
                        y,
                        (float) (Math.pow(-1, i) * speedX),
                        speedY,
                        abstractAircraft.getPower());
                res.add(abstractBullet);
            }
        }
        return res;
    }
}
