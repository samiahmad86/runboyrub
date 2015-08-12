package com.refiral.nomnom.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.refiral.nomnom.R;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.listeners.FragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomFragment extends BaseFragment implements View.OnClickListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CODE = "code";
    private int code;
    private FragmentInteractionListener fil;
    private static final String TAG = CustomFragment.class.getName();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param code tell the fragment the view to load.
     * @return A new instance of fragment CustomFragment.
     */
    public static CustomFragment newInstance(int code) {
        CustomFragment fragment = new CustomFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CODE, code);
        fragment.setArguments(args);
        return fragment;
    }

    public CustomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            code = getArguments().getInt(ARG_CODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment based on the instance variable code
        View view = null;
        switch (code) {
            case Constants.Values.STATUS_PLACEHOLDER : {
                view = inflater.inflate(R.layout.fragment_placeholder, container, false);
                break;
            }
            case Constants.Values.STATUS_CONFIRMED:
            case Constants.Values.STATUS_ARRIVED_AT_RESTAURANT: {
                view = inflater.inflate(R.layout.fragment_arrived_confirmed, container, false);

                break;
            }
            case Constants.Values.STATUS_PICKUP_MATCH: {
                view = inflater.inflate(R.layout.fragment_pickup_match, container, false);
                break;
            }
            case Constants.Values.STATUS_PICKUP_PAY: {
                view = inflater.inflate(R.layout.fragment_pickup_pay, container, false);
                break;
            }
            case Constants.Values.STATUS_PICKUP_PHOTO: {
                view = inflater.inflate(R.layout.fragment_pickup_photo, container, false);
                break;
            }
            case Constants.Values.STATUS_PICKUP_CONFIRM: {
                view = inflater.inflate(R.layout.fragment_pickup_confirm, container, false);
                break;
            }
            case Constants.Values.STATUS_REACHED_CUSTOMER: {
                view = inflater.inflate(R.layout.fragment_reached_customer, container, false);
                break;
            }
            case Constants.Values.STATUS_DELIVERED: {
                view = inflater.inflate(R.layout.fragment_delivered, container, false);
                break;
            }
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fil = (FragmentInteractionListener) activity;
        } catch (ClassCastException ex) {
            Log.d(TAG, "Your activity must implement FragmentInteractionListener");
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

        }
    }
}
