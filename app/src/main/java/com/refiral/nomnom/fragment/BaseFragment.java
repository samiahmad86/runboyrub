package com.refiral.nomnom.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.octo.android.robospice.SpiceManager;
import com.refiral.nomnom.listeners.FragmentInteractionListener;
import com.refiral.nomnom.service.APIService;

/**
 * Created by tanay on 7/8/15.
 */
public class BaseFragment extends Fragment {

    protected SpiceManager mSpiceManager = new SpiceManager(APIService.class);
    private static final String TAG = BaseFragment.class.getName();
    protected FragmentInteractionListener fil;

    @Override
    public void onStart() {
        super.onStart();
        mSpiceManager.start(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        mSpiceManager.shouldStop();
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

}
