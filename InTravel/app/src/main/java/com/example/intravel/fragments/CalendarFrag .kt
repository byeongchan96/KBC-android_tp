import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.example.intravel.databinding.ActivityMainCalendarBinding

class CalendarFrag : Fragment() {

  private var _binding: ActivityMainCalendarBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // FragmentCalendarBinding을 사용해 바인딩 초기화
    _binding = ActivityMainCalendarBinding.inflate(inflater, container, false)

    // CalendarView에서 날짜 선택 이벤트 처리
    binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
      val selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)


      // 선택한 날짜를 Toast로도 표시
      Toast.makeText(requireContext(), "Selected date: $selectedDate", Toast.LENGTH_SHORT).show()
    }

    // 바인딩된 뷰 반환
    return binding.root
  }

  // 뷰가 파괴될 때 바인딩을 해제하는 함수
  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}