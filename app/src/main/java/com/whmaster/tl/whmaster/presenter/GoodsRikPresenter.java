package com.whmaster.tl.whmaster.presenter;

import android.content.Context;
import android.util.Log;

import com.whmaster.tl.whmaster.common.Constants;
import com.whmaster.tl.whmaster.http.RetrofitHttp;
import com.whmaster.tl.whmaster.impl.GoodsrikInterface;
import com.whmaster.tl.whmaster.view.IMvpView;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by admin on 2017/11/24.
 *
 */

public class GoodsRikPresenter extends BasePresenter implements GoodsrikInterface{

    public GoodsRikPresenter(Context context, IMvpView mvpView){
        this.mImvpView = mvpView;
        this.mContext = context;
    }

    @Override
    public void goodsrikList(int page,String orderInType, String orderInCode) {
        Map map = new HashMap();
        map.put("page",page+"");
        map.put("limit","10");
        map.put("orderInCode",orderInCode);
        map.put("orderInType",orderInType);
        map.put("orderInStatus","");
        map.put("resourceFlag","");
        map.put("buyerName","");
        map.put("updateUserName","");
        map.put("product_sku","");
        mImvpView.showLoading();
        RetrofitHttp.getInstance(mContext).post(Constants.goodsReceiptList, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                Log.i("com.whmaster.tl.whmaster>>",e+"====onError====");
                mImvpView.hideLoading();
                mImvpView.onFail(e+"");
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        mImvpView.onSuccess("success",Constants.getJsonArray(mTempMap.get("records").toString()));
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
    public void getGoodsDetailList(int page,String orderInId, String orderInCode, String orderInType, String orderInStatus) {
        Map map = new HashMap();
        map.put("page",page+"");
        map.put("limit","10");
        map.put("orderInId",orderInId);
        map.put("orderInCode",orderInCode);
        map.put("orderInType",orderInType);
        map.put("orderInStatus",orderInStatus);
        map.put("resourceFlag","");
        map.put("buyerName","");
        map.put("updateUserName","");
        map.put("product_sku","");
        mImvpView.showLoading();
        RetrofitHttp.getInstance(mContext).post(Constants.goodsgetDetailList, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                Log.i("com.whmaster.tl.whmaster>>",e+"====onError====");
                mImvpView.hideLoading();
                mImvpView.onFail(e+"");
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        mImvpView.onSuccess("list",Constants.getJsonObject(mTempMap.get("result").toString()));
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
    public void updateStatus(String orderInId, String orderInType, String orderInStatus) {
        Map map = new HashMap();
        map.put("orderInId",orderInId);
//        map.put("orderInType",orderInType);
        map.put("orderInStatus",orderInStatus);
        map.put("resourceFlag","");
        map.put("buyerName","");
        map.put("updateUserName","");
        map.put("product_sku","");
        mImvpView.showLoading();
        RetrofitHttp.getInstance(mContext).post(Constants.updateStatus, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mImvpView.hideLoading();
            }
            @Override
            public void onError(Throwable e) {
                Log.i("com.whmaster.tl.whmaster>>",e+"====onError====");
                mImvpView.onFail(e+"");
                mImvpView.hideLoading();
            }
            @Override
            public void onNext(String s) {
                mTempMap = Constants.getJsonObject(s);
                if(mTempMap!=null){
                    if(mTempMap.get("resultCode")!=null && mTempMap.get("resultCode").toString().equals("0")){
                        mImvpView.onSuccess("updateSuccess",mTempMap);
                    }else{
                        mImvpView.onFail(mTempMap.get("resultMsg")+"");
                    }
                }
                mImvpView.hideLoading();
                Log.i("com.whmaster.tl.whmaster>>",s+"====onNext====");
            }
        });
    }

}
