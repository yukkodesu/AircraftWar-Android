package com.aircraftwar.android.basic;

import com.aircraftwar.android.application.MainGame;
import com.aircraftwar.android.application.ImageManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.sun.tools.javac.Main;

public abstract class AbstractFlyingObject {
    protected int height = -1;
    protected int width = -1;
    protected float speedX;
    protected float speedY;
    protected Texture image = null;
    protected Rectangle rigidObject = null;
    protected boolean isValid = true;
    protected ImageManager imageManager;


    public AbstractFlyingObject(float locationX, float locationY, float speedX, float speedY,ImageManager imageManager) {
        this.speedX = speedX;
        this.speedY = speedY;
        this.imageManager = imageManager;
        getImage();
        getWidth();
        getHeight();
        this.rigidObject = new Rectangle(locationX, locationY, this.width, this.height);
    }

    public boolean crash(AbstractFlyingObject flyingObject) {
        return this.rigidObject.overlaps(flyingObject.rigidObject);
    }

    public void forward() {
        this.rigidObject.x += speedX * Gdx.graphics.getDeltaTime();
        this.rigidObject.y -= speedY * Gdx.graphics.getDeltaTime();
        if (this.rigidObject.x + this.rigidObject.width/2 <= 0 || this.rigidObject.x + this.rigidObject.width/2 >= MainGame.viewportWidth) {
            speedX = -speedX;
        }
        if(this.rigidObject.y + height < 0) {
            vanish();
        }
    }

    public void setLocation(float locationX, float locationY) {
        this.rigidObject.x = locationX;
        this.rigidObject.y = locationY;
    }

    public float getLocationX() {
        return this.rigidObject.x;
    }

    public float getLocationY() {
        return this.rigidObject.y;
    }

    public float getSpeedY() {
        return speedY;
    }

    public boolean notValid() {
        return !this.isValid;
    }

    public void vanish() {
        isValid = false;
    }

    public Texture getImage() {
        if (image == null) {
            image = imageManager.get(this);
        }
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    public int getWidth() {
        if (width == -1) {
            width = imageManager.get(this).getWidth();
        }
        return width;
    }


    public int getHeight() {
        if (height == -1) {
            height = imageManager.get(this).getHeight();
        }
        return height;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
}
