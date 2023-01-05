package com.example.androidki.universities.dbWithRoom

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidki.universities.UniversityOperator

@Database(entities = [ UniversityOperator::class ], version=7, exportSchema = false)
abstract class AppDatabase: RoomDatabase()
{
    public abstract fun groupOperatorDao(): UniversityOperatorDao
}