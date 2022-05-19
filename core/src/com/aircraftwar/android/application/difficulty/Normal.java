package com.aircraftwar.android.application.difficulty;

public class Normal extends Difficulty{
    public Normal() {
        enemyMaxNumber = 5;
        eliteRate = 1;
        heroShootGenDuration = 1000000000;
        eliteShootGenDuration = 700000000;
        bossShootGenDuration = 700000000;
        mobHp = 10;
        eliteHp = 30;
        bossHp = 400;
        mobSpeedY = 250;
        eliteSpeedY = 150;
    }
}
