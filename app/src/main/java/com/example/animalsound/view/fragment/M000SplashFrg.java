package com.example.animalsound.view.fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.animation.AnimationUtils;

import com.example.animalsound.R;
import com.example.animalsound.databinding.FragmentM000SplashBinding;
import com.example.animalsound.viewmodel.CommonVM;

public class M000SplashFrg extends BaseFragment<FragmentM000SplashBinding, CommonVM> {
    public static final String TAG = M000SplashFrg.class.getName();

    @Override
    protected Class<CommonVM> getClassVM() {
        return CommonVM.class;
    }

    @Override
    protected FragmentM000SplashBinding initViewBinding(LayoutInflater inflater) {
        return FragmentM000SplashBinding.inflate(inflater);
    }

    @Override
    protected void initViews() {
        binding.frIcon.startAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_right));
        binding.tvTitle.startAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_left));
        callback.disableDrawer();
        new Handler().postDelayed(this::goMainScreen, 2500);

    }

    private void goMainScreen() {
        callback.showFragement(M001MainFrg.TAG, null, false);
    }
}
