package com.example.androidki.universities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.androidki.universities.dbWithRoom.UniversityOperatorConverter
import org.apache.commons.lang3.StringUtils
import java.util.*
import kotlin.collections.ArrayList

@Entity
class UniversityOperator
{
    @PrimaryKey
    private var id: Int = 1

    @TypeConverters(UniversityOperatorConverter::class)
    private var universities: ArrayList<University> = ArrayList()

    fun getUniversities(): ArrayList<University>
    {
        return universities
    }

    fun setUniversities(newUniversities: ArrayList<University>)
    {
        universities = newUniversities
    }

    fun setId(id: Int)
    {
        this.id = id
    }

    fun getId(): Int
    {
        return id
    }

    fun getFacultiesNames(indexGroup: Int): ArrayList<String>
    {
        val arrayListForReturn: ArrayList<String> = ArrayList()
        for (i in universities[indexGroup].listOfFaculties)
        {
            arrayListForReturn.add(i.name)
        }
        return arrayListForReturn
    }

    fun getFaculty(indexGroup: Int, indexExam: Int): Faculty
    {
        return universities[indexGroup].listOfFaculties[indexExam]
    }

    fun sortFaculties(universityIndex: Int, sortIndex: Int)
    {
        if (sortIndex == 0)
        {
            val tempArrayListOfTasksNames: ArrayList<String> = ArrayList()
            val tempArrayListOfFaculties: ArrayList<Faculty> = ArrayList()
            for (i in universities[universityIndex].listOfFaculties)
            {
                tempArrayListOfTasksNames.add(i.name.lowercase(Locale.ROOT))
            }
            tempArrayListOfTasksNames.sort()
            for (i in tempArrayListOfTasksNames)
            {
                for (j in universities[universityIndex].listOfFaculties)
                {
                    if (i == j.name.lowercase(Locale.ROOT)
                        && !tempArrayListOfFaculties.contains(j))
                    {
                        tempArrayListOfFaculties.add(j)
                        break
                    }
                }
            }
            universities[universityIndex].listOfFaculties = tempArrayListOfFaculties
        }

        if (sortIndex == 1)
        {
            val tempArrayListOfNums: ArrayList<Int> = ArrayList()
            val tempArrayListOfFaculties: ArrayList<Faculty> = ArrayList()
            for (i in universities[universityIndex].listOfFaculties)
            {
                tempArrayListOfNums.add(StringUtils.countMatches(i.directions, ','))
            }
            tempArrayListOfNums.sort()
            for (i in tempArrayListOfNums)
            {
                for (j in universities[universityIndex].listOfFaculties)
                {
                    if (i == StringUtils.countMatches(j.directions, ',')
                        && !tempArrayListOfFaculties.contains(j))
                    {
                        tempArrayListOfFaculties.add(j)
                        break
                    }
                }
            }
            universities[universityIndex].listOfFaculties = tempArrayListOfFaculties
        }

        if (sortIndex == 2)
        {
            val tempArrayListOfTasksNumbers: ArrayList<Int> = ArrayList()
            val tempArrayListOfFaculties: ArrayList<Faculty> = ArrayList()
            for (i in universities[universityIndex].listOfFaculties)
            {
                tempArrayListOfTasksNumbers.add(i.num)
            }
            tempArrayListOfTasksNumbers.sort()
            for (i in tempArrayListOfTasksNumbers)
            {
                for (j in universities[universityIndex].listOfFaculties)
                {
                    if (i == j.num && !tempArrayListOfFaculties.contains(j))
                    {
                        tempArrayListOfFaculties.add(j)
                        break
                    }
                }
            }
            universities[universityIndex].listOfFaculties = tempArrayListOfFaculties
        }

        if (sortIndex == 3)
        {
            val tempArrayListOfTasksNumOfParticipants: ArrayList<String> = ArrayList()
            val tempArrayListOfFaculties: ArrayList<Faculty> = ArrayList()
            for (i in universities[universityIndex].listOfFaculties)
            {
                tempArrayListOfTasksNumOfParticipants.add(i.email.lowercase(Locale.ROOT))
            }
            tempArrayListOfTasksNumOfParticipants.sort()
            for (i in tempArrayListOfTasksNumOfParticipants)
            {
                for (j in universities[universityIndex].listOfFaculties)
                {
                    if (i == j.email.lowercase(Locale.ROOT)
                        && !tempArrayListOfFaculties.contains(j))
                    {
                        tempArrayListOfFaculties.add(j)
                        break
                    }
                }
            }
            universities[universityIndex].listOfFaculties = tempArrayListOfFaculties
        }

        if (sortIndex == 4)
        {
            val tempArrayListOfTasksNumOfParticipants: ArrayList<GregorianCalendar> = ArrayList()
            val tempArrayListOfTours: ArrayList<Faculty> = ArrayList()
            for (i in universities[universityIndex].listOfFaculties)
            {
                val d: List<String> = i.dateOfFoundation.split(".")
                tempArrayListOfTasksNumOfParticipants.add(GregorianCalendar(d[2].toInt(),
                    d[1].toInt(), d[0].toInt()))
            }
            tempArrayListOfTasksNumOfParticipants.sort()
            for (i in tempArrayListOfTasksNumOfParticipants)
            {
                for (j in universities[universityIndex].listOfFaculties)
                {
                    val d: List<String> = j.dateOfFoundation.split(".")
                    val tempGregorianCalendar = GregorianCalendar(d[2].toInt(), d[1].toInt(),
                        d[0].toInt())
                    if (i == tempGregorianCalendar && !tempArrayListOfTours.contains(j))
                    {
                        tempArrayListOfTours.add(j)
                        break
                    }
                }
            }
            universities[universityIndex].listOfFaculties = tempArrayListOfTours
        }

        if (sortIndex == 5)
        {
            val tempArrayListOfTasksMaxScore: ArrayList<Int> = ArrayList()
            val tempArrayListOfFaculties: ArrayList<Faculty> = ArrayList()
            for (i in universities[universityIndex].listOfFaculties)
            {
                tempArrayListOfTasksMaxScore.add(i.students)
            }
            tempArrayListOfTasksMaxScore.sort()
            for (i in tempArrayListOfTasksMaxScore)
            {
                for (j in universities[universityIndex].listOfFaculties)
                {
                    if (i == j.students && !tempArrayListOfFaculties.contains(j))
                    {
                        tempArrayListOfFaculties.add(j)
                        break
                    }
                }
            }
            universities[universityIndex].listOfFaculties = tempArrayListOfFaculties
        }

        if (sortIndex == 6)
        {
            val tempArrayListOfTasksIsComplicated: ArrayList<Int> = ArrayList()
            val tempArrayListOfFaculties: ArrayList<Faculty> = ArrayList()
            for (i in universities[universityIndex].listOfFaculties)
            {
                tempArrayListOfTasksIsComplicated.add(i.isHaveDistanceLearning)
            }
            tempArrayListOfTasksIsComplicated.sort()
            for (i in tempArrayListOfTasksIsComplicated)
            {
                for (j in universities[universityIndex].listOfFaculties)
                {
                    if (i == j.isHaveDistanceLearning && !tempArrayListOfFaculties.contains(j))
                    {
                        tempArrayListOfFaculties.add(j)
                        break
                    }
                }
            }
            universities[universityIndex].listOfFaculties = tempArrayListOfFaculties
        }

        if (sortIndex == 7)
        {
            val tempArrayListOfTasksHints: ArrayList<String> = ArrayList()
            val tempArrayListOfFaculties: ArrayList<Faculty> = ArrayList()
            for (i in universities[universityIndex].listOfFaculties)
            {
                tempArrayListOfTasksHints.add(i.comment.lowercase(Locale.ROOT))
            }
            tempArrayListOfTasksHints.sort()
            for (i in tempArrayListOfTasksHints)
            {
                for (j in universities[universityIndex].listOfFaculties)
                {
                    if (i == j.comment.lowercase(Locale.ROOT)
                        && !tempArrayListOfFaculties.contains(j))
                    {
                        tempArrayListOfFaculties.add(j)
                        break
                    }
                }
            }
            universities[universityIndex].listOfFaculties = tempArrayListOfFaculties
        }
    }
}