package com.llk.mytaobao.bean;

/**
 * Created by LG on 2019/10/11.
 */
public class TbjsonBean<T> {
    private T ResultList;

    public T getResultList() {
        return ResultList;
    }

    public void setResultList(T resultList) {
        ResultList = resultList;
    }
}
