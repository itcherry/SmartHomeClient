package com.chernysh.smarthome.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.chernysh.smarthome.R
import com.chernysh.smarthome.domain.model.BoilerScheduleViewState
import com.chernysh.smarthome.domain.model.BoilerViewState
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.utils.toHourAndMinute
import com.chernysh.timerangepicker.TimeRange
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.layout_boiler_data.view.*


class BoilerDataView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0, defStyleRes: Int = 0
) :
    FrameLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    init {
        val view = View.inflate(getContext(), R.layout.layout_boiler_data, null)
        addView(view)
    }

    fun enableBoilerScheduleStateIntent(): Observable<Boolean> = RxView.clicks(cvBoilerDataContainer)
        .map {
            switchAllDay.isChecked = !switchAllDay.isChecked
            switchAllDay.isChecked
        }

    fun render(boilerViewState: BoilerViewState) {
        renderBoilerScheduleState(boilerViewState.schedule)
        renderBoilerScheduleEnabledState(boilerViewState.scheduleEnabledState)
        renderBoilerEnabledState(boilerViewState.enabledState)
    }

    private fun renderBoilerEnabledState(state: BooleanViewState) {
        when(state){
            is BooleanViewState.LoadingState -> {
                pbTimeRangesLoading.visibility = View.VISIBLE
                tvTimeRanges.visibility = View.GONE
            }
            is BooleanViewState.DataState -> {
                pbTimeRangesLoading.visibility = View.GONE
                if(!state.data) {
                    viewDisabledState.visibility = View.VISIBLE
                    tvTimeRanges.text = context.getString(R.string.boiler_disabled)
                }
            }
            else -> {
                pbTimeRangesLoading.visibility = View.GONE
                viewDisabledState.visibility = View.VISIBLE
                tvTimeRanges.text = context.getString(R.string.server_error)
            }
        }
    }

    private fun renderBoilerScheduleState(scheduleState: BoilerScheduleViewState){
        when(scheduleState){
            is BoilerScheduleViewState.LoadingState -> {
                pbTimeRangesLoading.visibility = View.VISIBLE
                tvTimeRanges.visibility = View.GONE
            }
            is BoilerScheduleViewState.SubmitSuccessState -> renderScheduleData(scheduleState.data)
            is BoilerScheduleViewState.DataState -> renderScheduleData(scheduleState.data)
            else -> {
                pbTimeRangesLoading.visibility = View.GONE
                tvTimeRanges.visibility = View.VISIBLE
                viewDisabledState.visibility = View.VISIBLE
                tvTimeRanges.text = context.getString(R.string.server_error)
            }
        }
    }

    private fun renderScheduleData(timeRanges: List<TimeRange>) {
        pbTimeRangesLoading.visibility = GONE
        tvTimeRanges.visibility = VISIBLE
        if (timeRanges.isEmpty()) {
            tvTimeRanges.text = context.getString(R.string.boiler_disabled_schedule)
        } else {
            setTimeRangesTextFromList(timeRanges)
        }
    }

    fun setTimeRangesTextFromList(timeRanges: List<TimeRange>) {
        tvTimeRanges.text = timeRanges.sortedBy { it.startTime }
            .mapIndexed { index, timeRange ->
                "${index + 1}) ${timeRange.startTime.toHourAndMinute()} - ${timeRange.endTime.toHourAndMinute()}"
            }
            .joinToString("\n")
    }

    private fun renderBoilerScheduleEnabledState(boilerEnabledState: BooleanViewState) {
        when(boilerEnabledState) {
            is BooleanViewState.DataState -> {
                switchAllDay.isChecked = boilerEnabledState.data
                if(boilerEnabledState.data){
                    tvTimeRanges.text = context.getString(R.string.boiler_all_day)
                }
            }
            is BooleanViewState.LoadingState -> {
                pbTimeRangesLoading.visibility = View.VISIBLE
                tvTimeRanges.visibility = View.GONE
            }
            else -> {
                switchAllDay.isChecked = !switchAllDay.isChecked
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        viewDisabledState.visibility = if(enabled) View.GONE else View.VISIBLE
        cvBoilerDataContainer.isEnabled = isEnabled
    }
}