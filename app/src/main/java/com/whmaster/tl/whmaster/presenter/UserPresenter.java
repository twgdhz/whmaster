package com.whmaster.tl.whmaster.presenter;

import android.content.Context;
import android.util.Log;

import com.whmaster.tl.whmaster.common.Constants;
import com.whmaster.tl.whmaster.http.RetrofitHttp;
import com.whmaster.tl.whmaster.impl.UserInterface;
import com.whmaster.tl.whmaster.model.User;
import com.whmaster.tl.whmaster.view.IMvpView;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by admin on 2017/10/26.
 */

public class UserPresenter extends BasePresenter implements UserInterface{
    public UserPresenter(Context context, IMvpView mvpView){
        this.mImvpView = mvpView;
        this.mContext = context;
    }
    @Override
    public void login(String username, String password,String type) {
        Map map = new HashMap();
        map.put("username",username);
        map.put("password",password);
        map.put("loginType",type);
        RetrofitHttp.getInstance(mContext).post(Constants.login, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                Log.i("com.whmaster.tl.whmaster>>返回数据",e+"============");
                mImvpView.hideLoading();
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        User user = new User();
                        user = (User) Constants.getGsonObject(mTempMap.get("result").toString(),user);
                        mImvpView.onSuccess("login",Constants.getGsonObject(mTempMap.get("result").toString(),user));
                    }else{
                        mImvpView.onFail(mTempMap.get("resultMsg")+"");
                    }
                }
                mImvpView.hideLoading();
                Log.i("com.whmaster.tl.whmaster>>获得数据",s+"========");
            }
        });
    }

    @Override
    public void islogin() {
        Map map = new HashMap();
        RetrofitHttp.getInstance(mContext).get(Constants.islogin, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                Log.i("com.whmaster.tl.whmaster>>是否登录",e+"============");
                mImvpView.hideLoading();
                mImvpView.onFail(e+"");
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null){
                        mImvpView.onSuccess("islogin",mTempMap);
                    }
                }
                mImvpView.hideLoading();
                Log.i("com.whmaster.tl.whmaster>>是否登录",s+"========");
            }
        });
    }

    @Override
    public void loginout() {
        Map map = new HashMap();
        RetrofitHttp.getInstance(mContext).get(Constants.loginout, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                Log.i("com.whmaster.tl.whmaster>>返回数据",e+"============");
                mImvpView.onSuccess("loginout",null);
                mImvpView.hideLoading();
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        mImvpView.onSuccess("loginout",null);
                    }else{
                        mImvpView.onSuccess("loginout",null);
//                        mImvpView.onFail(mTempMap.get("resultMsg")+"");
                    }
                }
                mImvpView.hideLoading();
                Log.i("com.whmaster.tl.whmaster>>获得数据",s+"========"+mTempMap);
            }
        });
    }

    @Override
    public void perms() {
        Map map = new HashMap();
        map.put("terminalType","10");
        RetrofitHttp.getInstance(mContext).post(Constants.perms, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                Log.i("com.whmaster.tl.whmaster>>返回数据",e+"============");
                mImvpView.hideLoading();
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        mImvpView.onSuccess("perms",null);
                    }else{
                        mImvpView.onFail(mTempMap.get("resultMsg")+"");
                    }
                }
                mImvpView.hideLoading();
                Log.i("com.whmaster.tl.whmaster>>获取权限",s+"========");
            }
        });
    }


}
