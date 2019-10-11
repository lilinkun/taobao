package com.llk.mytaobao.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LG on 2019/10/11.
 */
public class TaobaoUtil {
    public static final int PAGE_HOMEPAGE = 0;
    public static final int PAGE_MALL = 1;
    //    public static final int PAGE_HEALTHHOME = 2;
    public static final int PAGE_ME = 2;

    public static  final String PAGE_COUNT = "20";

    public static final String APP_ID = "wx27fb4ad747521493";


    public static final int GOODS_ALL_WEAR = 3756;
    public static final int GOODS_WOMAN_WEAR = 3767;
    public static final int GOODS_HOUSE = 3758;
    public static final int GOODS_DIGITAL = 3759;
    public static final int GOODS_SHOES = 3762;
    public static final int GOODS_MAKEUP = 3763;
    public static final int GOODS_MAN_WEAR = 3764;
    public static final int GOODS_UNDERWEAR = 3765;
    public static final int GOODS_MOTHER = 3760;
    public static final int GOODS_FOOD = 3761;
    public static final int GOODS_MOTION = 3766;

    public static final String TYPEID = "TYPEID";
    public static final String LOGIN = "login";
    public static final String ORDERID = "orderid";
    public static final String ORDERAMOUNT = "orderamount";
    public static final String WHERE = "where";

    public static String RESULT_SUCCESS = "success";
    public static String RESULT_FAIL = "fail";

    public static String[] strs = {"tk_total_sales_des","total_sales_des","price_des","price_asc","tk_total_commi_des","tk_total_commi_asc"};


    public static final void setInputMethod(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public static String getCurDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String curDate = simpleDateFormat.format(date);
        return curDate;
    }

    public static String redecuStr(String total,String reduce){
        String dd = "";
        if(total.contains(reduce)){
            dd = total.substring(0,total.indexOf(reduce));
            dd = dd + total.substring(total.indexOf(reduce)+reduce.length(),total.length());
        }
        return dd;
    }
}
