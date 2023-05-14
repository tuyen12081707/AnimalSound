package com.example.animalsound.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.example.animalsound.App;
import com.example.animalsound.MTask;
import com.example.animalsound.R;
import com.example.animalsound.databinding.FragmentM002DetailBinding;
import com.example.animalsound.model.Animal;
import com.example.animalsound.view.adapter.DetailAdapter;
import com.example.animalsound.view.dialog.DetailInfoDialog;
import com.example.animalsound.viewmodel.M002DetailVM;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class M002DetailFrg extends BaseFragment<FragmentM002DetailBinding, M002DetailVM> {
    public static final String TAG = M002DetailFrg.class.getName();
    public final String KEY_SAVE_PHOTO = "KEY_SAVE_PHOTO";
    public int index = 0;

    @Override
    protected Class<M002DetailVM> getClassVM() {
        return M002DetailVM.class;
    }

    @Override
    protected FragmentM002DetailBinding initViewBinding(LayoutInflater inflater) {
        return FragmentM002DetailBinding.inflate(inflater);
    }

    @Override
    protected void initViews() {
        callback.makeBackArrow(App.getInstance().getStorage().typeAnimal);
        Animal animal = (Animal) mData;
        index = getListAnimal().indexOf(animal);
        callback.disableDrawer();
        binding.vpAnimal.setAdapter(new DetailAdapter(context, App.getInstance().getStorage().listAnimal, this::clickView));

        binding.vpAnimal.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Animal animal = App.getInstance().getStorage().listAnimal.get(position);
                binding.tvTitle.setText(animal.getName());
            }
        });
        binding.vpAnimal.setCurrentItem(App.getInstance().getStorage().listAnimal.indexOf(animal));
    }

    @Override
    protected void clickView(View view) {
        switch (view.getId()) {
            case R.id.iv_start: {
                playSound((Animal) view.getTag());
                break;
            }
            case R.id.iv_search: {
//                searchImage(((Animal)view.getTag()).getName());
                showInfoDialog((Animal) view.getTag());
                break;
            }
            case R.id.iv_download: {
                saveToStorage((Animal) view.getTag());
                break;
            }
        }
    }

    private void saveToStorage(Animal animal) {
        if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101)
            ;
        }
        new MTask(KEY_SAVE_PHOTO, new MTask.OnCallBack() {
            @Override
            public Object execTask(String key, Object param, MTask task) {
                return viewModel.copyPhotoStorage(animal);
            }

            @Override
            public void completeTask(String key, Object value) {
                if (value == null) {
                    Toast.makeText(context, "Cann't save this photo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Photo is saved", Toast.LENGTH_SHORT).show();
                }
            }

        }).startAsync(null);
    }

    private void playSound(Animal animal) {
        AssetFileDescriptor afd = null;
        try {
            afd = App.getInstance().getAssets().openFd(animal.getIdSound());
            MediaPlayer player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showInfoDialog(Animal animal) {
        Log.i(TAG, "animal" + animal);
        DetailInfoDialog dialog = new DetailInfoDialog(context, animal);
        dialog.show();
    }

    private void searchImage(String name) {
        try {
            String word = URLEncoder.encode(name, "UTF-8");
            Uri uri = Uri.parse("https://www.google.com/search?hl=vi&q=" + word);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private ArrayList<Animal> getListAnimal() {
        return App.getInstance().getStorage().listAnimal;
    }


}
