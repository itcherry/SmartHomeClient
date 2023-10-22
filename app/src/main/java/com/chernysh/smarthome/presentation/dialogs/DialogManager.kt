package com.chernysh.smarthome.presentation.dialogs

import android.content.Context
import com.chernysh.smarthome.R
import team.uptech.huddle.Huddle
import team.uptech.huddle.util.extension.dialog
import team.uptech.huddle.builder.Builder
import team.uptech.huddle.util.extension.dp

fun Context.buildConnectivityErrorDialog(
    positiveListener: (dialog: Huddle) -> Unit = {}
) = dialog<Huddle, Builder> {
    isCancelableOnTouchOutside = false
    content {
        title = getString(R.string.dialog_connectivity_error_title)
        message = getString(R.string.dialog_connectivity_error_text)
    }
    image {
        resource = R.drawable.ic_network_error
        width = 38f.dp.toInt()
        height = 38f.dp.toInt()
    }
    positiveCTA {
        text = getString(R.string.action_ok)
        onClick { positiveListener.invoke(it) }
    }
}

fun Context.buildGenericErrorDialog(
    positiveListener: (dialog: Huddle) -> Unit = {}
) = dialog<Huddle, Builder> {
    isCancelableOnTouchOutside = false
    content {
        title = getString(R.string.dialog_generic_error_title)
        message = getString(R.string.dialog_connectivity_error_text)
    }
    image {
        resource = R.drawable.ic_network_error
        width = 38f.dp.toInt()
        height = 38f.dp.toInt()
    }
    positiveCTA {
        text = getString(R.string.action_ok)
        onClick { positiveListener.invoke(it) }
    }
}