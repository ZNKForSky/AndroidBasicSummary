package com.android.basic.summary.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.basic.summary.MainActivity;
import com.android.basic.summary.R;
import com.android.basic.summary.utils.ImageUtil;
import com.android.basic.summary.utils.PermessionUtils;
import com.android.basic.summary.utils.QRCodeHelper;
import com.hjq.toast.ToastUtils;

import java.io.File;

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
public class ZXingActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.edt_qrcode_content)
    EditText edtQrcodeContent;
    @BindView(R.id.edt_qrcode_width)
    EditText edtQrcodeWidth;
    @BindView(R.id.edt_qrcode_height)
    EditText edtQrcodeHeight;
    @BindView(R.id.sp_fault_tolerance)
    Spinner spFaultTolerance;
    @BindView(R.id.sp_margin)
    Spinner spMargin;
    @BindView(R.id.sp_color_black)
    Spinner spColorBlack;
    @BindView(R.id.sp_color_white)
    Spinner spColorWhite;
    @BindView(R.id.iv_add_logo)
    ImageView ivAddLogo;
    @BindView(R.id.select_black_img)
    ImageView selectBlackImg;
    @BindView(R.id.btn_generate_qrcode)
    Button btnGenerateQrcode;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;

    private String mQrCodeContent;//二维码的内容
    private int mQrCodeWidth;//二维码的宽度
    private int mQrcodeHeight;//二维码的高度
    private String mToleranceLevel;//生成二维码的容错率
    private String mBlankMargin;//二维码的空白边距
    private int mColorBlack;//二维码黑色色块的替换色
    private int mColorWhite;//二维码白色色块的替换色
    private Bitmap mLogoBitmap;//logo图片
    private Bitmap mBlackBitmap;//代替黑色色块的图片
    private Bitmap mQrCodeBitmap;//生成的二维码图片

    private boolean createQrcodeSucess = true;//生成二维码是否成功
    private final static int CAMERA_REQUEST_CODE = 0; // 拍照回传码
    private final static int GALLERY_REQUEST_CODE = 1;// 相册选择回传码
    private AlertDialog.Builder builder;//图片选取方式的弹框
    private int remark;//标记返回的是logo还是代替黑色色块图片
    Uri imageUri;//图片的Uri

    @Override
    protected int getResLayoutId() {
        return R.layout.activity_zxing;
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()) {
            case R.id.sp_fault_tolerance://容错率
                mToleranceLevel = getResources().getStringArray(R.array.spinarr_error_correction)[position];
                break;
            case R.id.sp_margin://二维码的空白边距
                mBlankMargin = getResources().getStringArray(R.array.spinarr_margin)[position];
                break;
            case R.id.sp_color_black://选择二维码黑色色块的替换色
                String strColorBlack = getResources().getStringArray(R.array.spinarr_color_black)[position];
                if (strColorBlack.equals("黑色")) {
                    mColorBlack = Color.BLACK;
                } else if (strColorBlack.equals("白色")) {
                    mColorBlack = Color.WHITE;
                } else if (strColorBlack.equals("蓝色")) {
                    mColorBlack = Color.BLUE;
                } else if (strColorBlack.equals("绿色")) {
                    mColorBlack = Color.GREEN;
                } else if (strColorBlack.equals("黄色")) {
                    mColorBlack = Color.YELLOW;
                } else if (strColorBlack.equals("红色")) {
                    mColorBlack = Color.RED;
                } else if (strColorBlack.equals("紫色")) {
                    mColorBlack = Color.parseColor("#9370DB");
                } else if (strColorBlack.equals("粉红色")) {
                    mColorBlack = Color.parseColor("#ffc0cb");
                } else if (strColorBlack.equals("薄荷色")) {
                    mColorBlack = Color.parseColor("#BDFCC9");
                } else {
                    mColorBlack = Color.BLACK;
                }
                break;
            case R.id.sp_color_white://选择二维码黑色色块的替换色
                String strColorWhite = getResources().getStringArray(R.array.spinarr_color_white)[position];
                if (strColorWhite.equals("黑色")) {
                    mColorWhite = Color.BLACK;
                } else if (strColorWhite.equals("白色")) {
                    mColorWhite = Color.WHITE;
                } else if (strColorWhite.equals("蓝色")) {
                    mColorWhite = Color.BLUE;
                } else if (strColorWhite.equals("绿色")) {
                    mColorWhite = Color.GREEN;
                } else if (strColorWhite.equals("黄色")) {
                    mColorWhite = Color.YELLOW;
                } else if (strColorWhite.equals("红色")) {
                    mColorWhite = Color.RED;
                } else if (strColorWhite.equals("紫色")) {
                    mColorWhite = Color.parseColor("#9370DB");
                } else if (strColorWhite.equals("粉红色")) {
                    mColorWhite = Color.parseColor("#ffc0cb");
                } else if (strColorWhite.equals("薄荷色")) {
                    mColorWhite = Color.parseColor("#BDFCC9");
                } else {
                    mColorWhite = Color.WHITE;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void initListeners() {
        ivQrcode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {//TODO 保存和分享

                return true;
            }
        });
    }

    @Override
    protected void initData() {

    }

    //=======================动态权限的申请========================<
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void savaQrcode() {
        Log.e(TAG, "savaQrcode: ");
        boolean isSuccess = ImageUtil.saveImageToGallery(this, mQrCodeBitmap, "qr_" + System.currentTimeMillis() + ".jpg");
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
    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void showNeverAskForReadAndWrite() {
        showPermissionDenied();
    }

    /**
     * 如果用户不授予权限调用的方法
     */

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
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

    @OnClick({R.id.iv_add_logo, R.id.select_black_img, R.id.btn_generate_qrcode})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_logo://添加Logo
                remark = 0;
                showSelectImgDailog();
                break;
            case R.id.select_black_img://选择替代黑色色块的图片
                remark = 1;
                showSelectImgDailog();
                break;
            case R.id.btn_generate_qrcode://生成二维码
//                String info = "http://fwh.xftm.com/#/wx/wzApply?applyCode=SH000&timeStamp=1568774613352";
                String info = edtQrcodeContent.getText().toString();
                if (!TextUtils.isEmpty(info)) {
                    mQrCodeWidth = Integer.parseInt(edtQrcodeWidth.getText().toString().equals("") ? "650" : edtQrcodeWidth.getText().toString().trim());
                    mQrcodeHeight = Integer.parseInt(edtQrcodeHeight.getText().toString().equals("") ? "650" : edtQrcodeHeight.getText().toString().trim());
                    spFaultTolerance.setOnItemSelectedListener(this);
                    spMargin.setOnItemSelectedListener(this);
                    spColorBlack.setOnItemSelectedListener(this);
                    spColorWhite.setOnItemSelectedListener(this);
                    Bitmap qrCodeBitmap = QRCodeHelper.createQRCodeBitmap(info, mQrCodeWidth, mQrcodeHeight, "UTF-8", mToleranceLevel, mBlankMargin, mColorBlack, mColorWhite);
                    Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_cat);
                    mQrCodeBitmap = QRCodeHelper.addLogo(qrCodeBitmap, logoBitmap, 0.3F);
                    if (mQrCodeBitmap != null) {
                        createQrcodeSucess = true;
                        ivQrcode.setImageBitmap(mQrCodeBitmap);
                    } else {
                        createQrcodeSucess = false;
                        ToastUtils.show("生成二维码失败");
                    }
                } else {
                    ToastUtils.show("您是不是忘记输入二维码内容了呢");
                }

                break;
            //TODO 保存二维码的方法
