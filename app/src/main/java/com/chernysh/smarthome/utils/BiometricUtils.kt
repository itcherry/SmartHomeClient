package com.chernysh.smarthome.utils

import android.content.Context
import android.os.Build
import androidx.core.hardware.fingerprint.FingerprintManagerCompat


/**
 * Created by Andrii Chernysh on 2020-01-15
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
object BiometricUtils {
    fun isFingerprintPromptAvailable(context: Context): Boolean =
        isBiometricPromptEnabled() &&
                isSdkVersionSupported() &&
                isHardwareSupported(context) &&
                isFingerprintAvailable(context)


    private fun isBiometricPromptEnabled(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    }

    /*
     * Condition I: Check if the android version in device is greater than
     * Marshmallow, since fingerprint authentication is only supported
     * from Android 6.0.
     * Note: If your project's minSdkversion is 23 or higher,
     * then you won't need to perform this check.
     *
     * */
    private fun isSdkVersionSupported(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    /*
     * Condition II: Check if the device has fingerprint sensors.
     * Note: If you marked android.hardware.fingerprint as something that
     * your app requires (android:required="true"), then you don't need
     * to perform this check.
     *
     * */
    private fun isHardwareSupported(context: Context): Boolean {
        val fingerprintManager: FingerprintManagerCompat = FingerprintManagerCompat.from(context)
        return fingerprintManager.isHardwareDetected()
    }

    /*
     * Condition III: Fingerprint authentication can be matched with a
     * registered fingerprint of the user. So we need to perform this check
     * in order to enable fingerprint authentication
     *
     * */
    private fun isFingerprintAvailable(context: Context): Boolean {
        val fingerprintManager: FingerprintManagerCompat = FingerprintManagerCompat.from(context)
        return fingerprintManager.hasEnrolledFingerprints()
    }
}