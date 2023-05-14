package com.example.animalsound.view.fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;

import com.example.animalsound.App;
import com.example.animalsound.R;
import com.example.animalsound.databinding.FragmentM001MainBinding;
import com.example.animalsound.model.Animal;
import com.example.animalsound.view.act.MainActivity;
import com.example.animalsound.view.adapter.AnimalAdapter;
import com.example.animalsound.view.dialog.MiniGameDialog;
import com.example.animalsound.viewmodel.M001VM;

import java.util.Locale;

public class M001MainFrg extends BaseFragment<FragmentM001MainBinding, M001VM> {
    public static final String TAG = M001MainFrg.class.getName();
    private TextToSpeech tts;

    @Override
    protected Class getClassVM() {
        return M001VM.class;
    }

    @Override
    protected FragmentM001MainBinding initViewBinding(LayoutInflater inflater) {
        return FragmentM001MainBinding.inflate(inflater);
    }

    @Override
    protected void initViews() {
        callback.enableDrawer();
        initData(MainActivity.TYPE_MAMMAL);
        initAnimalView();
        binding.btGame.setOnClickListener(this);
        tts = new TextToSpeech(context, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.UK);
            }
        });

    }

    private void initData(String typeAnimal) {
        binding.tvTitle.setText(typeAnimal.toUpperCase());
        viewModel.initData(typeAnimal);
    }

    private void initAnimalView() {
        binding.rvAnimal.setAdapter(new AnimalAdapter(context, App.getInstance().getStorage().listAnimal, this::clickView));
    }

    @Override
    protected void clickView(View view) {
        if (view.getId() == R.id.bt_game) {
            showMiniGame();
            return;
        }
        Animal tag = (Animal) view.getTag();
        goToDetailScreen(tag);
    }

    private void showMiniGame() {
        MiniGameDialog dialog = new MiniGameDialog(context, this, App.getInstance().getStorage().listAnimal);
        dialog.show();
    }

    private void goToDetailScreen(Animal animal) {
        tts.speak(animal.getName(), TextToSpeech.QUEUE_FLUSH, null);
        App.getInstance().getStorage().typeAnimal = binding.tvTitle.getText().toString();
        callback.showFragement(M002DetailFrg.TAG, animal, true);
    }


    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }


    public void showListAnimal(String typeMammal) {
        initData(typeMammal);
        initAnimalView();
    }
}
