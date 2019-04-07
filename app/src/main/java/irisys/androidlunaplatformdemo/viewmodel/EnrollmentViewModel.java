package irisys.androidlunaplatformdemo.viewmodel;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import irisys.androidlunaplatformdemo.contract.CameraContract;
import irisys.androidlunaplatformdemo.contract.EnrollViewContract;
import irisys.androidlunaplatformdemo.model.LunaService;
import irisys.androidlunaplatformdemo.view.LunaReposApplication;
import irisys.androidlunaplatformdemo.view.RGBCameraPreview;
import kr.co.irisys.kmodule.kmodule;

public class EnrollmentViewModel implements  kmodule.OnDetectListener, kmodule.OnExtractListener {

    private final String TAG = getClass().getName();

    private final EnrollViewContract enrollView;
    private final LunaService lunaService;
    private kmodule kmodue;

    private RGBCameraPreview rgbCameraPreview;

    private byte[] previewData;

    public EnrollmentViewModel(EnrollViewContract enrollView , LunaService lunaService , kmodule kmodule) {
        this.enrollView = enrollView;
        this.lunaService = lunaService;
        this.kmodue = kmodule;

        kmodue.setDetectListener(this);
        kmodue.setExtractListener(this);
    }

    public void setRgbCameraPreview(RGBCameraPreview rgbCameraPreview) {
        this.rgbCameraPreview = rgbCameraPreview;
        onCustomPreviewFrame();
    }

    public void onBackBtnClicked() {
        rgbCameraPreview.releaseCamera();
        enrollView.startMainActivity();
    }

    public void onCustomPreviewFrame(){
        rgbCameraPreview.setCameraContract(new CameraContract() {
            @Override
            public void onPreviewFrame(byte[] data) {
                kmodue.detect(data, false); //No More Fake
                previewData = data;
            }
        });
    }

    @Override
    public void OnDetect(boolean b, Rect rect) {
        if (b) {
            Log.d(TAG, "FACE DETECT ==> " + "rect top : " + rect.top + " rect bottom : " + rect.bottom);
        }
    }

    @Override
    public void OnExtract(boolean b, byte[] bytes) {
        if (b) {

            //PreviewData 를 Bitmap으로 만들기




//            resetTimer = 0;
//            isEnrollEnable = false;
//            /** 사진 저장 **/
//            faceCode = bytes;
//            mCamera.takePicture(null, null, jpegCallback);
        }
    }
}
