/*
 * Copyright (C) 2018 Ashesh Bharadwaj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.einfoplanet.demo

import android.app.Application
import com.einfoplanet.demo.di.component.ApplicationComponent
import com.einfoplanet.demo.di.component.DaggerApplicationComponent

class UserSampleApp : Application() {

    private val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: UserSampleApp
            private set
    }

    fun getApplicationComponent(): ApplicationComponent = appComponent
}
