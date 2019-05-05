package com.chernysh.smarthome.utils

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat

/**
 * Utility class that processes runtime permissions
 *
 * @author Andrii Chernysh
 * Developed by <u>Transcendensoft</u>
 */

object PermissionUtils {
    private val MNC = "M"

    //Request codes
    val CAMERA_AND_STORAGE_REQUEST_CODE = 1
    val READ_STORAGE_REQUEST_CODE = 2
    val STORAGE_REQUEST_CODE = 3

    // Calendar group.
    val READ_CALENDAR = Manifest.permission.READ_CALENDAR
    val WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR

    // Camera group.
    val CAMERA = Manifest.permission.CAMERA

    // Storage group
    val WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    val READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE

    // Contacts group.
    val READ_CONTACTS = Manifest.permission.READ_CONTACTS
    val WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS

    // Location group.
    val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION

    // Microphone group.
    val RECORD_AUDIO = Manifest.permission.RECORD_AUDIO

    // Phone group.
    val READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE
    val CALL_PHONE = Manifest.permission.CALL_PHONE
    val READ_CALL_LOG = Manifest.permission.READ_CALL_LOG
    val WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG
    val ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL
    val USE_SIP = Manifest.permission.USE_SIP
    val PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS

    // Sensors group.
    val BODY_SENSORS = Manifest.permission.BODY_SENSORS
    val USE_FINGERPRINT = Manifest.permission.USE_FINGERPRINT

    // SMS group.
    val SEND_SMS = Manifest.permission.SEND_SMS
    val RECEIVE_SMS = Manifest.permission.RECEIVE_SMS
    val READ_SMS = Manifest.permission.READ_SMS
    val RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH
    val RECEIVE_MMS = Manifest.permission.RECEIVE_MMS
    val READ_CELL_BROADCASTS = "android.permission.READ_CELL_BROADCASTS"

    // Bookmarks group.
    val READ_HISTORY_BOOKMARKS = "com.android.browser.permission.READ_HISTORY_BOOKMARKS"
    val WRITE_HISTORY_BOOKMARKS = "com.android.browser.permission.WRITE_HISTORY_BOOKMARKS"

    private val isMNC: Boolean
        get() = Build.VERSION.SDK_INT >= 23

    /**
     * Create an array from a given permissions.
     *
     * @throws IllegalArgumentException
     */
    fun asArray(vararg permissions: String): Array<String?> {
        if (permissions.size == 0) {
            throw IllegalArgumentException("There is no given permission")
        }

        val dest = arrayOfNulls<String>(permissions.size)
        var i = 0
        val len = permissions.size
        while (i < len) {
            dest[i] = permissions[i]
            i++
        }
        return dest
    }

    /**
     * Check that given permission have been granted.
     */
    fun hasGranted(grantResult: Int): Boolean {
        return grantResult == PERMISSION_GRANTED
    }

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value [android.content.pm.PackageManager.PERMISSION_GRANTED].
     */
    fun hasGranted(grantResults: IntArray): Boolean {
        for (result in grantResults) {
            if (!hasGranted(result)) {
                return false
            }
        }
        return true
    }

    /**
     * Returns true if the Context has access to a given permission.
     * Always returns true on platforms below M.
     */
    fun hasSelfPermission(context: Context, permission: String): Boolean {
        return if (isMNC) {
            permissionHasGranted(context, permission)
        } else true
    }

    /**
     * Returns true if the Context has access to all given permissions.
     * Always returns true on platforms below M.
     */
    fun hasSelfPermissions(context: Context, permissions: Array<String>): Boolean {
        if (!isMNC) {
            return true
        }

        for (permission in permissions) {
            if (!permissionHasGranted(context, permission)) {
                return false
            }
        }
        return true
    }

    /**
     * Requests permissions to be granted to this application.
     */
    fun requestAllPermissions(activity: Activity, permissions: Array<String>, requestCode: Int) {
        if (isMNC) {
            internalRequestPermissions(activity, permissions, requestCode)
        }
    }

    /**
     * Requests permissions to be granted to this application.
     */
    fun requestAllPermissions(fragment: androidx.fragment.app.Fragment, permissions: Array<String>, requestCode: Int) {
        if (isMNC) {
            internalRequestPermissions(fragment, permissions, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun internalRequestPermissions(fragment: androidx.fragment.app.Fragment?, permissions: Array<String>, requestCode: Int) {
        if (fragment == null) {
            throw IllegalArgumentException("Given activity is null.")
        }
        fragment.requestPermissions(permissions, requestCode)
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun internalRequestPermissions(activity: Activity?, permissions: Array<String>, requestCode: Int) {
        if (activity == null) {
            throw IllegalArgumentException("Given activity is null.")
        }
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun permissionHasGranted(context: Context, permission: String): Boolean {
        return hasGranted(ContextCompat.checkSelfPermission(context, permission))
    }
}
