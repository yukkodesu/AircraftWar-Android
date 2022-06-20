package com.aircraftwar.android.application.difficulty;

public abstract class Difficulty {
    public int enemyMaxNumber = 5;
    public int eliteRate = 1;
    public long enemyGenDuration = 1000000000;
    public long heroShootGenDuration = 500000000;
    public long eliteShootGenDuration = 500000000;
    public long bossShootGenDuration = 500000000;
    public int mobHp = 10;
    public int eliteHp = 20;
    public int bossHp = 200;
    public int mobSpeedY = 200;
    public int eliteSpeedY = 100;

    public Difficulty(){}
}

