package com.aircraftwar.android.bullet;

import com.aircraftwar.android.aircraft.AbstractAircraft;
import com.aircraftwar.android.application.ImageManager;
import com.aircraftwar.android.application.MainGame;
import com.aircraftwar.android.basic.AbstractFlyingObject;
import com.badlogic.gdx.Gdx;

public class AbstractBullet extends AbstractFlyingObject {

    private int power = 10;

    public AbstractBullet(float locationX, float locationY, float speedX, float speedY, int power, ImageManager imageManager) {
        super(locationX, locationY, speedX, speedY,imageManager);
        this.power = power;
    }

    @Override
    public void forward() {
        this.rigidObject.x += speedX * Gdx.graphics.getDeltaTime();
        this.rigidObject.y -= speedY * Gdx.graphics.getDeltaTime();
        if (this.rigidObject.y + height < 0 ||this.rigidObject.y > MainGame.viewportHeight || this.rigidObject.x + width < 0 || this.rigidObject.x + width / 2 > MainGame.viewportWidth) {
            vanish();
        }
    }


    public int getPower() {
        return power;
    }
}
