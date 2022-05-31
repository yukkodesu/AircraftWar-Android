package com.aircraftwar.android.aircraft;

import com.aircraftwar.android.aircraft.shootstrategy.ShootStrategy;
import com.aircraftwar.android.application.ImageManager;
import com.aircraftwar.android.basic.AbstractFlyingObject;
import com.aircraftwar.android.bullet.AbstractBullet;
import com.badlogic.gdx.utils.Array;


public abstract class AbstractAircraft extends AbstractFlyingObject {
    protected int maxHp;
    protected int hp;
    protected int shootNum;
    protected int power;
    protected ShootStrategy shootStrategy;

    public AbstractAircraft(float locationX, float locationY, float speedX, float speedY, int hp, ImageManager imageManager) {
        super(locationX, locationY, speedX, speedY, imageManager);
        this.hp = hp;
        this.maxHp = hp;
        this.shootNum = 1;
        this.power = 10;
    }

    public void decreaseHp(int decrease) {
        hp -= decrease;
        if (hp <= 0) {
            hp = 0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void increaseHp(int increase) {
        this.hp += increase;
    }

    public int getMaxHp() {
        return this.maxHp;
    }

    public void setShootNum(int shootNum) {
        this.shootNum = shootNum;
    }

    public int getShootNum() {
        return shootNum;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    public Array<AbstractBullet> shoot(){
        return shootStrategy.shoot(this);
    }


//    public abstract List<AbstractBullet> shoot();
}
