package com.example.appcontentprovider.provider.user

import android.content.ContentValues
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appcontentprovider.ui.user.UserDB

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserDB)

    @Query("SELECT * FROM user ORDER BY id DESC")
    fun getAllUsers(): LiveData<List<UserDB>>

    @Delete
    fun deleteUser(user: UserDB)

    @Query("DELETE FROM user WHERE id = :id")
    fun deleteUserId(id: Int)

    @Update
    fun updateUser(user: UserDB): Int

    // Provider
    @Query("SELECT * FROM user ORDER BY id DESC")
    fun getAllUsersProvider(): Cursor

    @Insert
    fun insertUserProvider(contents: ContentValues) {
        val user = UserDB(
            userName = contents.getAsString("userName"),
            userAge = contents.getAsInteger("userAge"),
            avatar = contents.getAsByteArray("avatar"),
            cityName = contents.getAsString("cityName")
        )
        insertUser(user)
    }

    @Query("DELETE FROM user WHERE id = :id")
    fun deleteUserIdProvider(id: Int): Int

    @Update
    fun updateUserProvider(contents: ContentValues): Int {
        val user = UserDB(
            id = contents.getAsInteger("id"),
            userName = contents.getAsString("userName"),
            userAge = contents.getAsInteger("userAge"),
            avatar = contents.getAsByteArray("avatar"),
            cityName = contents.getAsString("cityName")
        )
        Log.d("TAG", "updateUserProvider called")
        return updateUser(user)
    }
}
