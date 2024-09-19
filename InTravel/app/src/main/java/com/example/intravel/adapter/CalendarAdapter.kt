package com.example.intravel.adapter



import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.intravel.data.TravelData
import com.example.intravel.databinding.ItemCalendarBinding
import kotlin.random.Random

class CalendarAdapter(
  private var travelList: MutableList<TravelData>,
  private val onItemClick: (TravelData) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

  // 색상 리스트 정의
  private val colorList = listOf(
    "red200" to "#FF7043",
    "orange200" to "#FFB300",
    "yellow200" to "#F4E242",
    "green200" to "#00BFA5",
    "green100" to "#78AA00",
    "blue200" to "#42A5F5",
    "pink100" to "#FF5675"
  )

  // 카테고리 목록 추가
  private val cateList = listOf("혼자", "친구", "가족", "연인")

  // ViewHolder 정의
  inner class CalendarViewHolder(val binding: ItemCalendarBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(travelData: TravelData) {
      binding.travTitle.text = travelData.travTitle
      binding.tvDates.text = "날짜 : ${travelData.startDate} ~ ${travelData.endDate}"

      // 카테고리에 따른 색상 설정
      val selectedColor = getColorForCategory(travelData.cate ?: "default")
      binding.ivColor.setBackgroundColor(Color.parseColor(selectedColor))

      // 카테고리 설정
      val cateIndex = travelData.cate?.toIntOrNull() ?: -1
      binding.tvCate.text = if (cateIndex in cateList.indices) {
        "카테고리 : ${cateList[cateIndex]}"
      } else {
        "카테고리 : 미정"
      }

      // 완료 상태 설정
      binding.tvCompleteStatus.text = "진행 상태 : ${if (travelData.travComplete == 'Y') "완료" else "진행중"}"

      // 클릭 이벤트 설정
      itemView.setOnClickListener {
        onItemClick(travelData)
      }
    }
  }

  // ViewHolder 생성
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
    val binding = ItemCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return CalendarViewHolder(binding)
  }

  // 데이터 바인딩
  override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
    holder.bind(travelList[position])
  }

  override fun getItemCount(): Int {
    return travelList.size
  }

  // 특정 카테고리에 해당하는 색상 반환 함수
  private fun getColorForCategory(category: String): String {
    val randomIndex = Random.nextInt(colorList.size)
    return when (category) {
      "0" -> colorList[2].second // yellow200
      "1" -> colorList[3].second // green200
      "2" -> colorList[5].second // blue200
      "3" -> colorList[6].second // pink100
      else -> colorList[randomIndex].second // 랜덤 색상
    }
  }

  // 전체 리스트 업데이트 후 DiffUtil 적용하여 효율적으로 부분 새로 고침
  fun updateList(newList: List<TravelData>) {
    val diffCallback = TravelDataDiffCallback(travelList, newList)
    val diffResult = DiffUtil.calculateDiff(diffCallback)

    travelList.clear()
    travelList.addAll(newList)
    diffResult.dispatchUpdatesTo(this)
  }

  // DiffUtil 콜백 클래스
  class TravelDataDiffCallback(
    private val oldList: List<TravelData>,
    private val newList: List<TravelData>
  ) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
      return oldList.size
    }

    override fun getNewListSize(): Int {
      return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      // 각 아이템의 고유 ID(travId)를 비교하여 동일한지 확인
      return oldList[oldItemPosition].travId == newList[newItemPosition].travId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      // 아이템의 내용이 동일한지 비교
      return oldList[oldItemPosition] == newList[newItemPosition]
    }
  }
}