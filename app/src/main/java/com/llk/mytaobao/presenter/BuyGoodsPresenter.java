package com.llk.mytaobao.presenter;

import android.content.Context;

import com.llk.mytaobao.base.BasePresenter;
import com.llk.mytaobao.contract.BuyGoodsContract;
import com.llk.mytaobao.http.callback.HttpResultCallBack;
import com.llk.mytaobao.manager.DataManager;
import com.llk.mytaobao.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/10/11.
 */
public class BuyGoodsPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private BuyGoodsContract buyGoodsContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        buyGoodsContract = (BuyGoodsContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }


}
