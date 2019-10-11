package com.llk.mytaobao.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.llk.mytaobao.R;
import com.llk.mytaobao.adapter.SmallImageAdapter;
import com.llk.mytaobao.base.BaseActivity;
import com.llk.mytaobao.base.ProApplication;
import com.llk.mytaobao.bean.TbMaterielBean;
import com.llk.mytaobao.contract.BuyGoodsContract;
import com.llk.mytaobao.presenter.BuyGoodsPresenter;
import com.llk.mytaobao.util.Eyes;
import com.llk.mytaobao.util.TaobaoUtil;
import com.llk.mytaobao.util.UiHelper;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/10/11.
 */
public class BuyGoodsActivity extends BaseActivity implements BuyGoodsContract {

    @BindView(R.id.img_big_icon)
    ImageView mImgBigIcon;
    @BindView(R.id.img_small_icon)
    ImageView mImgSmallIcon;
    @BindView(R.id.tx_title)
    TextView mTvTitle;
    @BindView(R.id.tx_pay_money)
    TextView mTvMoney;
    @BindView(R.id.tx_ord_price)
    TextView mOrdPrice;
    @BindView(R.id.tx_quan_price)
    TextView mCouponPrice;
    @BindView(R.id.tx_tb_money)
    TextView mTbMoney;
    @BindView(R.id.tx_msg_about)
    TextView mTvMsgAbout;
    @BindView(R.id.expiry_date)
    TextView mTvExpiryDate;
    @BindView(R.id.quan_number)
    TextView mTvCoupon;
    @BindView(R.id.tv_goods_volume)
    TextView mTvGoodsVolume;
    @BindView(R.id.tx_TB)
    TextView mTvTb;
    @BindView(R.id.iv_goods_back)
    ImageView mIvGoodsBack;
    @BindView(R.id.rv_small_pic)
    RecyclerView recyclerView;
    @BindView(R.id.tx_more_picture)
    TextView moreText;
    @BindView(R.id.iv_up_down)
    ImageView mUpAndDown;
    @BindView(R.id.img_iscollect)
    ImageView mIsCollect;
    @BindView(R.id.tx_go_TB)
    TextView mTvGoTb;
    @BindView(R.id.tx_after_quan)
    TextView tx_after_quan;

