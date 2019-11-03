package com.chernysh.smarthome.presentation.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.media.AudioAttributes
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.chernysh.smarthome.R
import com.chernysh.smarthome.data.cache.UserCacheDataSource
import com.chernysh.smarthome.di.qualifier.ApplicationContext
import com.chernysh.smarthome.presentation.flat.FlatActivity
import com.chernysh.smarthome.presentation.login.LoginActivity
import com.crashlytics.android.Crashlytics
import javax.inject.Inject

class NotificationManager @Inject constructor(
    @ApplicationContext val context: Context,
    val userCacheDataSource: UserCacheDataSource
) {
    companion object {
        private const val NEPTUN_NOTIFICATION_ID = 1
        private const val SECURITY_ENABLED_NOTIFICATION_ID = 2
        private const val SECURITY_NOTIFICATION_ID = 3
        private const val FIRE_NOTIFICATION_ID = 4
        private const val CPU_NOTIFICATION_ID = 5

        private const val NEPTUN_NOTIFICATION_REQUEST_CODE = 100
        private const val SECURITY_ENABLED_NOTIFICATION_REQUEST_CODE = 101
        private const val SECURITY_NOTIFICATION_REQUEST_CODE = 102
        private const val FIRE_NOTIFICATION_REQUEST_CODE = 103
        private const val CPU_NOTIFICATION_REQUEST_CODE = 104

        private const val NEPTUN_CHANNEL_ID = "NEPTUN_CHANNEL"
        private const val SECURITY_ENABLED_CHANNEL_ID = "SECURITY_ENABLED_CHANNEL"
        private const val SECURITY_CHANNEL_ID = "SECURITY_CHANNEL"
        private const val FIRE_CHANNEL_ID = "FIRE_CHANNEL"
        private const val CPU_CHANNEL_ID = "CPU_CHANNEL"
    }

    fun notifyNeptun() {
        val notification = getNotificationInstance(
            NEPTUN_CHANNEL_ID,
            R.string.notification_neptun_channel_title,
            R.string.notification_neptun_alarm_title,
            context.getString(R.string.notification_neptun_alarm_message),
            NEPTUN_NOTIFICATION_REQUEST_CODE,
            true
        )

        notify(NEPTUN_NOTIFICATION_ID, notification)
    }

    fun notifySecurityEnabled(isEnabled: Boolean) {
        val messageRes = if (isEnabled) R.string.notification_security_enabled_alarm_message else R.string.notification_security_disabled_alarm_message
        val notification = getNotificationInstance(
            SECURITY_ENABLED_CHANNEL_ID,
            R.string.notification_security_enabled_channel_title,
            R.string.notification_security_enabled_alarm_title,
            context.getString(messageRes),
            SECURITY_ENABLED_NOTIFICATION_REQUEST_CODE,
            false
        )

        notify(SECURITY_ENABLED_NOTIFICATION_ID, notification)
    }

    fun notifySecurityAlarm() {
        val notification = getNotificationInstance(
            SECURITY_CHANNEL_ID,
            R.string.notification_security_channel_title,
            R.string.notification_security_alarm_title,
            context.getString(R.string.notification_security_alarm_message),
            SECURITY_NOTIFICATION_REQUEST_CODE,
            true
        )

        notify(SECURITY_NOTIFICATION_ID, notification)
    }

    fun notifyCpuIsTooHot(temperature: Int) {
        val notification = getNotificationInstance(
            CPU_CHANNEL_ID,
            R.string.notification_cpu_channel_title,
            R.string.notification_cpu_alarm_title,
            context.getString(R.string.notification_cpu_alarm_message, "$temperature &#8451;"),
            CPU_NOTIFICATION_REQUEST_CODE,
            true
        )

        notify(CPU_NOTIFICATION_ID, notification)
    }

    fun notifyFire() {
        val notification = getNotificationInstance(
            FIRE_CHANNEL_ID,
            R.string.notification_fire_channel_title,
            R.string.notification_fire_alarm_title,
            context.getString(R.string.notification_fire_alarm_message),
            FIRE_NOTIFICATION_REQUEST_CODE,
            true
        )

        notify(FIRE_NOTIFICATION_ID, notification)
    }

    private fun getNotificationInstance(
        channelId: String,
        @StringRes channelTitleRes: Int,
        @StringRes titleRes: Int,
        message: String,
        requestCode: Int,
        isAlarmSound: Boolean
    ): Notification {
        createNotificationChannel(channelId, context.getString(channelTitleRes), isAlarmSound)

        val title = context.getString(titleRes)
        val spannableTitle = SpannableString(title)
        spannableTitle.setSpan(
            StyleSpan(Typeface.BOLD),
            0, title.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return notification(channelId, isAlarmSound) {
            setContentTitle(spannableTitle)
            setContentText(message)

            val intent = if (userCacheDataSource.getToken().isNotBlank()) {
                Intent(context, FlatActivity::class.java)
            } else {
                Intent(context, LoginActivity::class.java)
            }.addFlags(
                Intent.FLAG_ACTIVITY_SINGLE_TOP or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_NEW_TASK
            )

            val pendingIntent = PendingIntent.getActivity(
                context, requestCode,
                intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            setContentIntent(pendingIntent)
        }
    }

    private fun notify(notificationId: Int, notification: Notification) {
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE)
                as NotificationManager
        notificationManager.notify(notificationId, notification)
    }

    private fun notification(
        channelId: String,
        isAlarmSound: Boolean,
        block: NotificationCompat.Builder.() -> Unit
    ): Notification {
        val notificationBuilder = NotificationCompat.Builder(context, channelId)

        val soundUri = Uri.parse(
            "android.resource://com.transcendensoft.hedbanz/" +
                    if (isAlarmSound) R.raw.alarm_sound else R.raw.lock_sound
        )
        notificationBuilder.setSound(soundUri, AudioManager.STREAM_NOTIFICATION)
            .setLights(Color.argb(100, 250, 185, 5), 2000, 700)
            .setPriority(Notification.PRIORITY_MAX)
            .setAutoCancel(false)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.mipmap.ic_launcher
                )
            )
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0L, 100L, 300L, 100L))
            .apply(block)

        try {
            notificationBuilder.setSmallIcon(R.drawable.ic_launcher)
        } catch (remoteServiceException: RuntimeException) {
            val e = RuntimeException("Can`t set image icon notification")
            e.stackTrace = remoteServiceException.stackTrace
            Crashlytics.logException(e)
        }

        return notificationBuilder.build()
    }

    private fun createNotificationChannel(channelId: String, channelTitle: String, isAlarmSound: Boolean) {
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelTitle, NotificationManager.IMPORTANCE_HIGH
            )

            // Configure the notification channel.
            notificationChannel.enableLights(true)
            val soundUri = Uri.parse(
                "android.resource://com.transcendensoft.hedbanz/" +
                        if (isAlarmSound) R.raw.alarm_sound else R.raw.lock_sound
            )
            val attrs = AudioAttributes.Builder()
            attrs.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            attrs.setUsage(AudioAttributes.USAGE_NOTIFICATION)

            notificationChannel.setSound(soundUri, attrs.build())
            notificationChannel.shouldVibrate()
            notificationChannel.vibrationPattern = longArrayOf(0L, 100L, 300L, 100L)
            notificationChannel.lightColor = Color.argb(100, 250, 185, 5)

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}