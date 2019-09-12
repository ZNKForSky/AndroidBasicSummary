package com.android.basic.summary.activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.basic.summary.R;
import com.android.basic.summary.activity.BaseActivity;
import com.android.basic.summary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProgressbarActivity extends BaseActivity {

    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    private int mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.activity_progressbar;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }

    @OnClick({R.id.btn_add_progress, R.id.btn2_add_progress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_progress:
                mProgress = progressbar.getProgress();
                mProgress += 10;
                progressbar.setProgress(mProgress);
                ToastUtil.showLongToast(this, "我被点击了");
                break;
            case R.id.btn2_add_progress:
                Intent intent = new Intent(this, AlertDialogActivity.class);
                startActivity(intent);
                break;
        }
    }
}
