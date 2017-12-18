package com.whmaster.tl.whmaster.presenter;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.whmaster.tl.whmaster.view.IMvpView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admin on 2017/10/13.
 */

public abstract class BasePresenter{
    protected HashMap<String, Object>  mHashMap;
    protected ArrayList<HashMap<String, Object>> mHashList;
    protected ArrayMap<String, Object>  mTempMap,mTempMap2,mDataMap;
    protected ArrayList<ArrayMap<String, Object>> mTempList;
    protected Context mContext;
    protected IMvpView mImvpView;
}
