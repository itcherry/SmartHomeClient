package com.chernysh.smarthome.data.repository

import com.chernysh.smarthome.data.network.source.FirebaseIdApiDataSource
import com.chernysh.smarthome.domain.repository.FirebaseIdRepository
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Copyright 2018. Andrii Chernysh
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Interface that represents a Repository (or Gateway)
 * for binding and unbinding firebase id
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class FirebaseIdRepositoryImpl @Inject constructor(
    private val mFirebaseIdApiDataSource: FirebaseIdApiDataSource
) : FirebaseIdRepository {
    override fun bindFirebaseToken(userId: Long, token: String): Maybe<Any> =
        mFirebaseIdApiDataSource.bindFirebaseToken(userId, token)

    override fun unbindFirebaseToken(userId: Long): Maybe<Any> =
        mFirebaseIdApiDataSource.unbindFirebaseToken(userId)

}