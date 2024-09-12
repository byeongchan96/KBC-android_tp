package com.example.intravel

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.intravel.adapter.DetaiTabFragmentAdapter
import com.example.intravel.client.Client
import com.example.intravel.data.TravelData
import com.example.intravel.databinding.ActivitySubmainBinding
import com.example.intravel.databinding.CustomDdayBinding
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class DetailMainActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySubmainBinding // ViewBinding 변수 선언
  private lateinit var savedTravelData: TravelData // MainActivity로부터 받아올 TravelData
  private lateinit var cateSelected: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // View binding 설정
    binding = ActivitySubmainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    //------------------------------------------------------------------------------//
    // 시스템 바 패딩 설정
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tablayout)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.right, systemBars.top, systemBars.bottom)
      insets
    }
    //------------------------------------------------------------------------------//

    //  색상 배열
    val colors = listOf(
      R.color.yellow100,
      R.color.purple100,
      R.color.brown100,
      R.color.orange100,
      R.color.pink100,
      R.color.blue100
    )

    // 랜덤 색상 선택
    val randomColor = colors[Random.nextInt(colors.size)]
    // TabLayout 배경색 설정
    binding.tablayout.setBackgroundColor(ContextCompat.getColor(this, randomColor))
    binding.navHeader.setBackgroundColor(ContextCompat.getColor(this, randomColor))



    //------------------------------------------------------------------------------//


    // ViewPager2 및 TabLayout 설정
    val viewPager2Adapter = DetaiTabFragmentAdapter(this)
    binding.viewpager2.adapter = viewPager2Adapter

    val tabElement: List<String> = listOf("To-Do", "Memo", "Menu")

    // TabLayout과 ViewPager2 연결
    TabLayoutMediator(binding.tablayout, binding.viewpager2) { tab, position ->
      val textView = TextView(this@DetailMainActivity).apply {
        text = tabElement[position]
        textAlignment = View.TEXT_ALIGNMENT_CENTER
        setTypeface(typeface, Typeface.BOLD)
      }
      tab.customView = textView
    }.attach()
    //------------------------------------------------------------------------------//

    // MainActivity로부터 전달된 데이터 받아오기
    getIntentData()

    // MainActivity로 돌아가는 버튼 클릭 이벤트 처리
    binding.iconLeft.setOnClickListener {
      navigateToMainActivity()
    }
    //------------------------------------------------------------------------------//

    // D-Day 수정 다이얼로그 표시 및 수정 트리거
    binding.iconRight.setOnClickListener {
      showEditDdayDialog()
    }
  }

  //------------------------------------------------------------------------------//

  // MainActivity로부터 전달된 데이터를 받아오는 함수
  private fun getIntentData() {
    intent?.let {
      val travId = it.getLongExtra("tId", 0)
      val travTitle = it.getStringExtra("tTitle") ?: ""
      val startDate = it.getStringExtra("tStartDate") ?: ""
      val endDate = it.getStringExtra("tEndDate") ?: ""
      val cate = it.getStringExtra("cate") ?: ""
      savedTravelData = TravelData(travId, travTitle, null, startDate, endDate, cate, 'N')
      cateSelected = cate
    }
  }

  // D-Day 수정 다이얼로그를 표시하는 함수
  private fun showEditDdayDialog() {
    val dialogInsert = CustomDdayBinding.inflate(layoutInflater) // CustomDdayBinding 인플레이트

    // 카테고리 데이터
    val cateList = listOf("---선택해주세요---", "혼자", "친구", "가족", "연인")
    val cateAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cateList)
    dialogInsert.cateSpinner.adapter = cateAdapter

    // 다이얼로그에 저장된 데이터 미리 채우기
    dialogInsert.ddayName.setText(savedTravelData.travTitle)
    dialogInsert.edtStart.setText(savedTravelData.startDate)
    dialogInsert.edtEnd.setText(savedTravelData.endDate)
    dialogInsert.cateSpinner.setSelection(cateList.indexOf(savedTravelData.cate))

    // 다이얼로그 빌더 설정
    val dialog = AlertDialog.Builder(this).apply {
      setTitle("D-Day 수정하기")
      setMessage("날짜를 선택해주세요")
      setView(dialogInsert.root)

      // 출발 날짜 선택 처리
      dialogInsert.edtStart.setOnClickListener {
        // 클릭 시 배경색을 green100으로 변경
        dialogInsert.edtStart.setBackgroundColor(ContextCompat.getColor(this@DetailMainActivity, R.color.gray100))
        dialogInsert.edtEnd.setBackgroundColor(ContextCompat.getColor(this@DetailMainActivity, R.color.white)) // 원래 색상으로 복원


        dialogInsert.calendarView.setOnDateChangeListener { _, year, month, date ->
          val e_month = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"
          val e_date = if (date < 10) "0$date" else "$date"
          dialogInsert.edtStart.setText("$year$e_month$e_date")
        }
      }

      // 마감 날짜 선택 처리
      dialogInsert.edtEnd.setOnClickListener {
        // 클릭 시 배경색을 pink100으로 변경
        dialogInsert.edtEnd.setBackgroundColor(ContextCompat.getColor(this@DetailMainActivity, R.color.grey300))
        dialogInsert.edtStart.setBackgroundColor(ContextCompat.getColor(this@DetailMainActivity, R.color.white)) // 원래 색상으로 복원



        dialogInsert.calendarView.setOnDateChangeListener { _, year, month, date ->
          val e_month = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"
          val e_date = if (date < 10) "0$date" else "$date"
          dialogInsert.edtEnd.setText("$year$e_month$e_date")
        }
      }

      // 카테고리 선택 처리
      dialogInsert.cateSpinner.onItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
          override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            cateSelected = cateList[position]
          }

          override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

      // 저장 버튼 클릭 시 데이터 수정
      setPositiveButton("수정") { _, _ ->
        Log.d("Dialog", "수정 버튼 클릭됨")

        // 수정된 데이터로 TravelData 생성
        val updatedTravelData = savedTravelData.copy(
          travTitle = dialogInsert.ddayName.text.toString(),
          startDate = dialogInsert.edtStart.text.toString(),
          endDate = dialogInsert.edtEnd.text.toString(),
          cate = cateSelected
        )

        // 수정된 데이터 서버에 저장
        updateTravelData(updatedTravelData)
        Toast.makeText(this@DetailMainActivity, "D-Day가 성공적으로 수정되었습니다.", Toast.LENGTH_SHORT).show()
      }

      // 취소 버튼 클릭 시 다이얼로그 닫기
      setNegativeButton("취소") { dialogInterface, _ ->
        Log.d("Dialog", "취소 버튼 클릭됨")
        dialogInterface.dismiss()
      }
    }.create()

    // 다이얼로그 표시
    dialog.show()
  }

  // 날짜 포맷 함수 (YYYYMMDD 형식으로 변환)
  private fun formatDate(year: Int, month: Int, day: Int): String {
    val formattedMonth = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"
    val formattedDay = if (day < 10) "0$day" else "$day"
    return "$year$formattedMonth$formattedDay"
  }


  // 수정된 TravelData를 서버에 저장하는 함수
  private fun updateTravelData(travelData: TravelData) {
    Client.retrofit.update(travelData.travId, travelData).enqueue(object : Callback<TravelData> {
      override fun onResponse(call: Call<TravelData>, response: Response<TravelData>) {
        if (response.isSuccessful) {

          Log.d("UpdateSuccess", "D-Day가 서버에서 성공적으로 수정되었습니다.")
        } else {
          Log.e("UpdateError", "D-Day 수정 실패: ${response.code()}")
        }
      }

      override fun onFailure(call: Call<TravelData>, t: Throwable) {
        Log.e("UpdateError", "D-Day 수정 중 오류 발생", t)
      }
    })
  }
  //------------------------------------------------------------------------------//

  // MainActivity로 돌아가는 함수
  private fun navigateToMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    startActivity(intent)
    finish()  // 현재 액티비티 종료
  }

  //------------------------------------------------------------------------------//
}