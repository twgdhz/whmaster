package com.whmaster.tl.whmaster.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.whmaster.tl.whmaster.R;
import com.whmaster.tl.whmaster.utils.RecyclerUtil;

/**
 * Created by admin on 2017/12/26.
 * 拣货单
 */

public class TempPickingListActivity extends BaseActivity{

    private XRecyclerView mRecyclerView;
    private RecyAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.temp_pick_list_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerUtil.init(mRecyclerView,this);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mRecyclerView.refreshComplete();
                        mRecyclerView.setLoadingMoreEnabled(true);
                    }
                }, 1000);
            }
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mRecyclerView.setLoadingMoreEnabled(false);
                        mRecyclerView.loadMoreComplete();
                    }
                }, 1000);
            }
        });
        mAdapter = new RecyAdapter();
//        mRecyclerView.setAdapter(mAdapter);
        //
    }

    @Override
    public void initViews() {
        super.initViews();
//        mRecyclerView = findViewById(R.id.pick_list_recyview);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
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
        mTitle.setText("拣货单");
    }

    class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    TempPickingListActivity.this).inflate(R.layout.pick_list_item_layout, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.mContentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(PickDetailListActivity.class,null);
                }
            });
        }
//        public void notifiList(ArrayList<ArrayMap<String, Object>> list) {
//            mList.addAll(list);
//            this.notifyDataSetChanged();
//        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView mOrderCode, mProductName, mProductNum;
            LinearLayout mContentLayout;

            public MyViewHolder(View view) {
                super(view);
                mContentLayout = view.findViewById(R.id.content_layout);
            }
        }
    }
}
