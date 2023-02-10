package com.aircraftwar.android.application.difficulty;

public class Simple extends Difficulty{
    public Simple() {
        enemyMaxNumber = 5;
        eliteRate = 1;
        heroShootGenDuration = 500000000;
        eliteShootGenDuration = 1000000000;
        bossShootGenDuration = 500000000;
        mobHp = 10;
        eliteHp = 20;
        bossHp = 200;
        mobSpeedY = 200;
        eliteSpeedY = 100;
    }
}
