package com.chernysh.smarthome.data.source

import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Getting and setting boiler state in data sources
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
interface SecurityDataSource {
    fun setSecurityState(isEnabled: Boolean): Maybe<Boolean>
    fun getSecurityState(): Single<Boolean>
    fun isFireAtHome(): Single<Boolean>
}