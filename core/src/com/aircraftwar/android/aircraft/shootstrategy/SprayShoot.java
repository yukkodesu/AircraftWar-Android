package com.aircraftwar.android.aircraft.shootstrategy;

import com.aircraftwar.android.aircraft.AbstractAircraft;
import com.aircraftwar.android.application.ImageManager;
import com.aircraftwar.android.bullet.AbstractBullet;
import com.aircraftwar.android.bullet.HeroBullet;
import com.badlogic.gdx.utils.Array;

public class SprayShoot implements ShootStrategy{

    private int direction = -1;
    private int bulletHeight;
    private int bulletWidth;
    @Override
    public Array<AbstractBullet> shoot(AbstractAircraft abstractAircraft) {
        bulletHeight = ImageManager.HERO_BULLET_IMAGE.getHeight();
        bulletWidth = ImageManager.HERO_BULLET_IMAGE.getWidth();
        Array<AbstractBullet> res = new Array<>();
        int shootnum = abstractAircraft.getShootNum();
        float x = abstractAircraft.getLocationX() + abstractAircraft.getWidth()/2- bulletWidth/2;
        float y = abstractAircraft.getLocationY() + abstractAircraft.getHeight()-bulletHeight;
        float speedX = 0;
        float speedY = 0;
        int speed = 100;
        AbstractBullet abstractBullet;
        if(shootnum % 2 == 1) {
            for (int i = 0; i < shootnum; i++) {
                if (i % 2 == 1) {
                    speedX += 30;
                }
                speedY = (float) (direction * Math.sqrt(speed * speed - speedX * speedX));
                abstractBullet = new HeroBullet(x,
                        y,
                        (float) (Math.pow(-1, i) * speedX),
                        speedY,
                        abstractAircraft.getPower());
                res.add(abstractBullet);
            }
        }
        else {
            speedX = 15F;
            for (int i = 0; i < shootnum; i++) {
                if (i % 2 == 0 && i > 1) {
                    speedX += 30;
                }
                speedY = (float) (direction * Math.sqrt(speed * speed - speedX * speedX));
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
