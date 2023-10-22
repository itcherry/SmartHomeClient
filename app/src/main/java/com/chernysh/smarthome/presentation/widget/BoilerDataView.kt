package com.chernysh.smarthome.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.chernysh.smarthome.R
import com.chernysh.smarthome.databinding.LayoutBoilerDataBinding
import com.chernysh.smarthome.domain.model.BoilerScheduleViewState
import com.chernysh.smarthome.domain.model.BoilerViewState
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.utils.toHourAndMinute
import com.chernysh.timerangepicker.TimeRange
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable


class BoilerDataView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0, defStyleRes: Int = 0
) :
    FrameLayout(context, attributeSet, defStyleAttr, defStyleRes) {
    private var binding: LayoutBoilerDataBinding
    init {
        binding = LayoutBoilerDataBinding.inflate(LayoutInflater.from(context), this, true);
    }

    fun enableBoilerScheduleStateIntent(): Observable<Boolean> = RxView.clicks(binding.cvBoilerDataContainer)
        .map {
            binding.switchAllDay.isChecked = !binding.switchAllDay.isChecked
            binding.switchAllDay.isChecked
        }

    fun render(boilerViewState: BoilerViewState) {
        renderBoilerScheduleState(boilerViewState.schedule)
        renderBoilerScheduleEnabledState(boilerViewState.scheduleEnabledState)
        renderBoilerEnabledState(boilerViewState.enabledState)
    }

    private fun renderBoilerEnabledState(state: BooleanViewState) {
        when(state){
            is BooleanViewState.LoadingState -> {
               binding.pbTimeRangesLoading.visibility = View.VISIBLE
               binding.tvTimeRanges.visibility = View.GONE
            }
            is BooleanViewState.DataState -> {
                binding.pbTimeRangesLoading.visibility = View.GONE
                if(!state.data) {
                    binding.viewDisabledState.visibility = View.VISIBLE
                    binding.tvTimeRanges.text = context.getString(R.string.boiler_disabled)
                }
            }
            else -> {
                binding.pbTimeRangesLoading.visibility = View.GONE
                binding.viewDisabledState.visibility = View.VISIBLE
                binding.tvTimeRanges.text = context.getString(R.string.server_error)
            }
        }
    }

    private fun renderBoilerScheduleState(scheduleState: BoilerScheduleViewState){
        when(scheduleState){
            is BoilerScheduleViewState.LoadingState -> {
                binding.pbTimeRangesLoading.visibility = View.VISIBLE
                binding.tvTimeRanges.visibility = View.GONE
            }
            is BoilerScheduleViewState.SubmitSuccessState -> renderScheduleData(scheduleState.data)
            is BoilerScheduleViewState.DataState -> renderScheduleData(scheduleState.data)
            else -> {
                binding.pbTimeRangesLoading.visibility = View.GONE
                binding.tvTimeRanges.visibility = View.VISIBLE
                binding.viewDisabledState.visibility = View.VISIBLE
                binding.tvTimeRanges.text = context.getString(R.string.server_error)
            }
        }
    }

    private fun renderScheduleData(timeRanges: List<TimeRange>) {
        binding.pbTimeRangesLoading.visibility = GONE
        binding.tvTimeRanges.visibility = VISIBLE
        if (timeRanges.isEmpty()) {
            binding.tvTimeRanges.text = context.getString(R.string.boiler_disabled_schedule)
        } else {
            setTimeRangesTextFromList(timeRanges)
        }
    }

    fun setTimeRangesTextFromList(timeRanges: List<TimeRange>) {
        binding.tvTimeRanges.text = timeRanges.sortedBy { it.startTime }
            .mapIndexed { index, timeRange ->
                "${index + 1}) ${timeRange.startTime.toHourAndMinute()} - ${timeRange.endTime.toHourAndMinute()}"
            }
            .joinToString("\n")
    }

    private fun renderBoilerScheduleEnabledState(boilerEnabledState: BooleanViewState) {
        when(boilerEnabledState) {
            is BooleanViewState.DataState -> {
                binding.switchAllDay.isChecked = boilerEnabledState.data
                if(boilerEnabledState.data){
                    binding.tvTimeRanges.text = context.getString(R.string.boiler_all_day)
                }
            }
            is BooleanViewState.LoadingState -> {
                binding.pbTimeRangesLoading.visibility = View.VISIBLE
                binding.tvTimeRanges.visibility = View.GONE
            }
            else -> {
                binding.switchAllDay.isChecked = !binding.switchAllDay.isChecked
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        binding.viewDisabledState.visibility = if(enabled) View.GONE else View.VISIBLE
        binding.cvBoilerDataContainer.isEnabled = isEnabled
    }
}