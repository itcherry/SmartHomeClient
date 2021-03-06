package com.chernysh.smarthome.presentation.base

/**
 * Copyright 2018. Andrii Chernysh
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.ProgressDialog
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.chernysh.smarthome.R
import com.chernysh.smarthome.utils.AndroidUtils
import com.chernysh.smarthome.utils.KeyboardUtils
import com.chernysh.smarthome.utils.Notification
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.hannesdorfmann.mosby3.mvp.MvpView
import javax.inject.Inject
import dagger.android.AndroidInjection

/**
 * It is base activity for all activities in application.
 * It describes basic operations like show error or loading
 * for all activities.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
abstract class BaseActivity<V : MvpView, P : BasePresenter<V, *>>() : MviActivity<V, P>(), BaseView {
    private var mProgressDialog: ProgressDialog? = null
    @Inject
    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState)

        initProgressDialog()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.onCreate()
    }

    private fun initProgressDialog() {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog!!.setMessage(this.getString(R.string.action_loading))
        mProgressDialog!!.setCancelable(false)
        mProgressDialog!!.isIndeterminate = true
    }

    override fun onPause() {
        super.onPause()
        mProgressDialog!!.dismiss()
        presenter.onViewPaused()
    }

    override fun onDestroy() {
        super.onDestroy()

        mProgressDialog!!.dismiss()
        mProgressDialog = null

        presenter.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewResumed()
    }

    override fun createPresenter() = presenter

    override fun showNetworkSnackbarError(action: () -> Unit) {
        Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.network_error),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.action_reload) { action.invoke() }
            .setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
            .show()
    }

    override fun showNetworkSnackbarError() {
        Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.network_error),
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun showServerSnackbarError(action: () -> Unit) {
        Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.server_error),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.action_reload) { action.invoke() }
            .setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
            .show()
    }

    override fun showServerSnackbarError() {
        Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.server_error),
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun showSnackMessage(messageRes: Int) {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, getString(messageRes), Snackbar.LENGTH_LONG).show()
    }

    override fun showSnackMessage(message: String) {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
    }

    override fun showShortToastMessage(messageRes: Int) {
        AndroidUtils.showShortToast(this, messageRes)
    }

    override fun showShortToastMessage(message: String) {
        AndroidUtils.showShortToast(this, message)
    }

    override fun showLongToastMessage(messageRes: Int) {
        AndroidUtils.showLongToast(this, messageRes)
    }

    override fun showLongToastMessage(message: String) {
        AndroidUtils.showLongToast(this, message)
    }

    override fun handleKeyboard(state: KeyboardUtils.KeyboardState, editText: EditText?) {
        when (state) {
            KeyboardUtils.KeyboardState.HIDE -> KeyboardUtils.hideSoftInput(this)
            KeyboardUtils.KeyboardState.SHOW -> KeyboardUtils.showSoftInput(editText, this)
            KeyboardUtils.KeyboardState.TOGGLE -> KeyboardUtils.toggleSoftInput(this)
        }
    }

    override fun showLoadingDialog() {
        if (mProgressDialog != null) {
            mProgressDialog!!.show()
        }
    }

    override fun hideLoadingDialog() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }
}
