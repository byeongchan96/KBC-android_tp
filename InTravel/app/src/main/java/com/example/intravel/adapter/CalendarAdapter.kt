package com.example.intravel.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intravel.MainActivity_calendar
import com.example.intravel.R
import com.example.intravel.data.TravelData

class CalendarAdapter(private val travelList: List<TravelData>) : RecyclerView.Adapter<CalendarAdapter.TravelViewHolder>() {

  // Listener interface to handle click events
  interface OnItemClickListener {
    fun onItemClick(data: TravelData, position: Int)
  }

  // Variable to hold the click listener
  var onItemClickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
    return TravelViewHolder(view)
  }

  override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
    val travelData = travelList[position]

    holder.tvTravTitle.text = travelData.travTitle
    holder.tvDates.text = "날짜 : ${travelData.startDate} ~ ${travelData.endDate}"
    holder.tvCategory.text = "카테고리 : ${travelData.cate}"
    holder.tvCompleteStatus.text = "진행 상태 : ${if (travelData.travComplete == 'Y') "완료됨" else "진행중"}"

    // Set click listener on item view
    holder.itemView.setOnClickListener {
      onItemClickListener?.onItemClick(travelData, position)

      // Launch MainActivity_calendar when an item is clicked
      val context = holder.itemView.context
      val intent = Intent(context, MainActivity_calendar::class.java).apply {
        putExtra("tId", travelData.travId)
        putExtra("startDate", travelData.startDate)
        putExtra("endDate", travelData.endDate)
        putExtra("category", travelData.cate)
        putExtra("travComplete", travelData.travComplete)
      }
      context.startActivity(intent)
    }
  }

  override fun getItemCount(): Int {
    return travelList.size
  }

  class TravelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTravTitle: TextView = itemView.findViewById(R.id.tv_trav_title)
    val tvDates: TextView = itemView.findViewById(R.id.tv_dates)
    val tvCategory: TextView = itemView.findViewById(R.id.tv_category)
    val tvCompleteStatus: TextView = itemView.findViewById(R.id.tv_complete_status)
  }

  companion object {
    lateinit var onItemClickListener: MainAdapter.OnItemClickListener
  }
}