package com.example.animalsound.viewmodel;

import android.os.Environment;
import android.util.Log;

import com.example.animalsound.App;
import com.example.animalsound.model.Animal;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class M002DetailVM extends BaseVM {
    private static final String TAG = M002DetailVM.class.getName();

    public Object copyPhotoStorage(Animal animal) {
        try {
            InputStream in = App.getInstance().getAssets().open(animal.getIdPhoto());
            byte[] buff = new byte[1024];

//                    String outPath = App.getInstance().getExternalFilesDir(null).getPath();
            String outPath = Environment.getDataDirectory().getPath() + "/data/" + App.getInstance().getPackageName();
//                    String outPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            String pathDir = outPath + "/" + animal.getName() + ".png";
            FileOutputStream out = new FileOutputStream(pathDir);
            int len = in.read(buff);
            while (len > 0) {
                out.write(buff, 0, len);
                len = in.read(buff);
            }
            out.close();
            in.close();
            Log.i(TAG, "photo is saved");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return true;
    }
}
