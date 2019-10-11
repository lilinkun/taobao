package com.llk.mytaobao.http;


import com.llk.mytaobao.bean.ResultBean;
import com.llk.mytaobao.bean.TbMaterielBean;
import com.llk.mytaobao.bean.TbjsonBean;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by LG on 2018/11/7.
 */

public interface RetrofitService {

//    @FormUrlEncoded
//    @POST("LoginDateServlet")
//    Observable<HashMap<String,String>> setTest(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<TbjsonBean<ArrayList<TbMaterielBean>>, Object>> tbApi(@FieldMap Map<String, String> map);


}
