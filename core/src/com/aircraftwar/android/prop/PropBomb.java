package com.aircraftwar.android.prop;

import com.aircraftwar.android.application.ImageManager;
import com.aircraftwar.android.basic.AbstractFlyingObject;

public class PropBomb extends AbstractFlyingObject {
    public PropBomb(float locationX, float locationY, float speedX, float speedY, ImageManager imageManager) {
        super(locationX, locationY, speedX, speedY, imageManager);
    }
}
