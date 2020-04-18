package com.einfoplanet.demo.di.module

import com.einfoplanet.demo.TimberLogger
import com.einfoplanet.demo.di.scope.ApplicationScope
import com.einfoplanet.demo.domain.Logger
import dagger.Binds
import dagger.Module

@Module(includes = [ApplicationModule.LoggerModule::class])
class ApplicationModule() {
    @Module
    interface LoggerModule{
        @Binds
        @ApplicationScope
        fun bindLogger(loagger : TimberLogger) : Logger
    }
}