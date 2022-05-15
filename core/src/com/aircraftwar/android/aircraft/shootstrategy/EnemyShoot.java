package com.aircraftwar.android.aircraft.shootstrategy;

import com.aircraftwar.android.aircraft.AbstractAircraft;
import com.aircraftwar.android.bullet.AbstractBullet;
import com.aircraftwar.android.bullet.EnemyBullet;
import com.aircraftwar.android.bullet.HeroBullet;
import com.badlogic.gdx.utils.Array;

public class EnemyShoot implements ShootStrategy{
    private int direction = 1;
    private EnemyBullet bulletTemplate;
    @Override
    public Array<AbstractBullet> shoot(AbstractAircraft abstractAircraft) {
        Array<AbstractBullet> res = new Array<>();
        bulletTemplate = new EnemyBullet(0,0,0,0,0);
        float x = abstractAircraft.getLocationX() + abstractAircraft.getWidth()/2 - bulletTemplate.getWidth()/2;
        float y = abstractAircraft.getLocationY() + abstractAircraft.getHeight()/2;
        float speedX = 0;
        float speedY = 250;
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
