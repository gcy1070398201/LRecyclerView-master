package com.example.mytext.utile;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by CTXD-24 on 2016/8/23.
 */

public class UIThreadUtile  {
    private static UIThreadUtile mUiThreadUtile;
    private Context mContext;
    private boolean isThread=true;
    TaskHandle taskHandle;
    public UIThreadUtile(Context context,ThreadUpdata mThreadUpdata) {
        this.mContext = context.getApplicationContext();
        this.mThreadUpdata = mThreadUpdata;
        taskHandle=new TaskHandle(mThreadUpdata.getActivity());
    }
    public static UIThreadUtile getInstence(Context context,ThreadUpdata mThreadUpdata){
        return mUiThreadUtile=new UIThreadUtile(context,mThreadUpdata);
    }
    /**
     * 开启线程
     * @param
     */
    public void startUiThread(final Type mType){
        new Thread(){
            @Override
            public void run() {
                super.run();
                if (isThread){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message=new Message();
                if (NetworkUtils.isNetAvailable(mContext)){
                    message.obj=mType;
                }else{
                    message.obj=Type.NONET;
                }
                    taskHandle.sendMessage(message);
                }
            }
        }.start();
    }
    /**
     * 关闭线程
     * @param
     */
    public void clossUiThread(){
        isThread=false;
    }
    private ThreadUpdata mThreadUpdata;
    public interface ThreadUpdata{
        /**
         * 上拉异步
         */
        void Up();
        /**
         * 下拉异步
         */
        void down();
        /**
         * 没网异步
         */
        void noNet();
        /**
         * 获得资源
         */
        Activity getActivity();
    }
    private class TaskHandle extends  Handler{
        WeakReference mWeakReference;
        public TaskHandle(Activity ac) {
            mWeakReference=new WeakReference<>(ac);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
          final Activity activity = (Activity) mWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            Type mType= (Type) msg.obj;
            switch (mType){
                case UP:
                    mThreadUpdata.Up();
                    break;
                case DOWN:
                    mThreadUpdata.down();
                    break;
                case NONET:
                    mThreadUpdata.noNet();
                    break;
            }
        }
    }
}
