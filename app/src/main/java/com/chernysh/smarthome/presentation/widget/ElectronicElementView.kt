package com.chernysh.smarthome.presentation.widget

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.StringRes
import com.chernysh.smarthome.R
import com.chernysh.smarthome.databinding.LayoutElementBinding
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable

/**
 * Created by Andrii Chernysh on 3/24/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class ElectronicElementView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0, defStyleRes: Int = 0
) :
    FrameLayout(context, attributeSet, defStyleAttr, defStyleRes) {
    private var binding: LayoutElementBinding

    private var isElementEnabled: Boolean = false
    private lateinit var type: Type

    init {
        binding = LayoutElementBinding.inflate(LayoutInflater.from(context), this, true);
        initElementType(context, attributeSet)

        binding.ivElementIcon.setImageDrawable(VectorDrawableCompat.create(context.resources, type.disabledImageRes, null))
        binding.tvElementName.text = context.getString(type.stringRes)
    }

    private fun initElementType(context: Context, attributeSet: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ElectronicElementView)
        type = Type.values()[typedArray.getInt(R.styleable.ElectronicElementView_type, 0)]
        typedArray.recycle()
    }

    fun setElementStateIntent(): Observable<Boolean> = RxView.clicks(binding.clElementContainer).map { !isElementEnabled }

    fun render(booleanViewState: BooleanViewState) {
        when (booleanViewState) {
            is BooleanViewState.LoadingState -> renderLoading()
            is BooleanViewState.DataState -> renderData(booleanViewState.data)
            is BooleanViewState.ErrorState -> renderError(booleanViewState.error)
            is BooleanViewState.ConnectivityErrorState -> renderConnectivityError()
        }
    }

    private fun renderLoading() {
        binding.pbElementStateLoading.visibility = View.VISIBLE
        binding.tvElementState.visibility = View.GONE
        binding.switchElement.isChecked = false
    }

    private fun renderData(isEnabled: Boolean) {
        binding.switchElement.isEnabled = true
        binding.switchElement.isChecked = isEnabled
        binding.pbElementStateLoading.visibility = View.GONE
        binding.tvElementState.visibility = View.VISIBLE
        binding.tvElementState.text = getStateString(isEnabled)
        this.isElementEnabled = isEnabled

        binding.ivElementIcon.setImageDrawable(VectorDrawableCompat.create(context.resources, type.getImageByState(isEnabled), null))
    }

    private fun getStateString(isEnabled: Boolean) = if (isEnabled) {
        context.getString(R.string.state_enabled)
    } else {
        context.getString(R.string.state_disabled)
    }

    private fun renderError(throwable: Throwable) {
        val state = getStateString(isElementEnabled)
        binding.tvElementState.text = "${context.getString(R.string.server_error)} ($state)"
        binding.switchElement.isEnabled = true
        binding.switchElement.isChecked = isElementEnabled
        binding.ivElementIcon.setImageDrawable(VectorDrawableCompat.create(context.resources, type.getImageByState(isElementEnabled), null))

        binding.pbElementStateLoading.visibility = View.GONE
        binding.tvElementState.visibility = View.VISIBLE
    }

    private fun renderConnectivityError() {
        val state = getStateString(isElementEnabled)
        binding.tvElementState.text = "${context.getString(R.string.network_error)} ($state)"
        binding.switchElement.isEnabled = true
        binding.switchElement.isChecked = isElementEnabled
        binding.ivElementIcon.setImageDrawable(VectorDrawableCompat.create(context.resources, type.getImageByState(isElementEnabled), null))

        binding.pbElementStateLoading.visibility = View.GONE
        binding.tvElementState.visibility = View.VISIBLE
    }

    enum class Type(@DrawableRes val enabledImageRes: Int, @DrawableRes val disabledImageRes: Int, @StringRes val stringRes: Int) {
        ROZETKA(R.drawable.ic_socket, R.drawable.ic_socket_dis, R.string.rozette_title),
        LIGHT(R.drawable.ic_lamp, R.drawable.ic_lamp_dis, R.string.lights_title),
        AQUARIUM(R.drawable.ic_aquarium_enabled, R.drawable.ic_aquarium_disabled, R.string.aquarium_title);
    }

    private fun Type.getImageByState(isEnabled: Boolean) = if(isEnabled) enabledImageRes else disabledImageRes
}