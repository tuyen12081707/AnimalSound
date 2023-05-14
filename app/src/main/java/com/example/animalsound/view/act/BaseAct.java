package com.example.animalsound.view.act;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.example.animalsound.R;
import com.example.animalsound.view.OnMainCallBack;
import com.example.animalsound.view.fragment.BaseFragment;
import com.example.animalsound.viewmodel.BaseVM;


import java.lang.reflect.Constructor;

public abstract class BaseAct<T extends ViewBinding,M extends BaseVM> extends AppCompatActivity implements View.OnClickListener, OnMainCallBack {
    protected T binding;
    protected M viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = initViewBinding();
        viewModel = new ViewModelProvider(this).get(getClassViewModel());
        setContentView(binding.getRoot());
        initView();
    }

    protected abstract Class<M> getClassViewModel();

    protected abstract void initView();

    protected abstract T initViewBinding();

    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_in));
        clickView(v);
    }

    private void clickView(View v) {
    }

    @Override
    public void showFragement(String tag, Object data, boolean isBacked) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(tag);
            Constructor<?> constructor = clazz.getConstructor();
            BaseFragment<?,?> baseFragment = (BaseFragment<?,?>) constructor.newInstance();
            baseFragment.setCallback(this);
            baseFragment.setData(data);
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (isBacked) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.ln_main, baseFragment, tag)
                        .addToBackStack(null)
                        .commit();
            } else  {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.ln_main, baseFragment, tag)
                        .commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
