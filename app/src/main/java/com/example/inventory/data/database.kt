package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [data::class], version = 1)
abstract class database:RoomDatabase() {

    abstract fun dao():Dao

    companion object{
        //The value of a volatile variable will never be cached, and all writes and reads will be done to and from the main memory.
        // This helps make sure the value of INSTANCE is always up-to-date and the same for all execution threads.
        // It means that changes made by one thread to INSTANCE are visible to all other threads immediately.
        @Volatile
        private var INSTANCE:database?=null

        fun getdatabase(context: Context):database{
            return INSTANCE ?: synchronized(this){
                //Multiple threads can potentially run into a race condition and ask for a database instance at the same time, resulting in two databases instead of one.
                // Wrapping the code to get the database inside a synchronized block means that only one thread of execution at a time can enter this block of code,
                // which makes sure the database only gets initialized once.
                val instance=Room.databaseBuilder(
                    context.applicationContext,
                    database::class.java,
                    "database"
                ).build()

                INSTANCE=instance

                return instance
            }
        }
    }
}