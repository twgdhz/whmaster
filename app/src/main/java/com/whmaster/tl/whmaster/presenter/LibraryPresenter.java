package com.whmaster.tl.whmaster.presenter;

import android.content.Context;
import android.util.Log;

import com.whmaster.tl.whmaster.common.Constants;
import com.whmaster.tl.whmaster.http.RetrofitHttp;
import com.whmaster.tl.whmaster.impl.LibraryInterface;
import com.whmaster.tl.whmaster.view.IMvpView;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by admin on 2017/11/16.
 */

public class LibraryPresenter extends BasePresenter implements LibraryInterface{
    public LibraryPresenter(Context context, IMvpView mvpView){
        this.mImvpView = mvpView;
        this.mContext = context;
    }

    @Override
    public void isPermission(String positionCode) {
        Map map = new HashMap();
        map.put("positionCode",positionCode);
        RetrofitHttp.getInstance(mContext).post(Constants.isPermission, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                mImvpView.hideLoading();
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        mImvpView.onSuccess("success",mTempMap);
                    }else{
                        mImvpView.onFail(mTempMap.get("resultMsg")+"");
                    }
                }
                mImvpView.hideLoading();
                Log.i("com.whmaster.tl.whmaster>>",s+"====onNext====");
            }
        });
    }

    @Override
    public void queryWhStockInfoByCode(String positionCode) {
        Map map = new HashMap();
        map.put("positionCode",positionCode);
        RetrofitHttp.getInstance(mContext).post(Constants.queryWhStockInfoByCode, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                mImvpView.hideLoading();
                mImvpView.onFail(e+"");
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        mImvpView.onSuccess("list",Constants.getJsonArray(mTempMap.get("result").toString()));
                    }else{
                        mImvpView.onFail(mTempMap.get("resultMsg")+"");
                    }
                }
                mImvpView.hideLoading();
                Log.i("com.whmaster.tl.whmaster>>",s+"====onNext====");
            }
        });
    }

    @Override
    public void getListByPositionCode(String positionCode) {
        Map map = new HashMap();
        map.put("positionCode",positionCode);
        mImvpView.showLoading();
        RetrofitHttp.getInstance(mContext).post(Constants.getListByPositionCode, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                mImvpView.hideLoading();
                mImvpView.onFail(e+"");
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        mImvpView.onSuccess("list",Constants.getJsonArray(mTempMap.get("result").toString()));
                    }else{
                        mImvpView.onFail(mTempMap.get("resultMsg")+"");
                    }
                }
                mImvpView.hideLoading();
                Log.i("com.whmaster.tl.whmaster>>",s+"====onNext====");
            }
        });
    }

    @Override
    public void checkIsSameWharehouseByCode(String oldCode, String newCode) {
        Map map = new HashMap();
        map.put("oldPositionCode",oldCode);
        map.put("newPositionCode",newCode);
        mImvpView.showLoading();
        RetrofitHttp.getInstance(mContext).post(Constants.checkIsSameWharehouseByCode, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                mImvpView.hideLoading();
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        mImvpView.onSuccess("isSameSuccess",mTempMap);
                    }else{
                        mImvpView.onFail(mTempMap.get("resultMsg")+"");
                    }
                }
                mImvpView.hideLoading();
                Log.i("com.whmaster.tl.whmaster>>",s+"====onNext====");
            }
        });
    }

    @Override
    public void transferFinished(String entity) {
        Map map = new HashMap();
        map.put("entity",entity);
        Log.i("com.whmaster.tl.whmaster>>",map+"====移库数据参数====");
        RetrofitHttp.getInstance(mContext).post(Constants.transferFinished, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        mImvpView.onSuccess("complete",mTempMap);
                    }else{
                        mImvpView.onFail(mTempMap.get("resultMsg")+"");
                    }
                }
                Log.i("com.whmaster.tl.whmaster>>",s+"====移库成功====");
            }
        });
    }
}
