package com.whmaster.tl.whmaster.impl;

import java.util.ArrayList;

/**
 * Created by admin on 2017/11/7.
 */

public interface StorageInterface {
    void getStorageList(String code, int page, int pageSize);

    void getStorageProductList(String orderid);

    void getStorageCount();

    void getTaskCount(String type);

    void queryStorePositionDetail(String detailId, String businessType);//上架详情

    void shelfProductStockInTask(String stockInDetailId, String list);//数量上架

    void executeStockInTask(String stockInId, String orderid);//执行完毕

    void shelfProductStockOutTask(String stockInDetailId, String actPackageNum, String actNum, String list);//拣货数量上架

    void rkdscList(String orderInStatus, String code, int page);//入库单生成

    void getStockConut();//入库单未生成数量获取

    void getStorageDetailList(String orderId);//入库单货品列表

    void getWharehouse(String wharehouseId);//仓库获取
    void getRegion(String wharehouseId);//库区获取
    void getPosition(String regionId);//库位获取

    void addGenerateList(String buyerId,String orderInId,String wharehouseId,String wharehouseName,String detail);//生成上架单

    void queryUnfinishedMaterialCount();//实物收货数量获取
}
