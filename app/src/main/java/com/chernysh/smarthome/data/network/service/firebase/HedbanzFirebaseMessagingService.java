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
import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.transcendensoft.hedbanz.data.prefs.PreferenceManager;
import com.transcendensoft.hedbanz.domain.entity.NotificationMessage;
import com.transcendensoft.hedbanz.domain.entity.NotificationMessageType;
import com.transcendensoft.hedbanz.presentation.notification.NotificationManager;

import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasServiceInjector;
import timber.log.Timber;

/**
 * Service that handles Firebase push notifications.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */

public class HedbanzFirebaseMessagingService extends FirebaseMessagingService implements HasServiceInjector {
    private static final String TAG = HedbanzFirebaseMessagingService.class.getName();
    public static final String FIELD_TYPE = "type";
    public static final String DATA_TYPE = "data";
    public static final String ACTION_ADD_NEW_ROOM = "com.hedbanz.ACTION_ADD_NEW_ROOM";
    public static final String ACTION_NEW_VERSION_AVAILABLE =
            "com.hedbanz.ACTION_NEW_VERSION_AVAILABLE";
    public static final String ACTION_LAST_USER =
            "com.hedbanz.ACTION_LAST_USER_IN_ROOM";

    @Inject DispatchingAndroidInjector<Service> serviceDispatchingAndroidInjector;
    @Inject Gson mGson;
    @Inject NotificationManager mNotificationManger;
    @Inject PreferenceManager mPreferenceManager;

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
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Timber.tag(TAG);

        // Check if message_received contains a notification payload.
        if (remoteMessage.getData() != null) {
            Map<String, String> bodyMap = remoteMessage.getData();
            int type = Integer.parseInt(bodyMap.get(FIELD_TYPE));
            Timber.i("FCM push. notification type:" + type);

            NotificationMessageType messageType = NotificationMessageType.
                    Companion.getTypeById(type);
            String dataJson = bodyMap.get(DATA_TYPE);
            Timber.i("FCM push. data:" + dataJson);

            processNotificationMessage(messageType, dataJson);
        }
    }

    private void processNotificationMessage(NotificationMessageType messageType, String dataJson) {
        NotificationMessage notificationMessage = mGson
                .fromJson(dataJson, NotificationMessage.class);;
        switch (messageType) {
            case MESSAGE:
                mNotificationManger.notifyMessage(notificationMessage);
                break;
            case SET_WORD:
                mNotificationManger.notifySetWord(notificationMessage);
                break;
            case GUESS_WORD:
                mNotificationManger.notifyGuessWord(notificationMessage);
                break;
            case ASKING_QUESTION:
                mNotificationManger.notifyAskingQuestion(notificationMessage);
                break;
            case FRIEND:
                mNotificationManger.notifyFriendRequest(notificationMessage);
                break;
            case INVITE:
                mNotificationManger.notifyInviteToGame(notificationMessage);
                break;
            case KICK_WARNING:
                mNotificationManger.notifyKickWarning(notificationMessage);
                break;
            case KICKED:
                mNotificationManger.notifyKick(notificationMessage);
                mPreferenceManager.setCurrentRoomId(-1);
                mPreferenceManager.setIsUserKicked(true);
                break;
            case GAME_OVER:
                mNotificationManger.notifyGameOver(notificationMessage);
                break;
            case LAST_PLAYER:
                if(notificationMessage.getRoomId() == (mPreferenceManager.getCurrentRoomId())) {
                    mPreferenceManager.setIsLastUser(true);
                    Intent lastPlayerIntent = new Intent(ACTION_LAST_USER);
                    sendBroadcast(lastPlayerIntent);
                    if(!mPreferenceManager.isGameEnabled()){
                        mNotificationManger.notifyLastUser(notificationMessage);
                    }
                } else {
                    mNotificationManger.notifyLastUser(notificationMessage);
                }
                break;
            case NEW_ROOM_CREATED:
                Intent roomCreatedIntent = new Intent(ACTION_ADD_NEW_ROOM);
                sendBroadcast(roomCreatedIntent);
                break;
            case APP_NEW_VERSION:
                mPreferenceManager.setAppNewVersion(true);
                Intent newVersionAvailableIntent = new Intent(ACTION_NEW_VERSION_AVAILABLE);
                sendBroadcast(newVersionAvailableIntent);
                break;
            case UNDEFINED:
                break;
            default:
        }
    }
}
