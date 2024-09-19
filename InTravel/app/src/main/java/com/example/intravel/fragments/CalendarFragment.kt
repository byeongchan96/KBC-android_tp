package com.example.intravel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.util.*

class CalendarFragment : Fragment() {

//  // ViewBinding 사용
//  private var _binding: FragmentCalendarBinding? = null
//
//  class FragmentCalendarBinding {
//    companion object {
//      fun inflate(layoutInflater: LayoutInflater, viewGroup: View?, b: Boolean): CalendarFragment.FragmentCalendarBinding? {
//
//
//
//
//  }
//
//  private val binding get() = _binding!!
//
//  override fun onCreateView(
//    inflater: LayoutInflater, container: ViewGroup?,
//    savedInstanceState: Bundle?
//  ): View {
//    // FragmentCalendarBinding을 사용하여 fragment_calendar.xml 레이아웃과 연결
//    _binding = FragmentCalendarBinding.inflate(inflater, container, false)
//    return binding.root
//  }
//
//  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//
//    // CalendarView에 오늘 날짜 설정
//    setTodayToCalendarView()
//
//    // 날짜 선택 리스너 설정 (선택한 날짜를 TextView에 출력)
//    binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
//      val selectedDate = "$year-${month + 1}-$dayOfMonth"
//      binding.selectedDateText.text = "선택한 날짜: $selectedDate"
//    }
//  }
//
//  // 오늘 날짜를 CalendarView에 설정하는 함수
//  private fun setTodayToCalendarView() {
//    val today = Calendar.getInstance()  // 현재 날짜 및 시간 가져오기
//    binding.calendarView.date = today.timeInMillis  // CalendarView에 현재 날짜 설정
//  }
//
//  override fun onDestroyView() {
//    super.onDestroyView()
//    _binding = null
//  }
}