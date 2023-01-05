package com.example.androidki.universities.dbWithRoom

import androidx.room.TypeConverter
import com.example.androidki.universities.University
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class UniversityOperatorConverter
{
    @TypeConverter
    fun fromGO(universities: ArrayList<University>): String
    {
        val gsonBuilder = GsonBuilder()
        val gson: Gson = gsonBuilder.create()
        return gson.toJson(universities)
    }

    @TypeConverter
    fun toGO(data: String): ArrayList<University>
    {
        val gsonBuilder = GsonBuilder()
        val gson: Gson = gsonBuilder.create()
        return try {
            val type: Type = object : TypeToken<ArrayList<University>>() {}.type
            gson.fromJson(data, type)
        } catch (e: Exception) {
            ArrayList()
        }
    }
}