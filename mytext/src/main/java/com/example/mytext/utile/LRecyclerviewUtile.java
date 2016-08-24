package com.example.mytext.utile;

import android.app.Activity;
import android.content.Context;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;

/**
 * Created by CTXD-24 on 2016/8/24.
 */

public class LRecyclerviewUtile implements UIThreadUtile.ThreadUpdata {

    private static LRecyclerviewUtile mLRecyclerviewUtile;
    private Context mContext;
    public LRecyclerviewUtile( Context mContext,LRecyclerviewScrollinterface mLRecyclerviewScrollinterface) {
        this.mLRecyclerviewScrollinterface = mLRecyclerviewScrollinterface;
        this.mContext=mContext.getApplicationContext();
    }
    public static LRecyclerviewUtile getInstence( Context mContext,LRecyclerviewScrollinterface mLRecyclerviewScrollinterface){
        return mLRecyclerviewUtile=new LRecyclerviewUtile(mContext,mLRecyclerviewScrollinterface);
    }

    public void LRecyclerviewScrollListener(final LRecyclerView mLRecyclerView,final LRecyclerviewScrollinterface mLRecyclerviewScrollinterface){

        mLRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(mLRecyclerView, LoadingFooter.State.Normal);

                UIThreadUtile.getInstence(mContext, (UIThreadUtile.ThreadUpdata) mLRecyclerviewScrollinterface.getActivity()).startUiThread(Type.DOWN);
            }

            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onBottom() {

            }

            @Override
            public void onScrolled(int distanceX, int distanceY) {

            }
        });
    }

    @Override
    public void Up() {

    }

    @Override
    public void down() {

    }

    @Override
    public void noNet() {

    }

    @Override
    public Activity getActivity() {
        return null;
    }
    private LRecyclerviewScrollinterface mLRecyclerviewScrollinterface;

    private interface LRecyclerviewScrollinterface extends UIThreadUtile.ThreadUpdata{


    }
}
