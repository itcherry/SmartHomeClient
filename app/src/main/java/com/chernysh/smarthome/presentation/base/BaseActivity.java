package com.chernysh.smarthome.presentation.base;

/**
 * Copyright 2018. Andrii Chernysh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import com.chernysh.smarthome.R;
import com.chernysh.smarthome.utils.AndroidUtils;
import com.chernysh.smarthome.utils.KeyboardUtils;
import com.hannesdorfmann.mosby3.mvi.MviActivity;
import com.hannesdorfmann.mosby3.mvi.MviPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import javax.inject.Inject;

/**
 * It is base activity for all activities in application.
 * It describes basic operations like show error or loading
 * for all activities.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         Developed by <u>Transcendensoft</u>
 */
public abstract class BaseActivity<V extends MvpView, P extends MviPresenter<V, ?>> extends MviActivity<V, P> implements BaseView {
    private ProgressDialog mProgressDialog;
    @Inject CompositeDisposable mViewCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initProgressDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mProgressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mViewCompositeDisposable != null && !mViewCompositeDisposable.isDisposed()) {
            mViewCompositeDisposable.dispose();
        }
        mProgressDialog.dismiss();
        mProgressDialog = null;
    }

    protected void addRxBindingDisposable(Disposable disposable){
        if(mViewCompositeDisposable != null){
            mViewCompositeDisposable.add(disposable);
        }
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(this.getString(R.string.action_loading));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
    }

    @Override
    public void showSnackError(int messageRes) {
        //TODO
    }

    @Override
    public void showSnackError(String message) {
        //TODO
    }

    @Override
    public void showSnackMessage(int messageRes) {
        View rootView = findViewById(android.R.id.content);
        Snackbar.make(rootView, getString(messageRes), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showSnackMessage(String message) {
        View rootView = findViewById(android.R.id.content);
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showShortToastMessage(int messageRes) {
        AndroidUtils.showShortToast(this, messageRes);
    }

    @Override
    public void showShortToastMessage(String message) {
        AndroidUtils.showShortToast(this, message);
    }

    @Override
    public void showLongToastMessage(int messageRes) {
        AndroidUtils.showLongToast(this, messageRes);
    }

    @Override
    public void showLongToastMessage(String message) {
        AndroidUtils.showLongToast(this, message);
    }

    @Override
    public void handleKeyboard(KeyboardUtils.KeyboardState state, @Nullable EditText editText) {
        switch (state) {
            case HIDE:
                KeyboardUtils.hideSoftInput(this);
                break;
            case SHOW:
                KeyboardUtils.showSoftInput(editText, this);
                break;
            case TOGGLE:
                KeyboardUtils.toggleSoftInput(this);
                break;
        }
    }

    @Override
    public void showLoadingDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
