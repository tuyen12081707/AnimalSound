package com.example.animalsound.view;

import com.example.animalsound.MTask;

public interface OnMainCallBack {
    void backToPrevious();
    void showFragement(String tag, Object data, boolean isBacked);

    void disableDrawer();

    void enableDrawer();

    void makeBackArrow(String typeAnimal);


}
