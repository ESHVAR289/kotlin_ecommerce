package com.einfoplanet.demo.di.component

import android.app.Application
import com.einfoplanet.demo.di.module.ShoppingDbModule
import com.einfoplanet.demo.di.module.RestaurantRetrofitModule
import com.einfoplanet.demo.di.scope.ApplicationScope
import dagger.BindsInstance
import dagger.Component


@Component(modules = [ShoppingDbModule::class,
    RestaurantRetrofitModule::class])
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