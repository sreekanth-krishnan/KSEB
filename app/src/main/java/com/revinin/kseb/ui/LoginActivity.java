package com.revinin.kseb.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.revinin.kseb.R;
import com.revinin.kseb.util.PrefUtil;

public class LoginActivity extends AppCompatActivity {

    public static final String ACTION_SIGNED_IN = "signedIn";
    public static final String ACTION_SIGNED_OUT = "signedOut";
    private TextInputLayout mConsumerNumberInput;
    private TextInputLayout mConsumerPINInput;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setup();
    }

    private void setup() {
        mConsumerNumberInput = (TextInputLayout) this.findViewById(R.id.input_consumer_number);
        mConsumerPINInput = (TextInputLayout) this.findViewById(R.id.input_consumer_pin);

        this.findViewById(R.id.button_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignIn();
            }
        });
    }

    private void attemptSignIn() {

        String consumerName = mConsumerNumberInput.getEditText().getText().toString();
        if (consumerName.isEmpty()) {
            mConsumerNumberInput.setError("Required!");
            mConsumerNumberInput.requestFocus();
            return;
        }

        mConsumerNumberInput.setError(null);
        mConsumerNumberInput.setErrorEnabled(false);

        String pin = mConsumerPINInput.getEditText().getText().toString();
        if (pin.isEmpty()) {
            mConsumerPINInput.setError("Required!");
            mConsumerPINInput.requestFocus();
            return;
        }

        mConsumerPINInput.setError(null);
        mConsumerPINInput.setErrorEnabled(false);

        signIn(consumerName, pin);


    }

    private void signIn(final String consumerName, final String pin) {
        final AsyncTask<Void, Void, Boolean> asyncTask = new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                return (consumerName.equals("1234") && pin.equals("1212"));
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                onSignInResult(aBoolean);
            }
        };

        showProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                asyncTask.execute();
            }
        }, 1000);
    }

    private void showProgress() {
        dismissProgress();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Signing you in.");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void onSignInResult(Boolean signedIn) {
        dismissProgress();

        if (!signedIn) {
            mConsumerPINInput.setError("Invalid consumer number or PIN!");
            mConsumerPINInput.requestFocus();
            return;
        }

        PrefUtil.getInstance(this).setIsLoggedIn(true);

        Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();

        this.setResult(RESULT_OK);
        this.finish();
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