    private String mLinkUrl;
    private TbMaterielBean tbDisCountBean;
    private SmallImageAdapter smallImageAdapter;
    private boolean bool = false;
    private BuyGoodsPresenter buyGoodsPresenter = new BuyGoodsPresenter();
    private boolean isCheckPoint = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_buy_goods;
    }

    @Override
    public void initEventAndData() {

        Eyes.translucentStatusBar(this);

        tbDisCountBean = (TbMaterielBean) getIntent().getBundleExtra(TaobaoUtil.TYPEID).getSerializable("discount");

        String pictUrl = tbDisCountBean.getPictUrl();
        if (pictUrl == null ){
            pictUrl = tbDisCountBean.getPictUrl();
        }
        if (pictUrl.startsWith("//")){
            pictUrl = "https:" + pictUrl;
        }
        Picasso.with(this).load(pictUrl).into(mImgBigIcon);


        if (Integer.valueOf(tbDisCountBean.getUserType()) == 1){
            mImgSmallIcon.setImageResource(R.mipmap.ic_tm);
            mTvGoTb.setText(R.string.go_Tm);
            tx_after_quan.setText(R.string.price_tm);
        }else if (Integer.valueOf(tbDisCountBean.getUserType()) == 0){
            mImgSmallIcon.setImageResource(R.mipmap.ic_tb);
            mTvGoTb.setText(R.string.go_Tb);
            tx_after_quan.setText(R.string.price_tb);
        }

        BigDecimal zkFinalPrice = new BigDecimal(tbDisCountBean.getZkFinalPrice());
        BigDecimal couponInfo = new BigDecimal(tbDisCountBean.getCouponInfo());
        double newPrice = zkFinalPrice.subtract(couponInfo).doubleValue();
        String price = "";
        if(Math.round(newPrice) - newPrice == 0){
            price = String.valueOf((long) newPrice);
        }else {
            price = String.valueOf(newPrice);
        }

        mTvTitle.setText(tbDisCountBean.getTitle());
        mLinkUrl = tbDisCountBean.getItemUrl();
        mTvMoney.setText(price + "");
        mOrdPrice.setText(tbDisCountBean.getZkFinalPrice()+"");
        mCouponPrice.setText(price + "");
        mTbMoney.setText(tbDisCountBean.getZkFinalPrice()+"");
        mTvMsgAbout.setText(tbDisCountBean.getShortTitle());
//        Date date = new Date(Long.valueOf(tbDisCountBean.getCouponEndTime()));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        mTvExpiryDate.setText("有效期至：" + tbDisCountBean.getCouponEndTime());
        mTvCoupon.setText(tbDisCountBean.getCouponInfo() + "元抵扣券");
        mTvGoodsVolume.setText(tbDisCountBean.getVolume() + "人已购买");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        buyGoodsPresenter.onCreate(this,this);

    }

    @OnClick({R.id.rel_go_TB,R.id.rel_tikey,R.id.tx_TB,R.id.iv_goods_back,R.id.ll_more_picture,R.id.img_iscollect})
    public void onClick(View view){
        bool = checkPackage(this, "com.taobao.taobao");
        switch (view.getId()) {
            case R.id.rel_go_TB:

                if (bool) {
                    if (mLinkUrl.startsWith("//")) {
                        mLinkUrl = "https" + mLinkUrl;
                    }
                    goLink(mLinkUrl);
                    return;
                }

                break;

            case R.id.rel_tikey:

            case R.id.tx_TB:

//                buyGoodsPresenter.isExChange(tbDisCountBean.getCouponInfo(),ProApplication.SESSIONID(BuyGoodsActivity.this),tbDisCountBean.getNumIid(),tbDisCountBean.getCouponId());
                exReChangeSuccess("1001");
                break;

            case R.id.iv_goods_back:

                finish();

                break;

            case R.id.ll_more_picture:

                if (smallImageAdapter == null) {
                    smallImageAdapter = new SmallImageAdapter(this, (ArrayList) (tbDisCountBean.getSmallImages()));
                    recyclerView.setAdapter(smallImageAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    mUpAndDown.setImageResource(R.mipmap.ic_pic_detail_select);
                }else {
                    if (recyclerView != null && recyclerView.isShown()){
                        recyclerView.setVisibility(View.GONE);
                        mUpAndDown.setImageResource(R.mipmap.ic_pic_detail_unselect);

                    }else {
                        recyclerView.setVisibility(View.VISIBLE);
                        mUpAndDown.setImageResource(R.mipmap.ic_pic_detail_select);
                    }
                }

                break;

            case R.id.img_iscollect:

//                buyGoodsPresenter.collectGoods(tbDisCountBean.getNumIid(), ProApplication.SESSIONID(this));

                break;
        }
    }

    private void goLink(String paramString)
    {

        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse("taobao:" + tbDisCountBean.getUrl()));
        startActivity(intent);
    }

    public void exReChangeSuccess(String msg) {
        if (bool){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse("taobao:" + tbDisCountBean.getCouponShareUrl()));
            startActivity(intent);
        }else {
            Bundle bundle = new Bundle();
            bundle.putString("link",tbDisCountBean.getCouponShareUrl());
            UiHelper.launcherBundle(BuyGoodsActivity.this,CouponLinkActivity.class,bundle);
        }
    }


    public static boolean checkPackage(Context paramContext, String paramString) {
        if ((paramString == null) || ("".equals(paramString))) {
            return false;
        }
        try {
            paramContext.getPackageManager().getPackageInfo(paramString, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }
}
