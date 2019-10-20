package com.chernysh.smarthome.data.exception

import com.chernysh.smarthome.data.source.DataPolicy

class DataPolicyNotImplemented(dataPolicy: DataPolicy): RuntimeException("Data policy: $dataPolicy is not implemented. Try to use another.")