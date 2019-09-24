package com.example.jokesapi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SecondPageFragment extends Fragment {

    private final String API_ADRESS = "http://www.icndb.com/api";
    private static Bundle webViewBundle;
    private WebView webView;

    public void onPause() {
        super.onPause();
        webViewBundle = new Bundle();
        webView.saveState(webViewBundle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView2 = inflater.inflate(R.layout.second_fragment_layout,container,false);
        webView = rootView2.findViewById(R.id.second_fragment__web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        if (webViewBundle != null) {
            webView.restoreState(webViewBundle);
        } else {
            webView.loadUrl(API_ADRESS);
        }
        return rootView2;
    }
}
