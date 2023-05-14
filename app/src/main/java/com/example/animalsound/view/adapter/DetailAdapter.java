package com.example.animalsound.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.animalsound.R;
import com.example.animalsound.model.Animal;

import java.util.List;

public class DetailAdapter extends PagerAdapter {
    private final Context context;
    private final List<Animal> animalList;
    private final View.OnClickListener event;


    public DetailAdapter(Context context, List<Animal> animalList, View.OnClickListener event) {
        this.context = context;
        this.animalList = animalList;
        this.event = event;
    }

    @Override
    public int getCount() {
        return animalList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_animal, container, false);
        Animal item = animalList.get(position);
        ImageView ivAnimal = view.findViewById(R.id.iv_animal);
        ImageView ivStart = view.findViewById(R.id.iv_start);
        ImageView ivSave = view.findViewById(R.id.iv_download);
        ImageView ivSearch = view.findViewById(R.id.iv_search);

        Glide.with(context)
                .load(Uri.parse("file:///android_asset/" + (item.getIdPhoto())))
                .into(ivAnimal);

        ivStart.setTag(item);
        ivSave.setTag(item);
        ivSearch.setTag(item);
        View.OnClickListener onClickListener = v -> {
            v.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
            event.onClick(v);
            v.setTag(item);
        };

        ivStart.setOnClickListener(onClickListener);
        ivSave.setOnClickListener(onClickListener);
        ivSearch.setOnClickListener(onClickListener);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
