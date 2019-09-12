package com.android.basic.summary.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.basic.summary.R;

/**
 * @author Harris Luffy
 * @version 1.0
 * @des 自定义Dialog
 * @updateAuthor Harris Luffy
 * @updateDes 2019/9/9
 */
public class CustomDialog extends Dialog implements View.OnClickListener {
    private static final String TAG = "CustomDialog";
    private TextView mTitle, mMessage, mCanel, mConfirm;

    private String title, message, canel, confirm;

    private OnCancelListtener onCancelListtener;

    private OnConfirmListtener onConfirmListtener;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeId) {
        super(context, themeId);
    }

    public CustomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public CustomDialog setCanelButton(String canel, OnCancelListtener onCancelListtener) {
        this.canel = canel;
        this.onCancelListtener = onCancelListtener;
        return this;
    }

    public CustomDialog setConfirmButton(String confirm, OnConfirmListtener onConfirmListtener) {
        this.confirm = confirm;
        this.onConfirmListtener = onConfirmListtener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);
        //如果对话框宽度异常，可以通过下方代码根据设备的宽度来设置弹窗宽度
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        Point point = new Point();
        display.getSize(point);
        params.width = (int) (point.x * 0.8);
        getWindow().setAttributes(params);
        mTitle = findViewById(R.id.title);
        mMessage = findViewById(R.id.message);
        mCanel = findViewById(R.id.canel);
        mConfirm = findViewById(R.id.confirm);
        mCanel.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        mTitle.setText(title);
        mMessage.setText(message);
        mCanel.setText(canel);
        mConfirm.setText(confirm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.canel:
                if (onCancelListtener != null) {
                    onCancelListtener.onCancel(this);
                }
                dismiss();
                break;
            case R.id.confirm:
                if (onConfirmListtener != null) {
                    onConfirmListtener.onConfirm(this);
                }
                dismiss();
                break;
        }
    }

    //自定义接口形式提供回调方法
    public interface OnCancelListtener {
        void onCancel(CustomDialog myDialog);
    }

    public interface OnConfirmListtener {
        void onConfirm(CustomDialog myDialog);
    }
}
