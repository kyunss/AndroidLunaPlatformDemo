package irisys.androidlunaplatformdemo.viewmodel;

import irisys.androidlunaplatformdemo.contract.MainViewContract;

public class MainViewModel  {

    private final MainViewContract mainView;

    public MainViewModel(MainViewContract mainView) {
        this.mainView = mainView;
    }

    public void displayDate(){

    }

    public void displayTime(){

    }

    public void onEnrollmentBtnClicked() {
        mainView.startFaceEnrollmentActivity();
    }

    public void onRecognitionBtnClicked() {
        mainView.startFaceRecognitionActivity();
    }
}
