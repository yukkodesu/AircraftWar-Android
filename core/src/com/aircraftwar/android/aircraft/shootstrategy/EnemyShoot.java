package com.aircraftwar.android.aircraft.shootstrategy;

import com.aircraftwar.android.aircraft.AbstractAircraft;
import com.aircraftwar.android.bullet.AbstractBullet;
import com.aircraftwar.android.bullet.EnemyBullet;
import com.badlogic.gdx.utils.Array;

public class EnemyShoot implements ShootStrategy{
    private int direction = 1;
    @Override
    public Array<AbstractBullet> shoot(AbstractAircraft abstractAircraft) {
        Array<AbstractBullet> res = new Array<>();
        float x = abstractAircraft.getLocationX();
        float y = abstractAircraft.getLocationY() + direction*2;
        float speedX = 0;
        float speedY = abstractAircraft.getSpeedY() + direction*7;
        AbstractBullet abstractBullet;
        for(int i=0; i<abstractAircraft.getShootNum(); i++){
            if (i % 2 == 1) {
                speedX += 2;
            }
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            abstractBullet = new EnemyBullet(x , y, (float) (speedX*Math.pow(-1,i)), speedY, abstractAircraft.getPower());
            res.add(abstractBullet);
        }
        return res;
    }
}
