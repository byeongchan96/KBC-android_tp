package com.example.intravel.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intravel.adapter.MemoAapter
import com.example.intravel.client.SubClient
import com.example.intravel.data.Memo
import com.example.intravel.databinding.FragmentMemoListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemoListFragment : Fragment() {

    private lateinit var binding: FragmentMemoListBinding
    private lateinit var memoAdapter: MemoAapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // ViewBinding 초기화
        binding = FragmentMemoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 부모 액티비티에서 전달된 여행 ID (tId) 가져오기
        val tId = activity?.intent?.getLongExtra("tId", 0) ?: 0

        // 리사이클러뷰 및 어댑터 초기화
        setupRecyclerView()

        // 서버에서 해당 여행 ID(tId)에 대한 메모 목록 가져오기
        fetchMemoListFromServer(tId)

        // 새로운 메모 추가 버튼 이벤트 처리
        binding.memoListRecyclerView.setOnClickListener {
            addNewMemo(tId)
        }
    }

    // 리사이클러뷰 초기화
    private fun setupRecyclerView() {
        memoAdapter = MemoAapter(mutableListOf())
        binding.memoListRecyclerView.adapter = memoAdapter
        binding.memoListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    // 서버에서 메모 목록 가져오기
    private fun fetchMemoListFromServer(tId: Long) {
        SubClient.retrofit.findAllMemo(tId).enqueue(object : Callback<List<Memo>> {
            override fun onResponse(call: Call<List<Memo>>, response: Response<List<Memo>>) {
                response.body()?.let {
                    memoAdapter.memoList = it.toMutableList()
                    memoAdapter.notifyDataSetChanged()  // 데이터 변경 사항을 어댑터에 알림
                }
            }

            override fun onFailure(call: Call<List<Memo>>, t: Throwable) {
                // 서버 요청 실패 시 처리
            }
        })
    }

    // 새로운 메모 추가
    private fun addNewMemo(tId: Long) {
        val newMemo = Memo(0, tId, "새로운 메모 제목", "새로운 메모 내용", "", "")
        SubClient.retrofit.insertMemo(tId, newMemo).enqueue(object : Callback<Memo> {
            override fun onResponse(call: Call<Memo>, response: Response<Memo>) {
                response.body()?.let { memoAdapter.addMemo(it) }
            }

            override fun onFailure(call: Call<Memo>, t: Throwable) {
                // 실패 처리
            }
        })
    }
}