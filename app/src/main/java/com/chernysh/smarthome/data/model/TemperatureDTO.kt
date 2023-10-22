package com.chernysh.smarthome.data.model

import com.chernysh.smarthome.domain.model.TemperatureData
import com.google.gson.annotations.SerializedName

data class TemperatureDTO(@SerializedName("temperature") val temperature: Double){
    fun mapToEntity() = TemperatureData(temperature)
}