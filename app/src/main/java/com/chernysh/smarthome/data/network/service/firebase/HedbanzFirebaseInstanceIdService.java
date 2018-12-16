package com.chernysh.smarthome.data.network.service.firebase;
/**
 * Copyright 2017. Andrii Chernysh
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

import android.app.Service;
import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.transcendensoft.hedbanz.data.prefs.PreferenceManager;
import com.transcendensoft.hedbanz.domain.entity.User;
import com.transcendensoft.hedbanz.domain.interactor.firebase.FirebaseBindTokenInteractor;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasServiceInjector;
import timber.log.Timber;

/**
 * Service for getting Firebase token
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
public class HedbanzFirebaseInstanceIdService extends FirebaseInstanceIdService implements HasServiceInjector {
    @Inject PreferenceManager mPreferenceManager;
    @Inject DispatchingAndroidInjector<Service> serviceDispatchingAndroidInjector;
    @Inject FirebaseBindTokenInteractor mFirebaseBindTokenInteractor;

    // Якщо ще не залогінився, тоді записуємо в PreferenceManager;                                  +
    // Інакше відразу після логіна беремо з PreferenceManager і відправляємо запит на сервер bind.  -
    // Якщо залогінився і в PreferenceManager немає токена, тоді відправляємо запит на сервер bindю +
    // Не забути про unbind.                                                                        -

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceDispatchingAndroidInjector;
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Timber.i("Firebase token %s", refreshedToken);

        User currentUser = mPreferenceManager.getUser();
        if (currentUser == null || TextUtils.isEmpty(currentUser.getLogin())) {
            mPreferenceManager.setFirebaseToken(refreshedToken);
        } else {
            mFirebaseBindTokenInteractor.execute(refreshedToken,
                    () -> mPreferenceManager.setFirebaseTokenBinded(true),
                    (err) -> mPreferenceManager.setFirebaseTokenBinded(false));
            mPreferenceManager.setFirebaseToken(refreshedToken);
        }
    }
}
