package com.llk.mytaobao.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.llk.mytaobao.R;
import com.llk.mytaobao.activity.BuyGoodsActivity;
import com.llk.mytaobao.activity.GoodsTypeActivity;
import com.llk.mytaobao.adapter.TbGoodsAdapter;
import com.llk.mytaobao.base.BaseFragment;
import com.llk.mytaobao.base.ProApplication;
import com.llk.mytaobao.bean.TbMaterielBean;
import com.llk.mytaobao.contract.TbAllContract;
import com.llk.mytaobao.presenter.TbAllPresenter;
import com.llk.mytaobao.util.ButtonUtils;
import com.llk.mytaobao.util.TaobaoUtil;
import com.llk.mytaobao.util.UiHelper;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by LG on 2019/10/11.
 */
public class TBAllFragment extends BaseFragment implements TbAllContract, TbGoodsAdapter.OnItemClickListener{

    @BindView(R.id.rv_all_tbgoods)
    RecyclerView mAllTbGoodsRv;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private int lastVisibleItem = 0;
    private int page_des = 0;
    private final String PAGE_COUNT = TaobaoUtil.PAGE_COUNT;
    private int PAGE_INDEX = 1;
    private TbGoodsAdapter tbGoodsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private TbAllPresenter tbAllPresenter = new TbAllPresenter();
    private ArrayList<TbMaterielBean> tbDisCountBeans;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_tb_all;
    }

    @Override
    public void initEventAndData() {
//            UToast.show(getActivity(),getArguments().getString("TBId"));
//        mTvTb.setText(getArguments().getString("TBId"));

        tbAllPresenter.onCreate(getActivity(),this);
        tbAllPresenter.setList(PAGE_INDEX + "",PAGE_COUNT,"64362000477",getArguments().getString("TBId"), TaobaoUtil.strs[page_des], "", GoodsTypeActivity.isMall+"");

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tbAllPresenter.setList(PAGE_INDEX + "",PAGE_COUNT,"64362000477",getArguments().getString("TBId"),TaobaoUtil.strs[page_des], "", GoodsTypeActivity.isMall+"");
            }
        });

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mAllTbGoodsRv.setLayoutManager(linearLayoutManager);
        mAllTbGoodsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (tbGoodsAdapter != null) {
                        if (tbGoodsAdapter.isFadeTips() == false && lastVisibleItem + 1 == tbGoodsAdapter.getItemCount()) {
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    PAGE_INDEX++;
                                    tbAllPresenter.setList(PAGE_INDEX + "",PAGE_COUNT,"64362000477",getArguments().getString("TBId"),"tk_total_sales_des", "", GoodsTypeActivity.isMall+"");
                                }
                            }, 200);
                        }

                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

//        TbGoodsAdapter tbGoodsAdapter = new TbGoodsAdapter(getActivity(), tbGoodsBeans,getLayoutInflater());
//        mAllTbGoodsRv.setAdapter(tbGoodsAdapter);

    }

    @Override
    public void onSuccess(ArrayList<TbMaterielBean> tbShopBeans) {

        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }

        if (tbDisCountBeans == null){
            tbDisCountBeans = tbShopBeans;
            tbGoodsAdapter = new TbGoodsAdapter(getActivity(), tbDisCountBeans,getLayoutInflater());
            mAllTbGoodsRv.setAdapter(tbGoodsAdapter);
            tbGoodsAdapter.setItemClickListener(this);
        }else {
            tbDisCountBeans.addAll(tbShopBeans);
            tbGoodsAdapter.setDatas(tbDisCountBeans);
        }
    }

    @Override
    public void onError(String msg) {

    }

    public void onPageChange(int page){
        tbDisCountBeans.clear();
        PAGE_INDEX = 1;
        tbAllPresenter.setList(PAGE_INDEX + "",PAGE_COUNT,"64362000477",getArguments().getString("TBId"),TaobaoUtil.strs[page], "", GoodsTypeActivity.isMall+"");
    }


    @Override
    public void onItemClick(int position,TbMaterielBean tbDisCountBean) {
        if (!ButtonUtils.isFastDoubleClick()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("discount", tbDisCountBean);
            UiHelper.launcherBundle(getActivity(), BuyGoodsActivity.class, bundle);
        }
    }

}