//            if (createQrcodeSucess) {
//                Log.e(TAG, "onViewClicked: 保存二维码");
//                ZXingActivityPermissionsDispatcher.savaQrcodeWithPermissionCheck(this);
//            }
        }
    }

    private void showSelectImgDailog() {
        if (builder == null) {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("请选择图片").setSingleChoiceItems(new String[]{"拍照", "从相册选择"}, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0://拍照
                        ZXingActivityPermissionsDispatcher.takePhotoWithPermissionCheck(ZXingActivity.this);
                        break;
                    case 1://从相册选择
                        ZXingActivityPermissionsDispatcher.openAlbumWithPermissionCheck(ZXingActivity.this);
                        break;
                    default:
                        break;
                }
            }
        }).show();
    }

    //拍照
    @NeedsPermission({Manifest.permission.CAMERA})
    public void takePhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            Log.e(TAG, "takePhoto: cameraIntent.resolveActivity(getPackageManager() == " + cameraIntent.resolveActivity(getPackageManager()));
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            File imageFile = ImageUtil.createFile(getApplicationContext());

            if (Build.VERSION.SDK_INT < 24) {
                imageUri = Uri.fromFile(imageFile);
            } else {
                imageUri = FileProvider.getUriForFile(ZXingActivity.this, "com.android.basic.summary.fileprovider", imageFile);
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(getApplicationContext(), R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * /打开相册
     */
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult: requestCode == " + requestCode);
        switch (requestCode) {

            case CAMERA_REQUEST_CODE:
                Log.e(TAG, "onActivityResult: CAMERA_REQUEST_CODE == ");
                if (resultCode == RESULT_OK) {
                    try {
                        Log.e(TAG, "onActivityResult: remark == " + remark);
                        if (remark == 0) {//logo
                            Log.e(TAG, "onActivityResult: mLogoBitmap" + mLogoBitmap);
                            mLogoBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                            Log.e(TAG, "onActivityResult: mLogoBitmap" + mLogoBitmap);
                            // 将拍摄的照片显示出来
                            ivAddLogo.setImageBitmap(mLogoBitmap);
                        } else if (remark == 1) {//black
                            mBlackBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                            Log.e(TAG, "onActivityResult: mBlackBitmap" + mBlackBitmap);
                            selectBlackImg.setImageBitmap(mBlackBitmap);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case GALLERY_REQUEST_CODE:
                Log.e(TAG, "onActivityResult: GALLERY_REQUEST_CODE == ");
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 4.4以后
     *
     * @param data
     */
    @SuppressLint("NewApi")
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    /**
     * 4.4版本以前，直接获取真实路径
     *
     * @param data
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 显示图片
     *
     * @param imagePath 图片路径
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {

            if (remark == 0) {//logo
                mLogoBitmap = BitmapFactory.decodeFile(imagePath);
                // 显示图片
                ivAddLogo.setImageBitmap(mLogoBitmap);
            } else if (remark == 1) {//black
                mBlackBitmap = BitmapFactory.decodeFile(imagePath);
                selectBlackImg.setImageBitmap(mBlackBitmap);
            } else {
            }
        } else {
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }
}
