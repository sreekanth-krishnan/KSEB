package com.revinin.kseb.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.revinin.kseb.R;
import com.revinin.kseb.util.PrefUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnHomeInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private OnHomeInteractionListener mListener;
    private View mAccountCard;
    private View mSigninCard;
    private BroadcastReceiver receiver;
    private TextView mLastRechargeView;
    private TextView mUnitsRemainingView;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateUI();
                updateVisibility();
            }
        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter(LoginActivity.ACTION_SIGNED_IN));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter(LoginActivity.ACTION_SIGNED_OUT));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter(RechargeActivity.ACTION_RECHARGE));
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setup(rootView);
        return rootView;
    }

    private void setup(View rootView) {
        mAccountCard = rootView.findViewById(R.id.account_card);
        mSigninCard = rootView.findViewById(R.id.sign_in_card);

        mLastRechargeView = (TextView) rootView.findViewById(R.id.text_last_recharge);
        mUnitsRemainingView = (TextView) rootView.findViewById(R.id.text_units_remaining);

        rootView.findViewById(R.id.button_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.signIn();
                }
            }
        });

        rootView.findViewById(R.id.button_recharge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.recharge();
                }
            }
        });

        updateVisibility();

        updateUI();
    }

    private void updateUI() {
        PrefUtil prefUtil = PrefUtil.getInstance(getContext());

        mLastRechargeView.setText(getString(R.string.price, prefUtil.getLastRecharge()));
        mUnitsRemainingView.setText(getString(R.string.units, prefUtil.getUnitsRemaining()));
    }

    private void updateVisibility() {
        boolean loggedIn = PrefUtil.getInstance(getContext()).isLoggedIn();
        mAccountCard.setVisibility(loggedIn ? View.VISIBLE : View.GONE);
        mSigninCard.setVisibility(!loggedIn ? View.VISIBLE : View.GONE);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.signIn();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeInteractionListener) {
            mListener = (OnHomeInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnHomeInteractionListener {
        // TODO: Update argument type and name
        void signIn();

        void recharge();
    }
}
