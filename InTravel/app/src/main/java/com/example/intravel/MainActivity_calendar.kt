package com.example.intravel

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intravel.adapter.CalendarAdapter
import com.example.intravel.client.Client
import com.example.intravel.data.TravelData
import com.example.intravel.databinding.ActivityMainCalendarBinding
import com.example.intravel.fragments.CalendarFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar


class MainActivity_calendar : AppCompatActivity() {

  private lateinit var binding: ActivityMainCalendarBinding
  private lateinit var adapter: CalendarAdapter
  private val travelList = mutableListOf<TravelData>()  // 여행 데이터 리스트

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainCalendarBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // RecyclerView 설정
    setupRecyclerView()

    // CalendarFragment 추가
    supportFragmentManager.beginTransaction()
      .replace(R.id.fragment_container, CalendarFragment())
      .commit()

    // CalendarView에 오늘 날짜(2024-09-19) 설정
    setSpecificDateToCalendarView(2024, 9, 19)

    // confirmButton 클릭 시 액티비티 종료
    binding.confirmButton.setOnClickListener {
      finish()  // 액티비티 종료
    }

    // 서버에서 데이터를 가져와 RecyclerView에 반영
    fetchTravelData()
  }

  // RecyclerView 설정 함수
  private fun setupRecyclerView() {
    binding.recyclerViewCalendar.layoutManager = LinearLayoutManager(this)
    adapter = CalendarAdapter(travelList) { travelData ->
      // 아이템 클릭 시 처리할 내용 추가 가능
    }
    binding.recyclerViewCalendar.adapter = adapter
  }

  // 특정 날짜를 CalendarView에 설정하는 함수 (예: 2024년 9월 19일)
  private fun setSpecificDateToCalendarView(year: Int, month: Int, day: Int) {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, day)  // Calendar에서 월은 0부터 시작하므로 1을 빼야 함
    binding.calendarView.date = calendar.timeInMillis

    // 날짜 선택 리스너 설정
    binding.calendarView.setOnDateChangeListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
      val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDayOfMonth"
      Log.d("MainActivity_calendar", "선택한 날짜: $selectedDate")
    }
  }

  // 서버에서 모든 여행 데이터를 가져오는 함수
  private fun fetchTravelData() {
    Client.retrofit.findAll().enqueue(object : Callback<List<TravelData>> {
      override fun onResponse(call: Call<List<TravelData>>, response: Response<List<TravelData>>) {
        if (response.isSuccessful) {
          val travelDataList = response.body()
          travelDataList?.let {
            adapter.updateList(it)  // 어댑터에 데이터 반영
          }
        } else {
          Log.e("MainActivity_calendar", "서버 응답 실패: ${response.errorBody()}")
        }
      }

      override fun onFailure(call: Call<List<TravelData>>, t: Throwable) {
        Log.e("MainActivity_calendar", "서버 통신 실패: ${t.message}")
      }
    })
  }
}