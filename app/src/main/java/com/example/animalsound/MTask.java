package com.example.animalsound;

import android.os.AsyncTask;


public final class MTask extends AsyncTask<Object, Object, Object> {
    private final String key;
    private final OnCallBack callBack;

    public MTask(String key, OnCallBack callBack) {
        this.key = key;
        this.callBack = callBack;
    }

    public void requestUI(Object data) {
        publishProgress(data);
    }

    @Override
    protected void onPreExecute() {
        callBack.preExec(key);
    }

    @Override
    protected Object doInBackground(Object... params) {
        return callBack.execTask(key, params == null ? null : params[0], this);
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        callBack.updateUI(key, values[0]);
    }

    @Override
    protected void onPostExecute(Object o) {
        callBack.completeTask(key, o);

    }

    @Override
    protected void onCancelled(Object o) {
        callBack.cancleTask(key, o);
    }

    public void start(Object data) {
        execute(data);
    }

    public void startAsync(Object data) {
        executeOnExecutor(THREAD_POOL_EXECUTOR, data);
    }

    public void stop() {
        cancel(true);
    }

    public interface OnCallBack {
        default void preExec(String key) {
            // do nothing
        }

        Object execTask(String key, Object param, MTask task);

        default void updateUI(String key, Object data) {

        }

        void completeTask(String key, Object data);
        default void cancleTask(String key, Object data) {
        }

    }
}
