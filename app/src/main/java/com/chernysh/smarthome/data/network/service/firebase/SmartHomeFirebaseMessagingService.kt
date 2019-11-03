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
import com.chernysh.smarthome.domain.model.NotificationMessageType
import com.chernysh.smarthome.presentation.notification.NotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasServiceInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Service that handles Firebase push notifications.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */

class SmartHomeFirebaseMessagingService : FirebaseMessagingService(), HasServiceInjector {
    @Inject lateinit var serviceDispatchingAndroidInjector: DispatchingAndroidInjector<Service>
    @Inject lateinit var notificationManger: NotificationManager

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun serviceInjector(): AndroidInjector<Service>? {
        return serviceDispatchingAndroidInjector
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Check if message_received contains a notification payload.
        if (remoteMessage.data != null) {
            val bodyMap = remoteMessage.data
            val type = Integer.parseInt(bodyMap[FIELD_TYPE]!!)
            Timber.i("FCM push. notification type:$type")

            val messageType = NotificationMessageType.getTypeById(type)
            val dataJson = bodyMap[DATA_TYPE]
            Timber.i("FCM push. data:" + dataJson!!)

            processNotificationMessage(messageType, dataJson)
        }
    }

    private fun processNotificationMessage(messageType: NotificationMessageType, dataJson: String?) {
        Timber.d("messageType: ${messageType.id}, data: $dataJson")
        when(messageType) {
            NotificationMessageType.HIGH_CPU_TEMPERATURE -> notificationManger.notifyCpuIsTooHot(dataJson?.toInt() ?: -1)
            NotificationMessageType.NEPTUN_ALARM -> notificationManger.notifyNeptun()
            NotificationMessageType.SECURITY_ALARM -> notificationManger.notifySecurityAlarm()
            NotificationMessageType.SECURITY_ENABLED -> notificationManger.notifySecurityEnabled(dataJson?.toBoolean() ?: false)
            NotificationMessageType.FIRE_ALARM -> notificationManger.notifyFire()
            else -> {}
        }
    }

    companion object {
        val FIELD_TYPE = "type"
        val DATA_TYPE = "data"
    }
}
