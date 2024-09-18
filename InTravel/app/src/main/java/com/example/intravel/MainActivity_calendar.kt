package com.example.intravel

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intravel.adapter.CalendarAdapter
import com.example.intravel.data.TravelData
import com.example.intravel.databinding.ActivityMainCalendarBinding

class MainActivity_calendar : AppCompatActivity() {
  private lateinit var binding: ActivityMainCalendarBinding
  private lateinit var calendarAdapter: CalendarAdapter
  private var travelDataList = mutableListOf<TravelData>() // Example travel data list

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Inflate the layout using ViewBinding
    binding = ActivityMainCalendarBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Receive data passed via Intent
    val tId = intent.getLongExtra("tId", -1L)
    val startDate = intent.getStringExtra("startDate")
    val endDate = intent.getStringExtra("endDate")
    val category = intent.getStringExtra("category")
    val travComplete = intent.getStringExtra("travComplete")

    // Ensure all data is valid before proceeding
    if (tId != -1L && startDate != null && endDate != null && category != null && travComplete != null) {
      // Display the data (if needed)
      Toast.makeText(this, "Travel Data Received", Toast.LENGTH_SHORT).show()
    } else {
      // If data is invalid, show a toast and finish the activity
      Toast.makeText(this, "Invalid data received", Toast.LENGTH_SHORT).show()
      finish()
    }

    // Set up CalendarView (optional interaction)
    binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
      val selectedDate = "$year-${month + 1}-$dayOfMonth"
      Toast.makeText(this, "Date selected: $selectedDate", Toast.LENGTH_SHORT).show()
    }

    // Confirm Button Click Event
    binding.confirmButton.setOnClickListener {
      Toast.makeText(this, "Confirm Button Clicked", Toast.LENGTH_SHORT).show()
      // You can handle further actions like filtering the list based on selected dates here.
    }

    // Set up RecyclerView
    setupRecyclerView()
  }

  // Setup RecyclerView with sample data
  private fun setupRecyclerView() {
    travelDataList = getSampleTravelData() // Sample data for the RecyclerView
    calendarAdapter = CalendarAdapter(travelDataList)

    binding.recyclerView.apply {
      layoutManager = LinearLayoutManager(this@MainActivity_calendar)
      adapter = calendarAdapter
    }
  }

  // Sample data for RecyclerView, replace with your actual data
  private fun getSampleTravelData(): MutableList<TravelData> {
    return mutableListOf(
    )
  }

  private fun TravelData(travId: Long, travTitle: String, createDate: String, startDate: String, endDate: String, cate: String) {
    return

  }
}