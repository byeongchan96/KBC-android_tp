package com.example.intravel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intravel.R
import com.example.intravel.data.Memo
import com.example.intravel.databinding.ItemMemolistBinding

class MemoAapter(var memoList: MutableList<Memo>):RecyclerView.Adapter<MemoAapter.MemoHolder>() {

    // ViewHolder 클래스, ViewBinding 사용하여 UI 요소 연결
    class MemoHolder(val binding: ItemMemolistBinding) : RecyclerView.ViewHolder(binding.root)


    // DB 연결용 클릭 리스너 인터페이스
    interface OnItemClickListener {
        fun onItemClick(memo: Memo, pos: Int)
    }

    // 리스너 선언
    var onItemClickListener: OnItemClickListener? = null
    // 추가
    fun addMemo(memo: Memo) {
        memoList.add(memo)
        notifyItemInserted(memoList.size - 1)
    }

    // 수정
    fun updateMemo(memo: Memo, position: Int) {
        memoList[position] = memo
        notifyItemChanged(position)
    }

    // 삭제
    fun removeMemo(position: Int) {
        memoList.removeAt(position)
        notifyItemRemoved(position)
    }

    // ViewHolder 생성 시 ViewBinding 사용
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoHolder {
        val binding = ItemMemolistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemoHolder(binding)
    }

    override fun onBindViewHolder(holder: MemoAapter.MemoHolder, position: Int) {
        val memoItem = memoList[position]
        // 아이템의 제목과 날짜를 설정
        holder.binding.mTitle.text = memoItem.mTitle
        holder.binding.choiceDate.text = memoItem.choiceDate

        // 아이템 클릭 리스너 설정 (수정 기능)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(memoItem, position)
        }
    }

    override fun getItemCount(): Int {
        return memoList.size
    }
}