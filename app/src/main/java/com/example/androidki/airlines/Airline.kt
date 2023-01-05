package com.example.androidki.airlines

data class Airline(
    val name: String,
    var listOfPlanes: ArrayList<Plane> = ArrayList()
)
