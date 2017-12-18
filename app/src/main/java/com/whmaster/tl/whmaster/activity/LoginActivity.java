package com.whmaster.tl.whmaster.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.whmaster.tl.whmaster.R;
import com.whmaster.tl.whmaster.customview.AlertDialog;
import com.whmaster.tl.whmaster.presenter.UserPresenter;
import com.whmaster.tl.whmaster.utils.ScreenUtils;
import com.whmaster.tl.whmaster.view.IMvpView;

import java.io.Serializable;

/**
 * Created by admin on 2017/10/23.
 */

public class LoginActivity extends BaseActivity implements IMvpView{
    private Button mLoginBtn;
    private UserPresenter userPresenter;
    private EditText mUserNameEdit,mPwdEdit;
    private Dialog mDialog;
    private RelativeLayout mContentLayout;
    private Bundle mBundle;
    private String mType;
    @Override
    protected int getLayoutId() {
        return R.layout.login_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPresenter = new UserPresenter(this,this);
        View view = LayoutInflater.from(this).inflate(
                R.layout.log_load_layout, null);
        mContentLayout = view.findViewById(R.id.log_load_content_layout);
        ViewGroup.LayoutParams params = mContentLayout.getLayoutParams();
        params.height = ScreenUtils.getScreenWidth(this)/3;
        params.width = ScreenUtils.getScreenWidth(this)/3;
        mContentLayout.setLayoutParams(params);
        mDialog = new Dialog(this, R.style.AlertDialogStyle);
        mDialog.setContentView(view);

        mBundle = getIntent().getExtras();

            getUser();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.login_btn:
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mPwdEdit.getWindowToken(), 0);
                    if (!mUserNameEdit.getText().toString().equals("") && !mPwdEdit.getText().toString().equals("")) {
                        showLoading();
                        handler.sendEmptyMessageDelayed(1,500);
                    } else {
                        mAlertDialog.builder().setTitle("提示")
                                .setMsg("请输入帐号或密码！")
                                .setPositiveButton("确认", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                }).show();
                    }
                break;
        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    userPresenter.login(mUserNameEdit.getText().toString(),mPwdEdit.getText().toString(),"10");
                    break;
            }
        }
    };
    @Override
    public void initViews() {
        super.initViews();
        mLoginBtn = findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(this);
        mUserNameEdit = findViewById(R.id.username);
        mPwdEdit = findViewById(R.id.pwd);
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
        mTitle.setText("登录");
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
    public void onSuccess(String type,Object o) {
        hideLoading();
        rememberUser(mUserNameEdit.getText().toString(),mPwdEdit.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable("object", (Serializable) o);
        bundle.putString("username",mUserNameEdit.getText().toString());
        startActivity(MainActivity.class, bundle);
        finish();
    }
    //记住密码
    private void rememberUser(String username,String pwd) {
        SharedPreferences sp = getSharedPreferences("whmasterUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", username);
        editor.putString("pwd", pwd);
        editor.commit();
    }
    //获取用户
    private void getUser() {
        SharedPreferences sp = getSharedPreferences("whmasterUser", MODE_PRIVATE);
        if (sp != null) {
            mUserNameEdit.setText(sp.getString("name", ""));
            mPwdEdit.setText(sp.getString("pwd", ""));
        }
        Log.i("获取缓存用户",sp.getString("userName", "")+"==================");
    }
    @Override
    public void showLoading() {
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        if(mDialog!=null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
}
