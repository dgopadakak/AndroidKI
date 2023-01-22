package com.example.androidki

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidki.universities.University
import com.example.androidki.universities.UniversityOperator
import com.example.androidki.universities.Faculty
import com.example.androidki.universities.dbWithRoom.UniversityOperatorDao
import com.example.androidki.universities.dbWithRoom.App
import com.example.androidki.universities.dbWithRoom.AppDatabase
import com.example.androidki.databinding.ActivityMainBinding
import com.example.androidki.forRecyclerView.CustomRecyclerAdapterForFaculties
import com.example.androidki.forRecyclerView.RecyclerItemClickListener
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    FacultyDetailsDialogFragment.OnInputListenerSortId
{
    private val gsonBuilder = GsonBuilder()
    private val gson: Gson = gsonBuilder.create()
    private val serverIP = "192.168.1.69"
    private val serverPort = 9779
    private lateinit var connection: Connection
    private var connectionStage: Int = 0
    private var startTime: Long = 0

    private lateinit var db: AppDatabase
    private lateinit var roDao: UniversityOperatorDao

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var nv: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerViewFaculties: RecyclerView
    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts
            .StartActivityForResult())
    { result ->
        if (result.resultCode == Activity.RESULT_OK)
        {
            val data: Intent? = result.data
            processOnActivityResult(data)
        }
    }

    private var unO: UniversityOperator = UniversityOperator()
    private var currentUniversityID: Int = -1
    private var currentFacultyID: Int = -1
    private var waitingForUpdate: Boolean = false
    private lateinit var airlineTitle: String

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        drawerLayout = binding.drawerLayout
        nv = binding.navView
        nv.setNavigationItemSelectedListener(this)
        toolbar = findViewById(R.id.toolbar)
        toolbar.apply { setNavigationIcon(R.drawable.ic_my_menu) }
        toolbar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        progressBar = findViewById(R.id.progressBar)
        recyclerViewFaculties = findViewById(R.id.recyclerViewExams)
        recyclerViewFaculties.visibility = View.INVISIBLE
        recyclerViewFaculties.layoutManager = LinearLayoutManager(this)

        recyclerViewFaculties.addOnItemTouchListener(
            RecyclerItemClickListener(
                recyclerViewFaculties,
                object : RecyclerItemClickListener.OnItemClickListener
                {
                    override fun onItemClick(view: View, position: Int)
                    {
                        currentFacultyID = position
                        val tempStudentsList = unO.getFaculty(currentUniversityID, currentFacultyID)
                            .students.split(", ")
                        var tempStudentsNum = 0
                        for (i in tempStudentsList)
                        {
                            tempStudentsNum += i.toInt()
                        }
                        val toast = Toast.makeText(
                            applicationContext,
                            "Студентов: $tempStudentsNum",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                    override fun onItemLongClick(view: View, position: Int)
                    {
                        currentFacultyID = position
                        val facultyDetails = FacultyDetailsDialogFragment()
                        val tempFaculty = unO.getFaculty(currentUniversityID, currentFacultyID)
                        var tempNumOfAllStudents = 0
                        val tempListOfStrings: List<String> = tempFaculty.students
                            .split(", ")
                        for (i in tempListOfStrings)
                        {
                            tempNumOfAllStudents += i.toInt()
                        }
                        val bundle = Bundle()
                        bundle.putString("name", tempFaculty.name)
                        bundle.putString("directions", tempFaculty.directions)
                        bundle.putString("number", tempFaculty.num.toString())
                        bundle.putString("email", tempFaculty.email)
                        bundle.putString("dateOfFoundation", tempFaculty.dateOfFoundation)
                        bundle.putString("students", tempNumOfAllStudents.toString())
                        bundle.putString("isHaveDistanceLearning", tempFaculty.
                        isHaveDistanceLearning.toString())
                        bundle.putString("comment", tempFaculty.comment)
                        bundle.putString("connection", connectionStage.toString())
                        facultyDetails.arguments = bundle
                        facultyDetails.show(fragmentManager, "MyCustomDialog")
                    }
                }
            )
        )

        db = App.instance?.database!!
        roDao = db.groupOperatorDao()
        startTime = System.currentTimeMillis()
        connection = Connection(serverIP, serverPort, "REFRESH", this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean
    {
        if (currentUniversityID != -1 && connectionStage == 1)
        {
            menu.getItem(0).isVisible = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        val id = item.itemId
        if (id == R.id.action_add)
        {
            val intent = Intent()
            intent.setClass(this, EditFacultyActivity::class.java)
            intent.putExtra("action", 1)
            resultLauncher.launch(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    internal inner class Connection(
        private val SERVER_IP: String,
        private val SERVER_PORT: Int,
        private val refreshCommand: String,
        private val activity: Activity
    ) {
        private var outputServer: PrintWriter? = null
        private var inputServer: BufferedReader? = null
        var thread1: Thread? = null
        private var threadT: Thread? = null

        internal inner class Thread1Server : Runnable {
            override fun run()
            {
                val socket: Socket
                try {
                    socket = Socket(SERVER_IP, SERVER_PORT)
                    outputServer = PrintWriter(socket.getOutputStream())
                    inputServer = BufferedReader(InputStreamReader(socket.getInputStream()))
                    Thread(Thread2Server()).start()
                    sendDataToServer(refreshCommand)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        internal inner class Thread2Server : Runnable {
            override fun run() {
                while (true) {
                    try {
                        val message = inputServer!!.readLine()
                        if (message != null)
                        {
                            activity.runOnUiThread { processingInputStream(message) }
                        } else {
                            thread1 = Thread(Thread1Server())
                            thread1!!.start()
                            return
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        internal inner class Thread3Server(private val message: String) : Runnable
        {
            override fun run()
            {
                outputServer!!.write(message)
                outputServer!!.flush()
            }
        }

        internal inner class ThreadT : Runnable
        {
            override fun run() {
                while (true)
                {
                    if (System.currentTimeMillis() - startTime > 5000L && connectionStage == 0)
                    {
                        activity.runOnUiThread { val toast = Toast.makeText(
                            applicationContext,
                            "Работа оффлайн",
                            Toast.LENGTH_SHORT)
                            toast.show() }
                        connectionStage = -1
                        activity.runOnUiThread { progressBar.visibility = View.INVISIBLE }
                        unO = roDao.getById(1)
                        for (i in 0 until unO.getUniversities().size)
                        {
                            activity.runOnUiThread { nv.menu.add(0, i, 0,
                                unO.getUniversities()[i].name as CharSequence) }
                        }
                    }
                }
            }
        }

        fun sendDataToServer(text: String)
        {
            Thread(Thread3Server(text + "\n")).start()
        }

        private fun processingInputStream(text: String)
        {
            roDao.delete(UniversityOperator())
            val tempGo: UniversityOperator = gson.fromJson(text, UniversityOperator::class.java)
            roDao.insert(tempGo)

            if (connectionStage != 1)
            {
                val toast = Toast.makeText(
                    applicationContext,
                    "Работа с сервером.",
                    Toast.LENGTH_SHORT)
                toast.show()
            }

            progressBar.visibility = View.INVISIBLE
            for (i in 0 until unO.getUniversities().size)
            {
                nv.menu.removeItem(i)
            }
            val tempArrayListUniversities: ArrayList<University> = tempGo.getUniversities()
            unO.setUniversities(tempArrayListUniversities)
            for (i in 0 until tempArrayListUniversities.size)
            {
                nv.menu.add(
                    0, i, 0,
                    tempArrayListUniversities[i].name as CharSequence
                )
            }
            if (waitingForUpdate || connectionStage == -1)
            {
                waitingForUpdate = false
                if (currentUniversityID != -1)
                {
                    recyclerViewFaculties.adapter = CustomRecyclerAdapterForFaculties(
                        unO.getFacultiesNames(currentUniversityID)
                    )
                }
            }
            connectionStage = 1
        }

        init {
            thread1 = Thread(Thread1Server())
            thread1!!.start()
            threadT = Thread(ThreadT())
            threadT!!.start()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        airlineTitle = "${item.title}"
        toolbar.title = airlineTitle
        invalidateOptionsMenu()
        currentUniversityID = item.itemId
        recyclerViewFaculties.adapter = CustomRecyclerAdapterForFaculties(
            unO.getFacultiesNames(currentUniversityID))
        recyclerViewFaculties.visibility = View.VISIBLE
        return true
    }

    fun delFaculty()
    {
        connection.sendDataToServer("d$currentUniversityID,$currentFacultyID")
        waitingForUpdate = true
    }

    override fun sendInputSortId(sortId: Int)
    {
        if (sortId > -1 && sortId < 8)      // Сортировка
        {
            unO.sortFaculties(currentUniversityID, sortId)
            if (connectionStage == 1)
            {
                connection.sendDataToServer("u" + gson.toJson(unO))
            }
            toolbar.title = when (sortId)
            {
                0 -> "$airlineTitle сорт. Название"
                1 -> "$airlineTitle сорт. Кол-во направлений"
                2 -> "$airlineTitle сорт. №"
                3 -> "$airlineTitle сорт. e-mail"
                4 -> "$airlineTitle сорт. Дата осн."
                5 -> "$airlineTitle сорт. Кол-во студентов"
                6 -> "$airlineTitle сорт. ЗФО"
                7 -> "$airlineTitle сорт. Описание"
                else -> airlineTitle
            }
        }
        if (sortId == 8)        // Удаление
        {
            val manager: FragmentManager = supportFragmentManager
            val myDialogFragmentDelFaculty = MyDialogFragmentDelFaculty()
            val bundle = Bundle()
            bundle.putString("name", unO.getFaculty(currentUniversityID, currentFacultyID).name)
            myDialogFragmentDelFaculty.arguments = bundle
            myDialogFragmentDelFaculty.show(manager, "myDialog")
        }
        if (sortId == 9)        // Изменение
        {
            val tempFaculty = unO.getFaculty(currentUniversityID, currentFacultyID)
            val intent = Intent()
            intent.setClass(this, EditFacultyActivity::class.java)
            intent.putExtra("action", 2)
            intent.putExtra("name", tempFaculty.name)
            intent.putExtra("directions", tempFaculty.directions)
            intent.putExtra("number", tempFaculty.num.toString())
            intent.putExtra("email", tempFaculty.email)
            intent.putExtra("dateOfFoundation", tempFaculty.dateOfFoundation)
            intent.putExtra("students", tempFaculty.students)
            intent.putExtra("isHaveDistanceLearning", tempFaculty.isHaveDistanceLearning.toString())
            intent.putExtra("comment", tempFaculty.comment)
            resultLauncher.launch(intent)
        }
        if (sortId == 10)
        {
            val tempFaculty = unO.getFaculty(currentUniversityID, currentFacultyID)
            val intent = Intent()
            intent.setClass(this, DirectionsActivity::class.java)
            intent.putExtra("directions", tempFaculty.directions)
            intent.putExtra("students", tempFaculty.students)
            startActivity(intent)
        }
        recyclerViewFaculties.adapter = CustomRecyclerAdapterForFaculties(
            unO.getFacultiesNames(currentUniversityID))
    }

    private fun processOnActivityResult(data: Intent?)
    {
        val action = data!!.getIntExtra("action", -1)
        val facultyName = data.getStringExtra("name")
        val directionsNames = data.getStringExtra("directions")
        val facultyNum = data.getIntExtra("number", -1)
        val facultyEmail = data.getStringExtra("email")
        val dateOfFoundation = data.getStringExtra("dateOfFoundation")
        val students = data.getStringExtra("students")
        val isHaveDL = data.getIntExtra("isHaveDistanceLearning", 0)
        val comment = data.getStringExtra("comment")
        val tempFaculty = Faculty(facultyName!!, directionsNames!!, facultyNum, facultyEmail!!,
            dateOfFoundation!!, students!!, isHaveDL, comment!!)
        val tempPlaneJSON: String = gson.toJson(tempFaculty)

        if (action == 1)
        {
            val tempStringToSend = "a${unO.getUniversities()[currentUniversityID].name}#" +
                    "#$tempPlaneJSON"
            connection.sendDataToServer(tempStringToSend)
            waitingForUpdate = true
        }
        if (action == 2)
        {
            val tempStringToSend = "e$currentUniversityID,$currentFacultyID##$tempPlaneJSON"
            connection.sendDataToServer(tempStringToSend)
            waitingForUpdate = true
        }
        if (action == -1)
        {
            val toast = Toast.makeText(
                applicationContext,
                "Ошибка добавления/изменения!",
                Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}