package com.android.basic.summary.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.basic.summary.R;
import com.android.basic.summary.utils.ImageUtil;
import com.android.basic.summary.utils.PermessionUtils;
import com.android.basic.summary.utils.PermissionsChecker;
import com.android.basic.summary.utils.QRCodeHelper;
import com.hjq.toast.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author Harris Luffy
 * @version 1.0
 * @des 使用ZXing实现生成二维码的功能
 * e-mail : 744423651@qq.com
 * @updateDes 2019/9/17 14:54
 */
@RuntimePermissions
public class ZXingActivity extends BaseActivity {

    @BindView(R.id.btn_create_qrcode)
    Button btnCreateQrcode;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R.id.btn_save_qrcode)
    Button btnSaveQrcode;
    private Bitmap mBitmap;
    boolean createQrcodeSucess = true;//生成二维码是否成功
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private PermissionsChecker mPermissionsChecker; // 权限检测器

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


    @OnClick({R.id.btn_create_qrcode, R.id.btn_save_qrcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_create_qrcode://生成二维码
                String info = "http://fwh.xftm.com/#/wx/wzApply?applyCode=SH000&timeStamp=1568774613352";
                Bitmap qrCodeBitmap = QRCodeHelper.createQRCodeBitmap(info, 480, 480, "UTF-8", "H", "2", Color.RED, Color.WHITE);
                Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_cat);
                mBitmap = QRCodeHelper.addLogo(qrCodeBitmap, logoBitmap, 0.3F);
                if (mBitmap != null) {
                    createQrcodeSucess = true;
                    ivQrcode.setImageBitmap(mBitmap);
                } else {
                    createQrcodeSucess = false;
                    ToastUtils.show("生成二维码失败");
                }
                break;
            case R.id.btn_save_qrcode://保存二维码
                //                if (mPermissionsChecker == null) {
                //                    mPermissionsChecker = new PermissionsChecker(this);
                //                }
                if (createQrcodeSucess) {
                    Log.e(TAG, "onViewClicked: 保存二维码");
                    ZXingActivityPermissionsDispatcher.savaQrcodeWithPermissionCheck(this);
                }
                break;

            default:
                break;
        }
    }

    //=======================动态权限的申请===========================================================<
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void savaQrcode() {
        Log.e(TAG, "savaQrcode: ");
        boolean isSuccess = ImageUtil.saveImageToGallery(this, mBitmap, "qr_" + System.currentTimeMillis() + ".jpg");
        if (isSuccess) {
            ToastUtils.show("二维码已保存至本地");
        } else {
            ToastUtils.show("保存失败");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ZXingActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /**
     * 如果用户选择了让设备“不再询问”，而调用的方法
     */
    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showNeverAskForReadAndWrite() {
        showPermissionDenied();
    }

    /**
     * 如果用户不授予权限调用的方法
     */

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showDeniedForCamera() {
        showPermissionDenied();
    }

    public void showPermissionDenied() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permissIntro)
                .setCancelable(false)
                .setMessage(R.string.permissIntroContent)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //缺少权限直接退出App
                        //                        ActivityTaskManager.getInstance().closeAllActivity();
                        ToastUtils.show("您已取消授权，保存二维码失败");
                    }
                })
                .setPositiveButton(R.string.permissGive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //                        isBack = true;
                        PermessionUtils.getInstance().goToPermessionView(ZXingActivity.this);
                    }
                }).create().show();
    }
}
