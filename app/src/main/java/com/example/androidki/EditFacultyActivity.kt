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

        val action = intent.getSerializableExtra("action") as Int

        findViewById<Button>(R.id.button_confirm).setOnClickListener { confirmChanges(action) }

        if (action == 2)
        {
            editName.setText(intent.getSerializableExtra("name") as String)
            editDirections.setText(intent.getSerializableExtra("directions") as String)
            editNumber.setText(intent.getSerializableExtra("number") as String)
            editEmail.setText(intent.getSerializableExtra("email") as String)
            editDateOfFoundation.setText(intent.getSerializableExtra("dateOfFoundation")
                    as String)
            editStudents.setText(intent.getSerializableExtra("students") as String)
            if (intent.getSerializableExtra("isHaveDistanceLearning") as String == "1")
            {
                editIsHaveDistanceLearning.setText("да")
            }
            else
            {
                editIsHaveDistanceLearning.setText("нет")
            }
            editComment.setText(intent.getSerializableExtra("comment") as String)
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
                    val intent = Intent(this@EditFacultyActivity,
                        MainActivity::class.java)
                    intent.putExtra("action", action)
                    intent.putExtra("name", editName.text.toString().trim())
                    intent.putExtra("directions", editDirections.text.toString().trim())
                    intent.putExtra("number", editNumber.text.toString().trim().toInt())
                    intent.putExtra("email", editEmail.text.toString().trim())
                    intent.putExtra("dateOfFoundation", editDateOfFoundation.text.toString().trim())
                    intent.putExtra("students", editStudents.text.toString().trim().toInt())
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
                        "Проверьте дату!",
                        Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
            else
            {
                val toast = Toast.makeText(
                    applicationContext,
                    "Поле \"${R.string.is_complicated}\" поддерживает только " +
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
}