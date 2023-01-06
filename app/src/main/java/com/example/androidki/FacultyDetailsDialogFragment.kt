package com.example.androidki

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class FacultyDetailsDialogFragment: android.app.DialogFragment()
{
    private val exceptionTag = "PharmacyDetailsDialogFragment"

    interface OnInputListenerSortId
    {
        fun sendInputSortId(sortId: Int)
    }

    lateinit var onInputListenerSortId: OnInputListenerSortId

    private lateinit var textViewNameTitle: TextView
    private lateinit var textViewName: TextView
    private lateinit var textViewDirectionsTitle: TextView
    private lateinit var textViewDirections: TextView
    private lateinit var textViewNumTitle: TextView
    private lateinit var textViewNum: TextView
    private lateinit var textViewEmailTitle: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewDateOfFoundationTitle: TextView
    private lateinit var textViewDateOfFoundation: TextView
    private lateinit var textViewStudentsTitle: TextView
    private lateinit var textViewStudents: TextView
    private lateinit var textViewIsHaveDistanceLearningTitle: TextView
    private lateinit var textViewIsHaveDistanceLearning: TextView
    private lateinit var textViewCommentTitle: TextView
    private lateinit var textViewComment: TextView
    private lateinit var buttonDel: Button
    private lateinit var buttonEdit: Button
    private lateinit var buttonOk: Button
    private lateinit var textViewCurrSort: TextView

    private var currentIdForSort: Int = -1

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        val view: View = inflater!!.inflate(R.layout.faculty_details, container, false)
        textViewNameTitle = view.findViewById(R.id.textViewExamNameTitle)
        textViewName = view.findViewById(R.id.textViewExamName)
        textViewDirectionsTitle = view.findViewById(R.id.textViewTeacherNameTitle)
        textViewDirections = view.findViewById(R.id.textViewTeacherName)
        textViewNumTitle = view.findViewById(R.id.textViewAuditoryTitle)
        textViewNum = view.findViewById(R.id.textViewAuditory)
        textViewEmailTitle = view.findViewById(R.id.textViewDateTitle)
        textViewEmail = view.findViewById(R.id.textViewDate)
        textViewDateOfFoundationTitle = view.findViewById(R.id.textViewTimeTitle)
        textViewDateOfFoundation = view.findViewById(R.id.textViewTime)
        textViewStudentsTitle = view.findViewById(R.id.textViewPeopleTitle)
        textViewStudents = view.findViewById(R.id.textViewPeople)
        textViewIsHaveDistanceLearningTitle = view.findViewById(R.id.textViewAbstractTitle)
        textViewIsHaveDistanceLearning = view.findViewById(R.id.textViewAbstract)
        textViewCommentTitle = view.findViewById(R.id.textViewCommentTitle)
        textViewComment = view.findViewById(R.id.textViewComment)
        buttonDel = view.findViewById(R.id.button_details_delete)
        buttonEdit = view.findViewById(R.id.button_details_edit)
        buttonOk = view.findViewById(R.id.button_details_ok)
        textViewCurrSort = view.findViewById(R.id.textViewCurrentSort)

        textViewNameTitle.setOnLongClickListener { setSortId(0) }
        textViewName.setOnLongClickListener { setSortId(0) }
        textViewDirectionsTitle.setOnLongClickListener { setSortId(1) }
        textViewDirections.setOnLongClickListener { setSortId(1) }
        textViewNumTitle.setOnLongClickListener { setSortId(2) }
        textViewNum.setOnLongClickListener { setSortId(2) }
        textViewEmailTitle.setOnLongClickListener { setSortId(3) }
        textViewEmail.setOnLongClickListener { setSortId(3) }
        textViewDateOfFoundationTitle.setOnLongClickListener { setSortId(4) }
        textViewDateOfFoundation.setOnLongClickListener { setSortId(4) }
        textViewStudentsTitle.setOnLongClickListener { setSortId(5) }
        textViewStudents.setOnLongClickListener { setSortId(5) }
        textViewIsHaveDistanceLearningTitle.setOnLongClickListener { setSortId(6) }
        textViewIsHaveDistanceLearning.setOnLongClickListener { setSortId(6) }
        textViewCommentTitle.setOnLongClickListener { setSortId(7) }
        textViewComment.setOnLongClickListener { setSortId(7) }

        buttonDel.setOnClickListener { returnDel() }
        buttonEdit.setOnClickListener { returnEdit() }
        buttonOk.setOnClickListener { returnIdForSort() }

        val arguments: Bundle = getArguments()
        textViewName.text = arguments.getString("name")
        textViewDirections.text = arguments.getString("directions")
        textViewNum.text = arguments.getString("number")
        textViewEmail.text = arguments.getString("email")
        textViewDateOfFoundation.text = arguments.getString("dateOfFoundation")
        textViewStudents.text = arguments.getString("students")
        if (arguments.getString("isHaveDistanceLearning") == "1")
        {
            textViewIsHaveDistanceLearning.text = "да"
        }
        else
        {
            textViewIsHaveDistanceLearning.text = "нет"
        }
        textViewComment.text = arguments.getString("comment")
        if (arguments.getString("connection") != "1")
        {
            buttonDel.isEnabled = false
            buttonEdit.isEnabled = false
        }

        return view
    }

    override fun onAttach(activity: Activity?)
    {
        super.onAttach(activity)
        try {
            onInputListenerSortId = getActivity() as OnInputListenerSortId
        }
        catch (e: ClassCastException)
        {
            Log.e(exceptionTag, "onAttach: ClassCastException: " + e.message)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setSortId(id: Int): Boolean
    {
        currentIdForSort = id
        if (currentIdForSort == 0)
        {
            textViewCurrSort.text = "Сортировка по названию"
        }
        else if (currentIdForSort == 1)
        {
            textViewCurrSort.text = "Сортировка по кол-ву направлений"
        }
        else if (currentIdForSort == 2)
        {
            textViewCurrSort.text = "Сортировка по номеру"
        }
        else if (currentIdForSort == 3)
        {
            textViewCurrSort.text = "Сортировка по e-mail"
        }
        else if (currentIdForSort == 4)
        {
            textViewCurrSort.text = "Сортировка по дате основания"
        }
        else if (currentIdForSort == 5)
        {
            textViewCurrSort.text = "Сортировка по кол-ву студентов"
        }
        else if (currentIdForSort == 6)
        {
            textViewCurrSort.text = "Сортировка по наличию ЗФО"
        }
        else if (currentIdForSort == 7)
        {
            textViewCurrSort.text = "Сортировка по описанию"
        }
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(200
            , VibrationEffect.DEFAULT_AMPLITUDE))
        return true
    }

    private fun returnIdForSort()
    {
        onInputListenerSortId.sendInputSortId(currentIdForSort)
        dialog.dismiss()
    }

    private fun returnDel()
    {
        currentIdForSort = 8
        returnIdForSort()
    }

    private fun returnEdit()
    {
        currentIdForSort = 9
        returnIdForSort()
    }
}