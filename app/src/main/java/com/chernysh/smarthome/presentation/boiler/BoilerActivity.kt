package com.chernysh.smarthome.presentation.boiler

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.chernysh.smarthome.R
import com.chernysh.smarthome.databinding.ActivityBoilerBinding
import com.chernysh.smarthome.domain.model.BoilerScheduleViewState
import com.chernysh.smarthome.domain.model.BoilerViewState
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.presentation.base.BaseActivity
import com.chernysh.smarthome.presentation.dialogs.buildConnectivityErrorDialog
import com.chernysh.smarthome.presentation.dialogs.buildGenericErrorDialog
import com.chernysh.timerangepicker.TimeRange
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import team.uptech.huddle.Huddle
import team.uptech.huddle.util.extension.compose
import team.uptech.huddle.util.extension.isLaunched

/**
 * Copyright 2021. Andrii Chernysh
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

/**
 * Activity for displaying bedroom device states.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class  BoilerActivity : BaseActivity<BoilerContract.View, BoilerPresenter>(), BoilerContract.View {
    private var dialog: Huddle? = null
    private val timeRanges: MutableList<TimeRange> = mutableListOf()
    private lateinit var binding: ActivityBoilerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoilerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.fabMenu.setOnClickListener {
            onBackPressed()
        }

        binding.scheduleTimePicker.timeRangesSelected = {
            binding.boilerDataView.setTimeRangesTextFromList(it)
            binding.btnSave.isEnabled = (timeRanges != it) && (timeRanges.isNotEmpty() || it.isNotEmpty())
        }
    }

    override fun saveBoilerScheduleIntent(): Observable<List<TimeRange>> =
        RxView.clicks(binding.btnSave).map { binding.scheduleTimePicker.getSelectedTimeRanges() }

    override fun setBoilerStateIntent(): Observable<Boolean> = RxView.clicks(binding.ivBoilerEnabler)
        .map { !(binding.ivBoilerEnabler.tag as? Boolean ?: false) }

    override fun setBoilerScheduleStateIntent(): Observable<Boolean> =
        binding.boilerDataView.enableBoilerScheduleStateIntent()

    override fun render(state: BoilerViewState) {
        val (enabledState, scheduleEnabledState, schedule) = state

        renderBoilerEnabledStates(enabledState)

        when (scheduleEnabledState) {
            is BooleanViewState.LoadingState -> disableRangePickerAndOtherData()
            is BooleanViewState.ConnectivityErrorState -> renderConnectivityError()
            is BooleanViewState.ErrorState -> renderGenericError()
            is BooleanViewState.DataState -> {
                binding.scheduleTimePicker.enableAllDay(scheduleEnabledState.data)
            }
        }

        when (schedule) {
            is BoilerScheduleViewState.LoadingState -> disableRangePickerAndOtherData()
            is BoilerScheduleViewState.SubmitSuccessState -> {
                renderBoilerScheduleData(schedule.data)
                Toast.makeText(
                    this,
                    R.string.boiler_schedule_successfully_updated,
                    Toast.LENGTH_LONG
                ).show()
            }
            is BoilerScheduleViewState.ConnectivityErrorState -> renderConnectivityError()
            is BoilerScheduleViewState.ErrorState -> renderGenericError()
            is BoilerScheduleViewState.DataState -> renderBoilerScheduleData(schedule.data)
        }

        binding.boilerDataView.render(state)
    }

    private fun renderBoilerScheduleData(newTimeRanges: List<TimeRange>) {
        binding.scheduleTimePicker.setSelectedTimeRanges(newTimeRanges)
        timeRanges.apply {
            clear()
            addAll(newTimeRanges)
        }
        binding.btnSave.isEnabled = false
    }

    private fun renderBoilerEnabledStates(enabledState: BooleanViewState) {
        when (enabledState) {
            is BooleanViewState.LoadingState -> {
                binding.pbBoilerEnableLoading.visibility = View.VISIBLE
                binding.ivBoilerEnabler.visibility = View.GONE
                binding.boilerDataView.isEnabled = false
            }
            is BooleanViewState.DataState -> {
                binding.pbBoilerEnableLoading.visibility = View.GONE
                binding.ivBoilerEnabler.visibility = View.VISIBLE
                if (enabledState.data) {
                    binding.ivBoilerEnabler.tag = true
                    binding.ivBoilerEnabler.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.ic_switch_on
                        )
                    )
                    enableRangePickerAndOtherData()
                } else {
                    binding.ivBoilerEnabler.tag = false
                    binding.ivBoilerEnabler.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.ic_switch_off
                        )
                    )
                    disableRangePickerAndOtherData()
                }
            }
            is BooleanViewState.ConnectivityErrorState -> renderConnectivityError()
            is BooleanViewState.ErrorState -> renderGenericError()
        }
    }

    private fun renderConnectivityError() {
        if (!dialog.isLaunched()) {
            disableRangePickerAndOtherData()
            buildConnectivityErrorDialog {
                onBackPressed()
            }.compose(this).also { dialog = it }
        }
    }

    private fun renderGenericError() {
        if (!dialog.isLaunched()) {
            disableRangePickerAndOtherData()
            buildGenericErrorDialog {
                onBackPressed()
            }.compose(this).also { dialog = it }
        }
    }

    private fun enableRangePickerAndOtherData() {
       binding.boilerDataView.isEnabled = true
       binding.btnSave.isEnabled = (timeRanges != binding.scheduleTimePicker.getSelectedTimeRanges()) &&
                (timeRanges.isNotEmpty() || binding.scheduleTimePicker.getSelectedTimeRanges().isNotEmpty())

        binding.scheduleTimePicker.apply {
            isEnabled = true
            setArcColor(R.color.colorPrimaryDark)
            setCircleTextColor(R.color.textPrimary)
            setThumbColor(R.color.colorPrimaryDark)
            setCenterTextColor(R.color.colorPrimaryDark)
            setDotsColor(R.color.textSecondary)
            invalidate()
        }
    }

    private fun disableRangePickerAndOtherData() {
        binding.boilerDataView.isEnabled = false
        binding.btnSave.isEnabled = false
        binding.scheduleTimePicker.apply {
            isEnabled = false
            setThumbColor(R.color.textSecondary)
            setArcColor(R.color.textSecondary)
            setCircleTextColor(R.color.textSecondary)
            setDotsColor(R.color.textSecondaryLight)
            invalidate()
        }
    }
}