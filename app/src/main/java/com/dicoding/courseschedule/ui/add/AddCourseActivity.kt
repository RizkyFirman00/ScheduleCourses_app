package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textview.MaterialTextView

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddCourseViewModel
    private lateinit var startTimeTextView: TextView
    private lateinit var endTimeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_course)

        setupViewModel()

        startTimeTextView = findViewById(R.id.tv_start_time)
        endTimeTextView = findViewById(R.id.tv_end_time)

        findViewById<ImageButton>(R.id.ib_start_time).setOnClickListener {
            showTimePickerDialog(TimePickerType.START_TIME)
        }

        findViewById<ImageButton>(R.id.ib_end_time).setOnClickListener {
            showTimePickerDialog(TimePickerType.END_TIME)
        }


    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        when (tag) {
            TimePickerType.START_TIME.tag -> {
                val selectedTime = String.format("%02d:%02d", hour, minute)
                startTimeTextView.text = selectedTime
            }

            TimePickerType.END_TIME.tag -> {
                val selectedTime = String.format("%02d:%02d", hour, minute)
                endTimeTextView.text = selectedTime
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, AddCourseViewModelFactory.createFactory(this))[AddCourseViewModel::class.java]
    }

    private fun showTimePickerDialog(timePickerType: TimePickerType) {
        val timeFragment = TimePickerFragment()
        timeFragment.show(supportFragmentManager, timePickerType.tag)
    }

    private enum class TimePickerType(val tag: String) {
        START_TIME("startTimePicker"),
        END_TIME("endTimePicker")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val courseName = findViewById<EditText>(R.id.ed_course_name).text.toString()
        val day = findViewById<Spinner>(R.id.spinner_day).selectedItemPosition
        val courseLecturer = findViewById<EditText>(R.id.ed_lecturer).text.toString()
        val startTime = findViewById<MaterialTextView>(R.id.tv_start_time).text.toString()
        val endTime = findViewById<MaterialTextView>(R.id.tv_end_time).text.toString()
        val note = findViewById<EditText>(R.id.ed_note).text.toString()
        val isFilled =
            courseName.isNotEmpty() && courseLecturer.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty()

        return when (item.itemId) {

            R.id.home -> {
                onBackPressed()
                finish()
                true
            }

            R.id.action_insert -> {
                if (isFilled) {
                    viewModel.insertCourse(
                        courseName,
                        day,
                        startTime,
                        endTime,
                        courseLecturer,
                        note
                    )
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.input_empty_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
