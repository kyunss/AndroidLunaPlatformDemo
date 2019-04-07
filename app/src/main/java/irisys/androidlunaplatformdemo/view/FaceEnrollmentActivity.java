package irisys.androidlunaplatformdemo.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.hardware.Camera;
import android.os.Bundle;

import irisys.androidlunaplatformdemo.R;
import irisys.androidlunaplatformdemo.contract.EnrollViewContract;
import irisys.androidlunaplatformdemo.databinding.ActivityFaceEnrollmentBinding;
import irisys.androidlunaplatformdemo.viewmodel.EnrollmentViewModel;


public class FaceEnrollmentActivity extends BaseActivity implements EnrollViewContract {

    private RGBCameraPreview rgbCameraPreview;
    private Camera mCamera;
    private EnrollmentViewModel enrollmentViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lunaService = ((LunaReposApplication)getApplication()).getLunaService();
        kmodule = ((LunaReposApplication)getApplication()).getKmodule(2);

        ActivityFaceEnrollmentBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_face_enrollment);
        enrollmentViewModel = new EnrollmentViewModel(this , lunaService , kmodule);
        binding.setViewModel(enrollmentViewModel);

        if (!checkCameraHardware(this)) {
            BaseDialog.Builder noCameraDialog = new BaseDialog.Builder(this);
            noCameraDialog.setIcon(R.drawable.mini_picto_failure)
                    .setCanceledOnKey(false)
                    .setCanceledOnTouchOutside(false)
                    .setMessage(R.string.camera_undetected);

        }

        rgbCameraPreview = new RGBCameraPreview(FaceEnrollmentActivity.this);
        enrollmentViewModel.setRgbCameraPreview(rgbCameraPreview);
        binding.layoutEnrollPreview.addView(rgbCameraPreview);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        rgbCameraPreview.releaseCamera();
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(FaceEnrollmentActivity.this, MainActivity.class));
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
}
