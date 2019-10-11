package com.llk.mytaobao.base;

import android.content.Context;

import com.llk.mytaobao.mvp.IView;

/**
 * Created by LG on 2019/10/11.
 */
public interface Presenter {
    void onCreate(Context context,IView view);

    void onStart();

    void onStop();

}