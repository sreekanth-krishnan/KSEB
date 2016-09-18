package com.revinin.kseb.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.revinin.kseb.ui.RechargeActivity;
import com.revinin.kseb.util.PrefUtil;

public class UsageUpdateReceiver extends BroadcastReceiver {
    private static final String TAG = "UsageUpdateReceiver";

    public UsageUpdateReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        double units = intent.getDoubleExtra("units", 0);

        float unitsRemaining = PrefUtil.getInstance(context).getUnitsRemaining();
        unitsRemaining -= units;
        PrefUtil.getInstance(context).setUnitsRemaining(unitsRemaining);
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(RechargeActivity.ACTION_RECHARGE));

        Log.d(TAG, "onReceive: " + units);
    }
}
