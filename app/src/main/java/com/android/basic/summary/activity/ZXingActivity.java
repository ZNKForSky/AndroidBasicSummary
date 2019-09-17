package com.android.basic.summary.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.basic.summary.R;
import com.android.basic.summary.utils.QRCodeHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Harris Luffy
 * @version 1.0
 * @des 使用ZXing实现生成二维码的功能
 * e-mail : 744423651@qq.com
 * @updateDes 2019/9/17 14:54
 */
public class ZXingActivity extends BaseActivity {

    @BindView(R.id.btn_create_qrcode)
    Button btnCreateQrcode;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;

    @Override
    protected int getResLayoutId() {
        return R.layout.activity_zxing;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }


    @OnClick(R.id.btn_create_qrcode)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_create_qrcode://生成二维码
                Bitmap qrCodeBitmap = QRCodeHelper.createQRCodeBitmap("Harris Luffy", 480, 480, "UTF-8", "H", "2", Color.RED, Color.WHITE);
                Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_cat);
                Bitmap bitmap = QRCodeHelper.addLogo(qrCodeBitmap, logoBitmap, 0.3F);
                ivQrcode.setImageBitmap(bitmap);
                break;

            default:
                break;
        }
    }
}
