package coxaxle.cox.automotive.com.coxaxle.presentation;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import coxaxle.cox.automotive.com.coxaxle.R;

public class ScheduleAppointmentActivity extends AppCompatActivity {

    WebView xtimeWebView;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_appointment);

       /* String url = "https://consumer-ptr1.xtime.com/scheduling/?webKey=HUS20131206112630208569&VIN=&Provider=COAXEL&Keyword=" +
                "SCHEDULE&cfn=JOAN&cln=SMITH&cpn=6785551212cem=JSMITH@FAKEMAIL." +
                "COM&NOTE=NOTE4Q3&extid=SCHEDULE&extctxt=COAXLE";*/

        String url = "https://consumer-ptr1.xtime.com/scheduling/?" +
                "webKey=hus20131206112630208569&VIN=5J6RE3H74AL049448&" +
                "Provider=COXAXLE&Keyword=SCHEDULE&cfn=kishore&cln=thorata&" +
                "cpn=9700964538&cem=kishore.thorata@vensaiinc.com&NOTE=NOTE4Q3&" +
                "extid=SCHEDULE&extctxt=COXAXLE&dest=VEHICLE";

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(100);

        xtimeWebView = (WebView) findViewById(R.id.schedule_appointment_webview);


        xtimeWebView.getSettings().setLoadsImagesAutomatically(true);
        //xtimeWebView.getSettings().setDomStorageEnabled(true);
        xtimeWebView.getSettings().setJavaScriptEnabled(true);
        xtimeWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //xtimeWebView.clearCache(true);
        xtimeWebView.setWebViewClient(new XTimeBrowser());
        xtimeWebView.loadUrl(url);
    }

    /*  }
  });
}*/
    class XTimeBrowser extends WebViewClient {

       @SuppressWarnings("deprecation")
       @Override
       public boolean shouldOverrideUrlLoading(WebView view, String url) {
           view.loadUrl(url);
           return true;
       }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @SuppressWarnings("deprecation")
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view,String url) {
            return super.shouldInterceptRequest(view, url);
        }

       /* @TargetApi(Build.VERSION_CODES.N)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest webResourceRequest) {
            WebResourceResponse response = super.shouldInterceptRequest(view, webResourceRequest);
            String url = webResourceRequest.getUrl().toString();
            return tryToInterceptRequests(view, url, response);
        }*/


        @Override
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(View.GONE);
            ScheduleAppointmentActivity.this.progress.setProgress(100);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress.setVisibility(View.VISIBLE);
            ScheduleAppointmentActivity.this.progress.setProgress(0);
            super.onPageStarted(view, url, favicon);
        }
    }

  }
