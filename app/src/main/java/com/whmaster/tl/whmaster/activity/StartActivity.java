package com.whmaster.tl.whmaster.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.LinearLayout;
import com.whmaster.tl.whmaster.R;
import com.whmaster.tl.whmaster.presenter.UserPresenter;
import com.whmaster.tl.whmaster.view.IMvpView;


/**
 * Created by admin on 2017/10/23.
 */

public class StartActivity extends BaseActivity implements IMvpView{
    private LinearLayout mTitleLayout;
    private SharedPreferences sp;
    private boolean isLogin = false;
    private UserPresenter userPresenter;
    private ArrayMap<String,Object> mMap;
    @Override
    protected int getLayoutId() {
        return R.layout.start_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPresenter = new UserPresenter(this,this);
        userPresenter.islogin();
        sp = getSharedPreferences("whmasterUser", Context.MODE_PRIVATE);
        logcat(sp.getString("cookie",null)+"==============");

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if(isLogin){
                        Bundle bundle = new Bundle();
                        bundle.putString("username",sp.getString("name",null)+"");
                        startActivity(MainActivity.class,bundle);
                    }else{
                        startActivity(LoginActivity.class,null);
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void initViews() {
        super.initViews();
        mTitleLayout = findViewById(R.id.title);
        mTitleLayout.setVisibility(View.GONE);
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
    public void setHeader() {
        super.setHeader();
        mBackImage.setVisibility(View.GONE);
    }

    @Override
    public void onFail(String errorMsg) {
        startActivity(LoginActivity.class,null);
        finish();
    }

    @Override
    public void onSuccess(String type, Object object) {
        mMap = (ArrayMap<String, Object>) object;
        if(mMap!=null && mMap.size()>0){
            switch (mMap.get("resultCode").toString()){
                case "0":
                    isLogin = true;
                    break;
                default:
                    isLogin = false;
                    break;
            }
        }
        handler.sendEmptyMessageDelayed(1, 1200);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
