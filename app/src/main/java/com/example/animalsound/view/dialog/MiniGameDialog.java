package com.example.animalsound.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bumptech.glide.Glide;
import com.example.animalsound.App;
import com.example.animalsound.CommonUtils;
import com.example.animalsound.R;
import com.example.animalsound.databinding.ViewMiniGameBinding;
import com.example.animalsound.model.Animal;
import com.example.animalsound.viewmodel.MiniGameVM;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MiniGameDialog extends Dialog implements View.OnClickListener {

    private static final String TAG = MiniGameDialog.class.getName();
    private static final String KEY_SCORE = "KEY_SCORE";
    private final ViewMiniGameBinding binding;
    private final Context context;
    private final MiniGameVM viewModel;
    private AskDialog closeDialog;


    public MiniGameDialog(@NonNull Context context, ViewModelStoreOwner owner, List<Animal> listAnimal) {
        super(context, R.style.Them_Dialog);
        this.context = context;
        viewModel = new ViewModelProvider(owner).get(MiniGameVM.class);
        viewModel.initAnimalList(listAnimal);
        binding = ViewMiniGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView(owner);
    }

    private void initView(ViewModelStoreOwner owner) {
        binding.ivBack.setOnClickListener(this);
        binding.tvCard.setOnClickListener(this);
        binding.ivCard.setOnClickListener(this);
        binding.tvA.setOnClickListener(this);
        binding.tvB.setOnClickListener(this);
        initCard();

        String txtScore = CommonUtils.getInstance().getPref(KEY_SCORE);
        if (txtScore != null) {
            viewModel.initScore(Integer.parseInt(txtScore));
            binding.tvScore.setText(String.format("Score: %s", viewModel.getScore()));
        }
        viewModel.getScore().observe((LifecycleOwner) owner, score -> {
            binding.tvScore.setText(String.format("Score: %s", score));
            CommonUtils.getInstance().savePref(KEY_SCORE, score + "");
        });

    }

    private void initCard() {
        String[] listNum = viewModel.initCard();
        String textA = listNum[0];
        String textB = listNum[1];
        binding.tvA.setText(textA);
        binding.tvB.setText(textB);
        int lenA = textA.length();
        int lenB = textB.length();

        String max = lenA > lenB ? textA : textB;
        Rect bounds = new Rect();
        Paint textPaint = binding.tvA.getPaint();
        textPaint.getTextBounds(max, 0, max.length(), bounds);
        int width = bounds.width();
        Log.i(TAG, "width " + width);
        binding.tvA.setWidth(width + 150);
        binding.tvB.setWidth(width + 150);
    }


    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        if (v.getId() == R.id.iv_back) {
            dismiss();
        } else if (v.getId() == R.id.tv_card || v.getId() == R.id.iv_card) {
            binding.frCard.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
            try {
                showCardAnimal();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.tv_a || v.getId() == R.id.tv_b) {
            checkAnswer(((TextView) v).getText().toString());
        }
    }

    private void checkAnswer(String ans) {
        if (viewModel.checkAnswer(ans)) {
            initCard();

        } else {
            AskDialog toast = new AskDialog(context,viewModel.getAnimal());
            toast.getWindow().setBackgroundDrawableResource(R.color.grayAlPha);
            toast.show();
//            Toast.makeText(context, "Wrong answer :(", Toast.LENGTH_SHORT).show();
            initCard();
        }

    }


    private void showCardAnimal() throws IOException {
        Toast toast = new Toast(context);
        ImageView ivAnimal = new ImageView(context);
        InputStream input = App.getInstance().getAssets().open(viewModel.getAnimal().getIdPhoto());
        ivAnimal.setLayoutParams(new LinearLayout.LayoutParams(500, 500));
//        ivAnimal.setImageBitmap((BitmapFactory.decodeStream(input)));
        Glide.with(context)
                .load(Uri.parse("file:///android_asset/" + (viewModel.getAnimal().getIdPhoto())))
                .into(ivAnimal);

        toast.setView(ivAnimal);
        toast.setGravity(Gravity.TOP, 0, 400);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
