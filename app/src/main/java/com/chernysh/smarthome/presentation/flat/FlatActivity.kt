package com.chernysh.smarthome.presentation.flat

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.chernysh.smarthome.R
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.FlatViewState
import com.chernysh.smarthome.domain.model.TemperatureHumidityViewState
import com.chernysh.smarthome.presentation.base.BaseActivity
import com.chernysh.smarthome.presentation.bedroom.BedroomActivity
import com.chernysh.smarthome.presentation.corridor.CorridorActivity
import com.chernysh.smarthome.presentation.kitchen.KitchenActivity
import com.chernysh.smarthome.presentation.living_room.LivingRoomActivity
import com.chernysh.smarthome.utils.Notification
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_flat.*
import kotlinx.android.synthetic.main.layout_flat_main.*

class FlatActivity : BaseActivity<FlatContract.View, FlatPresenter>(), FlatContract.View {
    private val turnOnAlarmSubject: PublishSubject<Boolean> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat)
    }

    override fun openBedroomActivity(): Observable<Any> = RxView.clicks(btnBedroom)

    override fun openKitchenActivity(): Observable<Any> = RxView.clicks(btnKitchen)

    override fun openLivingRoomActivity(): Observable<Any> = RxView.clicks(btnLivingRoom)

    override fun openCorridorActivity(): Observable<Any> = RxView.clicks(btnCorridor)

    override fun setBoilerStateIntent(): Observable<Boolean> = RxCompoundButton.checkedChanges(switchBoiler)

    override fun setDoorStateIntent(): Observable<Boolean> = RxCompoundButton.checkedChanges(switchDoor)

    override fun showAlarmDialog(): Observable<Any> = RxCompoundButton
            .checkedChanges(switchAlarm)
            .doOnNext { if (!it) turnOnAlarmSubject.onNext(false) }
            .filter { it }
            .map { Notification.INSTANCE }

    override fun acceptedAlarmIntent(): Observable<Boolean> = turnOnAlarmSubject

    override fun render(state: FlatViewState) {
        when (state) {
            is FlatViewState.BedroomClicked -> startActivity(Intent(this, BedroomActivity::class.java))
            is FlatViewState.CorridorClicked -> startActivity(Intent(this, CorridorActivity::class.java))
            is FlatViewState.LivingRoomClicked -> startActivity(Intent(this, LivingRoomActivity::class.java))
            is FlatViewState.KitchenClicked -> startActivity(Intent(this, KitchenActivity::class.java))
            is FlatViewState.ShowAlarmDialogClicked -> showDialogForAlarm()
            is FlatViewState.SafetyViewState -> renderSafetyViewState(state)
        }
    }

    private fun showDialogForAlarm() {
        AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_warning)
                .setTitle(getString(R.string.flat_alert_dialog_title))
                .setMessage(getString(R.string.flat_alert_dialog_text))
                .setPositiveButton(getString(R.string.action_yes)) { _: DialogInterface, _: Int ->
                    turnOnAlarmSubject.onNext(true)
                }
                .setNegativeButton(getString(R.string.action_no)) { dialog: DialogInterface, _: Int ->
                    dialog.dismiss()
                }
                .setCancelable(true)
                .show()
    }

    private fun renderSafetyViewState(state: FlatViewState.SafetyViewState) {
        renderAlarm(state.alarmViewState)
        renderBoiler(state.boilerViewState)
        renderNeptun(state.neptunViewState)
        renderDoor(state.doorViewState)
        renderTemperatureHumidity(state.temperatureHumidityOutsideViewState)
    }

    private fun renderAlarm(state: BooleanViewState) {
        switchAlarm.isEnabled = true

        when (state) {
            is BooleanViewState.ErrorState,
            is BooleanViewState.ConnectivityErrorState -> switchAlarm.isChecked = !switchAlarm.isChecked
            is BooleanViewState.LoadingState -> switchAlarm.isEnabled = false
            is BooleanViewState.DataState -> switchAlarm.isChecked = state.data
        }
    }

    // This one renders error message to screen
    private fun renderBoiler(state: BooleanViewState) {
        switchBoiler.isEnabled = true

        when (state) {
            is BooleanViewState.ErrorState -> {
                showSnackError(getString(R.string.server_error_with_message, state.error.message
                        ?: ""))
                switchBoiler.isChecked = !switchBoiler.isChecked
            }
            is BooleanViewState.ConnectivityErrorState -> {
                showSnackError(getString(R.string.network_error))
                switchBoiler.isChecked = !switchBoiler.isChecked
            }
            is BooleanViewState.LoadingState -> switchBoiler.isEnabled = false
            is BooleanViewState.DataState -> switchBoiler.isChecked = state.data
        }
    }

    private fun renderNeptun(state: BooleanViewState) {
        pbNeptunLoading.visibility = View.GONE
        tvNeptun.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        when (state) {
            is BooleanViewState.ErrorState,
            is BooleanViewState.ConnectivityErrorState -> {
                tvNeptun.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_close, 0)
            }
            is BooleanViewState.LoadingState -> {
                pbNeptunLoading.visibility = View.VISIBLE
            }
            is BooleanViewState.DataState -> {
                val icon = if (state.data) {
                    R.drawable.ic_error
                } else {
                    R.drawable.ic_thumbs_up
                }
                tvNeptun.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0)
            }
        }
    }

    private fun renderDoor(state: BooleanViewState) {
        switchDoor.isEnabled = true

        when (state) {
            is BooleanViewState.ErrorState,
            is BooleanViewState.ConnectivityErrorState -> switchDoor.isChecked = !switchDoor.isChecked
            is BooleanViewState.LoadingState -> switchDoor.isEnabled = false
            is BooleanViewState.DataState -> switchDoor.isChecked = state.data
        }
    }

    private fun renderTemperatureHumidity(state: TemperatureHumidityViewState) {
        when (state) {
            is TemperatureHumidityViewState.SocketConnectedState,
            is TemperatureHumidityViewState.NoDataState -> {
                tvTemperature.text = getString(R.string.temperature_humidity_no_data)
                tvHumidity.text = getString(R.string.temperature_humidity_no_data)
            }
            is TemperatureHumidityViewState.SocketErrorState,
            is TemperatureHumidityViewState.ErrorState,
            is TemperatureHumidityViewState.SocketDisconnectedState -> {
                tvTemperature.text = getString(R.string.server_error)
                tvHumidity.text = getString(R.string.server_error)
            }
            is TemperatureHumidityViewState.SocketReconnectingState -> {
                tvTemperature.text = getString(R.string.action_loading)
                tvHumidity.text = getString(R.string.action_loading)
            }
            is TemperatureHumidityViewState.DataState -> {
                tvTemperature.text = getString(R.string.temperature_text, state.data.temperature)
                tvHumidity.text = getString(R.string.humidity_text, state.data.humidity)
            }
        }
    }

}
