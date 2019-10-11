package com.llk.mytaobao.manager;

import android.content.Context;


import com.llk.mytaobao.bean.ResultBean;
import com.llk.mytaobao.bean.TbMaterielBean;
import com.llk.mytaobao.bean.TbjsonBean;
import com.llk.mytaobao.http.RetrofitHelper;
import com.llk.mytaobao.http.RetrofitService;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Observable;


/**
 * Created by LG on 2018/11/27.
 */
public class DataManager {
    private RetrofitService mRetrofitService;

    public DataManager(Context context){
        this.mRetrofitService = RetrofitHelper.getInstance(context).getServer();
    }

    public Observable<ResultBean<TbjsonBean<ArrayList<TbMaterielBean>>, Object>> tbList(HashMap<String,String> hashMap){
        return mRetrofitService.tbApi(hashMap);
    }

}
