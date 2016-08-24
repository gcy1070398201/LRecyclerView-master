package com.example.mytext;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytext.adapter.ListBaseAdapter;
import com.example.mytext.api.Api;
import com.example.mytext.mode.SelectGoodEntity;
import com.example.mytext.utile.Type;
import com.example.mytext.utile.UIThreadUtile;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CTXD-24 on 2016/8/22.
 */

public class DataActivity extends AppCompatActivity implements UIThreadUtile.ThreadUpdata{
    /**服务器端一共多少条数据*/
    private static final int TOTAL_COUNTER = 23;

    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 20;
    /**已经获取到多少条数据了*/
    private static int mCurrentCounter = 0;
    private int page=1;
    List<SelectGoodEntity.Goods> mGoodList=new ArrayList<>();
    LRecyclerView mLRecyclerView;
    LRecyclerViewAdapter mLRecyclerViewAdapter;
    Context mContext;
    boolean isRefresh=false;
    DataAdapter mDataAdapter;
    Api mApi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        mApi=App.getInstance().getApi();
        mLRecyclerView= (LRecyclerView) findViewById(R.id.ll);
        mLRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initData("",page,8);
        initView();
    }

    private void initView() {
        mLRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(mLRecyclerView, LoadingFooter.State.Normal);
                mCurrentCounter=0;
                isRefresh=true;
                UIThreadUtile.getInstence(mContext,DataActivity.this).startUiThread(Type.DOWN);
            }
            @Override
            public void onScrollUp() {

            }
            @Override
            public void onScrollDown() {

            }

            @Override
            public void onBottom() {
                LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mLRecyclerView);
                if(state == LoadingFooter.State.Loading) {
                    return;
                }
                if (mCurrentCounter<TOTAL_COUNTER){
                    RecyclerViewStateUtils.setFooterViewState(DataActivity.this, mLRecyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
                    UIThreadUtile.getInstence(mContext,DataActivity.this).startUiThread(Type.UP);
                }else{
                    RecyclerViewStateUtils.setFooterViewState(DataActivity.this, mLRecyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
                }
            }
            @Override
            public void onScrolled(int distanceX, int distanceY) {

            }
        });
    }

    private void initData(String str_name,final int page,int supplierId) {
        mApi.getShopInfo(str_name, page,supplierId).enqueue(new Callback<SelectGoodEntity>() {
            @Override
            public void onResponse(Call<SelectGoodEntity> call, Response<SelectGoodEntity> response) {
                if (response.isSuccessful()){
                    SelectGoodEntity mSelectGoodEntity=response.body();

                    if (page==1){
                        if (mSelectGoodEntity.getData().getGoods().size()>0){
                            mCurrentCounter=mSelectGoodEntity.getData().getGoods().size();
                          for (int i = 0; i <mSelectGoodEntity.getData().getGoods().size() ; i++) {
                            mGoodList.add(mSelectGoodEntity.getData().getGoods().get(i));
                          }
                        }
                    mDataAdapter=new DataAdapter(mContext);
                    mDataAdapter.addAll(mGoodList);
                    mLRecyclerViewAdapter=new LRecyclerViewAdapter(mContext,mDataAdapter);
                    mLRecyclerView.setAdapter(mLRecyclerViewAdapter);
                    mLRecyclerView.setRefreshProgressStyle(ProgressStyle.BallGridPulse);
                    mLRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

                    }else{
                        addItems(mSelectGoodEntity.getData().getGoods());
                    }

                }else{
                    Toast.makeText(DataActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SelectGoodEntity> call, Throwable t) {
                Toast.makeText(DataActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }
    private void addItems(List<SelectGoodEntity.Goods> list) {
        mDataAdapter.addAll(list);
        mCurrentCounter += list.size();
    }
    @Override
    public void Up() {
        page++;
        initData("",page,8);
        if (isRefresh) {
            isRefresh = false;
            mLRecyclerView.refreshComplete();
            notifyDataSetChanged();
        }else{
            RecyclerViewStateUtils.setFooterViewState(mLRecyclerView, LoadingFooter.State.Normal);
        }
    }
    @Override
    public void down() {
        page=1;
        mDataAdapter.clear();
        initData("",page,8);
        if (isRefresh) {
            isRefresh = false;
            mLRecyclerView.refreshComplete();
            notifyDataSetChanged();
        }else{
            RecyclerViewStateUtils.setFooterViewState(mLRecyclerView, LoadingFooter.State.Normal);
        }
    }
    @Override
    public void noNet() {
        if (isRefresh){
            isRefresh=false;
            mLRecyclerView.refreshComplete();
            mLRecyclerViewAdapter.notifyDataSetChanged();
        }else{
            RecyclerViewStateUtils.setFooterViewState(DataActivity.this, mLRecyclerView, REQUEST_COUNT, LoadingFooter.State.NetWorkError, mFooterClick);
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(DataActivity.this, mLRecyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
            UIThreadUtile.getInstence(mContext,DataActivity.this).startUiThread(Type.UP);
        }
    };
    private class DataAdapter extends ListBaseAdapter<SelectGoodEntity.Goods> {

        private LayoutInflater mLayoutInflater;

        public DataAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
            mContext = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.list_item_text, parent, false));
        }
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SelectGoodEntity.Goods item=mDataList.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.textView.setText(item.getName());
        }
        private class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;
            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.info_text);
            }
        }
    }
}
