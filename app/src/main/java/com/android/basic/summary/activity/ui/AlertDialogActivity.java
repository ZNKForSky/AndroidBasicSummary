package com.android.basic.summary.activity.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.basic.summary.R;
import com.android.basic.summary.activity.BaseActivity;
import com.android.basic.summary.utils.ToastUtil;
import com.android.basic.summary.view.CustomDialog;
import com.hjq.toast.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Harris Luffy
 * @version 1.0
 * @des 警示框的测试类
 * @updateAuthor Harris Luffy
 * @updateDes 2019/9/9
 */
public class AlertDialogActivity extends BaseActivity {
    private static final String TAG = "AlertDialogActivity";

    @Override
    protected int getResLayoutId() {
        return R.layout.activity_alertdialog;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_show_default_alert, R.id.btn_show_sigle_alert, R.id.btn_show_multiple_alert, R.id.btn_show_sigle_alert2,
            R.id.btn_show_progress_dialog, R.id.btn_show_custom_alert, R.id.btn_show_custom_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_show_default_alert://默认警示框
                showDefaultAlert();
                break;
            case R.id.btn_show_sigle_alert://单选警示框
                showSigleAlert();
                break;
            case R.id.btn_show_sigle_alert2://单选警示框2
                showSigleAlert2();
                break;
            case R.id.btn_show_multiple_alert://多选警示框
                showMultiAlert();
                break;
            case R.id.btn_show_progress_dialog://进度条Dialog
                showProgressDialog();
                break;

            case R.id.btn_show_custom_alert://半自定义警示框
                CustomAlertDialog();
                break;
            case R.id.btn_show_custom_dialog://自定义样式的警示框
                showCustomAlert();
                break;
        }
    }

    /**
     * 默认警示框
     */
    private void showDefaultAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle("请说出你真实的想法")
                .setMessage("你觉得我帅吗?")
                .setPositiveButton("帅的不要不要的~", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.showShortToast(AlertDialogActivity.this, "你真有眼光，我也这么觉得");
                    }
                })
                .setNeutralButton("一般般吧", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.showShortToast(AlertDialogActivity.this, "那你再瞅瞅~");
                    }
                })
                .setNegativeButton("噗,帅跟你有毛关系", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.showShortToast(AlertDialogActivity.this, "gun!");
                    }
                }).show();
    }

    /**
     * 单选警示框 方式一：
     */
    private void showSigleAlert() {
        final String[] gender = {"小姐姐", "小哥哥"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请问你是小哥哥还是小姐姐？").
                setItems(gender, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.showShortToast(AlertDialogActivity.this, gender[i]);
                    }
                }).show();
    }

    /**
     * 单选警示框 方式二：
     */
    private void showSigleAlert2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] dish = new String[]{"红烧鱼", "粉蒸肉", "小龙虾", "蒜蓉油麦", "锅包肉"};
        builder.setTitle("你喜欢吃什么？")
                .setSingleChoiceItems(dish, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.showShortToast(AlertDialogActivity.this, dish[i]);
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    /**
     * 多选警示框
     */
    private void showMultiAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] starts = {"邓紫棋", "蔡徐坤", "古天乐", "周星驰", "赵丽颖"};
        boolean[] begin = new boolean[]{false, false, false, false, false};
        builder.setTitle("你喜欢下面哪些明星？")
                .setMultiChoiceItems(starts, begin, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        ToastUtil.showShortToast(AlertDialogActivity.this, starts[i]);
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.showShortToast(AlertDialogActivity.this, "OK,我了解了~");

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.showShortToast(AlertDialogActivity.this, "难道你都不喜欢？");
                    }
                }).show();

    }

    /**
     * 进度条Dialog
     */
    private void showProgressDialog() {
        /* @setProgress 设置初始进度
         * @setProgressStyle 设置样式（水平进度条）
         * @setMax 设置进度最大值
         */
        final int Max = 100;
        final ProgressDialog progressDialog = new ProgressDialog(AlertDialogActivity.this);
        progressDialog.setProgress(0);
        progressDialog.setIcon(R.mipmap.ic_cat);
        progressDialog.setTitle("我是一个进度条Dialog");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(Max);
        progressDialog.show();
        /**
         * 开个线程
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                int p = 0;
                while (p < Max) {
                    try {
                        Thread.sleep(100);
                        p++;
                        progressDialog.setProgress(p);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.cancel();//达到最大就消失
            }

        }).start();
    }

    /**
     * “半自定义Dialog” 控制普通的dialog的位置，大小，透明度
     * 在普通的dialog.show下面添加相关配置
     */
    private void CustomAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogActivity.this);
        builder.setIcon(R.mipmap.ic_cat);//图标
        builder.setTitle("简单的dialog");//文字
        builder.setMessage("生存还是死亡");//提示消息
        //积极的选择
        builder.setPositiveButton("生存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AlertDialogActivity.this, "点击了生存", Toast.LENGTH_SHORT).show();
            }
        });
        //消极的选择
        builder.setNegativeButton("死亡", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AlertDialogActivity.this, "点击了死亡", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNeutralButton("不生不死", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AlertDialogActivity.this, "点击了不生不死", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.show();
        //自定义的东西
        //放在show()之后，不然有些属性是没有效果的，比如height和width
        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // 设置高度和宽度
        p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.6); // 宽度设置为屏幕的0.65

        p.gravity = Gravity.TOP;//设置位置

        p.alpha = 0.8f;//设置透明度
        dialogWindow.setAttributes(p);
    }

    /**
     * 自定义样式的警示框
     */
    private void showCustomAlert() {
        //定义一个自己的dialog
        CustomDialog customDialog;
        //实例化自定义的dialog
        customDialog = new CustomDialog(this);
        //绑定点击事件
        customDialog.setTitle("提示")
                .setMessage("您确定要删除吗？")
                .setConfirmButton("确定", new CustomDialog.OnConfirmListtener() {
                    @Override
                    public void onConfirm(CustomDialog myDialog) {
//                        Toast.makeText(AlertDialogActivity.this, "确定", Toast.LENGTH_SHORT).show();
//                        ToastUtil.showShortToast(AlertDialogActivity.this, "确定");
                        ToastUtils.show("确定");
                    }
                })
                .setCanelButton("取消", new CustomDialog.OnCancelListtener() {
                    @Override
                    public void onCancel(CustomDialog myDialog) {
//                        ToastUtil.showShortToast(AlertDialogActivity.this, "取消");
//                        Toast.makeText(AlertDialogActivity.this, "取消", Toast.LENGTH_SHORT).show();
                        ToastUtils.show("取消了");
                    }
                }).show();
    }

}
