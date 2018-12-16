package com.chernysh.smarthome.data.network.source

import com.transcendensoft.hedbanz.data.source.FirebaseIdDataSource
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Copyright 2017. Andrii Chernysh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
/**
 * Bind and unbind firebase token on server
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
class FirebaseIdApiDataSource @Inject constructor() : ApiDataSource(), FirebaseIdDataSource {

    override fun unbindFirebaseToken(userId: Long): Completable =
            mService.unbindFirebaseToken(userId)


    override fun bindFirebaseToken(userId: Long, token: String): Completable =
            mService.bindFirebaseToken(userId, mapOf("fcmToken" to token))
}
