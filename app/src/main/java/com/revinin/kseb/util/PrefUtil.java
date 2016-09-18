package com.revinin.kseb.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by USER on 17-09-2016.
 */
public class PrefUtil {
    private static final String PREF = "pref";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String UNITS_REMAINING = "unitsRemaining";
    private static final String LAST_RECHARGE = "lastRecharge";
    private static PrefUtil ourInstance;
    private final SharedPreferences mSharedPref;
    private float unitsRemaining;
    private float lastRecharge;
    private boolean mIsLoggedIn;

    private PrefUtil(Context context) {
        mSharedPref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);

        mIsLoggedIn = mSharedPref.getBoolean(IS_LOGGED_IN, false);
        unitsRemaining = mSharedPref.getFloat(UNITS_REMAINING, 0);
        lastRecharge = mSharedPref.getFloat(LAST_RECHARGE, 0);
    }

    public static PrefUtil getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new PrefUtil(context);
        }
        return ourInstance;
    }

    public float getUnitsRemaining() {
        return unitsRemaining;
    }

    public void setUnitsRemaining(float unitsRemaining) {
        this.unitsRemaining = Math.max(unitsRemaining, 0);
        mSharedPref.edit().putFloat(UNITS_REMAINING, this.unitsRemaining).apply();
    }

    public float getLastRecharge() {
        return lastRecharge;
    }

    public void setLastRecharge(float lastRecharge) {
        this.lastRecharge = lastRecharge;
        mSharedPref.edit().putFloat(LAST_RECHARGE, lastRecharge).apply();
    }

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public void setIsLoggedIn(boolean mIsLoggedIn) {
        this.mIsLoggedIn = mIsLoggedIn;

        mSharedPref.edit().putBoolean(IS_LOGGED_IN, mIsLoggedIn).apply();
    }
}
