package com.revinin.kseb.ui.dummy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<RechargePackage> ITEMS = new ArrayList<RechargePackage>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, RechargePackage> ITEM_MAP = new HashMap<String, RechargePackage>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        /*for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }*/
    }

    private static void addItem(RechargePackage item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static RechargePackage createDummyItem(int position) {
        return new RechargePackage();//new RechrgePackage(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class RechargePackage implements Parcelable{
        public String id;
        public float units;
        private float price;
        private long validity;
        public RechargePackage() {
        }

        protected RechargePackage(Parcel in) {
            id = in.readString();
            units = in.readFloat();
            price = in.readFloat();
            validity = in.readLong();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeFloat(units);
            dest.writeFloat(price);
            dest.writeLong(validity);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<RechargePackage> CREATOR = new Creator<RechargePackage>() {
            @Override
            public RechargePackage createFromParcel(Parcel in) {
                return new RechargePackage(in);
            }

            @Override
            public RechargePackage[] newArray(int size) {
                return new RechargePackage[size];
            }
        };

        public float getUnits() {
            return units;
        }

        public String getId() {
            return id;
        }


        public long getValidity() {
            return validity;
        }

        public float getPrice() {
            return price;
        }

    }
}
