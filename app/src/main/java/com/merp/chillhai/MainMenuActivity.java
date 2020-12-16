package com.merp.chillhai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainMenuActivity extends AppCompatActivity {

    private static final String TAG="premDebug";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);
    }

    public void clickPlayWithFriendOffline(View view) {
        startActivity(new Intent(MainMenuActivity.this, InGameActivity.class));//screen(activity) change to the new activity

    }
    public void clickPlayWithComputer(View view) {
        startActivity(new Intent(MainMenuActivity.this, ComputerGameActivity.class));//screen(activity) change to the new activity

    }
    //below fn will take us to the login screen
    public void menuPlayOnlineClicked(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }
}