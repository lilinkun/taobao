package com.llk.mytaobao.contract;

import com.llk.mytaobao.bean.TbMaterielBean;
import com.llk.mytaobao.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/10/11.
 */
public interface TbAllContract extends IView {
    public void onSuccess(ArrayList<TbMaterielBean> tbShopBeans);
    public void onError(String msg);

}

