package com.whmaster.tl.whmaster.presenter;

import android.content.Context;
import android.util.Log;

import com.whmaster.tl.whmaster.common.Constants;
import com.whmaster.tl.whmaster.http.RetrofitHttp;
import com.whmaster.tl.whmaster.impl.PickingInterface;
import com.whmaster.tl.whmaster.view.IMvpView;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by admin on 2017/11/8.
 */

public class PickingPresenter extends BasePresenter implements PickingInterface{
    public PickingPresenter(Context context, IMvpView mvpView){
        this.mImvpView = mvpView;
        this.mContext = context;
    }
    @Override
    public void pickingList(String stockOutCode,String stockOutType, int page) {
        Map map = new HashMap();
        map.put("stockOutCode",stockOutCode);
        map.put("stockOutType",stockOutType);
        map.put("page",page+"");
        map.put("limit","10");
        map.put("sidx","");
        map.put("order","");
        mImvpView.showLoading();
        RetrofitHttp.getInstance(mContext).post(Constants.pickingList, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                Log.i("com.whmaster.tl.whmaster>>返回数据",e+"=====onError=======");
                mImvpView.hideLoading();
                mImvpView.onFail(e+"");
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        mTempList = Constants.getJsonArray(mTempMap.get("records").toString());
                        mImvpView.onSuccess("list",mTempList);

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
    public void pickingGoodsList(String id) {
        Map map = new HashMap();
        map.put("stockOutId",id);
        mImvpView.showLoading();
        RetrofitHttp.getInstance(mContext).post(Constants.pickingDetailList, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                Log.i("com.whmaster.tl.whmaster>>返回数据",e+"=====onError=======");
                mImvpView.hideLoading();
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        mTempList = Constants.getJsonArray(mTempMap.get("result").toString());
                        mImvpView.onSuccess("list",mTempList);
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
    public void pickingGoodsCheck(String id, String type) {
        Map map = new HashMap();
        map.put("detailId",id);
        map.put("businessType",type);
        RetrofitHttp.getInstance(mContext).post(Constants.pickingDetailList, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                Log.i("com.whmaster.tl.whmaster>>返回数据",e+"=====onError=======");
                mImvpView.hideLoading();
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        mTempList = Constants.getJsonArray(mTempMap.get("result").toString());
                        mImvpView.onSuccess("list",mTempList);
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
    public void executeStockOutTask(String stockInId,String postId) {
        Map map = new HashMap();
        map.put("stockOutId",stockInId);
        map.put("postId",postId);
        mImvpView.showLoading();
        RetrofitHttp.getInstance(mContext).post(Constants.executePick, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                Log.i("com.whmaster.tl.whmaster>>",e+"======onError======");
                mImvpView.onFail(e+"");
                mImvpView.hideLoading();
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        mImvpView.onSuccess("execute",mTempMap);
                    }else{
                        mImvpView.onFail(mTempMap.get("resultMsg")+"");
                    }
                }
                mImvpView.hideLoading();
                Log.i("com.whmaster.tl.whmaster>>执行完毕",s+"====onNext====");
            }
        });
    }

    @Override
    public void fhCheck(String stockOutId) {
        Map map = new HashMap();
        map.put("stockOutId",stockOutId);
        mImvpView.showLoading();
        RetrofitHttp.getInstance(mContext).post(Constants.fhcheck, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                Log.i("com.whmaster.tl.whmaster>>",e+"======onError======");
                mImvpView.onFail(e+"");
                mImvpView.hideLoading();
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        mImvpView.onSuccess("execute",mTempMap);
                    }else{
                        mImvpView.onFail(mTempMap.get("resultMsg")+"");
                    }
                }
                mImvpView.hideLoading();
                Log.i("com.whmaster.tl.whmaster>>执行完毕",s+"====onNext====");
            }
        });
    }
}
