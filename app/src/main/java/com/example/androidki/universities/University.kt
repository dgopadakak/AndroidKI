package com.example.androidki.universities

data class University(
    val name: String,
    var listOfFaculties: ArrayList<Faculty> = ArrayList()
)
