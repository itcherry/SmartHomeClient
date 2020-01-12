package com.chernysh.smarthome.data.network.source

import com.chernysh.smarthome.data.source.BedroomDataSource
import com.chernysh.smarthome.data.source.SecurityDataSource
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject


/**
 * Data source for changing and getting state of
 * rozetkas or light at bedroom
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class SecurityApiDataSource @Inject constructor() :
    ApiDataSource(), SecurityDataSource {

    override fun setSecurityState(isEnabled: Boolean): Maybe<Boolean> =
        service.setSecurityEnabled(isEnabled)

    override fun getSecurityState(): Single<Boolean> = service.isSecurityEnabled()

    override fun isFireAtHome(): Single<Boolean> = service.isFireAtHome()
}