package com.example.androidki

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MyDialogFragmentDelFaculty: DialogFragment()
{
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        val arguments: Bundle? = arguments
        val facultyName = arguments?.getString("name")
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Будет удален факультет: $facultyName")
            .setTitle("Внимание!")
            .setPositiveButton("Ок"
            ) { _, _ -> (activity as MainActivity?)?.delTask() }
            .setNegativeButton("Отмена") { _, _ -> }
        return builder.create()
    }
}