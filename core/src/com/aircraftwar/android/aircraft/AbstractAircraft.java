package com.aircraftwar.android.aircraft;

import com.aircraftwar.android.basic.AbstractFlyingObject;

public abstract class AbstractAircraft extends AbstractFlyingObject {
    protected int maxHp;
    protected int hp;

    public AbstractAircraft(float locationX, float locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
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


//    public abstract List<AbstractBullet> shoot();
}
