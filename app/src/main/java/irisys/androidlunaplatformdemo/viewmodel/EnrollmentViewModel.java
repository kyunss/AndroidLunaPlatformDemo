package irisys.androidlunaplatformdemo.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.location.Location;
import android.util.Log;

import java.util.Locale;

import irisys.androidlunaplatformdemo.contract.CameraContract;
import irisys.androidlunaplatformdemo.contract.EnrollViewContract;
import irisys.androidlunaplatformdemo.model.LunaService;
import irisys.androidlunaplatformdemo.view.LunaReposApplication;
import irisys.androidlunaplatformdemo.view.RGBCameraPreview;
import kr.co.irisys.kmodule.kmodule;
import rx.schedulers.Schedulers;

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
            Bitmap bitmap = BitmapFactory.decodeByteArray(previewData , 0, previewData.length);
            lunaService.createPerson();


            Person person = new Person();

            final Location location = locationHelper.getLastKnowLocation();
            if (location != null) {
                final String latLong = String.format(Locale.ENGLISH, "%s,%s", location.getLatitude(), location.getLongitude());
                person.setPlaceOfBirth(latLong);
            }

            person.setIdentification(registrationModel.email + "; " + DeviceInfoHelper.getDeviceName() + "; " + DeviceInfoHelper.getDeviceOSVersion());
            person.setFirstName(registrationModel.login);
            person.setPhone(registrationModel.phone);

            request = new CreatePersonRequest();
            request.user_data = new String("_@login@_:" + registrationModel.login);
            Log.i("LUNA2_DATA","user_data field is"+request.user_data);

            api.createPersonLuna2(server + "/"+storage.APIVersion+"/storage/persons", EncodedUserData, request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onCreatePersonSuccess,this::onCommonFail);



//            resetTimer = 0;
//            isEnrollEnable = false;
//            /** 사진 저장 **/
//            faceCode = bytes;
//            mCamera.takePicture(null, null, jpegCallback);
        }
    }
}
