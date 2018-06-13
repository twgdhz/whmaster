package com.whmaster.tl.whmaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.whmaster.tl.whmaster.R;
import com.whmaster.tl.whmaster.presenter.LibraryPresenter;
import com.whmaster.tl.whmaster.utils.DensityUtils;
import com.whmaster.tl.whmaster.utils.RecyclerUtil;
import com.whmaster.tl.whmaster.view.IMvpView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2017/11/14.
 * 选择货品
 */

public class ChooseLibraryGoodsActivity extends BaseActivity implements IMvpView{

    private XRecyclerView xRecyclerView;
    private RecyAdapter mAdapter;
    private Button mNextBtn;
    private Bundle mBundle;
    private String mPositionCode,mCode;
    private LibraryPresenter libraryPresenter;
    private ArrayList<ArrayMap<String,Object>> mList;
    private TextView mOldText;
    private boolean isNext = false;
    private LinearLayout mEmptyLayout,mTitleLayout;
    private ArrayList<ArrayMap<String,Object>> mDataList;

    @Override
    protected int getLayoutId() {
        return R.layout.chose_goods_list_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        libraryPresenter = new LibraryPresenter(this,this);
        mBundle = getIntent().getExtras();
        if(mBundle!=null){
            mPositionCode = mBundle.getString("positionCode");
            mCode = mBundle.getString("code");
//            libraryPresenter.getListByPositionCode(moldPositionCode);
            mOldText.setText("库位码："+mPositionCode);
        }
        RecyclerUtil.init(xRecyclerView,this);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setPullRefreshEnabled(false);
        libraryPresenter.getProductByPosition(mCode);
//        libraryPresenter.getProductByPosition(mPositionCode);
        mDataList = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.sub_btn:
                if(mDataList!=null && mDataList.size()>0){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list",mDataList);
                    bundle.putString("code",mCode);
                    startActivity(ChooseGoodsNumbersActivity.class,bundle);
                }
                break;
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        mTitleLayout = findViewById(R.id.title);
        mNextBtn = findViewById(R.id.sub_btn);
        mNextBtn.setOnClickListener(this);
        xRecyclerView = findViewById(R.id.choose_goods_recy_view);
        mOldText = findViewById(R.id.old_code);
        mEmptyLayout = findViewById(R.id.empty_layout);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                isNext = false;
                break;
        }
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
    public void logcat(String msg) {
        super.logcat(msg);
    }

    @Override
    public void setHeader() {
        super.setHeader();
        mTitleLayout.setVisibility(View.GONE);
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
    public void onSuccess(String type, Object object) {
        switch (type){
            case "list":
                mList = (ArrayList<ArrayMap<String, Object>>) object;
                if(mList!=null && mList.size()>0){
                    mAdapter = new RecyAdapter();
                    xRecyclerView.setAdapter(mAdapter);
                    xRecyclerView.setVisibility(View.VISIBLE);
                    mEmptyLayout.setVisibility(View.GONE);
                    mNextBtn.setVisibility(View.VISIBLE);
                }else{
                    mNextBtn.setVisibility(View.GONE);
                    xRecyclerView.setVisibility(View.GONE);
                    mEmptyLayout.setVisibility(View.VISIBLE);
                }
                  break;
        }
    }

    @Override
    public void showLoading() {
        loadingDialog.builder().show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.MyViewHolder> {
        MyViewHolder holder;
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            holder = new MyViewHolder(LayoutInflater.from(
                    ChooseLibraryGoodsActivity.this).inflate(R.layout.chose_goods_list_item_layout, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            if(mList.get(position).get("productName")!=null){
                holder.productName.setText(mList.get(position).get("productName").toString());
            }
            if(mList.get(position).get("productSkuCode")!=null){
                holder.productSku.setText(mList.get(position).get("productSkuCode").toString());
            }
            if(mList.get(position).get("batchNo")!=null){
                holder.productNo.setText(mList.get(position).get("batchNo").toString());
            }
            if(mList.get(position).get("inventoryNum")!=null){
                holder.sumNumbers.setText(mList.get(position).get("inventoryNum").toString()+mList.get(position).get("baseUnitCn").toString());
            }
            if(mList.get(position).get("packageSpec")!=null){
                holder.productGuige.setText(mList.get(position).get("packageSpec").toString());
            }
            if(mList.get(position).get("packageCount")!=null){
                int packCount =  Integer.parseInt(mList.get(position).get("packageCount").toString());
                int moveNum = Integer.parseInt(mList.get(position).get("inventoryNum").toString()) % packCount;
                int moveZs = Integer.parseInt(mList.get(position).get("inventoryNum").toString()) / packCount;
                if(moveZs>0){
                    holder.productNumbers.setText(moveZs+mList.get(position).get("packageUnitCn").toString() + "/" + moveNum+mList.get(position).get("baseUnitCn").toString());
                }else{
                    holder.productNumbers.setText(moveNum+mList.get(position).get("baseUnitCn").toString());
                }
            }
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        mDataList.add(mList.get(position));
                    }else{
                        mDataList.remove(mList.get(position));
                    }
                    logcat(mDataList+"=====");
                }
            });
        }
        @Override
        public int getItemCount() {
            return mList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView productName, productSku,productNo,productNumbers,productGuige,sumNumbers;
            LinearLayout mContentLayout;
            CheckBox checkBox;
            public MyViewHolder(View view) {
                super(view);
                checkBox = view.findViewById(R.id.check_box);
                mContentLayout = view.findViewById(R.id.content_layout);
//                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) mContentLayout.getLayoutParams();
//                params.height = DensityUtils.dp2px(ChooseLibraryGoodsActivity.this,250);
//                mContentLayout.setLayoutParams(params);
                productName = view.findViewById(R.id.product_name);
                productSku = view.findViewById(R.id.product_sku_value);
                productNo = view.findViewById(R.id.product_no_value);
                productNumbers = view.findViewById(R.id.product_ke_move_value);
                productGuige = view.findViewById(R.id.product_guige_value);
                sumNumbers = view.findViewById(R.id.product_he_ji_value);
            }
        }
    }
}
