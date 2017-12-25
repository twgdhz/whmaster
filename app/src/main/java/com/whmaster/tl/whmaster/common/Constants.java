package com.whmaster.tl.whmaster.common;


import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by Administrator on 2016/9/7.
 */
public class Constants {
//      public static final String apiHead = "http://139.224.13.216/"; //华信测试环境
//      public static final String apiHead = "http://192.168.2.30:8085/";
//      public static final String apiHead = "http://172.19.12.164/";//研发环境
        public static final String apiHead = "http://rdotms.tianlu56.com.cn/";//研发环境公网
//      public static final String apiHead = "http://139.196.142.179/";//预生产环境公网
//      public static final String apiHead = "http://172.19.12.180/";//预生产环境
//        public static final String apiHead = "https://otms.tianlu56.com.cn/";//生产环境
    //登陆
    public static final String login = apiHead + "sys/sys/loginApp";
    //是否登录
    public static final String islogin = apiHead + "sys/isLogin";
    //注销
    public static final String loginout = apiHead + "sys/logout";
    //获取权限
    public static final String perms = apiHead + "sys/menu/perms";
    //入库单列表 page limit stockInCode
    public static final String getStorageList = apiHead + "wh/stockIn/queryStockInTaskListByShelfUserId";
    //入库单货品列表
    public static final String getStorageProductList = apiHead + "wh/stockIn/queryStockInProductListById";
    //未完成入库任务数获取接口
    public static final String getStorageCount = apiHead + "wh/stockIn/queryUnfinishedStockInCount";
    //拣货出库任务数获取接口
    public static final String getTaskCount = apiHead + "wh/stockOut/task/count";
    //货品数量上架or拣货详情获取接口
    public static final String queryStorePositionDetail = apiHead + "wh/stockIn/queryStorePositionDetail";
    //拣货单列表
    public static final String pickingList = apiHead + "wh/stockOut/task/list";
    //拣出货品列表
    public static final String pickingDetailList = apiHead + "wh/stockOut/detail/list";
    //数量上架
    public static final String shelfProductStockInTask = apiHead + "wh/stockIn/shelfProductStockInTask";
    //入库执行完毕
    public static final String executeStockInTask = apiHead + "wh/stockIn/executeStockInTask";
    //拣出货品
    public static final String executeStockOutTask = apiHead + "wh/stockOut/pick/num";
    //拣货执行完毕
    public static final String executePick = apiHead + "wh/stockOut/execute/pick";
    //复核完毕
    public static final String fhcheck = apiHead + "wh/stockOut/execute/check";
    //装车确认列表
    public static final String toConfirmLoad = apiHead + "delivery/delivery/toConfirmLoad";
    //装车货品列表
    public static final String getDetailListOnCar = apiHead + "delivery/delivery/getDetailListOnCar";
    //扫码确认列表
    public static final String scanCompleted = apiHead + "delivery/delivery/scanCompleted";
    //是否有权限
    public static final String isPermission = apiHead + "sys/position/checkUserHasPermissionByCode";
    //库位库存
    public static final String queryWhStockInfoByCode = apiHead + "wh/stockLedger/queryWhStockInfoByCode";
    //选择货品
    public static final String getListByPositionCode = apiHead + "wh/goods/inventory/getListByPositionCode";
    //扫描完毕
    public static final String updateDeliveryLoadStatus = apiHead + "delivery/delivery/updateDeliveryLoadStatus";
    //装车完毕
    public static final String loadCompleted = apiHead + "delivery/delivery/loadCompleted";
    //取消条码
    public static final String updateBatchByCaseCode = apiHead + "wh/caseCode/updateBatchByCaseCode";
    //是否是同一库位
    public static final String checkIsSameWharehouseByCode = apiHead + "sys/position/checkIsSameWharehouseByCode";
    //移库完毕
    public static final String transferFinished = apiHead + "wh/transfer/transferFinished";
    //实物收货
    public static final String goodsReceiptList = apiHead + "order/orderIn/app/material/list";
    //实物收货详情列表
    public static final String goodsgetDetailList = apiHead + "order/orderIn/app/material/getDetailList";
    //实物收货确认收货
    public static final String updateStatus = apiHead + "order/orderIn/updateMateralStatus";
    //上架单查看列表
    public static final String stockList = apiHead + "order/orderIn/app/stock/list";
    //上架单数量获取
    public static final String stockTotal = apiHead + "order/orderIn/app/stock/total";
    //上架单货品列表
    public static final String getOrderDetailList = apiHead + "order/orderIn/app/getOrderDetail";
    //获取仓库信息
    public static final String getWharehouse = apiHead + "order/orderIn/app/list/wharehouse";
    //生成上架单
    public static final String addGenerateList = apiHead + "wh/stockIn/app/add";
    //实物收货数量获取
    public static final String queryUnfinishedMaterialCount = apiHead + "order/orderIn/app/queryUnfinishedMaterialCount";

    //获取仓库
    public static final String queryWarehouse = apiHead + "order/orderIn/queryWarehouseByorgId";
    //获取库区
    public static final String queryRegion = apiHead + "order/orderIn/queryRegionListByWharehouseId";
    //获取Position库位
    public static final String queryPosition = apiHead + "order/orderIn/queryPositoinByRegionId";
    public static Object getGsonObject(String json, Object o) {
        Gson g = new Gson();
        o = g.fromJson(json, o.getClass());
        return o;
    }

    public static ArrayMap<String, Object> getJson2Object(String json)
            throws JSONException {
        ArrayMap<String, Object> map = new ArrayMap<String, Object>();
        JSONObject jsonObject = new JSONObject(json);
        Iterator<?> it = jsonObject.keys();
        String a;
        while (it.hasNext()) {

            a = it.next().toString();
            map.put(a, jsonObject.get(a).toString());
        }
        return map;
    }
    public static ArrayMap<String, Object> getJsonObject(String json) {
        ArrayMap<String, Object> map = new ArrayMap<String, Object>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<?> it = jsonObject.keys();
            String a;
            while (it.hasNext()) {
                a = it.next().toString();
                map.put(a, jsonObject.get(a).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static ArrayMap<String, Object> getJsonObjectByData(String json) {
        ArrayMap<String, Object> map = new ArrayMap<String, Object>();
        ArrayMap<String, Object> map2 = new ArrayMap<String, Object>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<?> it = jsonObject.keys();
            String a;
            while (it.hasNext()) {
                a = it.next().toString();
                map.put(a, jsonObject.get(a).toString());
            }
            map2 = getJsonObject(map.get("Data").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map2;
    }

    // 解析json数组数据
    public static ArrayList<ArrayMap<String, Object>> getJsonArray(
            String jsonString) {
        ArrayList<ArrayMap<String, Object>> list = new ArrayList<ArrayMap<String, Object>>();
        try {
            list = JSON.parseObject(jsonString,
                    new TypeReference<ArrayList<ArrayMap<String, Object>>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static HashMap<String, Object> getJsonObject2(String json) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<?> it = jsonObject.keys();
            String a;
            while (it.hasNext()) {
                a = it.next().toString();
                map.put(a, jsonObject.get(a).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    // 解析json数组数据
    public static ArrayList<HashMap<String, Object>> getJsonArray2(
            String jsonString) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        try {
            list = JSON.parseObject(jsonString,
                    new TypeReference<ArrayList<HashMap<String, Object>>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
