package com.whmaster.tl.whmaster.activity.inventory;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.whmaster.tl.whmaster.R;
import com.whmaster.tl.whmaster.activity.BaseActivity;
import com.whmaster.tl.whmaster.utils.RecyclerUtil;

import java.util.ArrayList;

/**
 * Created by admin on 2018/4/3.
 */

public class ChoseInvertoryDetailListActivity extends BaseActivity{

    private XRecyclerView mRecyclerView;
    private RecyAdapter mAdapter;
    private LinearLayout mTitleLayout;
    private ImageView mBackImage;

    @Override
    protected int getLayoutId() {
        return R.layout.chose_invertory_detail_list_layout;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerUtil.init(mRecyclerView, this);
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
                    }
                }, 1000);
            }
        });

        mAdapter = new RecyAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initViews() {
        super.initViews();
        mTitleLayout = findViewById(R.id.title);
        mTitleLayout.setVisibility(View.GONE);
        mRecyclerView = findViewById(R.id.invertory_detail_list_recyview);
        mBackImage = findViewById(R.id.back_image);
        mBackImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.back_image:
                finish();
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
    }

    class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.MyViewHolder> {

        @Override
        public RecyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyAdapter.MyViewHolder holder = new RecyAdapter.MyViewHolder(LayoutInflater.from(
                    ChoseInvertoryDetailListActivity.this).inflate(R.layout.chose_invertory_detail_list_item, parent,
                    false));
            return holder;
        }

        public void notifiList(ArrayList<ArrayMap<String, Object>> list) {
        }

        @Override
        public void onBindViewHolder(RecyAdapter.MyViewHolder holder, final int position) {
            holder.mContentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    openActivityForResult(ChoseInvertoryDetailActivity.class, 0, bundle);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 2;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout mContentLayout;

            public MyViewHolder(View view) {
                super(view);
                mContentLayout = view.findViewById(R.id.content_layout);
            }
        }
    }
}
