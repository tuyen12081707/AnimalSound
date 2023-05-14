package com.example.animalsound.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.example.animalsound.R;
import com.example.animalsound.databinding.ViewDetailInfoBinding;
import com.example.animalsound.model.Animal;

public class DetailInfoDialog extends Dialog implements View.OnClickListener {

    private final ViewDetailInfoBinding binding;
    private final Animal animal;
    private final Context context;

    public DetailInfoDialog(@NonNull Context context, Animal animal) {
        super(context, R.style.Them_Dialog);
        this.animal = animal;
        this.context = context;
        binding = ViewDetailInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.ivBack.setOnClickListener(this);
        binding.tvTitle.setText(animal.getName());
        binding.webDetail.getSettings().setJavaScriptEnabled(true);
        binding.webDetail.getSettings().setAllowContentAccess(true);
        binding.webDetail.getSettings().setBuiltInZoomControls(true);
        binding.webDetail.getSettings().setAllowFileAccess(true);
        binding.webDetail.getSettings().setSupportZoom(true);
        binding.webDetail.getSettings().setDomStorageEnabled(true);
        binding.webDetail.setWebChromeClient(new WebChromeClient());
        binding.webDetail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        binding.webDetail.loadUrl("https://vi.wikipedia.org/wiki/" + animal.getName());
    }

    @Override
    public void dismiss() {
        if (!binding.webDetail.canGoBack()) {
            super.dismiss();
            return;
        }
        binding.webDetail.goBack();

    }

    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        if (v.getId() == R.id.iv_back) {
            dismiss();
        }
    }
}
