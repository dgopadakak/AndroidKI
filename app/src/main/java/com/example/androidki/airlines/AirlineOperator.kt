package com.example.androidki.airlines

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.androidki.airlines.dbWithRoom.AirlineOperatorConverter
import java.util.*
import kotlin.collections.ArrayList

@Entity
class AirlineOperator()
{
    @PrimaryKey
    private var id: Int = 1

    @TypeConverters(AirlineOperatorConverter::class)
    private var airlines: ArrayList<Airline> = ArrayList()

    fun getAirlines(): ArrayList<Airline>
    {
        return airlines
    }

    fun setAirlines(newAirlines: ArrayList<Airline>)
    {
        airlines = newAirlines
    }

    fun setId(id: Int)
    {
        this.id = id
    }

    fun getId(): Int
    {
        return id
    }

    fun getPlaneModels(indexGroup: Int): ArrayList<String>
    {
        val arrayListForReturn: ArrayList<String> = ArrayList()
        for (i in airlines[indexGroup].listOfPlanes)
        {
            arrayListForReturn.add(i.model)
        }
        return arrayListForReturn
    }

    fun getPlanesNumbers(indexGroup: Int): ArrayList<Int>
    {
        val arrayListForReturn: ArrayList<Int> = ArrayList()
        for (i in airlines[indexGroup].listOfPlanes)
        {
            arrayListForReturn.add(i.num)
        }
        return arrayListForReturn
    }

    fun getPlane(indexGroup: Int, indexExam: Int): Plane
    {
        return airlines[indexGroup].listOfPlanes[indexExam]
    }

