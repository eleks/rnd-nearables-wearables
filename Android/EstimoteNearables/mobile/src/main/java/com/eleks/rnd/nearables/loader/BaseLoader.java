package com.eleks.rnd.nearables.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

public abstract class BaseLoader<T> extends AsyncTaskLoader<T> {

    private T mResult;
    protected Bundle bundle;

    public BaseLoader(Context context, Bundle bundle) {
        super(context);
        this.bundle = bundle;
    }

    @Override
    protected void onStartLoading() {
        if (mResult != null) {
            deliverResult(mResult);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void deliverResult(T data) {
        super.deliverResult(data);
        mResult = data;
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        mResult = null;
    }

    protected Bundle getBundle() {
        if(bundle == null) {
            bundle = new Bundle();
        }
        return bundle;
    }

    public static class LoaderResult {
        protected Exception exception;

        public Exception getException() {
            return exception;
        }

        public void setException(Exception exception) {
            this.exception = exception;
        }

        public boolean wasSuccessful() {
            return exception == null;
        }
    }
}
