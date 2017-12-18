package com.whmaster.tl.whmaster.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.whmaster.tl.whmaster.R;
import com.whmaster.tl.whmaster.presenter.LibraryPresenter;
import com.whmaster.tl.whmaster.view.IMvpView;

/**
 * Created by admin on 2017/11/16.
 * 库内移动
 */

public class ChooseLibraryActivity extends BaseActivity implements IMvpView{
    private LinearLayout mLayout;
    private String mOldPositionCode = "";//CK510112A-I01-31
    private LibraryPresenter libraryPresenter;
    private String m_Broadcastname;


    @Override
    protected int getLayoutId() {
        return R.layout.chose_library_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        libraryPresenter = new LibraryPresenter(this,this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.chose_layout:
//                Bundle bundle = new Bundle();
//                bundle.putString("code","CK510112A-I01-31");
//                startActivity(ChooseLibraryGoodsActivity.class,bundle);
                break;
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        mLayout = findViewById(R.id.chose_layout);
        mLayout.setOnClickListener(this);
    }

    @Override
    public void initListeners() {
        super.initListeners();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void logcat(String msg) {
        super.logcat(msg);
    }

    @Override
    public void setHeader() {
        super.setHeader();
        mTitle.setText("选择原库位");
    }

    @Override
    public void onFail(String errorMsg) {
        mAlertDialog.builder().setTitle("提示")
                .setMsg(errorMsg)
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }

    @Override
    public void onSuccess(String type, Object object) {
        Bundle bundle = new Bundle();
        bundle.putString("code",mOldPositionCode);
        startActivity(ChooseLibraryGoodsActivity.class,bundle);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        m_Broadcastname = "com.barcode.sendBroadcast";// com.barcode.sendBroadcastScan
        intentFilter.addAction(m_Broadcastname);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (receiver != null) {
                unregisterReceiver(receiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            if (arg1.getAction().equals(m_Broadcastname)) {
                String str = arg1.getStringExtra("BARCODE");
                if (!"".equals(str)) {
                    mOldPositionCode = str;
                    logcat("获取获取扫描条形码" + str);
                    libraryPresenter.isPermission(mOldPositionCode);
                }else{
                    Toast.makeText(ChooseLibraryActivity.this,"请扫描正确的条形码！",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}
