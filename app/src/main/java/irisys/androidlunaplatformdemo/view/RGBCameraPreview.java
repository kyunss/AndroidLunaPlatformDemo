package irisys.androidlunaplatformdemo.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import irisys.androidlunaplatformdemo.contract.CameraContract;
import irisys.androidlunaplatformdemo.viewmodel.EnrollmentViewModel;

public class RGBCameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private final String TAG = getClass().getName();
    private Context mContext;
    private SurfaceHolder mHoler;
    private Camera mCamera;
    private CameraContract cameraContract;


    public RGBCameraPreview(Context context) {
        super(context);
        mContext = context;


        mHoler = getHolder();
        mHoler.addCallback(this);
        mHoler.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            Log.d(TAG, "surfaceCreated");
            createCameraInstance();
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(640,480);
            mCamera.setParameters(parameters);
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error Setting Camera Preview : " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d(TAG, "surfaceChanged");

        //Preview surface does not exit
        if (mHoler.getSurface() == null)
            return;

        //Start Preview with new Setting
        try {
            mCamera.setPreviewDisplay(mHoler);
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        Log.d(TAG , "" + bytes.length);
        cameraContract.onPreviewFrame(bytes);
    }

    public void setCameraContract(CameraContract cameraContract) {
        this.cameraContract = cameraContract;
    }

    public boolean checkCameraHardware() {
        if (mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            return true;
        else
            return false;
    }

    public void createCameraInstance(){
        final int rgbCameraID = 0;
        try {
            mCamera = Camera.open(rgbCameraID);
        } catch (Exception e) {

        }
    }

    public void releaseCamera() {
        if (mCamera != null) {
            try {

                mCamera.stopPreview();
                mCamera.setPreviewDisplay(null);
                mCamera.release();
                mCamera = null;
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
