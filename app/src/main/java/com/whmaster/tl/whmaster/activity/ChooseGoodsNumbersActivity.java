package com.whmaster.tl.whmaster.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.whmaster.tl.whmaster.R;
import com.whmaster.tl.whmaster.utils.RecyclerUtil;
import com.whmaster.tl.whmaster.view.IMvpView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admin on 2018/6/11.
 * 移库数量选择
 */

public class ChooseGoodsNumbersActivity extends BaseActivity implements IMvpView{

    private Bundle mBundle;
    private XRecyclerView mRecyclerView;
    private ArrayList<HashMap<String,Object>> mList;
    private RecyAdapter mAdapter;
    private LinearLayout mTitleLayout;
    private Button mBtn;
    private EditText mMemoEdit;
    private String mPositionCode;
    private TextView mCodeText;

    @Override
    protected int getLayoutId() {
        return R.layout.choose_goods_numbers_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerUtil.init(mRecyclerView,this);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mBundle = getIntent().getExtras();
        if(mBundle!=null){
            mList = (ArrayList<HashMap<String, Object>>) mBundle.getSerializable("list");
            mPositionCode = mBundle.getString("code");
        }
        mCodeText.setText("移动单位："+mPositionCode);
        mAdapter = new RecyAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initViews() {
        super.initViews();
        mCodeText = findViewById(R.id.old_code);
        mTitleLayout = findViewById(R.id.title);
        mRecyclerView = findViewById(R.id.choose_goods_numbers_recy_view);
        mBtn = findViewById(R.id.sub_btn);
        mBtn.setOnClickListener(this);
        mMemoEdit = findViewById(R.id.memo_edit);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.sub_btn:
                Bundle bundle = new Bundle();
                bundle.putSerializable("list",mList);
                bundle.putString("memo",mMemoEdit.getText().toString());
                bundle.putString("code",mPositionCode);
                startActivity(ChooseGoodsMoveActivity.class,bundle);
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
    public void onSuccess(String type, Object object) {

    }
    @Override
    public void onFail(String errorMsg) {

    }
    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.MyViewHolder> {
        MyViewHolder holder;
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            holder = new MyViewHolder(LayoutInflater.from(
                    ChooseGoodsNumbersActivity.this).inflate(R.layout.chose_goods_numbers_list_item_layout, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            mList.get(position).put("moveNum","0");
            if(mList.get(position).get("productName")!=null){
                holder.productName.setText(mList.get(position).get("productName").toString());
            }
            if(mList.get(position).get("productSkuCode")!=null){
                holder.productSku.setText(mList.get(position).get("productSkuCode").toString());
            }
            if(mList.get(position).get("batchNo")!=null){
                holder.productNo.setText(mList.get(position).get("batchNo").toString());
            }
            if(mList.get(position).get("packageSpec")!=null){
                holder.productGuige.setText(mList.get(position).get("packageSpec").toString());
            }
            if(mList.get(position).get("inventoryNum")!=null){
                holder.sumNumbers.setText(mList.get(position).get("baseUnitCn").toString());
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
            holder.baseUnitEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int m = 0,sum = 0,zs = 0;
                    int packCount = Integer.parseInt(mList.get(position).get("packageCount").toString());
                    if(s!=null && !s.toString().equals("")){
                        m = Integer.parseInt(s.toString());
                    }
                    if(holder.baseZsEdit.getText().toString()!=null && !holder.baseZsEdit.getText().toString().equals("")){
                        zs = Integer.parseInt(holder.baseZsEdit.getText().toString());
                    }
                    sum = m + packCount*zs;
                    if(sum <= Integer.parseInt(mList.get(position).get("inventoryNum").toString())){
                        holder.sumNumbers.setText(sum + mList.get(position).get("baseUnitCn").toString());
                        mList.get(position).put("moveNum",sum+"");
                    }else{
                        Toast.makeText(ChooseGoodsNumbersActivity.this,"移库数量大于可移库总量",Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            holder.baseZsEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int m = 0,sum = 0,zs = 0;
                    int packCount = Integer.parseInt(mList.get(position).get("packageCount").toString());
                    if(s!=null && !s.toString().equals("")){
                        zs = Integer.parseInt(s.toString());
                    }
                    if(holder.baseUnitEdit.getText().toString()!=null && !holder.baseUnitEdit.getText().toString().equals("")){
                        m = Integer.parseInt(holder.baseUnitEdit.getText().toString());
                    }
                    sum = m + packCount*zs;
                    if(sum <= Integer.parseInt(mList.get(position).get("inventoryNum").toString())) {
                        holder.sumNumbers.setText(sum + mList.get(position).get("baseUnitCn").toString());
                        mList.get(position).put("moveNum",sum+"");
                    }else{
                        Toast.makeText(ChooseGoodsNumbersActivity.this,"移库数量大于可移库总量",Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            holder.baseUnit.setText(mList.get(position).get("baseUnitCn").toString());
            holder.baseZs.setText(mList.get(position).get("packageUnitCn").toString());
        }
        @Override
        public int getItemCount() {
            return mList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView productName, productSku,productNo,productNumbers,productGuige,sumNumbers,baseUnit,baseZs;
            EditText baseUnitEdit,baseZsEdit;
            LinearLayout mContentLayout;
            public MyViewHolder(View view) {
                super(view);
                baseUnitEdit = view.findViewById(R.id.base_unit_edit);
                baseZsEdit = view.findViewById(R.id.base_zs_edit);
                baseUnit = view.findViewById(R.id.base_unit_text);
                baseZs = view.findViewById(R.id.base_zs_text);
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
