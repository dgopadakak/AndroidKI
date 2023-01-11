package com.example.androidki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidki.forRecyclerView.CustomRecyclerAdapterForDirections

class DirectionsActivity : AppCompatActivity()
{
    private lateinit var recyclerViewDirections: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directions)

        val directionsString = intent.getStringExtra("directions")
        val studentsString = intent.getStringExtra("students")
        val directionsList: ArrayList<String> = directionsString!!.split(", ")
                as ArrayList<String>
        val studentsList: ArrayList<String> = studentsString!!.split(", ")
                as ArrayList<String>
        if (directionsList.size > studentsList.size)
        {
            val directionsListSize = directionsList.size
            val studentsListSize = studentsList.size
            for (i in 0 until (directionsListSize - studentsListSize))
            {
                studentsList.add("0")
            }
        }
        else if (directionsList.size < studentsList.size)
        {
            val directionsListSize = directionsList.size
            val studentsListSize = studentsList.size
            for (i in 0 until (studentsListSize - directionsListSize))
            {
                studentsList.removeAt(directionsListSize)
            }
        }

        recyclerViewDirections = findViewById(R.id.recyclerViewDirections)
        recyclerViewDirections.layoutManager = LinearLayoutManager(this)
        recyclerViewDirections.adapter = CustomRecyclerAdapterForDirections(
            directionsList, studentsList
        )
    }
}