package com.cetnaline.findproperty.ui.fragments.home;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseFragment;
import com.cetnaline.findproperty.base.IPresenter;

import butterknife.BindView;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.web_view)
    protected WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    protected void init() {
        showLoadingDialog(false);
        webView.loadUrl("http://m.sh.centanet.com/shangye/?utm_source=wap");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= 19) { //对加载的优化
            webView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }
        //解决漏洞
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");
        //        web_view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);  //设置 缓存模式
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); //滑动条的样式
        webView.setHorizontalScrollBarEnabled(false);  //取消Horizontal ScrollBar显示
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                cancelLoadingDialog();
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }
}
