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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.whmaster.tl.whmaster.R;
import com.whmaster.tl.whmaster.common.Constants;
import com.whmaster.tl.whmaster.presenter.StoragePresenter;
import com.whmaster.tl.whmaster.utils.RecyclerUtil;
import com.whmaster.tl.whmaster.view.IMvpView;

import java.util.ArrayList;

/**
 * Created by admin on 2017/11/27.
 * 入库上架
 */

public class RksjActivity extends BaseActivity implements IMvpView {

    private StoragePresenter storagePresenter;
    private Bundle mBundle;
    private String mBusinessType = "10", mDetailId, storePositionId;
    private TextView mProductName, mSkuText, mKuquText, mKuweiText, mPlanZs, mPlanNum;
//    private ImageView mLeftReduce, mLeftAdd, mRightReduce, mRightAdd;
    private EditText mActualZs, mActualNum;
    private ArrayList<ArrayMap<String, Object>> mList;
    private int mMaxLeft, mMaxRight, mLeft, mRight;
    private Button mSubBtn;
    private XRecyclerView mRecyclerView;
    private ArrayList<ArrayMap<String, Object>> list;
    private ArrayMap<String, Object> map;
    private RecyAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.rksj_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerUtil.init(mRecyclerView, this);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setPullRefreshEnabled(false);
        storagePresenter = new StoragePresenter(this, this);
        storagePresenter.queryStorePositionDetail(mDetailId, mBusinessType);
    }

    @Override
    public void initViews() {
        super.initViews();
        mRecyclerView = findViewById(R.id.rksj_recyview);
        mSubBtn = findViewById(R.id.sub_btn);
        mSubBtn.setOnClickListener(this);
        mProductName = findViewById(R.id.product_name);
        mSkuText = findViewById(R.id.product_sku);
        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mDetailId = mBundle.getString("detailId");
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sub_btn:
//                if (Integer.parseInt(mActualZs.getText().toString()) > 0 || Integer.parseInt(mActualNum.getText().toString()) > 0) {
//                        msgLoadingDialog.builder().setMsg("正在上架").show();
//                    ArrayList list = new ArrayList();
//                    ArrayMap map = new ArrayMap();
//                    map.put("stockInDetailId", mDetailId);
//                    map.put("actPackageNum", mLeft + "");
//                    map.put("actNum", mRight + "");
//                    map.put("storePositionId", storePositionId);
//                    list.add(map);
                    logcat("数据上架"+list);
                    storagePresenter.shelfProductStockInTask(mDetailId, JSON.toJSONString(list));

                break;
            case R.id.left_reduce_image:
                if (mLeft > 0) {
                    mLeft--;
                    mActualZs.setText(mLeft + "");
                }
                break;
            case R.id.left_add_image:
                if (mLeft < mMaxLeft) {
                    mLeft++;
                    mActualZs.setText(mLeft + "");
                } else {
                    mAlertDialog.builder().setTitle("提示")
                            .setMsg("数量不能大于" + mMaxLeft)
                            .setPositiveButton("确认", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            }).show();
                }
                break;
            case R.id.right_reduce_image:
                if (mRight > 0) {
                    mRight--;
                    mActualNum.setText(mRight + "");
                }
                break;
            case R.id.right_add_image:
                if (mRight < mMaxRight) {
                    mRight++;
                    mActualNum.setText(mRight + "");
                } else {
                    mAlertDialog.builder().setTitle("提示")
                            .setMsg("数量不能大于" + mMaxRight)
                            .setPositiveButton("确认", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            }).show();
                }
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
        mTitle.setText("入库上架");
    }

    @Override
    public void onFail(String errorMsg) {
        mAlertDialog.builder().setTitle("提示")
                .setMsg(errorMsg)
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAlertDialog.dismiss();
                    }
                }).show();
    }

    private void callBack() {
        Bundle bundle = new Bundle();
        bundle.putString("type", "refresh");
        setResultOk(bundle);
    }

    @Override
    public void onSuccess(String type, Object object) {
        final ArrayMap<String, Object> map = (ArrayMap<String, Object>) object;
        switch (type) {
            case "detail":
                if (map != null && map.size() > 0) {
                    list = new ArrayList<>();

                    mList = Constants.getJsonArray(map.get("storePositionList") + "");

                    mProductName.setText("货品名称：" + map.get("productName"));
                    mSkuText.setText("货品SKU码：" + map.get("productSku"));
                    mAdapter = new RecyAdapter();
                    mRecyclerView.setAdapter(mAdapter);
                }

                break;
            case "up":
                callBack();
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
        int left = 0, right = 0, maxLeft = 0, maxRight = 0;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    RksjActivity.this).inflate(R.layout.rksj_item_layout, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final RecyAdapter.MyViewHolder holder, final int position) {

            if (mList.get(position).get("planPackageNum") != null && !mList.get(position).get("planPackageNum").toString().equals("")) {
                holder.planZs.setText(mList.get(position).get("planPackageNum").toString());
                holder.mZs.setText(mList.get(position).get("planPackageNum").toString());
                maxLeft = Integer.parseInt(mList.get(position).get("planPackageNum") + "");
                left = maxLeft;
            } else {
                holder.planZs.setText("0");
                holder.mZs.setText("0");
            }
            if (mList.get(position).get("planNum") != null && !mList.get(position).get("planNum").toString().equals("")) {
                holder.planNum.setText(mList.get(position).get("planNum").toString());
                holder.mNum.setText(mList.get(position).get("planNum").toString());
                maxRight = Integer.parseInt(mList.get(position).get("planNum") + "");
                right = maxRight;
            } else {
                holder.planNum.setText("0");
                holder.mNum.setText("0");
            }
            storePositionId = mList.get(position).get("storePositionId") + "";
            String s = mList.get(position).get("positionCode").toString();
            int index = s.indexOf('-', s.indexOf('-') + 1);
            if (index >= 0) {
                String ss = s.substring(index + 1);
                holder.kuwei.setText("库位：" + ss);
            }
            holder.kuqu.setText("库区：" + mList.get(position).get("regionName"));


            holder.mZs.setTag(position);
            holder.mNum.setTag(position);
            holder.mLeftReduce.setOnClickListener(new onClick(holder));
            holder.mLeftAdd.setOnClickListener(new onClick(holder));
            holder.mRightReduce.setOnClickListener(new onClick(holder));
            holder.mRightAdd.setOnClickListener(new onClick(holder));
            holder.mZs.addTextChangedListener(new leftTextWatch(holder));
            holder.mNum.addTextChangedListener(new rightTextWatch(holder));
            map = new ArrayMap();

            map.put("stockInDetailId", mDetailId);
            map.put("actPackageNum", left + "");
            map.put("actNum", right + "");
            map.put("storePositionId", storePositionId);
            list.add(map);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class onClick implements View.OnClickListener {
            private MyViewHolder holder;

            public onClick(MyViewHolder viewholder) {
                this.holder = viewholder;
            }

            @Override
            public void onClick(View v) {
                int position = (int) holder.mZs.getTag();
                switch (v.getId()) {
                    case R.id.left_reduce_image:
                        left = Integer.parseInt(holder.mZs.getText().toString());
                        logcat("当前left====" + left);
                        if (left > 0) {
                            left--;
                            holder.mZs.setText(left + "");
                            list.get(position).put("actPackageNum", left + "");
                        }
                        break;
                    case R.id.left_add_image:
                        maxLeft = Integer.parseInt(mList.get(position).get("planPackageNum") + "");
                        left = Integer.parseInt(holder.mZs.getText().toString());
                        if (left < maxLeft) {
                            left++;
                            holder.mZs.setText(left + "");
                            list.get(position).put("actPackageNum", left + "");
                        }
                        break;
                    case R.id.right_reduce_image:
                        right = Integer.parseInt(holder.mNum.getText().toString());
                        if (right > 0) {
                            right--;
                            holder.mNum.setText(right + "");
                            list.get(position).put("actNum", right + "");
                        }
                        break;
                    case R.id.right_add_image:
                        maxRight = Integer.parseInt(mList.get(position).get("planNum") + "");
                        right = Integer.parseInt(holder.mNum.getText().toString());
                        if (right < maxRight) {
                            right++;
                            holder.mNum.setText(right + "");
                            list.get(position).put("actNum", right + "");
                        }
                        break;
                }
            }
        }

        public class leftTextWatch implements TextWatcher {
            private MyViewHolder holder;


            public leftTextWatch(MyViewHolder viewHolder) {
                this.holder = viewHolder;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                {
                    int position = (int) holder.mZs.getTag();
                    maxLeft = Integer.parseInt(mList.get(position).get("planPackageNum") + "");
                    int a = 0;
                    if (s != null && !s.toString().equals("")) {
                        a = Integer.parseInt(s.toString());
                        if (a < 0) {
                            holder.mZs.setText("0");
                        } else if (a > maxLeft) {
                            mAlertDialog.builder().setTitle("提示").setMsg("数量不能大于" + maxLeft)
                                    .setPositiveButton("确认", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                        }
                                    }).show();
                            holder.mZs.setText(maxLeft + "");
                        }
                    } else {
                        holder.mZs.setText("0");
                    }
                    left = Integer.parseInt(holder.mZs.getText().toString());
                    list.get(position).put("actPackageNum", left + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        }

        public class rightTextWatch implements TextWatcher {
            private MyViewHolder holder;
            public rightTextWatch(MyViewHolder viewHolder) {
                this.holder = viewHolder;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                {
                    int position = (int) holder.mNum.getTag();
                    maxRight = Integer.parseInt(mList.get(position).get("planNum") + "");
                    int a = 0;
                    if (s != null && !s.toString().equals("")) {
                        a = Integer.parseInt(s.toString());
                        if (a < 0) {
                            holder.mNum.setText("0");
                        } else if (a > maxRight) {
                            mAlertDialog.builder().setTitle("提示").setMsg("数量不能大于" + maxRight)
                                    .setPositiveButton("确认", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                        }
                                    }).show();
                            holder.mNum.setText(maxRight + "");
                        }
                    } else {
                        holder.mNum.setText("0");
                    }
                    right = Integer.parseInt(holder.mNum.getText().toString());
                    list.get(position).put("actNum", right + "");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView planNum, planZs, kuqu, kuwei;
            EditText mNum, mZs;
            ImageView mLeftReduce, mLeftAdd, mRightReduce, mRightAdd;

            public MyViewHolder(View view) {
                super(view);
                mLeftReduce = view.findViewById(R.id.left_reduce_image);
                mLeftAdd = view.findViewById(R.id.left_add_image);
                mRightReduce = view.findViewById(R.id.right_reduce_image);
                mRightAdd = view.findViewById(R.id.right_add_image);

                planZs = view.findViewById(R.id.plan_zs_text);
                planNum = view.findViewById(R.id.plan_num_text);
                kuqu = view.findViewById(R.id.library_qu);
                kuwei = view.findViewById(R.id.library_wei);
                mZs = view.findViewById(R.id.left_text);
                mNum = view.findViewById(R.id.right_text);
            }
        }
    }
}
