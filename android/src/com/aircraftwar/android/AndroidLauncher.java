package com.aircraftwar.android;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.aircraftwar.android.application.difficulty.Difficult;
import com.aircraftwar.android.application.difficulty.Normal;
import com.aircraftwar.android.application.difficulty.Simple;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.aircraftwar.android.application.MainGame;

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        int difficulty = getIntent().getIntExtra("Difficulty", 1);
        boolean isOnline = getIntent().getBooleanExtra("isOnline", false);
        boolean isAudioOn = getIntent().getBooleanExtra("isAudioOn", true);
        if (difficulty == 1) {
            initialize(new MainGame(new CommunicationImpl(this), new Simple(), isOnline, isAudioOn), config);
        } else if (difficulty == 2) {
            initialize(new MainGame(new CommunicationImpl(this), new Normal(), isOnline, isAudioOn), config);
        } else if (difficulty == 3) {
            initialize(new MainGame(new CommunicationImpl(this), new Difficult(), isOnline, isAudioOn), config);
        }
    }
}
