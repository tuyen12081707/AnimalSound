package com.example.animalsound.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;

import com.example.animalsound.R;
import com.example.animalsound.databinding.ViewAskContinueBinding;
import com.example.animalsound.model.Animal;

public class AskDialog extends Dialog {
    private final ViewAskContinueBinding binding;
    private final Context context;

    public AskDialog(@NonNull Context context, Animal animal) {
        super(context, android.R.style.ThemeOverlay);
        this.context = context;
        binding = ViewAskContinueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView(animal);
    }

    private void initView(Animal animal) {
        binding.tvTitle.setText(animal.getName());
        binding.tvContent.setText("Do You want to continue");
        binding.btOk.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
            dismiss();
        });

    }


}
