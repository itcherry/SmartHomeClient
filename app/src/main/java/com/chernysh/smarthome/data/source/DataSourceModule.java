package com.chernysh.smarthome.data.source;
/**
 * Copyright 2018. Andrii Chernysh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.chernysh.smarthome.di.scope.ApplicationScope;
import dagger.Binds;
import dagger.Module;
import ua.andrii.chernysh.rxsockets.data.network.source.TemperatureHumiditySocketDataSource;

/**
 * Dagger 2 module to provide different sockets and DB data sources.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */
@Module
public interface DataSourceModule {
    @Binds
    @ApplicationScope
    TemperatureHumidityDataSource provideTemperatureHumiditySocketDataSource(TemperatureHumiditySocketDataSource dataSource);
}
