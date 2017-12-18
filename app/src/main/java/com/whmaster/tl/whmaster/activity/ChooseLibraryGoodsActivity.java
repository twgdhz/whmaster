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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.whmaster.tl.whmaster.R;
import com.whmaster.tl.whmaster.presenter.LibraryPresenter;
import com.whmaster.tl.whmaster.utils.RecyclerUtil;
import com.whmaster.tl.whmaster.view.IMvpView;

import java.util.ArrayList;

/**
 * Created by admin on 2017/11/14.
 * 选择货品
 */

public class ChooseLibraryGoodsActivity extends BaseActivity implements IMvpView{

    private XRecyclerView xRecyclerView;
    private RecyAdapter mAdapter;
    private Button mNextBtn;
    private Bundle mBundle;
    private String moldPositionCode;
    private LibraryPresenter libraryPresenter;
    private ArrayList<ArrayMap<String,Object>> mList;
    private TextView mOldText;
    private boolean isNext = false;
    private LinearLayout mEmptyLayout;
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
            moldPositionCode = mBundle.getString("code");
            libraryPresenter.getListByPositionCode(moldPositionCode);
            mOldText.setText("原库位编码："+moldPositionCode);
        }

        RecyclerUtil.init(xRecyclerView,this);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setPullRefreshEnabled(false);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.sub_btn:
                if(mList!=null && mList.size()>0){
                    for(int i=0;i<mList.size();i++){
                        if(mList.get(i).get("transferNum")!=null && mList.get(i).get("transferPackageNum")!=null){
                            if(Integer.parseInt(mList.get(i).get("transferNum").toString())>0 || Integer.parseInt(mList.get(i).get("transferPackageNum").toString())>0){
                                isNext = true;
                            }
                        }
                    }
                    if(isNext){
                        Bundle bundle = new Bundle();
                        bundle.putString("code",moldPositionCode);
                        bundle.putSerializable("list",mList);
                        openActivityForResult(ChooseNewLibraryActivity.class,0,bundle);
                    }else{
                        mAlertDialog.builder().setTitle("提示")
                                .setMsg("移库数量不能位0！")
                                .setPositiveButton("确认", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                }).show();
                    }
                }
                break;
        }
    }

    @Override
    public void initViews() {
        super.initViews();
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
                libraryPresenter.getListByPositionCode(moldPositionCode);
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
        mTitle.setText("选择货品");
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
            mList.get(position).put("transferNum","0");
            mList.get(position).put("transferPackageNum","0");
            if(mList.get(position).get("productName")!=null){
                holder.productName.setText("货品名称："+mList.get(position).get("productName").toString());
            }
            if(mList.get(position).get("productSku")!=null){
                holder.productSku.setText("货品SKU码："+mList.get(position).get("productSku").toString());
            }
            if(mList.get(position).get("consignorName")!=null){
                holder.userName.setText("货主："+mList.get(position).get("consignorName").toString());
            }
            if(mList.get(position).get("batchNo")!=null){
                holder.numbers.setText("批次号："+mList.get(position).get("batchNo").toString());
            }
            if(mList.get(position).get("availablePackageNum")!=null){
                holder.Planpackages.setText("可移整数："+mList.get(position).get("availablePackageNum")+"");
            }
            if(mList.get(position).get("availableNum")!=null){
                holder.PlanNums.setText("可移零散数："+mList.get(position).get("availableNum").toString());
            }
            holder.mActualPackages.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int max = Integer.parseInt(mList.get(position).get("availablePackageNum").toString());
                    if(s!=null && !s.toString().equals("")){
                        int a = Integer.parseInt(s.toString());
                        if(a<0){
                            mAlertDialog.builder().setTitle("提示").setMsg("数量不能小于0")
                                    .setPositiveButton("确认", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {}}).show();
                            holder.mActualPackages.setText("0");
                            a = 0;
                        }else if(a>max){
                            mAlertDialog.builder().setTitle("提示").setMsg("数量不能大于"+max)
                                    .setPositiveButton("确认", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {}}).show();
                            holder.mActualPackages.setText(max+"");
                            a = max;
                        }
                        mList.get(position).put("transferPackageNum",a+"");
                    }else{
                        holder.mActualPackages.setText("0");
                        mList.get(position).put("transferPackageNum","0");
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            holder.mActualNums.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int max = Integer.parseInt(mList.get(position).get("availableNum").toString());
                    if(s!=null && !s.toString().equals("")){
                        int a = Integer.parseInt(s.toString());
                        if(a<0){
                            mAlertDialog.builder().setTitle("提示").setMsg("数量不能小于0")
                                    .setPositiveButton("确认", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {}}).show();
                            holder.mActualNums.setText("0");
                            a = 0;
                        }else if(a>max){
                            mAlertDialog.builder().setTitle("提示").setMsg("数量不能大于"+max)
                                    .setPositiveButton("确认", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {}}).show();
                            holder.mActualNums.setText(max+"");
                            a = max;
                        }
                        mList.get(position).put("transferNum",a+"");
                    }else{
                        holder.mActualNums.setText("0");
                        mList.get(position).put("transferNum","0");
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            holder.mLeftReduceImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int m = Integer.parseInt(holder.mActualPackages.getText().toString());
                    if(m>0){
                        m--;
                        holder.mActualPackages.setText(m+"");
                    }
                    mList.get(position).put("transferPackageNum",m+"");
                }
            });
            holder.mLeftAddImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int m = Integer.parseInt(holder.mActualPackages.getText().toString());
                    int max = Integer.parseInt(mList.get(position).get("availablePackageNum").toString());
                    if(m<max){
                        m++;
                        holder.mActualPackages.setText(m+"");
                    }else{
                        mAlertDialog.builder().setTitle("提示").setMsg("数量不能大于"+max)
                                .setPositiveButton("确认", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {}}).show();
                    }
                    mList.get(position).put("transferPackageNum",m+"");
                }
            });
            holder.mRightReduceImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int m = Integer.parseInt(holder.mActualNums.getText().toString());
                    if(m>0){
                        m--;holder.mActualNums.setText(m+"");
                    }
                    mList.get(position).put("transferNum",m+"");
                }
            });
            holder.mRightAddImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int m = Integer.parseInt(holder.mActualNums.getText().toString());
                    int max = Integer.parseInt(mList.get(position).get("availableNum").toString());
                    if(m<max){
                        m++;
                        holder.mActualNums.setText(m+"");
                    }else{
                        mAlertDialog.builder().setTitle("提示").setMsg("数量不能大于"+max)
                                .setPositiveButton("确认", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {}}).show();
                    }
                    mList.get(position).put("transferNum",m+"");
                }
            });

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView productName, productSku, userName, numbers, Planpackages,PlanNums;
            EditText mActualPackages,mActualNums;
            ImageView mLeftReduceImage,mLeftAddImage,mRightReduceImage,mRightAddImage;
            public MyViewHolder(View view) {
                super(view);
                productName = view.findViewById(R.id.product_name);
                productSku = view.findViewById(R.id.product_sku);
                userName = view.findViewById(R.id.user_name);
                numbers = view.findViewById(R.id.product_numbers);
                Planpackages = view.findViewById(R.id.jhsj_dw_text2);
                PlanNums = view.findViewById(R.id.jhsj_g_text2);
                mActualPackages = view.findViewById(R.id.left_text);
                mActualNums = view.findViewById(R.id.right_text);
                mLeftReduceImage = view.findViewById(R.id.left_reduce_image);
                mLeftAddImage = view.findViewById(R.id.left_add_image);
                mRightReduceImage = view.findViewById(R.id.right_reduce_image);
                mRightAddImage = view.findViewById(R.id.right_add_image);
            }
        }
    }
}
