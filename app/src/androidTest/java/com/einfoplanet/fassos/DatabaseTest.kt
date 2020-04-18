package com.einfoplanet.demo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.einfoplanet.demo.db.UserDao
import com.einfoplanet.demo.db.UserData
import com.einfoplanet.demo.db.UserDb
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var userDao: UserDao
    private lateinit var db: UserDb

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, UserDb::class.java).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val user = UserData(
                title = "Mrs",
                first = "Katarina",
                last = "Mostad",
                city = "Isfjorden",
                state = "Oslo",
                country = "Norway",
                latitude = "73.3382",
                longitude = "15.8943",
                date = "1982-09-28T14:36:13.425Z",
                age = 38,
                large = "https://randomuser.me/api/portraits/women/15.jpg",
                medium = "https://randomuser.me/api/portraits/med/women/15.jpg",
                thumbnail = "https://randomuser.me/api/portraits/thumb/women/15.jpg",
                id = "3230",
                page = 1,
                seed = "abc",
                gender = "female")

        userDao.insertUsers(user)
        val byName = userDao.getUser(user.id)
        assertThat(byName.id, equalTo(user.id))
    }
}