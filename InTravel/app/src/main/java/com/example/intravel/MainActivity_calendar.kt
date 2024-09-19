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
    binding.recyclerViewCalendar.layoutManager = LinearLayoutManager(this)
    adapter = CalendarAdapter(travelList) { travelData ->
      // 아이템 클릭 시 처리할 내용
    }
    // CalendarFragment 추가
    supportFragmentManager.beginTransaction()
      .replace(R.id.fragment_container, CalendarFragment())
      .commit()

    // CalendarView에 오늘 날짜 설정
    val today = Calendar.getInstance() // 현재 날짜 및 시간 가져오기
    binding.calendarView.date = today.timeInMillis  // CalendarView에 현재 날짜 설정

    // 선택한 날짜를 출력하는 예시
    binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
      val selectedDate = "$year-${month + 1}-$dayOfMonth"
      // 선택한 날짜를 처리하는 코드 작성
      println("선택한 날짜: $selectedDate")
    }
    binding.recyclerViewCalendar.adapter = adapter

    // confirmButton 클릭 시 액티비티 종료
    binding.confirmButton.setOnClickListener {
      finish()  // 액티비티 종료
    }

    // 서버에서 전체 데이터를 가져와 어댑터에 반영
    fetchTravelData()
  }

  // 서버에서 모든 여행 데이터를 가져오는 함수
  private fun fetchTravelData() {
    Client.retrofit.findAll().enqueue(object : Callback<List<TravelData>> {
      override fun onResponse(
        call: Call<List<TravelData>>,
        response: Response<List<TravelData>>
      ) {
        if (response.isSuccessful) {
          val travelDataList = response.body()
          if (travelDataList != null) {
            adapter.updateList(travelDataList)  // 어댑터에 데이터 반영
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
