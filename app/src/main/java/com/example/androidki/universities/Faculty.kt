package com.example.androidki.universities

data class Faculty(
    val name: String,
    val directions: String,
    val num: Int,
    val email: String,
    val dateOfFoundation: String,
    val students: Int,
    val isHaveDistanceLearning: Int,
    val comment: String
)
