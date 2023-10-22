package com.chernysh.smarthome.presentation.flat

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.AbsoluteLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import com.chernysh.smarthome.R
import com.chernysh.smarthome.databinding.ActivityCorridorBinding
import com.chernysh.smarthome.databinding.ActivityFlatBinding
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.FlatViewState
import com.chernysh.smarthome.domain.model.TemperatureHumidityViewState
import com.chernysh.smarthome.presentation.base.BaseActivity
import com.chernysh.smarthome.presentation.bedroom.BedroomActivity
import com.chernysh.smarthome.presentation.boiler.BoilerActivity
import com.chernysh.smarthome.presentation.camera.CameraActivity
import com.chernysh.smarthome.presentation.corridor.CorridorActivity
import com.chernysh.smarthome.presentation.kitchen.KitchenActivity
import com.chernysh.smarthome.presentation.living_room.LivingRoomActivity
import com.chernysh.smarthome.utils.Notification
import com.chernysh.smarthome.utils.dpToPx
import com.chernysh.smarthome.utils.expandClickArea
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class FlatActivity : BaseActivity<FlatContract.View, FlatPresenter>(), FlatContract.View {
    private val turnOnAlarmSubject: PublishSubject<Boolean> = PublishSubject.create()
    private val turnOnSecuritySubject: PublishSubject<Boolean> = PublishSubject.create()
    private val reloadDataSubject: PublishSubject<Any> = PublishSubject.create()
    private lateinit var binding: ActivityFlatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlatBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initButtons()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_logout)
                .setTitle(getString(R.string.logout_title))
                .setMessage(getString(R.string.logout_message))
                .setPositiveButton(getString(R.string.action_logout)) { _: DialogInterface, _: Int ->
                    finishAndRemoveTask()
                    super.onBackPressed()
                    System.exit(0)
                }
                .setNegativeButton(getString(R.string.action_stay)) { dialog: DialogInterface, _: Int ->
                    dialog.dismiss()
                }
                .setCancelable(true)
                .show()
        }

    }

    private fun initButtons() {
        val viewTreeObserver = binding.flatMainLayout.flatPlanView.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    binding.flatMainLayout.flatPlanView.viewTreeObserver.removeOnGlobalLayoutListener(
                        this
                    )

                    val xStart = 32.dpToPx(this@FlatActivity)
                    val xEnd =
                        binding.flatMainLayout.flatPlanView.measuredWidth - 32.dpToPx(this@FlatActivity)
                    val yStart = 100.dpToPx(this@FlatActivity)
                    val yEnd =
                        binding.flatMainLayout.flatPlanView.measuredHeight - 100.dpToPx(this@FlatActivity)

                    val flatWidth = xEnd - xStart
                    val flatHeight = yEnd - yStart

                    initKitchenButton(xStart, yStart, flatHeight)
                    initLivingRoomButton(xStart, flatWidth, yStart, flatHeight)
                    initBedroomButton(xEnd, flatWidth, yStart, flatHeight)
                    initCorridorButton(xStart, flatWidth, yStart, flatHeight)
                }
            })
        }

        binding.flatMainLayout.fabMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun initCorridorButton(xStart: Int, flatWidth: Int, yStart: Int, flatHeight: Int) {
        binding.flatMainLayout.btnCorridor.layoutParams =
            (binding.flatMainLayout.btnCorridor.layoutParams as AbsoluteLayout.LayoutParams).apply {
                x = xStart + (flatWidth / 5.8f).toInt()
                y = yStart + (4.6f * flatHeight / 7).toInt()
            }
        binding.flatMainLayout.btnCorridor.expandClickArea(
            binding.flatMainLayout.alFlatPlan,
            (flatHeight / 6.7f).toInt(),
            (flatHeight / 6.7f).toInt(),
            flatWidth / 5,
            12.dpToPx(this@FlatActivity)
        )
    }

    private fun initBedroomButton(xEnd: Int, flatWidth: Int, yStart: Int, flatHeight: Int) {
        binding.flatMainLayout.btnBedroom.layoutParams =
            (binding.flatMainLayout.btnBedroom.layoutParams as AbsoluteLayout.LayoutParams).apply {
                x = xEnd - (flatWidth / 4)
                y = yStart + (2.7f * flatHeight / 6).toInt()
            }
        binding.flatMainLayout.btnBedroom.expandClickArea(
            binding.flatMainLayout.alFlatPlan,
            (flatHeight / 7f).toInt(),
            (flatHeight / 7f).toInt(),
            20.dpToPx(this@FlatActivity),
            20.dpToPx(this@FlatActivity)
        )
    }

    private fun initLivingRoomButton(xStart: Int, flatWidth: Int, yStart: Int, flatHeight: Int) {
        binding.flatMainLayout.btnLivingRoom.layoutParams =
            (binding.flatMainLayout.btnLivingRoom.layoutParams as AbsoluteLayout.LayoutParams).apply {
                x = xStart + (flatWidth / 2.3f).toInt()
                y = yStart + (flatHeight / 3.5f).toInt()
            }
        binding.flatMainLayout.btnLivingRoom.expandClickArea(
            binding.flatMainLayout.alFlatPlan,
            (flatHeight / 3.5f).toInt(),
            (flatHeight / 3.5f).toInt(),
            12.dpToPx(this@FlatActivity),
            16.dpToPx(this@FlatActivity)
        )
    }

    private fun initKitchenButton(xStart: Int, yStart: Int, flatHeight: Int) {
        binding.flatMainLayout.btnKitchen.layoutParams =
            (binding.flatMainLayout.btnKitchen.layoutParams as AbsoluteLayout.LayoutParams).apply {
                x = xStart + 18.dpToPx(this@FlatActivity)
                y = yStart + flatHeight / 5
            }
        binding.flatMainLayout.btnKitchen.expandClickArea(
            binding.flatMainLayout.alFlatPlan,
            flatHeight / 5,
            flatHeight / 8,
            18.dpToPx(this@FlatActivity),
            18.dpToPx(this@FlatActivity)
        )
    }

    override fun openBedroomActivity(): Observable<Any> =
        RxView.clicks(binding.flatMainLayout.btnBedroom)

    override fun openKitchenActivity(): Observable<Any> =
        RxView.clicks(binding.flatMainLayout.btnKitchen)

    override fun openLivingRoomActivity(): Observable<Any> =
        RxView.clicks(binding.flatMainLayout.btnLivingRoom)

    override fun openCorridorActivity(): Observable<Any> =
        RxView.clicks(binding.flatMainLayout.btnCorridor)

    override fun openCameraActivity(): Observable<Any> = RxView.clicks(binding.tvCamera)

    override fun openDanfossActivity(): Observable<Any> = RxView.clicks(binding.tvDanfoss)

    override fun openBoilerActivity(): Observable<Any> = RxView.clicks(binding.tvBoiler)

    override fun openAirConditionerActivity(): Observable<Any> =
        RxView.clicks(binding.tvAirConditioner)

    override fun initDataIntent(): Observable<Boolean> = Observable.just(true)

    override fun showSecurityDialog(): Observable<Any> = RxView.clicks(binding.switchSecurity)
        .doOnNext { if (!binding.switchSecurity.isChecked) turnOnSecuritySubject.onNext(false) }
        .filter { binding.switchSecurity.isChecked }
        .map { Notification.INSTANCE }

    override fun showAlarmDialog(): Observable<Any> = RxView.clicks(binding.switchAlarm)
        .doOnNext { if (!binding.switchAlarm.isChecked) turnOnAlarmSubject.onNext(false) }
        .filter { binding.switchAlarm.isChecked }
        .map { Notification.INSTANCE }

    override fun acceptedAlarmIntent(): Observable<Boolean> = turnOnAlarmSubject

    override fun acceptedSecurityIntent(): Observable<Boolean> = turnOnSecuritySubject

    override fun render(state: FlatViewState) {
        when (state) {
            is FlatViewState.BedroomClicked -> startActivity(
                Intent(
                    this,
                    BedroomActivity::class.java
                )
            )

            is FlatViewState.CorridorClicked -> startActivity(
                Intent(
                    this,
                    CorridorActivity::class.java
                )
            )

            is FlatViewState.LivingRoomClicked -> startActivity(
                Intent(
                    this,
                    LivingRoomActivity::class.java
                )
            )

            is FlatViewState.KitchenClicked -> startActivity(
                Intent(
                    this,
                    KitchenActivity::class.java
                )
            )

            is FlatViewState.CameraClicked -> startActivity(
                Intent(
                    this,
                    CameraActivity::class.java
                )
            )

            is FlatViewState.BoilerClicked -> startActivity(
                Intent(
                    this,
                    BoilerActivity::class.java
                )
            )

            is FlatViewState.ShowAlarmDialogClicked -> showDialogForAlarm()
            is FlatViewState.ShowSecurityDialogClicked -> showDialogForSecurity()
            is FlatViewState.SafetyViewState -> renderSafetyViewState(state)
            is FlatViewState.AirConditionerClicked -> openThirdPartyApplication(FlatContract.AIR_CONDITIONER_PACKAGE_NAME)
            is FlatViewState.DanfossClicked -> openThirdPartyApplication(FlatContract.DANFOSS_PACKAGE_NAME)
            else -> {
                // TODO check if something is needed here
            }
        }
    }

    private fun openThirdPartyApplication(packageName: String) {
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)

        if (launchIntent != null) {
            startActivity(launchIntent);
        } else {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$packageName")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                    )
                )
            }
        }
    }

    override fun reloadDataObservable(): Observable<Any> = reloadDataSubject

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
                binding.switchAlarm.isChecked = false
            }
            .setCancelable(false)
            .show()
    }

    private fun showDialogForSecurity() {
        AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_shield)
            .setTitle(getString(R.string.flat_security_dialog_title))
            .setMessage(getString(R.string.flat_security_dialog_text))
            .setPositiveButton(getString(R.string.action_yes)) { _: DialogInterface, _: Int ->
                turnOnSecuritySubject.onNext(true)
            }
            .setNegativeButton(getString(R.string.action_no)) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
                binding.switchSecurity.isChecked = false
            }
            .setCancelable(false)
            .show()
    }

    private fun renderSafetyViewState(state: FlatViewState.SafetyViewState) {
        renderAlarm(state.alarmViewState)
        renderNeptun(state.neptunViewState)
        renderFire(state.fireViewState)
        renderSecurity(state.securityViewState)
        renderTemperatureHumidity(state.temperatureHumidityOutsideViewState)
    }

    private fun renderAlarm(state: BooleanViewState) {
        binding.switchAlarm.isEnabled = true

        when (state) {
            is BooleanViewState.ErrorState,
            is BooleanViewState.ConnectivityErrorState -> binding.switchAlarm.isChecked =
                !binding.switchAlarm.isChecked

            is BooleanViewState.LoadingState -> binding.switchAlarm.isEnabled = false
            is BooleanViewState.DataState -> binding.switchAlarm.isChecked = state.data
        }
    }

    private fun renderNeptun(state: BooleanViewState) {
        binding.pbNeptunLoading.visibility = View.GONE
        binding.tvNeptun.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        when (state) {
            is BooleanViewState.ErrorState,
            is BooleanViewState.ConnectivityErrorState -> {
                binding.tvNeptun.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_close,
                    0
                )
            }

            is BooleanViewState.LoadingState -> {
                binding.pbNeptunLoading.visibility = View.VISIBLE
            }

            is BooleanViewState.DataState -> {
                val icon = if (state.data) {
                    R.drawable.ic_error
                } else {
                    R.drawable.ic_thumbs_up
                }
                binding.tvNeptun.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0)
            }
        }
    }

    private fun renderFire(state: BooleanViewState) {
        binding.pbFireLoading.visibility = View.GONE
        binding.tvFire.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        when (state) {
            is BooleanViewState.ErrorState,
            is BooleanViewState.ConnectivityErrorState -> {
                binding.tvFire.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_close, 0)
            }

            is BooleanViewState.LoadingState -> {
                binding.pbFireLoading.visibility = View.VISIBLE
            }

            is BooleanViewState.DataState -> {
                val icon = if (state.data) {
                    R.drawable.ic_fire
                } else {
                    R.drawable.ic_thumbs_up
                }
                binding.tvFire.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0)
            }
        }
    }

    // This one renders error message to screen
    private fun renderSecurity(state: BooleanViewState) {
        binding.switchSecurity.isEnabled = true

        when (state) {
            is BooleanViewState.ErrorState -> {
                showServerSnackbarError { reloadDataSubject.onNext(Notification.INSTANCE) }
                binding.switchSecurity.isChecked = !binding.switchSecurity.isChecked
            }

            is BooleanViewState.ConnectivityErrorState -> {
                showNetworkSnackbarError { reloadDataSubject.onNext(Notification.INSTANCE) }
                binding.switchSecurity.isChecked = !binding.switchSecurity.isChecked
            }

            is BooleanViewState.LoadingState -> binding.switchSecurity.isEnabled = false
            is BooleanViewState.DataState -> binding.switchSecurity.isChecked = state.data
        }
    }

    private fun renderTemperatureHumidity(state: TemperatureHumidityViewState) {
        when (state) {
            is TemperatureHumidityViewState.SocketConnectedState,
            is TemperatureHumidityViewState.NoDataState -> {
                binding.flatMainLayout.tvTemperature.text =
                    getString(R.string.temperature_humidity_no_data)
            }

            is TemperatureHumidityViewState.SocketErrorState,
            is TemperatureHumidityViewState.ErrorState,
            is TemperatureHumidityViewState.SocketDisconnectedState -> {
                binding.flatMainLayout.tvTemperature.text = getString(R.string.server_error)
            }

            is TemperatureHumidityViewState.SocketReconnectingState -> {
                binding.flatMainLayout.tvTemperature.text = getString(R.string.action_loading)
            }

            is TemperatureHumidityViewState.DataState -> {
                binding.flatMainLayout.tvTemperature.text =
                    getString(R.string.temperature_text, state.data.temperature)
            }
        }
    }

}
