package com.revinin.kseb.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.revinin.kseb.R;
import com.revinin.kseb.ui.dummy.DummyContent.RechargePackage;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link RechargePackage} and makes a call to the
 * specified {@link com.revinin.kseb.ui.RechargePackagesFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RechargePackagesAdapter extends RecyclerView.Adapter<RechargePackagesAdapter.ViewHolder> {

    private final List<RechargePackage> mValues;
    private final RechargePackagesFragment.OnListFragmentInteractionListener mListener;

    public RechargePackagesAdapter(List<RechargePackage> items, RechargePackagesFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recharge_package, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Context context = holder.mAmountView.getContext();
        holder.mItem = mValues.get(position);
        holder.mUnitsView.setText(context.getString(R.string.units, mValues.get(position).getUnits()));
        holder.mValidityView.setText(Long.toString(mValues.get(position).getValidity()) + " days");
        holder.mAmountView.setText(context.getString(R.string.price, mValues.get(position).getPrice()));
        holder.mValidityView.setVisibility(mValues.get(position).getValidity() == 0 ? View.INVISIBLE : View.VISIBLE);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mUnitsView;
        public final TextView mValidityView;
        public final TextView mAmountView;
        public RechargePackage mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUnitsView = (TextView) view.findViewById(R.id.text_units);
            mValidityView = (TextView) view.findViewById(R.id.text_validity);
            mAmountView = (TextView) view.findViewById(R.id.text_amount);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mValidityView.getText() + "'";
        }
    }
}
