package com.einfoplanet.demo.di.module

import android.app.Application
import androidx.room.Room
import com.einfoplanet.demo.db.BookDb
import com.einfoplanet.demo.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class BookDbModule() {
    @Provides
    @ApplicationScope
    fun providBooksDb(application: Application) =
            Room.databaseBuilder(
                    application,
                    BookDb::class.java, "books.db"
            ).build()

    @Provides
    @ApplicationScope
    fun provideBookDao(bookDb: BookDb) = bookDb.bookDao()

}