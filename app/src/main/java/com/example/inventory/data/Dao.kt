package com.example.inventory.data

import android.content.ClipData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@androidx.room.Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: data)

    @Update
    suspend fun update(data: data)

    @Delete
    suspend fun delete(data: data)

    @Query("SELECT * from data WHERE id = :id")
    fun getitem(id:Int): Flow<data>

    @Query("SELECT * from data ORDER BY name ASC")
    fun getitems(): Flow<List<data>>

}