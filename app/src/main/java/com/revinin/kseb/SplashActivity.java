package com.revinin.kseb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.revinin.kseb.ui.LoginActivity;
import com.revinin.kseb.ui.MainActivity;
import com.revinin.kseb.util.PrefUtil;

public class SplashActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PrefUtil.getInstance(this).isLoggedIn()) {
            showMainActivity();
        } else {
            this.startActivityForResult(new Intent(this, LoginActivity.class), RC_SIGN_IN);
        }
    }

    private void showMainActivity() {
        this.startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_SIGN_IN:
                if(resultCode == RESULT_OK) {
                    showMainActivity();
                }else{
                    this.finish();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
