package com.einfoplanet.demo.di.module

import android.app.Application
import androidx.room.Room
import com.einfoplanet.demo.db.UserDb
import com.einfoplanet.demo.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class UserDbModule() {
    @Provides
    @ApplicationScope
    fun provideUserDb(application: Application) =
            Room.databaseBuilder(
                    application,
                    UserDb::class.java, "users.db"
            ).build()

    @Provides
    @ApplicationScope
    fun provideUserDao(userDb: UserDb) = userDb.userDao()
}