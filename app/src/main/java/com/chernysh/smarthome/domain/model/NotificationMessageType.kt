package com.chernysh.smarthome.domain.model

enum class NotificationMessageType(val id: Int) {
    HIGH_CPU_TEMPERATURE(1),
    NEPTUN_ALARM(2),
    SECURITY_ALARM(3),
    SECURITY_ENABLED(4),
    FIRE_ALARM(5),
    UNDEFINED(100500);

    companion object {
        fun getTypeById(id: Int): NotificationMessageType {
            NotificationMessageType.values().forEach {
                if (id == it.id) return it
            }
            return UNDEFINED
        }
    }
}
