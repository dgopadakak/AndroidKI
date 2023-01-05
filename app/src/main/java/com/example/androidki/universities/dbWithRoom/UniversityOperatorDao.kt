package com.example.androidki.universities.dbWithRoom

import androidx.room.*
import com.example.androidki.universities.UniversityOperator

@Dao
interface UniversityOperatorDao
{
    @Query("SELECT * FROM UniversityOperator")
    fun getAll(): List<UniversityOperator?>?

    @Query("SELECT * FROM UniversityOperator WHERE id = :id")
    fun getById(id: Int): UniversityOperator

    @Insert
    fun insert(go: UniversityOperator?)

    @Delete
    fun delete(go: UniversityOperator?)
}