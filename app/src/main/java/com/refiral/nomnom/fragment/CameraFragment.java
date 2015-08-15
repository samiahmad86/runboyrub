package com.refiral.nomnom.fragment;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.refiral.nomnom.R;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.util.DeviceUtils;
import com.refiral.nomnom.util.PrefUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by tanay on 13/8/15.
 */
public class CameraFragment extends BaseFragment implements SurfaceHolder.Callback, View.OnClickListener, Camera.PictureCallback {

    private Camera mCamera;
    private SurfaceView svCamera;
    private SurfaceHolder shCamera;
    private static final String TAG = CameraFragment.class.getName();

    /*
        Factory method to create instance of fragment
     */
    public static CameraFragment newInstance() {
        CameraFragment cf = new CameraFragment();
        return cf;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pickup_photo, container, false);
        svCamera = (SurfaceView) view.findViewById(R.id.sv_camera);
        shCamera = svCamera.getHolder();
        shCamera.addCallback(this);
        view.findViewById(R.id.iv_bada_gola).setOnClickListener(this);
        return view;
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            mCamera = Camera.open();
        } catch (RuntimeException ex) {
            Log.d(TAG, "cannot open camera" + ex.getMessage());
            ex.printStackTrace();
        }
        Camera.Parameters parameters = mCamera.getParameters();

        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);

        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
        Camera.Size size = DeviceUtils.getOptimalSize(sizeList, screenSize.x, screenSize.y);


        parameters.setPreviewSize(size.width, size.height);

        if(display.getRotation() == Surface.ROTATION_0)
        {
            mCamera.setDisplayOrientation(90);
            parameters.setRotation(90);
        }
        if(display.getRotation() == Surface.ROTATION_270)
        {
            mCamera.setDisplayOrientation(180);
            parameters.setRotation(180);
        }

        mCamera.setParameters(parameters);

        try {
            mCamera.setPreviewDisplay(shCamera);
            mCamera.startPreview();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    private void refreshCamera() {
        if (shCamera.getSurface() == null) {
            return;
        }
        try {
            mCamera.stopPreview();
            mCamera.setPreviewDisplay(shCamera);
            mCamera.startPreview();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_bada_gola: {
                mCamera.takePicture(null, null, this);
                break;
            }

        }
    }

    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {
        File billFolder = new File(Environment.getExternalStorageDirectory().toString(), "temp");
        billFolder.mkdirs();
        String billFile = "bill_" + System.currentTimeMillis() + ".jpg";
        File file = new File(billFolder, billFile);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file.getPath());
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(file.exists()) {
            PrefUtils.setStatus(Constants.Values.STATUS_PICKUP_CONFIRM);
            PrefUtils.setBillPhoto(file.getAbsolutePath());
            fil.onFragmentInteraction(Constants.Values.STATUS_PICKUP_CONFIRM, null);
        } else {
            refreshCamera();
        }
    }
}
