package irisys.androidlunaplatformdemo.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import irisys.androidlunaplatformdemo.R;
import irisys.androidlunaplatformdemo.contract.MainViewContract;
import irisys.androidlunaplatformdemo.databinding.ActivityMainBinding;
import irisys.androidlunaplatformdemo.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity implements MainViewContract {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(new MainViewModel(this));
    }

    @Override
    public void startFaceEnrollmentActivity() {
        startActivity(new Intent(MainActivity.this , FaceEnrollmentActivity.class));
//        startActivity(new Intent(MainActivity.this , FaceEnroll.class));
    }

    @Override
    public void startFaceRecognitionActivity() {
        startActivity(new Intent(MainActivity.this , FaceRecognitionActivity.class));
    }
}
