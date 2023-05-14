package com.example.animalsound.view.act;

import android.app.AlertDialog;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.animalsound.R;
import com.example.animalsound.databinding.ActivityMainBinding;
import com.example.animalsound.view.fragment.M000SplashFrg;
import com.example.animalsound.view.fragment.M001MainFrg;
import com.example.animalsound.viewmodel.CommonVM;


public class MainActivity extends BaseAct<ActivityMainBinding, CommonVM> {
    public static final String TYPE_MAMMAL = "mammal";
    public static final String TYPE_SEA = "sea";
    public static final String TYPE_BIRD = "bird";

    @Override
    protected Class getClassViewModel() {
        return CommonVM.class;
    }

    @Override
    protected void initView() {
        binding.actionbar.ivMenu.setOnClickListener(v -> {
            binding.drawer.openDrawer(GravityCompat.START);
        });
        binding.include.frMammal.setOnClickListener(this);
        binding.include.frBird.setOnClickListener(this);
        binding.include.frSea.setOnClickListener(this);
        showFragement(M000SplashFrg.TAG, null, false);
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_in));
        if (v.getId() == R.id.fr_mammal) {
            showListAnimal(TYPE_MAMMAL);
        } else if (v.getId() == R.id.fr_bird) {
            showListAnimal(TYPE_BIRD);
        } else if (v.getId() == R.id.fr_sea) {
            showListAnimal(TYPE_SEA);
        }
    }


    private void showListAnimal(String typeAnimal) {
        Fragment frg = getSupportFragmentManager().findFragmentByTag(M001MainFrg.TAG);
        if (frg == null) return;
        ((M001MainFrg) frg).showListAnimal(typeAnimal);
        binding.drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void backToPrevious() {
        onBackPressed();
    }

    @Override
    public void disableDrawer() {
        binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void enableDrawer() {
        binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        binding.actionbar.getRoot().setVisibility(View.VISIBLE);
        binding.actionbar.ivMenu.setImageResource(R.drawable.ic_menu);
        binding.actionbar.tvMenu.setText(R.string.app_name);

    }

    @Override
    public void makeBackArrow(String typeAnimal) {
        disableDrawer();
        binding.actionbar.ivMenu.setImageResource(R.drawable.ic_arrow_back_2);
        binding.actionbar.tvMenu.setText(typeAnimal);
        binding.actionbar.ivMenu.setOnClickListener(v -> onBackPressed());
    }





    @Override
    protected ActivityMainBinding initViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            askForExitApp();
            return;
        }
        super.onBackPressed();
    }

    private void askForExitApp() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Do you want to exit this app ?");
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", (dialogInterface, i) -> super.onBackPressed());

        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", (dialogInterface, i) -> {
        });
        dialog.show();
    }
}