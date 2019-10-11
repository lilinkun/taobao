package com.llk.mytaobao.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.llk.mytaobao.R;
import com.llk.mytaobao.base.BaseActivity;
import com.llk.mytaobao.util.Eyes;
import com.llk.mytaobao.util.TaobaoUtil;

import butterknife.BindView;

/**
 * Created by LG on 2019/10/11.
 */
public class CouponLinkActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon_link;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarColor(this,getResources().getColor(R.color.setting_title_color));

        this.webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.webview.getSettings().setLoadWithOverviewMode(true);

        String mUrlLink = getIntent().getBundleExtra(TaobaoUtil.TYPEID).getString("link");

        if (mUrlLink.startsWith("//")){
            mUrlLink = "https:" + mUrlLink;
        }
        loadDataFromService(mUrlLink);

    }

    protected void loadDataFromService(String paramString)
    {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient()
        {
            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
            {
                return false;
            }
        });
        webview.loadUrl(paramString);
    }

}
