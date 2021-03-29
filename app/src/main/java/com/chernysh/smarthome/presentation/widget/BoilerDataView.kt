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

    fun enableBoilerScheduleStateIntent(): Observable<Boolean> = RxView.clicks(switchAllDay)
        .map {
            switchAllDay.isChecked = !switchAllDay.isChecked
            switchAllDay.isChecked
        }

    fun render(boilerViewState: BoilerViewState) {
        renderBoilerScheduleEnabledState(boilerViewState.scheduleEnabledState)
        renderBoilerScheduleState(boilerViewState.schedule)
    }

    private fun renderBoilerScheduleState(scheduleState: BoilerScheduleViewState){
        when(scheduleState){
            is BoilerScheduleViewState.LoadingState -> {
                pbTimeRangesLoading.visibility = View.VISIBLE
                tvTimeRanges.visibility = View.GONE
            }
            is BoilerScheduleViewState.DataState -> {
                pbTimeRangesLoading.visibility = View.GONE
                tvTimeRanges.visibility = View.VISIBLE
                tvTimeRanges.text = scheduleState.data
                    .mapIndexed { index, timeRange ->
                        "${index+1}) ${timeRange.startTime.toHourAndMinute()} - ${timeRange.endTime.toHourAndMinute()}"
                    }
                    .joinToString("\n")
            }
            else -> {
                pbTimeRangesLoading.visibility = View.GONE
                tvTimeRanges.visibility = View.VISIBLE
                tvTimeRanges.text = ""
            }
        }
    }

    private fun renderBoilerScheduleEnabledState(boilerEnabledState: BooleanViewState) {
        when(boilerEnabledState) {
            is BooleanViewState.DataState -> {
                switchAllDay.isClickable = true
                switchAllDay.isEnabled = true
                switchAllDay.isChecked = boilerEnabledState.data
            }
            else -> {
                switchAllDay.isClickable = false
                switchAllDay.isEnabled = false
                switchAllDay.isChecked = false
            }
        }
    }
}