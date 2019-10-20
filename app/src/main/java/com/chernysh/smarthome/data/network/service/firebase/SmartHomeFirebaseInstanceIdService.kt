package com.chernysh.smarthome.data.network.service.firebase

/**
 * Copyright 2017. Andrii Chernysh
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

import android.app.Service
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasServiceInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Service for getting Firebase token
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
class SmartHomeFirebaseInstanceIdService : FirebaseInstanceIdService(), HasServiceInjector {
    //@Inject internal var mPreferenceManager: PreferenceManager? = null
    @Inject lateinit var serviceDispatchingAndroidInjector: DispatchingAndroidInjector<Service>
    //@Inject internal var mFirebaseBindTokenInteractor: FirebaseBindTokenInteractor? = null

    // Якщо ще не залогінився, тоді записуємо в PreferenceManager;                                  +
    // Інакше відразу після логіна беремо з PreferenceManager і відправляємо запит на сервер bind.  -
    // Якщо залогінився і в PreferenceManager немає токена, тоді відправляємо запит на сервер bindю +
    // Не забути про unbind.                                                                        -

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun serviceInjector(): AndroidInjector<Service>? {
        return serviceDispatchingAndroidInjector
    }

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Timber.i("Firebase token %s", refreshedToken)

        /*val currentUser = mPreferenceManager!!.getUser()
        if (currentUser == null || TextUtils.isEmpty(currentUser!!.getLogin())) {
            mPreferenceManager!!.setFirebaseToken(refreshedToken)
        } else {
            mFirebaseBindTokenInteractor!!.execute(refreshedToken,
                { mPreferenceManager!!.setFirebaseTokenBinded(true) },
                { err -> mPreferenceManager!!.setFirebaseTokenBinded(false) })
            mPreferenceManager!!.setFirebaseToken(refreshedToken)
        }*/ //TODO
    }
}
