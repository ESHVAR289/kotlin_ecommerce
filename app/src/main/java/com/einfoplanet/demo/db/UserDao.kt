package com.einfoplanet.demo.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.einfoplanet.demo.model.User

@Dao
interface UserDao {

    @Query("SELECT *  FROM users")
    fun getDataSourcefactory(): DataSource.Factory<Int, User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(users: List<UserData>)

    //This method is use for DB test
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(users: UserData)

    //This method is use for DB test
    @Query("SELECT * FROM users WHERE id= :id")
    fun getUser(id: String): User

    @Query("DELETE FROM users")
    fun deleteUsers()

    @Query("UPDATE users SET flagAcceptDecline=:flagAcceptDecline WHERE id = :userId")
    fun updateAcceptDeclineStatus(userId: String, flagAcceptDecline: Int)

    @Query("SELECT * FROM users where flagAcceptDecline= :flagAcceptedOrDeclined ORDER BY flagAcceptDecline DESC")
    fun getAcceptedOrDeclinedUsers(flagAcceptedOrDeclined: Int): List<User>
}