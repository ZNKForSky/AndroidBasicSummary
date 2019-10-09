package com.android.basic.summary.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import com.android.basic.summary.MyApplication;
import com.android.basic.summary.R;
import com.android.basic.summary.utils.PermessionUtils;
import com.android.basic.summary.utils.PermissionsChecker;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.PermissionUtils;

public class DeviceInfoActivity extends AppCompatActivity {

    @BindView(R.id.tv_imei_info)
    TextView tvImeiInfo;
    @BindView(R.id.tv_imsi_info)
    TextView tvImsiInfo;
    @BindView(R.id.tv_mac_info)
    TextView tvMacInfo;
    private String imei = "";
    private String mac = "";
    private static final String TAG = "DeviceInfoActivity";
    private static final int REQUEST_GETIMEI = 0;
    private static final String[] PERMISSION_GETIMEI = new String[]{"android.permission.READ_PHONE_STATE"};
//    private PermissionsChecker permissionsChecker = new PermissionsChecker(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        ButterKnife.bind(this);
        imei = getIMEI();
//        mac = getMacAddress();
        Log.e(TAG, "onCreate: imei == " + imei);
//        Log.e(TAG, "onCreate: mac == " + mac);
        initView();
//        DeviceInfoActivityPermissionsDispatcher
    }

    private void initView() {
        tvImeiInfo.setText(imei);
        tvMacInfo.setText(mac);
    }

    public String getIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSION_GETIMEI, REQUEST_GETIMEI);
        } else {
            imei = telephonyManager.getDeviceId();
            Log.e(TAG, "getIMEI: imei== " + imei);
        }
        return imei;
    }

//    public String getMacAddress() {
//
//        String macAddress = null;
//        WifiManager wifiManager =
//                (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = (null == wifiManager ? null : wifiManager.getConnectionInfo());
//
//        if (!wifiManager.isWifiEnabled()) {
//            //必须先打开，才能获取到MAC地址
//            wifiManager.setWifiEnabled(true);
//            wifiManager.setWifiEnabled(false);
//        }
//        if (null != info) {
//            macAddress = info.getMacAddress();
//        }
//        return macAddress;
//    }

    //    /**
//     * 获取手机IMSI
//     */
//    public static String getIMSI(Context context) {
//        try {
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            //获取IMSI号
//            if (ActivityCompat.checkSelfPermission(DeviceInfoActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return TODO;
//            }
//            String imsi = telephonyManager.getSubscriberId();
//            if(null==imsi){
//                imsi="";
//            }
//            return imsi;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_GETIMEI:
                if (verifyPermissions(grantResults)) {
                    getIMEI();
                } else {
                    if (shouldShowRequestPermissionRationale(this, PERMISSION_GETIMEI)) {
                        showPermissionDenied();
                    } else {
                        showPermissionDenied();
                    }
                }
                break;
        }

    }

    public static boolean verifyPermissions(int... grantResults) {
        if (grantResults.length == 0) {
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void showPermissionDenied() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permissIntro)
                .setCancelable(false)
                .setMessage(R.string.permiss_phone_state_content)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //缺少权限直接退出App
//                        ActivityTaskManager.getInstance().closeAllActivity();
                    }
                })
                .setPositiveButton(R.string.permissGive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermessionUtils.getInstance().goToPermessionView(DeviceInfoActivity.this);
                    }
                }).create().show();
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }
}
