package com.einfoplanet.demo.di.component

import android.app.Application
import com.einfoplanet.demo.di.module.*
import com.einfoplanet.demo.di.scope.ApplicationScope
import dagger.BindsInstance
import dagger.Component


@Component(modules = [BookDbModule::class,
    UserRetrofitModule::class])
@ApplicationScope
interface ApplicationComponent{
    fun plusFragmentComponent() : FragmentComponent


    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent
        @BindsInstance
        fun application(application: Application): Builder
    }

}