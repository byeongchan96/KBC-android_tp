package com.example.intravel

import android.os.Bundle
import android.widget.Button

import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.intravel.databinding.ActivityMainCalendarBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity_calendar : AppCompatActivity() {

  // ViewBinding을 위한 변수 선언
  private lateinit var binding: ActivityMainCalendarBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // ViewBinding 초기화
    binding = ActivityMainCalendarBinding.inflate(layoutInflater)
    setContentView(binding.root)
    // 전달받은 날짜 데이터를 받음
    val startDate = intent.getStringExtra("startDate")
    val endDate = intent.getStringExtra("endDate")

    // startDate와 endDate를 UI에 표시하거나 사용
    if (startDate != null && endDate != null) {
      Toast.makeText(this, "Start: $startDate, End: $endDate", Toast.LENGTH_LONG).show()
    } else {
      Toast.makeText(this, "No dates received", Toast.LENGTH_SHORT).show()
    }


    // CalendarView에서 날짜 선택 이벤트 처리
    binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
      val selectedDate = "$year-${month + 1}-$dayOfMonth"
      binding.toDay.text = "선택된 날짜: $selectedDate"
    }

    // 확인 버튼 클릭 이벤트
    binding.button.setOnClickListener {
      val selectedDateText = binding.toDay.text.toString()
      if (selectedDateText.isNotEmpty()) {
        Toast.makeText(this, selectedDateText, Toast.LENGTH_LONG).show()
      } else {
        Toast.makeText(this, "날짜를 선택하세요", Toast.LENGTH_SHORT).show()
      }
    }

    // 현재 시간 표시
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    val currentTime = sdf.format(Date())
    binding.relTime.text = "현재 시간: $currentTime"
  }
}