package com.example.androidki

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditFacultyActivity : AppCompatActivity()
{
    private lateinit var editName: EditText
    private lateinit var editDirections: EditText
    private lateinit var editNumber: EditText
    private lateinit var editEmail: EditText
    private lateinit var editDateOfFoundation: EditText
    private lateinit var editStudents: EditText
    private lateinit var editIsHaveDistanceLearning: EditText
    private lateinit var editComment: EditText

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_faculty)

        editName = findViewById(R.id.editTextExamName)
        editDirections = findViewById(R.id.editTextTeacherName)
        editNumber = findViewById(R.id.editTextAuditory)
        editEmail = findViewById(R.id.editTextDate)
        editDateOfFoundation = findViewById(R.id.editTextTime)
        editStudents = findViewById(R.id.editTextPeople)
        editIsHaveDistanceLearning = findViewById(R.id.editTextAbstract)
        editComment = findViewById(R.id.editTextComment)

        val action = intent.getIntExtra("action", 1)

        findViewById<Button>(R.id.button_confirm).setOnClickListener { confirmChanges(action) }

        if (action == 2)
        {
            editName.setText(intent.getStringExtra("name") as String)
            editDirections.setText(intent.getStringExtra("directions") as String)
            editNumber.setText(intent.getStringExtra("number") as String)
            editEmail.setText(intent.getStringExtra("email") as String)
            editDateOfFoundation.setText(intent.getStringExtra("dateOfFoundation")
                    as String)
            editStudents.setText(intent.getStringExtra("students") as String)
            if (intent.getStringExtra("isHaveDistanceLearning") as String == "1")
            {
                editIsHaveDistanceLearning.setText("да")
            }
            else
            {
                editIsHaveDistanceLearning.setText("нет")
            }
            editComment.setText(intent.getStringExtra("comment") as String)
        }
    }

    private fun confirmChanges(action: Int)
    {
        if (editName.text.toString() != "" && editDirections.text.toString() != ""
            && editNumber.text.toString() != "" && editEmail.text.toString() != ""
            && editDateOfFoundation.text.toString() != "" && editStudents.text.toString() != ""
            && editIsHaveDistanceLearning.text.toString() != "")
        {
            if (editIsHaveDistanceLearning.text.toString().trim().lowercase(Locale.ROOT) == "да"
                || editIsHaveDistanceLearning.text.toString().trim()
                    .lowercase(Locale.ROOT) == "нет")
            {
                if (isDateValid(editDateOfFoundation.text.toString().trim()))
                {
                    if(isNumOfStudentsValid(editStudents.text.toString().trim()))
                    {
                        val intent = Intent(this@EditFacultyActivity,
                            MainActivity::class.java)
                        intent.putExtra("action", action)
                        intent.putExtra("name", editName.text.toString().trim())
                        intent.putExtra("directions", editDirections.text.toString().trim())
                        intent.putExtra("number", editNumber.text.toString().trim().toInt())
                        intent.putExtra("email", editEmail.text.toString().trim())
                        intent.putExtra("dateOfFoundation", editDateOfFoundation.text.toString().trim())
                        intent.putExtra("students", editStudents.text.toString().trim())
                        if (editIsHaveDistanceLearning.text.toString().trim().lowercase(Locale.ROOT) == "да")
                        {
                            intent.putExtra("isHaveDistanceLearning", 1)
                        }
                        else
                        {
                            intent.putExtra("isHaveDistanceLearning", 0)
                        }
                        intent.putExtra("comment", editComment.text.toString().trim())
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                    else
                    {
                        val toast = Toast.makeText(
                            applicationContext,
                            "Проверьте кол-во студентов!",
                            Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }
                else
                {
                    val toast = Toast.makeText(
                        applicationContext,
                        "Проверьте дату!",
                        Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
            else
            {
                val toast = Toast.makeText(
                    applicationContext,
                    "Поле \"${getString(R.string.is_complicated)}\" поддерживает только " +
                            "значения \"да\" или \"нет\"!",
                    Toast.LENGTH_SHORT)
                toast.show()
            }
        }
        else
        {
            val toast = Toast.makeText(
                applicationContext,
                "Заполните обязательные поля!",
                Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun isDateValid(date: String?): Boolean
    {
        val myFormat = SimpleDateFormat("dd.MM.yyyy")
        myFormat.isLenient = false
        return try
        {
            if (date != null)
            {
                myFormat.parse(date)
            }
            true
        }
        catch (e: Exception)
        {
            false
        }
    }

    private fun isNumOfStudentsValid(s: String): Boolean
    {
        return try
        {
            val sl = s.split(", ")
            val sli: ArrayList<Int> = ArrayList()
            for (i in sl)
            {
                sli.add(i.toInt())
            }
            true
        }
        catch (e: Exception)
        {
            false
        }
    }
}