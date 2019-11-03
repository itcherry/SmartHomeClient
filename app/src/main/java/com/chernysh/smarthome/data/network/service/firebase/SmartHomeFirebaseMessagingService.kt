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
    @Inject internal lateinit var serviceDispatchingAndroidInjector: DispatchingAndroidInjector<Service>
    @Inject internal lateinit var mGson: Gson
   // @Inject internal var mNotificationManger: NotificationManager? = null
   // @Inject internal var mPreferenceManager: PreferenceManager? = null

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun serviceInjector(): AndroidInjector<Service>? {
        return serviceDispatchingAndroidInjector
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.tag("jhk")

        // Check if message_received contains a notification payload.
       /* if (remoteMessage.data != null) {
            val bodyMap = remoteMessage.data
            val type = Integer.parseInt(bodyMap[FIELD_TYPE]!!)
            Timber.i("FCM push. notification type:$type")

            val messageType = NotificationMessageType.Companion.getTypeById(type)
            val dataJson = bodyMap[DATA_TYPE]
            Timber.i("FCM push. data:" + dataJson!!)

            processNotificationMessage(messageType, dataJson)
        }*/
    }

    /*private fun processNotificationMessage(messageType: NotificationMessageType, dataJson: String?) {
        val notificationMessage = mGson!!
            .fromJson<NotificationMessage>(dataJson, NotificationMessage::class.java!!)
        when (messageType) {
            RxSocketEvent.MESSAGE -> mNotificationManger!!.notifyMessage(notificationMessage)
            SET_WORD -> mNotificationManger!!.notifySetWord(notificationMessage)
            GUESS_WORD -> mNotificationManger!!.notifyGuessWord(notificationMessage)
            ASKING_QUESTION -> mNotificationManger!!.notifyAskingQuestion(notificationMessage)
            FRIEND -> mNotificationManger!!.notifyFriendRequest(notificationMessage)
            INVITE -> mNotificationManger!!.notifyInviteToGame(notificationMessage)
            KICK_WARNING -> mNotificationManger!!.notifyKickWarning(notificationMessage)
            KICKED -> {
                mNotificationManger!!.notifyKick(notificationMessage)
                mPreferenceManager!!.setCurrentRoomId(-1)
                mPreferenceManager!!.setIsUserKicked(true)
            }
            GAME_OVER -> mNotificationManger!!.notifyGameOver(notificationMessage)
            LAST_PLAYER -> if (notificationMessage.getRoomId() === mPreferenceManager!!.getCurrentRoomId()) {
                mPreferenceManager!!.setIsLastUser(true)
                val lastPlayerIntent = Intent(ACTION_LAST_USER)
                sendBroadcast(lastPlayerIntent)
                if (!mPreferenceManager!!.isGameEnabled()) {
                    mNotificationManger!!.notifyLastUser(notificationMessage)
                }
            } else {
                mNotificationManger!!.notifyLastUser(notificationMessage)
            }
            NEW_ROOM_CREATED -> {
                val roomCreatedIntent = Intent(ACTION_ADD_NEW_ROOM)
                sendBroadcast(roomCreatedIntent)
            }
            APP_NEW_VERSION -> {
                mPreferenceManager!!.setAppNewVersion(true)
                val newVersionAvailableIntent = Intent(ACTION_NEW_VERSION_AVAILABLE)
                sendBroadcast(newVersionAvailableIntent)
            }
            UNDEFINED -> {
            }
        }
    }

    companion object {
        private val TAG = SmartHomeFirebaseMessagingService::class.java.name
        val FIELD_TYPE = "type"
        val DATA_TYPE = "data"
        val ACTION_ADD_NEW_ROOM = "com.hedbanz.ACTION_ADD_NEW_ROOM"
        val ACTION_NEW_VERSION_AVAILABLE = "com.hedbanz.ACTION_NEW_VERSION_AVAILABLE"
        val ACTION_LAST_USER = "com.hedbanz.ACTION_LAST_USER_IN_ROOM"
    }*/
}
