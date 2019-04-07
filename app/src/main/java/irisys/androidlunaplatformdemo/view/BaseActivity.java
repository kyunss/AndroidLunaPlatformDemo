package irisys.androidlunaplatformdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import irisys.androidlunaplatformdemo.model.LunaService;
import kr.co.irisys.kmodule.kmodule;

public class BaseActivity extends AppCompatActivity {

    protected LunaService lunaService;
    protected kmodule kmodule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }
}
