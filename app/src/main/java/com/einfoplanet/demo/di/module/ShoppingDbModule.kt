package com.einfoplanet.demo.di.module

import android.app.Application
import androidx.room.Room
import com.einfoplanet.demo.db.ShoppingDb
import com.einfoplanet.demo.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ShoppingDbModule() {
    @Provides
    @ApplicationScope
    fun provideShoppingDb(application: Application) =
            Room.databaseBuilder(
                    application,
                    ShoppingDb::class.java, "cart.db"
            ).build()

    @Provides
    @ApplicationScope
    fun provideCartDao(shoppingDb: ShoppingDb) = shoppingDb.cartDao()
}