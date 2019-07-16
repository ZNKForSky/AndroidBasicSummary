package com.android.basic.summary.activity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.basic.summary.R;

import java.util.zip.Inflater;

public class PopupWindowActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnSelectPhoto;//选择照片
    private PopupWindow mPopupWindowSelectPhoto;//选择照片方式的popupwindow

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);
        initViews();
        initListenners();
    }

    private void initViews() {
        mBtnSelectPhoto = findViewById(R.id.btn_select_photo);
    }

    private void initListenners() {
        mBtnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();//展示选择照片方式的popupwindow
            }
        });
    }

    private void showPopupWindow() {
        if (mPopupWindowSelectPhoto == null) {
            mPopupWindowSelectPhoto = new PopupWindow();
        }
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_popup_select_photo, null);
        mPopupWindowSelectPhoto.setContentView(contentView);
        mPopupWindowSelectPhoto.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindowSelectPhoto.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置点击popupwindow外部消失，这两个属性要一起设置，并且要在popupwindow show之前设置
        mPopupWindowSelectPhoto.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindowSelectPhoto.setOutsideTouchable(true);
        //显示PopupWindow
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_popup_window, null);
        mPopupWindowSelectPhoto.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

        TextView tvTakePhoto = (TextView) contentView.findViewById(R.id.tv_take_photo);
        TextView tvSelectFromAlbum = (TextView) contentView.findViewById(R.id.tv_select_from_album);
        TextView tvCancel = (TextView) contentView.findViewById(R.id.tv_cancel);
        tvTakePhoto.setOnClickListener(this);
        tvSelectFromAlbum.setOnClickListener(this);
        tvCancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_take_photo://拍照
                Toast.makeText(this, "拍照", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_select_from_album://从相册选择
                Toast.makeText(this, "从相册选择", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_cancel://取消
                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                break;
        }
        mPopupWindowSelectPhoto.dismiss();
        mPopupWindowSelectPhoto = null;
    }
}
