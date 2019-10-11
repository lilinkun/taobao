package com.llk.mytaobao.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.llk.mytaobao.base.BasePresenter;
import com.llk.mytaobao.bean.ResultBean;
import com.llk.mytaobao.bean.TbMaterielBean;
import com.llk.mytaobao.bean.TbjsonBean;
import com.llk.mytaobao.contract.TbAllContract;
import com.llk.mytaobao.http.callback.HttpResultCallBack;
import com.llk.mytaobao.manager.DataManager;
import com.llk.mytaobao.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/12/8.
 */

public class TbAllPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private TbAllContract tbAllContract;

    @Override
    public void onCreate(Context context, IView iView) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        tbAllContract = (TbAllContract) iView;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }

    public void setList(String PageIndex,String PageCount,String adzone_id,String q,String sort,String SessionId,String isMall){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","获取数据中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","TaobaoTbk");
        params.put("fun","dgMaterialOptional");
        params.put("PageIndex",PageIndex);
        params.put("PageCount",PageCount);
        params.put("adzone_id",adzone_id);
        params.put("has_coupon","1");
        params.put("sort",sort);
        params.put("SessionId",SessionId);
        if (q.equals("文体车品")){
            q = "汽车";
        }
        /*if (q.equals("全部")){
            q = " ";
        }*/
        params.put("q",q);
        params.put("is_tmall",isMall);

        mCompositeSubscription.add(manager.tbList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<TbjsonBean<ArrayList<TbMaterielBean>>,Object>(){

                    @Override
                    public void onResponse(TbjsonBean<ArrayList<TbMaterielBean>> arrayListTbjsonBean, String status,Object page) {
                        tbAllContract.onSuccess(arrayListTbjsonBean.getResultList());
//                        homeContract.onSuccess(arrayListTbjsonBean.getResults()));
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        tbAllContract.onError(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(ResultBean<TbjsonBean<ArrayList<TbMaterielBean>>, Object> result) {
                        super.onNext(result);
                    }
                }));
    }

}
