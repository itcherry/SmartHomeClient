package com.chernysh.smarthome.data.model

import com.google.gson.annotations.SerializedName

data class TemperatureHumidityDTO(@SerializedName("temperature") val temperature: Double,
                                  @SerializedName("humidity") val humidity: Double){
    /*fun mapToEntity() =
            TemperatureHumidityData(temperature, humidity)*/
}