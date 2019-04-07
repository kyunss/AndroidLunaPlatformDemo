package irisys.androidlunaplatformdemo.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import irisys.androidlunaplatformdemo.R;


public class BaseDialog extends Dialog {
    public BaseDialog(Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public static class Builder {
        private Context context = null;
        private Drawable icon = null;
        private String message = null;
        private int wait = -1;
        private boolean cancelable_key = true;
        private boolean cancelable_touch_outside = true;

        private DialogInterface.OnClickListener btnClickListener;
        private DialogInterface.OnClickListener btnWaitTimeListener;

        public Builder(Context context) { this.context = context;}

        public BaseDialog.Builder setMessage(String msg) {
            this.message = msg;
            return this;
        }
        public Builder setMessage(int msg_id) {
            this.message = (String) context.getText(msg_id);
            return this;
        }

        public BaseDialog.Builder setCanceledOnKey(boolean flag) {
            this.cancelable_key = flag;
            return this;
        }

        public BaseDialog.Builder setWaitTime(int value, DialogInterface.OnClickListener listener) {
            this.wait = value;
            this.btnWaitTimeListener = listener;
            return this;
        }

        public BaseDialog.Builder setCanceledOnTouchOutside(boolean flag) {
            this.cancelable_touch_outside = flag;
            return this;
        }

        public BaseDialog.Builder setClick(DialogInterface.OnClickListener listener) {
            this.btnClickListener = listener;
            return this;
        }

        public BaseDialog.Builder setIcon(Drawable icon) {
            this.icon = icon;
            return this;
        }

        public Builder setIcon(int icon) {
            this.icon = context.getResources().getDrawable(icon);
            return this;
        }

        public BaseDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final BaseDialog dialog = new BaseDialog(context);
            final View layout = inflater.inflate(R.layout.base_dialog, null);
            dialog.addContentView(layout,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            // * 팝업View 지속시간
            //   - setWaitTime(시간)을 호출하면 지정'시간'후에 dismiss()  ( 1000 = 1초 )
            //   - 호출안하면 팝업View 상태 유지
            if(wait > 0) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (btnWaitTimeListener != null) {
                            btnWaitTimeListener.onClick(dialog, 0);
                        }
                    }
                }, wait);
            }


            // Dialog외부 터치시 닫기
            dialog.setCanceledOnTouchOutside(cancelable_touch_outside); // true : 닫기,  false : Dialog유지

            // Icon
            if(icon == null)
                icon = context.getResources().getDrawable(R.drawable.mini_picto_success);
            ImageView imgIcon = layout.findViewById(R.id.base_dialog_image);
            if (imgIcon != null) {
                imgIcon.setVisibility(View.VISIBLE);
                imgIcon.setImageDrawable(icon);
            }

            // Message
            TextView txtMsg = layout.findViewById(R.id.base_dialog_text);
            if (txtMsg != null && message != null) {
                txtMsg.setVisibility(View.VISIBLE);
                txtMsg.setText(message);
            }

            dialog.setContentView(layout);
            return dialog;
        }
    }
}
