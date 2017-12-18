package com.whmaster.tl.whmaster.impl;

/**
 * Created by admin on 2017/11/16.
 */

public interface LibraryInterface {
    void isPermission(String positionCode);
    void queryWhStockInfoByCode(String positionCode);
    void getListByPositionCode(String positionCode);
    void checkIsSameWharehouseByCode(String oldCode,String newCode);
    void transferFinished(String entity);
}
