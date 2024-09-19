package com.example.intravel.adapter




import android.graphics.Color
import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.example.intravel.data.TravelData

import com.example.intravel.databinding.ItemCalendarBinding


class CalendarAdapter(
  private var travelList: MutableList<TravelData>,
  private val onItemClick: (TravelData) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

  // 색상 리스트 정의
  val colorList = listOf(
    "red100" to "#E53935",
    "red200" to "#FF7043",
    "orange100" to "#FB8C00",
    "orange200" to "#FFB300",
    "yellow100" to "#FDD835",
    "yellow200" to "#F4E242",
    "green400" to "#43A047",
    "green200" to "#00BFA5",
    "green100" to "#78AA00",
    "blue100" to "#1E88E5",
    "blue200" to "#42A5F5",
    "purple100" to "#AB47BC",
    "purple200" to "#8E24AA",
    "brown100" to "#8D6E63",
    "grey300" to "#BDBDBD",
    "grey200" to "#757575",
    "gray500" to "#6B7280",
    "gray100" to "#E7E4E4",
    "pink100" to "#FF5675"
  )
  // 카테고리 목록 추가
  val cateList = mutableListOf("혼자", "친구", "가족", "연인")

  // ViewBinding을 사용하는 ViewHolder
  inner class CalendarViewHolder(val binding: ItemCalendarBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(travelData: TravelData) {
      binding.travTitle.text = travelData.travTitle
      binding.tvDates.text = "날짜 : ${travelData.startDate} ~ ${travelData.endDate}"
      // 특정 색상 선택 (여기서는 travelData.cate에 따라 색상 선택 가능)
      val selectedColor = getColorForCategory(travelData.cate ?: "default")

      // ImageView의 배경 색상 설정
      binding.ivColor.setBackgroundColor(Color.parseColor(selectedColor))


      // 카테고리 설정
      val cateIndex = travelData.cate?.toIntOrNull() ?: -1
      binding.tvCate.text = if (cateIndex in cateList.indices) {
        "카테고리 : ${cateList[cateIndex]}"
      } else {
        "카테고리 : 미정"
      }

      binding.tvCompleteStatus.text = "진행 상태 : ${if (travelData.travComplete == 'Y') "완료" else "진행중"}"



      // 클릭 이벤트 설정
      itemView.setOnClickListener {
        onItemClick(travelData)
      }
    }
  }
  // onCreateViewHolder: ViewBinding을 사용하여 ViewHolder 생성
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
    val binding = ItemCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return CalendarViewHolder(binding)
  }

  // onBindViewHolder: ViewHolder에 데이터를 바인딩
  override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
    val travelData = travelList[position]
    holder.bind(travelData)
  }

  // 아이템 개수 반환
  override fun getItemCount(): Int {
    return travelList.size
  }
  // 특정 카테고리에 해당하는 색상 반환
  private fun getColorForCategory(category: String): String {
    return when (category) {
      "0" -> colorList.find { it.first == "red200" }?.second ?: "#FFFFFF"
      "1" -> colorList.find { it.first == "green400" }?.second ?: "#FFFFFF"
      "2" -> colorList.find { it.first == "blue100" }?.second ?: "#FFFFFF"
      "3" -> colorList.find { it.first == "pink100" }?.second ?: "#FFFFFF"
      else -> "#FFFFFF" // 기본 색상
    }
  }

  // 전체 리스트 업데이트 후 부분 새로 고침 (DiffUtil 적용)
  fun updateList(newList: List<TravelData>) {
    val diffCallback = TravelDataDiffCallback(travelList, newList)
    val diffResult = DiffUtil.calculateDiff(diffCallback)

    travelList.clear()
    travelList.addAll(newList)
    diffResult.dispatchUpdatesTo(this)
    notifyDataSetChanged()// DiffUtil을 통해 변경 사항을 반영
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
      // 각 아이템의 고유 ID를 비교하여 동일한지 확인 (예: travId)
      return oldList[oldItemPosition].travId == newList[newItemPosition].travId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      // 아이템의 내용이 동일한지 비교
      return oldList[oldItemPosition] == newList[newItemPosition]
    }
  }
}