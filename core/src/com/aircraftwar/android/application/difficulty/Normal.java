package com.aircraftwar.android.application.difficulty;

public class Normal extends Difficulty{
    public Normal() {
        enemyMaxNumber = 5;
        eliteRate = 1;
        heroShootGenDuration = 500000000;
        eliteShootGenDuration = 500000000;
        bossShootGenDuration = 500000000;
        mobHp = 10;
        eliteHp = 30;
        bossHp = 400;
        mobSpeedY = 250;
        eliteSpeedY = 150;
    }
}
