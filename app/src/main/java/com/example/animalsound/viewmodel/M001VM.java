package com.example.animalsound.viewmodel;

import android.util.Log;

import com.example.animalsound.App;
import com.example.animalsound.model.Animal;

public class M001VM extends BaseVM{
    private static final String TAG = M001VM.class.getName();

    public void initData(String typeAnimal) {
        App.getInstance().getStorage().listAnimal.clear();
        try {
            String[] paths = App.getInstance().getAssets().list(typeAnimal);

            for (String item : paths) {
                Log.i(TAG, "item" + item);
                String name = item.replace(".png", "");
                Animal animal = new Animal("sound/" + typeAnimal + "/" + name + ".mp3", typeAnimal + "/" + item, name);
                App.getInstance().getStorage().listAnimal.add(animal);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
