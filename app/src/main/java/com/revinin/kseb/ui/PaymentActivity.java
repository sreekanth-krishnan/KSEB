package com.revinin.kseb.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.revinin.kseb.R;

public class PaymentActivity extends AppCompatActivity {

    public static final String EXTRA_AMOUNT = "amount";
    private Button mPayButton;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setup();

        mPayButton.setText("Pay " + getString(R.string.price, getIntent().getFloatExtra(EXTRA_AMOUNT, 0)));
        mPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                new Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                setResult(RESULT_OK);
                                finish();
                            }
                        },
                        1000
                );

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setup() {
        mPayButton = (Button)this.findViewById(R.id.button_pay);
    }


    private void showProgress() {
        dismissProgress();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Completing payment.");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }


    private void dismissProgress() {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }

            mProgressDialog = null;
        }
    }

}