    fun sortPlanes(indexGroup: Int, sortIndex: Int)
    {
        if (sortIndex == 0)
        {
            val tempArrayListOfTasksNames: ArrayList<String> = ArrayList()
            val tempArrayListOfPlanes: ArrayList<Plane> = ArrayList()
            for (i in airlines[indexGroup].listOfPlanes)
            {
                tempArrayListOfTasksNames.add(i.model.lowercase(Locale.ROOT))
            }
            tempArrayListOfTasksNames.sort()
            for (i in tempArrayListOfTasksNames)
            {
                for (j in airlines[indexGroup].listOfPlanes)
                {
                    if (i == j.model.lowercase(Locale.ROOT)
                        && !tempArrayListOfPlanes.contains(j))
                    {
                        tempArrayListOfPlanes.add(j)
                        break
                    }
                }
            }
            airlines[indexGroup].listOfPlanes = tempArrayListOfPlanes
        }

        if (sortIndex == 1)
        {
            val tempArrayListOfTasksConditions: ArrayList<String> = ArrayList()
            val tempArrayListOfPlanes: ArrayList<Plane> = ArrayList()
            for (i in airlines[indexGroup].listOfPlanes)
            {
                tempArrayListOfTasksConditions.add(i.color.lowercase(Locale.ROOT))
            }
            tempArrayListOfTasksConditions.sort()
            for (i in tempArrayListOfTasksConditions)
            {
                for (j in airlines[indexGroup].listOfPlanes)
                {
                    if (i == j.color.lowercase(Locale.ROOT)
                        && !tempArrayListOfPlanes.contains(j))
                    {
                        tempArrayListOfPlanes.add(j)
                        break
                    }
                }
            }
            airlines[indexGroup].listOfPlanes = tempArrayListOfPlanes
        }

        if (sortIndex == 2)
        {
            val tempArrayListOfTasksNumbers: ArrayList<Int> = ArrayList()
            val tempArrayListOfPlanes: ArrayList<Plane> = ArrayList()
            for (i in airlines[indexGroup].listOfPlanes)
            {
                tempArrayListOfTasksNumbers.add(i.num)
            }
            tempArrayListOfTasksNumbers.sort()
            for (i in tempArrayListOfTasksNumbers)
            {
                for (j in airlines[indexGroup].listOfPlanes)
                {
                    if (i == j.num && !tempArrayListOfPlanes.contains(j))
                    {
                        tempArrayListOfPlanes.add(j)
                        break
                    }
                }
            }
            airlines[indexGroup].listOfPlanes = tempArrayListOfPlanes
        }

        if (sortIndex == 3)
        {
            val tempArrayListOfTasksNumOfParticipants: ArrayList<String> = ArrayList()
            val tempArrayListOfPlanes: ArrayList<Plane> = ArrayList()
            for (i in airlines[indexGroup].listOfPlanes)
            {
                tempArrayListOfTasksNumOfParticipants.add(i.factory.lowercase(Locale.ROOT))
            }
            tempArrayListOfTasksNumOfParticipants.sort()
            for (i in tempArrayListOfTasksNumOfParticipants)
            {
                for (j in airlines[indexGroup].listOfPlanes)
                {
                    if (i == j.factory.lowercase(Locale.ROOT)
                        && !tempArrayListOfPlanes.contains(j))
                    {
                        tempArrayListOfPlanes.add(j)
                        break
                    }
                }
            }
            airlines[indexGroup].listOfPlanes = tempArrayListOfPlanes
        }

        if (sortIndex == 4)
        {
            val tempArrayListOfTasksTimeForSolve: ArrayList<String> = ArrayList()
            val tempArrayListOfPlanes: ArrayList<Plane> = ArrayList()
            for (i in airlines[indexGroup].listOfPlanes)
            {
                tempArrayListOfTasksTimeForSolve.add(i.productionDate.lowercase(Locale.ROOT))
            }
            tempArrayListOfTasksTimeForSolve.sort()
            for (i in tempArrayListOfTasksTimeForSolve)
            {
                for (j in airlines[indexGroup].listOfPlanes)
                {
                    if (i == j.productionDate.lowercase(Locale.ROOT)
                        && !tempArrayListOfPlanes.contains(j))
                    {
                        tempArrayListOfPlanes.add(j)
                        break
                    }
                }
            }
            airlines[indexGroup].listOfPlanes = tempArrayListOfPlanes
        }

        if (sortIndex == 5)
        {
            val tempArrayListOfTasksMaxScore: ArrayList<Int> = ArrayList()
            val tempArrayListOfPlanes: ArrayList<Plane> = ArrayList()
            for (i in airlines[indexGroup].listOfPlanes)
            {
                tempArrayListOfTasksMaxScore.add(i.seats)
            }
            tempArrayListOfTasksMaxScore.sort()
            for (i in tempArrayListOfTasksMaxScore)
            {
                for (j in airlines[indexGroup].listOfPlanes)
                {
                    if (i == j.seats && !tempArrayListOfPlanes.contains(j))
                    {
                        tempArrayListOfPlanes.add(j)
                        break
                    }
                }
            }
            airlines[indexGroup].listOfPlanes = tempArrayListOfPlanes
        }

        if (sortIndex == 6)
        {
            val tempArrayListOfTasksIsComplicated: ArrayList<Int> = ArrayList()
            val tempArrayListOfPlanes: ArrayList<Plane> = ArrayList()
            for (i in airlines[indexGroup].listOfPlanes)
            {
                tempArrayListOfTasksIsComplicated.add(i.isCargo)
            }
            tempArrayListOfTasksIsComplicated.sort()
            for (i in tempArrayListOfTasksIsComplicated)
            {
                for (j in airlines[indexGroup].listOfPlanes)
                {
                    if (i == j.isCargo && !tempArrayListOfPlanes.contains(j))
                    {
                        tempArrayListOfPlanes.add(j)
                        break
                    }
                }
            }
            airlines[indexGroup].listOfPlanes = tempArrayListOfPlanes
        }

        if (sortIndex == 7)
        {
            val tempArrayListOfTasksHints: ArrayList<String> = ArrayList()
            val tempArrayListOfPlanes: ArrayList<Plane> = ArrayList()
            for (i in airlines[indexGroup].listOfPlanes)
            {
                tempArrayListOfTasksHints.add(i.comment.lowercase(Locale.ROOT))
            }
            tempArrayListOfTasksHints.sort()
            for (i in tempArrayListOfTasksHints)
            {
                for (j in airlines[indexGroup].listOfPlanes)
                {
                    if (i == j.comment.lowercase(Locale.ROOT)
                        && !tempArrayListOfPlanes.contains(j))
                    {
                        tempArrayListOfPlanes.add(j)
                        break
                    }
                }
            }
            airlines[indexGroup].listOfPlanes = tempArrayListOfPlanes
        }
    }
}