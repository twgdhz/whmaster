package com.whmaster.tl.whmaster.impl;

/**
 * Created by admin on 2017/11/8.
 */

public interface PickingInterface {
    public void pickingList(String stockOutCode,String type,int page);
    public void pickingGoodsList(String id);
    public void pickingGoodsCheck(String id,String type);
    void executeStockOutTask(String stockInId,String postId);//执行完毕
    void fhCheck(String stockOutId);//复核完毕
}
