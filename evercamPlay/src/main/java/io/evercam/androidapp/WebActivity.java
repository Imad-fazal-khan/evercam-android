package io.evercam.androidapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import io.evercam.androidapp.custom.CustomProgressDialog;

public abstract class WebActivity  extends ParentAppCompatActivity
{
    private final String TAG = "WebActivity";

    public static CustomProgressDialog progressDialog;
    protected Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        progressDialog = new CustomProgressDialog(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            this.bundle = bundle;
        }
        else
        {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public WebViewClient getWebViewClient()
    {
        WebViewClient client = new WebViewClient()
        {
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                progressDialog.show(WebActivity.this.getString(R.string.msg_loading));
            }

            public void onPageFinished(WebView view, String url)
            {
                progressDialog.dismiss();
            }

            //TODO: Refactor the code - currently all web view subclasses are using
            // the same WebViewClient.
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG, "shouldOverrideUrlLoading " + url);

                if(url.startsWith("data:image/jpeg;"))
                {
                    //TODO: Decode base64 and save the image

                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }
        };
        return client;
    }

    protected abstract void loadPage();
}
