package com.example.intravel.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.intravel.databinding.FragmentCalendarBinding
import java.util.*

class CalendarFragment : Fragment() {

//  private var _binding: FragmentCalendarBinding? = null
//  private val binding get() = _binding!!
//
//  override fun onCreateView(
//    inflater: LayoutInflater, container: ViewGroup?,
//    savedInstanceState: Bundle?
//  ): View {
//    _binding = FragmentCalendarBinding.inflate(inflater, container, false)
//    return binding.root
//  }
//
//  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//
//    // 특정 날짜를 CalendarView에 설정 (2024-09-19)
//    setTodayToCalendarView(2024, 9, 19)
//
//    // 날짜 선택 리스너 설정 (선택한 날짜를 TextView에 출력)
//    binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
//      val selectedDate = "$year-${month + 1}-$dayOfMonth"
//      binding.selectedDateText.text = "선택한 날짜: $selectedDate"
//    }
//  }
//
//  // 특정 날짜를 CalendarView에 설정하는 함수
//  private fun setTodayToCalendarView(year: Int, month: Int, day: Int) {
//    val calendar = Calendar.getInstance()
//    calendar.set(year, month - 1, day)  // Calendar의 month는 0부터 시작하므로 -1
//    binding.calendarView.date = calendar.timeInMillis  // CalendarView에 설정
//  }
//
//  override fun onDestroyView() {
//    super.onDestroyView()
//    _binding = null
//  }
}