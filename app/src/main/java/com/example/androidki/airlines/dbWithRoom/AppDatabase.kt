package com.example.androidki.airlines.dbWithRoom

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidki.airlines.AirlineOperator

@Database(entities = [ AirlineOperator::class ], version=6, exportSchema = false)
abstract class AppDatabase: RoomDatabase()
{
    public abstract fun groupOperatorDao(): AirlineOperatorDao
}