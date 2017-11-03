package uk.co.dancetrix.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import uk.co.dancetrix.R;
import uk.co.dancetrix.util.Configuration;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        final String css = "header, footer, .footer-widget-area, .page-header, iframe, .page-content span {display: none} "
                     + ".site-main {margin: 0}";
        final String javascript = "var styleTag = document.createElement(\"style\"); "
                            + "styleTag.textContent = \"" + css + "\"; "
                            + "document.documentElement.appendChild(styleTag); "
                            + "document.body.style.background = '#303030'; "
                            + "document.body.style.color = 'white'";

        final WebView webView = findViewById(R.id.about_us_web);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.evaluateJavascript(javascript, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                webView.setVisibility(View.VISIBLE);
                            }
                        }, 500);
                    }
                });
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(Configuration.getWebsiteUrl());

    }

}
