package com.revinin.kseb.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.revinin.kseb.R;
import com.revinin.kseb.ui.dummy.DummyContent;
import com.revinin.kseb.util.PrefUtil;

public class RechargeActivity extends AppCompatActivity {

    public static final String ACTION_RECHARGE = "recharge";
    public static final String EXTRA_PACKAGE = "package";
    private static final int RC_PAY = 1;
    private TextView mUnitsView;
    private TextView mValidityView;
    private TextView mAmountView;
    private DummyContent.RechargePackage mRechargePackage;
    private TextInputLayout mConsumerNumberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();

        updateUI();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void updateUI() {
        mRechargePackage = getIntent().getParcelableExtra(EXTRA_PACKAGE);
        mUnitsView.setText(getString(R.string.units, mRechargePackage.getUnits()));
        mValidityView.setText(Long.toString(mRechargePackage.getValidity()) + " days");
        mAmountView.setText(getString(R.string.price, mRechargePackage.getPrice()));
        mValidityView.setVisibility(mRechargePackage.getValidity() == 0 ? View.INVISIBLE : View.VISIBLE);

        if (PrefUtil.getInstance(this).isLoggedIn()) {
            mConsumerNumberView.getEditText().setText("1234");
        }
    }

    private void setup() {
        mUnitsView = (TextView) this.findViewById(R.id.text_units);
        mValidityView = (TextView) this.findViewById(R.id.text_validity);
        mAmountView = (TextView) this.findViewById(R.id.text_amount);

        mConsumerNumberView = (TextInputLayout) this.findViewById(R.id.input_consumer_number);

        this.findViewById(R.id.button_proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay();
            }
        });
    }

    private void pay() {
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PaymentActivity.EXTRA_AMOUNT, mRechargePackage.getPrice());
        this.startActivityForResult(intent, RC_PAY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_PAY:
                if(resultCode == RESULT_OK){

                    float balance = PrefUtil.getInstance(this).getUnitsRemaining() + mRechargePackage.getUnits();
                    PrefUtil.getInstance(this).setUnitsRemaining(balance);
                    PrefUtil.getInstance(this).setLastRecharge(mRechargePackage.getPrice());

                    new AlertDialog.Builder(this)
                            .setTitle("Recharge successful!")
                            .setMessage(mRechargePackage.getUnits() + " units added to your balance.")
                            .setPositiveButton("OK", null)
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    rechargeSuccess();
                                }
                            })
                            .show();

                }else{
                    new AlertDialog.Builder(this)
                            .setTitle("Payment failed or cancelled.")
                            .setMessage("Please try again later.")
                            .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    pay();
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
                break;
            default:
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void rechargeSuccess() {
        setResult(RESULT_OK);
        Intent result = new Intent();
        result.putExtra(EXTRA_PACKAGE, mRechargePackage);
        finish();
    }
}
