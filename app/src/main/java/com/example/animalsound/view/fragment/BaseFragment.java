package com.example.animalsound.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.example.animalsound.view.OnMainCallBack;
import com.example.animalsound.viewmodel.BaseVM;


public abstract class BaseFragment<V extends ViewBinding,M extends BaseVM> extends Fragment implements View.OnClickListener {

    protected Object mData;
    protected Context context;
    protected V binding;
    protected M viewModel;
    protected OnMainCallBack callback;

    public void setData(Object mData) {
        this.mData = mData;
    }

    public void setCallback(OnMainCallBack callback) {
        this.callback = callback;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = initViewBinding(inflater);
        viewModel = new ViewModelProvider(this).get(getClassVM());
        return binding.getRoot();
    }

    protected abstract Class<M> getClassVM();

    protected abstract V initViewBinding(LayoutInflater inflater);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    protected abstract void initViews();

    @Override
    public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        clickView(view);
    }

    protected void clickView(View view) {

    }
}
